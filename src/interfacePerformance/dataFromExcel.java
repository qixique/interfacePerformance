package interfacePerformance;

import interfacePerformance.util.dataStruction;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

public class dataFromExcel extends AbstractJavaSamplerClient {

	@SuppressWarnings("deprecation")
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		// TODO Auto-generated method stub
		SampleResult sr = new SampleResult();
		
		//build parameters
		JMeterVariables jmv = JMeterContextService.getContext().getVariables();
		String url = jmv.get("dburl");
		String user = jmv.get("dbuser");
		String pwd = jmv.get("dbpwd");
		url=url==null? "jdbc:mysql://192.168.20.71:9002/tuanmei" :url;
		String dbConfig = arg0.getParameter("dbConfig");
		//database url validation,get the local dbconfig first if local config not null,others get the dbconfig from testplan JMeterVariables
		if(!dbConfig.equals(""))
		{
			String[] dbConfigArray = dbConfig.split(";");
			if(dbConfigArray.length == 1){
				url = dbConfigArray[0];
				user = "dev";
				pwd = "jmdevcd";
			}else if(dbConfigArray.length >= 3){
				url = dbConfigArray[0];
				user =  dbConfigArray[1];
				pwd =  dbConfigArray[2];
			}else{
				sr.setResponseMessage("Êý¾Ý¿âÁ¬½ÓÅäÖÃ½âÎö´íÎó£¬Çë×ÐÏ¸¼ì²éÅäÖÃ");
				sr.setResponseData("Êý¾Ý¿âÁ¬½ÓÅäÖÃ½âÎö´íÎó",null);
				sr.setDataType(SampleResult.TEXT);
				sr.setSuccessful(false);
				return sr;
			}
		}
		String filePath=arg0.getParameter("filePath");
		String tableName=arg0.getParameter("table");
		String sheetForAdd=arg0.getParameter("sheetForAdd");
		//end for parameter build
		//begin to call sql strunction
		int insertnum=dataStruction.creatTestData(url, user, pwd, tableName, filePath, sheetForAdd, sheetForAdd);
		
		if(insertnum>0)
		{
			sr.setResponseData(String.format("the effect rows is:%d",insertnum));
			sr.setSuccessful(true);
		}
		else
		{
			sr.setResponseData(String.format("the effect rows is:%d",insertnum));
			sr.setSuccessful(false);
		}
		
		return sr;
	}
	public Arguments getDefaultParameters()
	{
		 Arguments args = new Arguments();
		 args.addArgument("dbConfig", "");
		 args.addArgument("filePath", "");
		 args.addArgument("table", "");
//		 args.addArgument("sheetForDel", "");
		 args.addArgument("sheetForAdd", "");
		return args;
		
	}

}
