package interfacePerformance;

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


public class LoadTestBasicSampler extends AbstractJavaSamplerClient {
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
		
		host = host == null ?  "192.168.16.140" : host;
		port = port == null ? "3201" : port;
		
		SampleResult sr=new SampleResult();
		String result=null;
		sr.sampleStart();
		try {
			BasicPortal bp=new BasicPortal();
			result=bp.exe(arg0.getParameter("class"),arg0.getParameter("function"),new JSONArray("[" + arg0.getParameter("parameter").trim() + "]"),host,port);
		} catch (JSONException | IOException | SecurityException | IllegalArgumentException e) {
			sr.setResponseMessage("调用接口异常:\n" + e.toString());
			sr.setResponseData("调用接口异常:\n" + e.toString(),null);
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
		
		sr.setResponseMessage(result);
		sr.setResponseData(result,null);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
}