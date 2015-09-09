package interfacePerformance;

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

import interfacePerformance.db.ModifyHelper;
import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;
//import interfacePerformance.util.QueryDB;
import interfacePerformance.util.StringTool;

public class BasicSample extends AbstractJavaSamplerClient {

//    private static long start = 0;  
//    private static long end = 0; 
	int timeout=900000;
	public String format(String str){
		if(str==null) return "";
		else return str.trim();
		}
	
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
//		start = System.currentTimeMillis();
		
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
	        args.addArgument("class", "JumeiProduct_");
	        args.addArgument("function", "");
	        args.addArgument("parameter", "123");
	        args.addArgument("sqlRecovery", "");
	        args.addArgument("sql", "");
	        args.addArgument("sqlParam", "");
	        args.addArgument("sortParam", "");
//	        args.addArgument("unicodeConvert", "false");
	        args.addArgument("responseFormat", "false");
	        args.addArgument("compareRecordsTotal", "true");
	        return args;
	}



	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
//		 end = System.currentTimeMillis();  
			 
//		 System.out.println("end time"+end+",start time"+start);
//		  System.err.println("cost time:" + (end - start) / 1000);  
	}
	
	@SuppressWarnings("deprecation")
	@Override 
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		//���ýӿڷ����ַ���˿ڣ����ݿ�������Ϣ
		JMeterVariables jmv = JMeterContextService.getContext().getVariables();
		String url = jmv.get("dburl");
		String user = jmv.get("dbuser");
		String pwd = jmv.get("dbpwd");
		String host = jmv.get("host");
		String port = jmv.get("port");
		//���ݿ���������
		url = url == null ? "jdbc:mysql://192.168.20.71:9002/tuanmei" : url;
		user = user == null ? "dev" : user;
		pwd = pwd == null ? "jmdevcd" : pwd;
		
		host = host == null ?  "192.168.16.140" : host;
		port = port == null ? "3201" : port;
		
		SampleResult sr=new SampleResult();
		//�������ݻָ�����
		String sql_recovery = arg0.getParameter("sqlRecovery");
		sql_recovery=format(sql_recovery);
		if(!sql_recovery.equals("")){
			boolean recoveryResult = ModifyHelper.update(url, user, pwd, sql_recovery);
			if(!recoveryResult){
				sr.setResponseMessage("׼���������ݴ���");
				sr.setResponseData("׼���������ݴ���",null);
				sr.setDataType(SampleResult.TEXT);
				sr.setSuccessful(false);
				return sr;
			}
		}
		
		String result=null;
		long start=0;
		long end =0;
		sr.sampleStart();
		String params = arg0.getParameter("parameter");
		params=format(params);
		try {
			BasicPortal bp=new BasicPortal();
			start=System.currentTimeMillis();
			result=bp.exe(format(arg0.getParameter("class")),format(arg0.getParameter("function")),new JSONArray("[" + params + "]"),host,port);
			end=System.currentTimeMillis();
		} catch (JSONException | IOException | SecurityException | IllegalArgumentException e) {
			sr.setResponseMessage(String.format("���ýӿ��쳣:\n%s",e.toString()));
			sr.setResponseData(String.format("���ýӿ��쳣:\n%s",e.toString(),null));
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			sr.sampleEnd();
			return sr;
		}
