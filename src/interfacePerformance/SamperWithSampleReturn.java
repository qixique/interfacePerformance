package interfacePerformance;

import interfacePerformance.db.QueryHelper;
import interfacePerformance.util.BasicPortal;
import interfacePerformance.util.StringTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

public class SamperWithSampleReturn extends AbstractJavaSamplerClient {

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
	        args.addArgument("sqlParam", "");
	        args.addArgument("sortParam", "");
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
			sr.setResponseMessage(String.format("���ýӿ��쳣:\n%s",e.toString()));
			sr.setResponseData(String.format("���ýӿ��쳣:\n%s",e.toString(),null));
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			sr.sampleEnd();
			return sr;
		}
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
		
		if(end-start > 3000){
			sr.setSuccessful(false);
			sr.setResponseMessage("Response time > 3000");
			result+="\n\nResponse time > 3000 !";
		}
		
		//���ݿ��ѯ���
		String other_result = null;
		
		//ת�����ؽ��
		String tempResult = result;
		tempResult = tempResult.replace("[", "").replace("]", "").replace("\"","");
		String[] resultArr = tempResult.split(",");
		List<String> resultList =  Arrays.asList(resultArr);
		
		String sql = arg0.getParameter("sql").trim();
		String compare_fields = arg0.getParameter("sqlParam").trim();
		if(sql.length() > 0){
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
			if(compare_fields.length() > 0){
				//������Ҫ�ȶԵ��ַ���
				String[] fields = compare_fields.split(",");
				other_result = StringTool.showDbResult(records,fields);
				String value = "";
				String key = "";
				List<String> list = null;
				for(String s : fields){
					key = s.trim();
					list = new ArrayList<String>();
					for(int i = 0 ; i < records.size(); i++){
						value = String.valueOf(records.get(i).get(key));
						list.add(value);
					}
					map.put(key,list);
				}
				
				//���ӿڷ��ص������Ƿ�������ݿⷵ�ص�����
				if(resultList.size() != list.size()){
					sr.setResponseMessage(String.format("�������ݸ���������:�ӿڷ���%d;���ݿⷵ��%d\n�ӿڵ��ò���:%s\n�ӿڷ���:%s\n���ݿⷵ��:%s\n",resultList.size(),list.size(),params,result,other_result));
					sr.setResponseData(String.format("�������ݸ���������:�ӿڷ���%d;���ݿⷵ��%d\n�ӿڵ��ò���:%s\n�ӿڷ���:%s\n���ݿⷵ��:%s\n",resultList.size(),list.size(),params,result,other_result),null);
					sr.setDataType(SampleResult.TEXT);
					sr.setSuccessful(false);
					return sr;
				}
				
				for(String t : fields){
					for(String s : map.get(t)){
						if(!resultList.contains(s)){
							sr.setResponseMessage(String.format("%s�ֶη��رȶԴ���:�ӿڷ�����û���ҵ�%s\n�ӿڵ��ò���:%s\n�ӿڷ���:%s\n���ݿⷵ��:%s\n",t,s,params,result,other_result));
							sr.setResponseData(String.format("%s�ֶη��رȶԴ���:�ӿڷ�����û���ҵ�%s\n�ӿڵ��ò���:%s\n�ӿڷ���:%s\n���ݿⷵ��:%s\n",t,s,params,result,other_result),null);
							sr.setDataType(SampleResult.TEXT);
							sr.setSuccessful(false);
							return sr;
						}
					}
				}
			}
		}
		
		//����ȶ�
		String needSort = arg0.getParameter("sortParam").trim();
		List<Float> resultFloatList = null;
		if(needSort.length() > 0){
			resultFloatList = new ArrayList<Float>();
			for(String s : resultList){
				resultFloatList.add(Float.valueOf(s));
			}
		}
		if(resultFloatList != null){
			float fTemp = 0.0f;
			if(needSort.equals("desc")){			
				for(float f : resultFloatList){
					if(fTemp < f){
						sr.setResponseMessage(String.format("���򷵻رȶԴ���.�ӿڵ��ò���:%s\n�ӿڷ���������%s",params,String.valueOf(resultFloatList)));
						sr.setResponseData(String.format("���򷵻رȶԴ���.�ӿڵ��ò���:%s\n�ӿڷ���������%s",params,String.valueOf(resultFloatList)),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
					fTemp = f;
				}
			}else{
				for(float f : resultFloatList){
					if(fTemp > f){
						sr.setResponseMessage(String.format("���򷵻رȶԴ���.�ӿڵ��ò���:%s\n�ӿڷ���������%s",params,String.valueOf(resultFloatList)));
						sr.setResponseData(String.format("���򷵻رȶԴ���.�ӿڵ��ò���:%s\n�ӿڷ���������%s",params,String.valueOf(resultFloatList)),null);
						sr.setDataType(SampleResult.TEXT);
						sr.setSuccessful(false);
						return sr;
					}
					fTemp = f;
				}
			}
		}
		
	
		if(other_result != null ){
			sr.setResponseData(String.format("�ӿڷ���:\n%s\n���ݿⷵ��:\n%s\n",result,other_result));
		}else{
			sr.setResponseData(result);
		}
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}