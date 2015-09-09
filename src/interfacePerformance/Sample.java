package interfacePerformance;

import java.io.IOException;
import java.sql.SQLException;








import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.json.JSONException;

import TestProduct.JumeiProduct_Read_InventoryInfo.getMediaInventoryByCondition;

public class Sample extends AbstractJavaSamplerClient {

    private static long start = 0;  
    private static long end = 0; 
	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		start = System.currentTimeMillis();
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
	        args.addArgument("host", "sku_no,product_id");
	        args.addArgument("port", "3201");
	        args.addArgument("classname", "getMediaInventoryByCondition");
	        return args;
	}



	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		 end = System.currentTimeMillis();  
		  System.err.println("cost time:" + (end - start) / 1000);  
	}
	
	@SuppressWarnings("deprecation")
	@Override 
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		SampleResult sr=new SampleResult();
		String result=null;
		sr.sampleStart();
		try {
		
			 result=getMediaInventoryByCondition.getMediaInventoryByCondition_perf();
			 
			 
			 
			
		} catch (ClassNotFoundException | JSONException | IOException
				| SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("setSuccessful:(false)");
			sr.setSuccessful(false);
			e.printStackTrace();sr.setResponseData(e.toString());sr.sampleEnd();return sr;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals("pass"))
		{
			sr.setSuccessful(true);
			sr.setResponseData(result);
		}
		else
		{
			sr.setSuccessful(false);
			sr.setResponseData(result);
		}
		sr.sampleEnd();
		return sr;
	}


}
