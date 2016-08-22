package interfacePerformance;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.QueryDB;
import interfacePerformance.util.StrFormat;

public class query_sqlSample extends AbstractJavaSamplerClient {

    
	private String resultTotal="";
	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		
		System.currentTimeMillis();
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
		 args.addArgument("dbConfig","");
		
	     args.addArgument("query_sql", "");
	     args.addArgument("expected_value", "");
	        return args;
	}



	@Override
	public void teardownTest(JavaSamplerContext context) {
		System.currentTimeMillis();
	}
	
	@SuppressWarnings("deprecation")
	@Override 
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		JMeterVariables jmv = JMeterContextService.getContext().getVariables();
		String url = jmv.get("dburl");
		String user = jmv.get("dbuser");
		String pwd = jmv.get("dbpwd");
		url = url == null ? "jdbc:mysql://192.168.20.71:9002/tuanmei" : url;
		String result="";
		SampleResult sr = new SampleResult();
		String dbConfig = arg0.getParameter("dbConfig");
		dbConfig=new StrFormat().format(dbConfig);
		if(!dbConfig.equals("")){
			String[] dbConfigArray = dbConfig.split(";");
			System.out.println("dbconfig length:"+dbConfigArray.length);
			
			if(dbConfigArray.length == 1){
				url = dbConfigArray[0];
				user = "dev";
				pwd = "jmdevcd";
			}else if(dbConfigArray.length >= 3){
				url = dbConfigArray[0];
				user =  dbConfigArray[1];
				pwd =  dbConfigArray[2];
			}else{
				sr.setResponseMessage("数据库连接配置解析错误，请仔细检查配置");
				sr.setResponseData("数据库连接配置解析错误",null);
				sr.setDataType(SampleResult.TEXT);
				sr.setSuccessful(false);
				return sr;
			}
		}
		sr.sampleStart();
		int flag=0;
		int parmlength=0;
		 String sql=arg0.getParameter("query_sql");
		 sql=new StrFormat().format(sql);
		 List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
		 Map<String, String> data=new HashMap<String, String>();
		try {
//			 String sql=arg0.getParameter("query_sql");
//			 sql=new StrFormat().format(sql);
			 if(!sql.equals("") && records != null && !records.isEmpty())
			 {
//				 List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
				
				result=records.toString();
				String parmv=arg0.getParameter("expected_value");
				String[] parm1=parmv.split(";");
				parmlength=parm1.length;
				String[][] parm2=new String[parm1.length][2];
				for(int i=0;i<parm1.length;i++)
				{
					parm2[i]=parm1[i].split(":");
//					value.put(parm2[i][0], parm2[i][1]);
				}
//				System.out.println("parm:"+parm2[0][0]+parm2[0][1]); 
				for(int j=0;j<records.size();j++)
				{
				for(int i=0;i<parm1.length;i++)
				{
					if(parm2[i].length==1)//处理值为空的情况
					{
						if(records.get(j).get(parm2[i][0]).equals(""))
						{ 
							flag++;
						}
						data.put(parm2[i][0], "");
					}
					else//值不为空的情况
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
			 else
				 flag =-1;
			
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(result!=0)
		if(flag==(records.size()*parmlength))
		{
			sr.setSuccessful(true);
			result+="\n数据对比完整正确！";
		}
		else if(flag==-1)
		{
			sr.setSuccessful(false);
			result="查无数据";
		}
		else
		{
			sr.setSuccessful(false);
			result+="\n期望结果:"+data;
		}
		sr.setResponseData(result+"");
		sr.sampleEnd();
		return sr;
	}
	

}
