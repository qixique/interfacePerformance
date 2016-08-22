package interfacePerformance;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

public class Memcache extends AbstractJavaSamplerClient{

	@Override
	public Arguments getDefaultParameters()
	{
		 Arguments args = new Arguments();	 
		 args.addArgument("memcacheIp","192.168.16.140");
		 args.addArgument("memcachePort","11211");
		 args.addArgument("cmd","");	
	     return args;
		
	}
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
//		start = System.currentTimeMillis();
		
	}
	
	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
//		 end = System.currentTimeMillis();  
			 
//		 System.out.println("end time"+end+",start time"+start);
//		  System.err.println("cost time:" + (end - start) / 1000);  
	}
	private SampleResult set(JavaSamplerContext arg0, String cmd, SampleResult sr, MemcachedClient memcacheClient){
		String[] setParams = cmd.trim().split(" ");
		if(setParams.length!=3){
			sr.setResponseMessage("set���������������3");
		    sr.setResponseData("set���������������3", null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(false);
		    return sr;
		}
		try {
			memcacheClient.set(setParams[0],Integer.parseInt(setParams[2]),setParams[1]);
		} catch (NumberFormatException | TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sr.setResponseMessage(String.format("set�����쳣��%s",e.toString()));
		    sr.setResponseData(String.format("set�����쳣��%s",e.toString()), null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(true);
			return sr;
		}
		sr.setResponseMessage("set�ɹ���");
	    sr.setResponseData("set�ɹ���", null);
	    sr.setDataType(SampleResult.TEXT);
	    sr.setSuccessful(true);
		return sr;
	}
	private SampleResult get(JavaSamplerContext arg0, String cmd, SampleResult sr, MemcachedClient memcacheClient){
		String[] getParams = cmd.trim().split(" ");
		if(getParams.length!=1){
			sr.setResponseMessage("get���������������1");
		    sr.setResponseData("get���������������1", null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(false);
		    return sr;
		}
		try {
			String result = memcacheClient.get(getParams[0]);
			sr.setResponseMessage(String.format("%s",result));
		    sr.setResponseData(String.format("%s",result), null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(true);
			return sr;
		} catch (NumberFormatException | TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sr.setResponseMessage(String.format("get�����쳣��%s",e.toString()));
		    sr.setResponseData(String.format("get�����쳣��%s",e.toString()), null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(true);
			return sr;
		}
	}
	
	private SampleResult delete(JavaSamplerContext arg0, String cmd, SampleResult sr, MemcachedClient memcacheClient){
		String[] deleteParams = cmd.trim().split(" ");
		if(deleteParams.length == 0){
			sr.setResponseMessage("delete������������>=1");
		    sr.setResponseData("delete������������>=1", null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(false);
		    return sr;
		}
		try {
			for(int i=0;i < deleteParams.length; i++){
				memcacheClient.delete(deleteParams[i]);
			}

		} catch (NumberFormatException | TimeoutException | InterruptedException | MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sr.setResponseMessage(String.format("delete�����쳣��%s",e.toString()));
		    sr.setResponseData(String.format("delete�����쳣��%s",e.toString()), null);
		    sr.setDataType(SampleResult.TEXT);
		    sr.setSuccessful(false);
			return sr;
		}
		sr.setResponseMessage("delete�ɹ���");
	    sr.setResponseData("delete�ɹ���", null);
	    sr.setDataType(SampleResult.TEXT);
	    sr.setSuccessful(true);
		return sr;
	}
	
	@SuppressWarnings("deprecation")	
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		SampleResult sr = new SampleResult();
		String host_port = arg0.getParameter("memcacheIp")+":"+arg0.getParameter("memcachePort");
		System.err.println(host_port);
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(host_port)); 
		MemcachedClient memcachedClient = null;
		try {
			memcachedClient = builder.build();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
		String[] records = arg0.getParameter("cmd").trim().split(";");
		for(String r : records){
			String[] cmdParams = r.trim().split(",");
			if(cmdParams[0].equals("set"))
				sr = set(arg0,cmdParams[1],sr,memcachedClient);
			else if(cmdParams[0].equals("get"))
				sr = get(arg0,cmdParams[1],sr,memcachedClient);
			else if(cmdParams[0].equals("delete"))
				sr = delete(arg0,cmdParams[1],sr,memcachedClient);
		}
			
		try { 
			//close memcached client 
			memcachedClient.shutdown(); 			
		} 
		catch (IOException e) { 
			System.err.println("Shutdown MemcachedClient fail"); 
			e.printStackTrace(); 
		}			
		return sr;
	}

}
