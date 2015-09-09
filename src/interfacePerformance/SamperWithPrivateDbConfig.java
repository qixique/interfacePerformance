package interfacePerformance;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;
import interfacePerformance.util.StrFormat;
import interfacePerformance.util.StringTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONValue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SamperWithPrivateDbConfig extends AbstractJavaSamplerClient {

	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
	        args.addArgument("dbUrl", "jdbc:mysql://192.168.20.71:9001/jumei_product");
	        args.addArgument("dbUser", "dev");
	        args.addArgument("dbPwd", "jmdevcd");
	        args.addArgument("class", "JumeiProduct_");
	        args.addArgument("function", "");
	        args.addArgument("parameter", "");
	        args.addArgument("sql", "");
	        args.addArgument("sqlParam", "");
	        args.addArgument("sortParam", "");
	        args.addArgument("responseFormat", "false");
	        args.addArgument("compareRecordsTotal", "true");
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
		String host = jmv.get("host");
		String port = jmv.get("port");
		
		String url = arg0.getParameter("dbUrl").trim();
		String user = arg0.getParameter("dbUser").trim();
		String pwd = arg0.getParameter("dbPwd").trim();
		//数据库连接设置
		url = url.length() < 1 ? "jdbc:mysql://192.168.20.71:9002/tuanmei" : url;
		user = user.length() < 1 ? "dev" : user;
		pwd = pwd.length() < 1 ? "jmdevcd" : pwd;
		
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
		
		// 检查是否有排序检查
		String sortParam = arg0.getParameter("sortParam").trim();
		Map<String, String> orderMap = null;
		Map<String, List<Float>> orderInfoMap = null;
		if (sortParam.length() > 0) {
			orderMap = StringTool.paraseStrToMap(result, null, ":");
			orderInfoMap = StringTool.parseOrderFields(result,
					orderMap.keySet());
		}
				
		//数据库查询结果
		String other_result = null;
		String compare_fields_result = null;
		//返回结果格式化显示
		String needFormat = arg0.getParameter("responseFormat").trim();
		if(needFormat.equals("true")){
			result = result.replace(":null", ":\"null\"");
			Object obj=JSONValue.parse(result);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			result = gson.toJson(obj);
		}		
		
		String sql = arg0.getParameter("sql").trim();
		String compare_fields = arg0.getParameter("sqlParam").trim();
		if(sql.equals("")){
			//字段比对
			if(compare_fields.length() > 0){
				String[] fields = compare_fields.split(",");
				compare_fields_result = compare_fields;
				for(String s : fields){
					if(!result.contains(s.trim())){
						sr.setResponseMessage(String.format("返回字段比对错误:没有找到[%s]\n接口调用参数:%s\n接口返回:\n$%s$中\n返回字段:\n%s",s.trim(),params,result,compare_fields_result));
						sr.setResponseData(String.format("返回字段比对错误:没有找到[%s]\n接口调用参数:%s\n接口返回:\n$%s$中\n返回字段:\n%s",s.trim(),params,result,compare_fields_result),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
				}
			}
		}else{
			//数据库比对
			if(compare_fields.length() > 0){
				Map<String,List<String>> compareMap = null;
				List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
				//解析需要比对的字符串
				String[] fields = compare_fields.split(",");
				other_result = StringTool.showDbResult(records,fields);
				compareMap = new HashMap<String,List<String>>();
				List<String> list = null;
				String key = "";
				String value = "";
				for(String s : fields){
					key = s.trim();
					list = new ArrayList<String>();
					for(int i = 0; i < records.size(); i++){
						value = String.valueOf(records.get(i).get(key));
						if(needFormat.equals("true")){
							list.add(String.format("\"%s\": \"%s\";;\"%s\": %s", key,value,key,value)); //非sql中的字段，将返回null
						}else{
							list.add(String.format("\"%s\":\"%s\";;\"%s\":%s", key,value,key,value)); //非sql中的字段，将返回null
						}
					}
					compareMap.put(key, list);
				}
				
				
				Pattern pattern = null;
				Matcher matcher = null;
				int i = 0;
				int db_count = 0;
				//循环从返回字符串中比对
				String compareRecordsTotal=new StrFormat().format(arg0.getParameter("compareRecordsTotal"));
				boolean isTotalCompare = compareRecordsTotal.equals("true") ? true : false;
				for(String k : compareMap.keySet()){
					if(isTotalCompare){
					//接口返回记录条数与数据查询条数比对
					pattern = Pattern.compile(String.format("\"%s\"", key));
					matcher = pattern.matcher(result);
					i = 0;
					while(matcher.find()){
						i++;
					}
					db_count = compareMap.get(k).size();
					if(i != db_count){
						sr.setResponseMessage(String.format("%s字段个数比对错误:接口返回%d条,数据库返回%d条.\n接口调用参数:%s\n接口返回:\n$%s$\n数据库返回:\n%s",k,i,db_count,params,result,other_result));
						sr.setResponseData(String.format("%s字段个数比对错误:接口返回%d条,数据库返回%d条.\n接口调用参数:%s\n接口返回:\n$%s$\n数据库返回:\n%s",k,i,db_count,params,result,other_result),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
					}
					//字段具体值比对
					String[] comStrs = null;
					for(String l : compareMap.get(k)){
						comStrs = l.split(";;");
						if(result.indexOf(comStrs[0]) < 0 && result.indexOf(comStrs[1]) < 0){
							sr.setResponseMessage(String.format("%s字段比对错误:没有找到[%s].\n接口调用参数:%s\n接口返回$%s$\n数据库返回:\n%s",k,comStrs[0],params,result,other_result));
							sr.setResponseData(String.format("%s字段比对错误:没有找到[%s].\n接口调用参数:%s\n接口返回$%s$\n数据库返回:\n%s",k,comStrs[0],params,result,other_result),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
					}
				}
			}
		}
		
		// 排序比对
		if (orderMap != null) {
			String orderStyle = "";
			List<Float> values = null;
			float fTemp = 0.0f;
			for (String key : orderMap.keySet()) {
				orderStyle = orderMap.get(key);
				if (orderStyle.equals("desc")) {
					fTemp = 99999999999f;
					values = orderInfoMap.get(key);
					for (float f : values) {
						if (fTemp < f) {
							sr.setResponseMessage(String.format("%s字段降序返回比对错误.\n接口调用参数:%s\n接口返回依次是%s", key,params,String.valueOf(values)));
							sr.setResponseData(String.format("%s字段降序返回比对错误.\n接口调用参数:%s\n接口返回依次是%s", key,params,String.valueOf(values)), null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
						fTemp = f;
					}
				} else {
					fTemp = -99999999999f;
					values = orderInfoMap.get(key);
					for (float f : values) {
						if (fTemp > f) {
							sr.setResponseMessage(String.format("%s字段升序返回比对错误.\n接口调用参数:%s\n接口返回依次是%s", key,params,String.valueOf(values)));
							sr.setResponseData(String.format("%s字段升序返回比对错误.\n接口调用参数:%s\n接口返回依次是%s", key,params,String.valueOf(values)), null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
						fTemp = f;
					}
				}
			}
		}

		if(other_result != null ){
			sr.setResponseData(String.format("接口返回:\n%s\n数据库返回:\n%s\n",result,other_result));
		}else if(compare_fields_result != null){
			sr.setResponseData(String.format("接口返回:\n%s\n需要返回字段:\n%s\n",result,compare_fields_result));
		}else{
			sr.setResponseData(result);
		}
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}
