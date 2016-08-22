package interfacePerformance;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.json.JSONArray;
import org.json.JSONException;




import interfacePerformance.db.ModifyHelper;
import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;
import interfacePerformance.util.StrFormat;



public class operationSampleForMessage extends AbstractJavaSamplerClient {
	public String format(String str){
		if(str==null) return "";
		else return str.trim();
		}
	
	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
	        args.addArgument("class", "Operation_Write_");
	        args.addArgument("function", "");
	        args.addArgument("parameter", "");
	        args.addArgument("sqlRecovery", "");
	        args.addArgument("querySql", "");
	        args.addArgument("column:value", "");
	        return args;
	}

	@SuppressWarnings("deprecation")
	@Override 
	public SampleResult runTest(JavaSamplerContext arg0) {
		//设置接口服务地址，端口，数据库连接信息
		JMeterVariables jmv = JMeterContextService.getContext().getVariables();
		String url = jmv.get("dburl");
		String user = jmv.get("dbuser");
		String pwd = jmv.get("dbpwd");
		String host = jmv.get("host");
		String port = jmv.get("port");
		//数据库连接设置
		url = url == null ? "jdbc:mysql://192.168.20.71:9001/jumei_product" : url;
		user = user == null ? "root" : user;
		pwd = pwd == null ? "123456" : pwd;
		
		host = host == null ?  "192.168.16.140" : host;
		port = port == null ? "3201" : port;
		
		SampleResult sr=new SampleResult();
		//测试数据恢复处理
		String sql_recovery = arg0.getParameter("sqlRecovery");
		sql_recovery=format(sql_recovery);
		if(!sql_recovery.equals("")){
			boolean recoveryResult = ModifyHelper.update(url, user, pwd, sql_recovery);
			if(!recoveryResult){
				sr.setResponseMessage("准备测试数据错误");
				sr.setResponseData("准备测试数据错误",null);
				sr.setDataType(SampleResult.TEXT);
				sr.setSuccessful(false);
				return sr;
			}
		}
		
		String result=null;
		sr.sampleStart();
		String params = arg0.getParameter("parameter");
		params=format(params);
		try {
			BasicPortal bp=new BasicPortal();
			result=bp.exe(format(arg0.getParameter("class")),format(arg0.getParameter("function")),new JSONArray("[" + params + "]"),host,port);
		} catch (JSONException | IOException | SecurityException | IllegalArgumentException e) {
			sr.setResponseMessage(String.format("调用接口异常:\n%s",e.toString()));
			sr.setResponseData(String.format("调用接口异常:\n%s",e.toString(),null));
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			sr.sampleEnd();
			return sr;
		}
		
		if(result == null){
			sr.setResponseMessage("接口返回为null");
			sr.setResponseData("接口返回为null",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			sr.sampleEnd();
			return sr;
		}
		
		//接口条用成功与否设置
		if(result.indexOf("[")!=-1 ||result.indexOf("{")!=-1 ||!result.equals("")){
			sr.setSuccessful(true);
		}else{
			sr.setSuccessful(false);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int flag=0;
		int parmlength=0;
		String sql_result=null;
		Map<String, String> data=new HashMap<String, String>();
		String sql=arg0.getParameter("querySql");
		sql=new StrFormat().format(sql);
		List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
		
		try {
			 if(!sql.equals(""))
			 {
				sql_result=records.toString();
				String parmv=arg0.getParameter("column:value");
				String[] parm1=parmv.split(";");
				parmlength=parm1.length;
				String[][] parm2=new String[parm1.length][2];
				for(int i=0;i<parm1.length;i++)
				{
					parm2[i]=parm1[i].split(":");
				}
//				for(int i=0;i<parm1.length;i++)
//				{
//					System.out.println(records.get(0).get(parm2[i][0]));
//					if(records.get(0).get(parm2[i][0]).equals(parm2[i][1]))
//					{
//						flag++;
//					}
//					else
//					data.put(parm2[i][0], parm2[i][1]);
//				}
				for(int j=0;j<records.size();j++)
				{
				for(int i=0;i<parm1.length;i++)
				{
					if(parm2[i].length==1)
					{
						if(records.get(j).get(parm2[i][0]).equals(""))
						{ 
							flag++;
						}
						data.put(parm2[i][0], "");
					}
					else
					{
						if(records.get(j).get(parm2[i][0]).equals(parm2[i][1]))
						{ 
							flag++;
						}
						data.put(parm2[i][0], parm2[i][1]);
					}
					
				}
				}
				
			 }
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(flag==(records.size()*parmlength))
			sr.setSuccessful(true);
		else
		{
			sr.setSuccessful(false);
			sql_result+="\n期望结果:"+data;
		}
//		sr.setResponseData(result+"");
//		sr.sampleEnd();
//		return sr;		
		if(result != null )
			sr.setResponseData(String.format("接口返回:\n%s\n数据库更新值验证:\n%s\n",result,sql_result));
		else
			sr.setResponseData(result);
		
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	
	}

}