//		if(arg0.getParameter("unicodeConvert").equals("true"))
//		{
//			 result=JSONValue.parse(result).toString();
//		}
		if(result == null){
			sr.setResponseMessage("�ӿڷ���Ϊnull");
			sr.setResponseData("�ӿڷ���Ϊnull",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			sr.sampleEnd();
			return sr;
		}
		//�ӿ����óɹ��������
		if(result.indexOf("[")!=-1 ||result.indexOf("{")!=-1 ||!result.equals("")){
			sr.setSuccessful(true);
		}else{
			sr.setSuccessful(false);
		}
		
		if(end-start > timeout){
			sr.setSuccessful(false);
			sr.setResponseMessage("Response time > "+timeout);
			result+="\n\nResponse time > "+timeout;
		}
		
		//����Ƿ���������
		String sortParam = arg0.getParameter("sortParam");
		sortParam=format(sortParam);
		Map<String,String> orderMap = null;
		Map<String,List<Float>> orderInfoMap = null;
		if(!sortParam.equals("")){
			orderMap = StringTool.paraseStrToMap(sortParam, null, ":");
			orderInfoMap = StringTool.parseOrderFields(result, orderMap.keySet());
		}
		//���ݿ��ѯ���
		String other_result = null;
		String compare_fields_result = null;
		//���ؽ����ʽ����ʾ
		String needFormat = arg0.getParameter("responseFormat");
		needFormat=format(needFormat);
		if(needFormat.equals("true")){
			result = result.replace(":null", ":\"null\"");
			Object obj=JSONValue.parse(result);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			result = gson.toJson(obj);
		}		
		
		String sql = arg0.getParameter("sql");
		sql=format(sql);
		String compare_fields = arg0.getParameter("sqlParam");
		compare_fields=format(compare_fields);
		if(sql.equals("")){
			//�ֶαȶ�
			if(!compare_fields.equals("")){
				String[] fields = compare_fields.split(",");
				compare_fields_result = compare_fields;
				for(String s : fields){
					s = s.trim();
					if(!s.startsWith("\""))
						s = "\"" + s;
					if(!s.endsWith("\""))
						s = s + "\"";
					if(!result.contains(s)){
						sr.setResponseMessage(String.format("�����ֶαȶԴ���:û���ҵ�[%s]\n�ӿڵ��ò���:%s\n�ӿڷ���:\n$%s$��\n�����ֶ�:\n%s",s,params,result,compare_fields_result));
						sr.setResponseData(String.format("�����ֶαȶԴ���:û���ҵ�[%s]\n�ӿڵ��ò���:%s\n�ӿڷ���:\n$%s$��\n�����ֶ�:\n%s",s,params,result,compare_fields_result),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
				}
			}
		}else{
			//���ݿ�ȶ�
//			System.out.println("compare_fields:"+compare_fields);
			if(!compare_fields.equals("")){
				Map<String,List<String>> compareMap = null;
				List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
				//������Ҫ�ȶԵ��ַ��� 
				String[] fields = compare_fields.split(",");
				other_result = StringTool.showDbResult(records,fields);
				compareMap = new HashMap<String,List<String>>();
				List<String> list = null;
				String key = "";
				String value = "";
				for(String s : fields){
					key = s.trim().replace("\"", "");
//					System.out.println("records size:"+records.size());
					if(records.toString().indexOf(key)<0&&records.size()>1)
					{
						sr.setResponseMessage(String.format("sqlparm ��ָ���ֶ�\"%s��%s��ѯ����в����ڣ�", key,records.toString()));
						sr.setResponseData(String.format("sqlparm ��ָ���ֶ�\"%s��%s��ѯ����в����ڣ�", key,records.toString()));
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
						
//					System.out.println(key);
					list = new ArrayList<String>();
					for(int i = 0; i < records.size(); i++){
						value = records.get(i).get(key).toString();
//						System.out.println("key:"+key+"--value:"+records.get(i).get(key));
						if(needFormat.equals("true")){
							list.add(String.format("\"%s\": \"%s\";;\"%s\": %s", key,value,key,value)); //��sql�е��ֶΣ�������null
						}else{
							list.add(String.format("\"%s\":\"%s\";;\"%s\":%s", key,value,key,value)); //��sql�е��ֶΣ�������null
						}
					}
					compareMap.put(key, list);
				}
				
				
				Pattern pattern = null;
				Matcher matcher = null;
				int i = 0;
				int db_count = 0;
				String compareRecordsTotal=format(arg0.getParameter("compareRecordsTotal"));
				boolean isTotalCompare = compareRecordsTotal.equals("true") ? true : false;
				//ѭ���ӷ����ַ����бȶ�
//				System.out.println(arg0.getParameter("compareRecordsTotal"));
				for(String k : compareMap.keySet()){
					//�ӿڷ��ؼ�¼���������ݲ�ѯ�����ȶ�
					if(isTotalCompare){
						pattern = Pattern.compile(String.format("\"%s\"", k));
						matcher = pattern.matcher(result);
						i = 0;
						while(matcher.find()){
							i++;
						}
//						System.out.println(i);
						db_count = compareMap.get(k).size();
						if(i != db_count){
							sr.setResponseMessage(String.format("%s�ֶθ����ȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���%d��,���ݿⷵ��%d��.\n�ӿڷ���:\n$%s$\n���ݿⷵ��:\n%s",k,params,i,db_count,result,other_result));
							sr.setResponseData(String.format("%s�ֶθ����ȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���%d��,���ݿⷵ��%d��.\n�ӿڷ���:\n$%s$\n���ݿⷵ��:\n%s",k,params,i,db_count,result,other_result),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
					}
					//�ֶξ���ֵ�ȶ�
					String[] comStrs = null;
					for(String l : compareMap.get(k)){
						comStrs = l.split(";;");
						if(result.indexOf(comStrs[0]) < 0 && result.indexOf(comStrs[1]) < 0){
							sr.setResponseMessage(String.format("%s�ֶαȶԴ���:û���ҵ�[%s].\n�ӿڵ��ò���:%s\n�ӿڷ���$%s$\n���ݿⷵ��:\n%s",k,comStrs[0],params,result,other_result));
							sr.setResponseData(String.format("%s�ֶαȶԴ���:û���ҵ�[%s].\n�ӿڵ��ò���:%s\n�ӿڷ���$%s$\n���ݿⷵ��:\n%s",k,comStrs[0],params,result,other_result),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
					}
				}
			}
		}
		
		//����ȶ�
		if(orderMap != null){
			String orderStyle = "";
			List<Float> values = null;
			float fTemp = 0.0f;
			for(String key : orderMap.keySet()){
				orderStyle = orderMap.get(key);
				if(orderStyle.equals("desc")){
					fTemp = 99999999999f;
					values = orderInfoMap.get(key);
					for(float f : values){
						if(fTemp < f){
							sr.setResponseMessage(String.format("%s�ֶν��򷵻رȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���������%s",key,params,String.valueOf(values)));
							sr.setResponseData(String.format("%s�ֶν��򷵻رȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���������%s",key,params,String.valueOf(values)),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
						fTemp = f;
					}
				}else{
					fTemp = -99999999999f;
					values = orderInfoMap.get(key);
					for(float f : values){
						if(fTemp > f){
							sr.setResponseMessage(String.format("%s�ֶ����򷵻رȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���������%s",key,params,String.valueOf(values)));
							sr.setResponseData(String.format("%s�ֶ����򷵻رȶԴ���.\n�ӿڵ��ò���:%s\n�ӿڷ���������%s",key,params,String.valueOf(values)),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
						fTemp = f;
					}
				}
			}
		}
		
	
		
		/**
		 String dbresult=null;
		 String[] expected=null;
		 int flag=0;
		if(arg0.getParameter("sql")!="")
		{
			QueryDB qdb=new QueryDB();
			try {
				dbresult= qdb.executeQueryByColumn(arg0.getParameter("sql"), arg0.getParameter("sqlParam"));
				expected=dbresult.split(",");
			} catch (ClassNotFoundException | UnsupportedEncodingException
					| SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  for(int i=0;i<expected.length;i++)
			  {
				  if(result.indexOf(expected[i])!=-1)
					  flag++;
//					sr.setSuccessful(true);
//					else 
//				{
				  
//					sr.setSuccessful(false);
//					result+="\n\ndbresult is not the substring of response result��";
//				}
			
			  }
//			  count count=new count();
			  int resultcount=count.count(result, arg0.getParameter("sqlParam"));
			  if(flag==resultcount&&flag!=0&&flag==expected.length)
				{
					sr.setSuccessful(true);
					result+="\n\ndbresult:"+dbresult+" !";
				}
			  else if((resultcount==0)&&(flag==expected.length))
			  {
//				  System.out.println("flag:"+flag+",dblength:"+expected.length);
				  sr.setSuccessful(true);
				  result+="\n\ndbresult:"+dbresult+" !";
			  }
				else
				{
					result+="\n\ndbresult:"+dbresult+"\n Assert faild!";
					sr.setSuccessful(false);
				}

		}
		**/
//		sr.setResponseMessage(jsonFormatResult);
		if(other_result != null ){
			sr.setResponseData(String.format("�ӿڷ���:\n%s\n���ݿⷵ��:\n%s\n",result,other_result));
		}else if(compare_fields_result != null){
			sr.setResponseData(String.format("�ӿڷ���:\n%s\n��Ҫ�����ֶ�:\n%s\n",result,compare_fields_result));
		}else{
			sr.setResponseData(result);
		}
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}
