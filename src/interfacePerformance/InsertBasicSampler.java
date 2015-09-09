package interfacePerformance;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;

import java.io.IOException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.json.JSONArray;
import org.json.JSONException;

public class InsertBasicSampler extends AbstractJavaSamplerClient {

	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
	        args.addArgument("class", "JumeiProduct_");
	        args.addArgument("function", "");
	        args.addArgument("parameter", "123");
	        args.addArgument("sql", "");
	        args.addArgument("expectedCount", "");
	        return args;
	}



	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
	}
	
	@SuppressWarnings("deprecation")
	@Override 
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		//设置接口服务地址，端口，数据库连接信息
		JMeterVariables jmv = JMeterContextService.getContext().getVariables();
		String url = jmv.get("dburl");
		String user = jmv.get("dbuser");
		String pwd = jmv.get("dbpwd");
		String host = jmv.get("host");
		String port = jmv.get("port");
		//数据库连接设置
		url = url == null ? "jdbc:mysql://192.168.20.71:9002/tuanmei" : url;
		user = user == null ? "dev" : user;
		pwd = pwd == null ? "jmdevcd" : pwd;
		
		host = host == null ?  "192.168.16.140" : host;
		port = port == null ? "3201" : port;
		
		SampleResult sr=new SampleResult();
		String result=null;
		long start=0;
		long end =0;
		String params = arg0.getParameter("parameter");
		sr.sampleStart();
		try {
			BasicPortal bp=new BasicPortal();
			start=System.currentTimeMillis();
			result=bp.exe(arg0.getParameter("class"),arg0.getParameter("function"),new JSONArray("[" + params +"]"),host,port);
			end=System.currentTimeMillis();
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
		
		if(end-start > 3000){
			sr.setSuccessful(false);
			sr.setResponseMessage("Response time > 3000");
			result+="\n\nResponse time > 3000 !";
		}
		
		String sql = arg0.getParameter("sql").trim();
		if(sql.length() > 0){
			int recordsSum = QueryHelper.queryRecordCount(url, user, pwd, sql);
			String expected = arg0.getParameter("expectedCount").trim();
			if(expected.length() == 0){
				if(recordsSum < 0){
					sr.setResponseMessage(String.format("数据库中没有找到插入的记录。\n接口调用参数:%s\n查询语句:%s\n接口返回:%s",params,sql,result));
					sr.setResponseData(String.format("数据库中没有找到插入的记录。\n接口调用参数:%s\n查询语句:%s\n接口返回:%s",params,sql,result),null);
					sr.setDataType(SampleResult.TEXT);
					sr.setSuccessful(false);
					return sr;
				}
			}else{
				int compareInt = Integer.parseInt(expected);
				if(compareInt != recordsSum){
					sr.setResponseMessage(String.format("数据库中的记录与期望的记录不一致。\n接口调用参数:%s\n数据库记录:%d\n期望记录:%d",params,recordsSum,compareInt));
					sr.setResponseData(String.format("数据库中的记录与期望的记录不一致。\n接口调用参数:%s\n数据库记录:%d\n期望记录:%d",params,recordsSum,compareInt),null);
					sr.setDataType(SampleResult.TEXT);
					sr.setSuccessful(false);
					return sr;
				}
			}
		}
		sr.setResponseData(result);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}
