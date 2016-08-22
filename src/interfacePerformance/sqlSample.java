package interfacePerformance;
import java.sql.SQLException;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;

import interfacePerformance.util.QueryDB;
import interfacePerformance.util.StrFormat;

public class sqlSample extends AbstractJavaSamplerClient {

    private static long start = 0;  
    private static long end = 0; 
    String sqls[] = {"sql","sql2","sql3","sql4","sql5","sql6"};
	private String resultTotal="";
	@Override
	public void setupTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		
		start = System.currentTimeMillis();
	}

	@Override
	public Arguments getDefaultParameters() {
		// TODO Auto-generated method stub
		 Arguments args = new Arguments();
		 args.addArgument("dbConfig","");
		 for(String sql:sqls)
	        args.addArgument(sql, "");
	        return args;
	}



	@Override
	public void teardownTest(JavaSamplerContext context) {
		// TODO Auto-generated method stub
		 end = System.currentTimeMillis();  
//		  System.err.println("cost time:" + (end - start) / 1000);  
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
		
		SampleResult sr = new SampleResult();
		String dbConfig = arg0.getParameter("dbConfig");
		dbConfig=new StrFormat().format(dbConfig);
		if(!dbConfig.equals("")){
			String[] dbConfigArray = dbConfig.split(";");
//			System.out.println("dbconfig length:"+dbConfigArray.length);
			
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
		int result = 0;
		sr.sampleStart();
		try {
		 QueryDB qdb = new QueryDB();
		 
		 for(String sql:sqls)
		 {
			 sql=new StrFormat().format(sql);
			 String sqlvalue=new StrFormat().format(arg0.getParameter(sql));
			 if(!sqlvalue.equals(""))
			 {
				 System.out.println(arg0.getParameter(sql));
				 result = qdb.updateDb(url, arg0.getParameter(sql), user, pwd);
				 this.resultTotal +=  Integer.toString(result)+",";
				 
			 }

		 }


			 
			
		} catch (ClassNotFoundException | SQLException e) {
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
//		if(result!=0)
		sr.setSuccessful(true);
		sr.setResponseData(this.resultTotal+"");
		this.resultTotal="";
		sr.sampleEnd();
		return sr;
	}
	

}
