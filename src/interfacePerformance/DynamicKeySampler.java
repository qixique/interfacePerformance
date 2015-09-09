package interfacePerformance;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;
import interfacePerformance.util.StringTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class DynamicKeySampler extends AbstractJavaSamplerClient {

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
	        args.addArgument("parameter", "");
	        args.addArgument("sql", "");
	        args.addArgument("pairKey", "");
//	        args.addArgument("sortParam", "");
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
		
		//数据库查询结果
		String other_result = null;
		
		//转换返回结果
		String tempResult = result;
		tempResult = tempResult.replace("{", "").replace("}", "").replace("\"","");
		String[] resultArr = tempResult.split(",");
		List<String> resultList =  Arrays.asList(resultArr);
		
		String sql = arg0.getParameter("sql").trim();
		if(sql.length() > 0){
			List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
			String key_var = arg0.getParameter("pairKey").trim();
			if(records.size() > 0 && key_var.length() > 0){
				String[] keys = key_var.split(",");	
				other_result = StringTool.showDbResult(records,keys);
				List<String> list = new ArrayList<String>();
				for(int i = 0 ; i < records.size() ; i++){
					list.add(String.valueOf(records.get(i).get(keys[0])) + ":" + String.valueOf(records.get(i).get(keys[1])));
				}
				
				//条数比较
				if(resultList.size() != list.size()){
					sr.setResponseMessage(String.format("返回比对错误:接口返回%d条记录,数据库返回%d条记录.\n接口调用参数:%s\n接口返回:%s\n数据库返回:%s\n",resultList.size(),list.size(),params,result,other_result));
					sr.setResponseData(String.format("返回比对错误:接口返回%d条记录,数据库返回%d条记录.\n接口调用参数:%s\n接口返回:%s\n数据库返回:%s\n",resultList.size(),list.size(),params,result,other_result),null);
					sr.setDataType(SampleResult.TEXT);
					sr.setSuccessful(false);
					return sr;
				}
				
				//具体结果比对
				for(String temp : list){
					if(!resultList.contains(temp)){
						sr.setResponseMessage(String.format("返回比对错误:接口返回中没有找到%s\n接口调用参数:%s\n接口返回:%s\n数据库返回:%s\n",temp,params,result,other_result));
						sr.setResponseData(String.format("返回比对错误:接口返回中没有找到%s\n接口调用参数:%s\n接口返回:%s\n数据库返回:%s\n",temp,params,result,other_result),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
				}
			}
		}
		
		if(other_result != null ){
			sr.setResponseData(String.format("接口返回:\n%s\n数据库返回:\n%s\n",result,other_result));
		}else{
			sr.setResponseData(result);
		}
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}
