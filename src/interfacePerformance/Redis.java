package interfacePerformance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import redis.clients.jedis.Jedis;


public class Redis extends AbstractJavaSamplerClient{

	private SampleResult keys(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//获取参数总个数
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("keys的参数个数必须等于1");
			sr.setResponseData("keys的参数个数必须等于1", null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			Set<String> result = new HashSet<String>();
			result = jedis.keys(paraArray[0]);
			sr.setResponseMessage(String.format("%s", result));
			sr.setResponseData(String.format("%s", result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hgetall异常");
			sr.setResponseData("hgetall异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult lpush(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数				
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length<2) {
			sr.setResponseMessage("lpush的参数个数必须大于2");
			sr.setResponseData("lpush的参数个数有误",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			//第一个参数是key值
			String LpushKey = paraArray[0];					
			//从第二个参数开始是value值
			for (int i = 1; i < paraArray.length; i++) {
				String lpushValue = paraArray[i];					
				System.out.println("exc: lpush "+ LpushKey + " "+ lpushValue);
				jedis.lpush(LpushKey, lpushValue);
				System.out.println(jedis.lrange(LpushKey, 0, -1).toString());		
			}
		} catch (Exception e) {
			sr.setResponseMessage("lpush异常");
			sr.setResponseData("lpush异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("lpush成功");
		sr.setResponseData("lpush成功",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	
	private SampleResult lrem(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 3) {
			sr.setResponseMessage("lrem的参数个数必须为3");
			sr.setResponseData("lrem的参数个数有误",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			//第一个参数是key值					
			String lremKey = paraArray[0];
			//第二个参数是count值				
			long count = Long.parseLong(paraArray[1]);	
			//第三个参数是value值					
			String value = paraArray[2];	
			jedis.lrem(lremKey, count, value);
		} catch (Exception e) {
			System.out.println("lrem false!");
			sr.setResponseMessage("lrem异常");
			sr.setResponseData("lrem异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("lrem成功");
		sr.setResponseData("lrem成功",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult hset(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 3) {
			sr.setResponseMessage("hset的参数个数必须为3");
			sr.setResponseData("hset的参数个数必须为3",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			String key = paraArray[0];				
			String field = paraArray[1];					
			String value = paraArray[2];	
			jedis.hset(key, field, value);
		} catch (Exception e) {
			sr.setResponseMessage("hset异常");
			sr.setResponseData("hset异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("hset成功");
		sr.setResponseData("hset成功",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult hget(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 2) {
			sr.setResponseMessage("hget的参数个数必须为2");
			sr.setResponseData("hget的参数个数必须为2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			String key = paraArray[0];				
			String field = paraArray[1];					
			String result = jedis.hget(key, field);
			sr.setResponseMessage(String.format(result));
			sr.setResponseData(String.format(result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hget异常");
			sr.setResponseData("hget异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	private SampleResult hmset(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数
		String[] paraArray = cmd.split(" ");  
		if (paraArray.length % 2 == 0) {
			sr.setResponseMessage("hmset的参数个数必须为奇数");
			sr.setResponseData("hmset的参数个数有误",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			String key = paraArray[0];	
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 1; i < paraArray.length-1; i=i+2) {
				map.put(paraArray[i], paraArray[i+1]);
			}	
			jedis.hmset(key, map);
		} catch (Exception e) {
			sr.setResponseMessage("hmset异常");
			sr.setResponseData("hmset异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("hmset成功");
		sr.setResponseData("hmset成功",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult hmget(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length < 2) {
			sr.setResponseMessage("hmget的参数个数必须>=2");
			sr.setResponseData("hmget的参数个数必须>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			String key = paraArray[0];				
			List<String> result = null;
			result = new ArrayList<String>();
			for(int i=1;i<paraArray.length;i++){
				result.addAll(jedis.hmget(key, paraArray[i]));
			}
			sr.setResponseMessage(String.format("%s",result));
			sr.setResponseData(String.format("%s",result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hmget异常");
			sr.setResponseData("hmget异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	private SampleResult hgetall(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//获取参数总个数
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("hgetall的参数个数必须等于1");
			sr.setResponseData("hgetall的参数个数必须等于1", null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			Map<String,String> result = new HashMap<String,String>();
			result = jedis.hgetAll(paraArray[0]);
			sr.setResponseMessage(String.format("%s", result));
			sr.setResponseData(String.format("%s", result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hgetall异常");
			sr.setResponseData("hgetall异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult hdel(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数				
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length<2) {
			sr.setResponseMessage("hdel的参数个数必须>=2");
			sr.setResponseData("hdel的参数个数必须>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//从第二个参数开始是field值
			long j = 0;
			for (int i = 1; i < paraArray.length; i++) {
				String field = paraArray[i];					
				j += jedis.hdel(key, field);
			    					
			}
			sr.setResponseMessage("hdel成功删除"+j+"个字段");
			sr.setResponseData("hdel成功删除"+j+"个字段",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hdel异常");
			sr.setResponseData("hdel异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	
	private SampleResult sadd(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数				
		String[] paraArray = cmd.split(" ");
		if (paraArray.length<2) {
			sr.setResponseMessage("sadd的参数个数必须>=2");
			sr.setResponseData("sadd的参数个数必须>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//从第二个参数开始是member值
			for (int i = 1; i < paraArray.length; i++) {
				String member = paraArray[i];					
				jedis.sadd(key,member);		
			}
		} catch (Exception e) {
			sr.setResponseMessage("sadd异常");
			sr.setResponseData("sadd异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("sadd成功!");
		sr.setResponseData("sadd成功!",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult smembers(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//获取参数总个数
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("smembers的参数个数必须等于1");
			sr.setResponseData("smembers的参数个数必须等于1", null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			Set<String> result = new HashSet<String>();
			result = jedis.smembers(paraArray[0]);
			sr.setResponseMessage(String.format("%s", result));
			sr.setResponseData(String.format("%s", result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("smembers异常");
			sr.setResponseData("smembers异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}

	private SampleResult scard(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//获取参数总个数
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("scard的参数个数必须等于1");
			sr.setResponseData("scard的参数个数必须等于1", null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			long result = jedis.scard(paraArray[0]);
			sr.setResponseMessage(Long.toString(result));
			sr.setResponseData(Long.toString(result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("smembers异常");
			sr.setResponseData("smembers异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult srem(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数				
		String[] paraArray=cmd.split(" ");
		if (paraArray.length<2) {
			sr.setResponseMessage("srem的参数个数必须>=2");
			sr.setResponseData("srem的参数个数必须>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//从第二个参数开始是member值
			for (int i = 1; i < paraArray.length; i++) {
				String member = paraArray[i];					
				jedis.srem(key, member);					
			}
		} catch (Exception e) {
			sr.setResponseMessage("srem异常");
			sr.setResponseData("srem异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("srem成功！");
		sr.setResponseData("srem成功！",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult set(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {			
		//获取参数总个数
		String[] paraArray=cmd.split(" "); 
		if (paraArray.length != 2) {
			sr.setResponseMessage("set的参数个数必须为2");
			sr.setResponseData("set的参数个数必须为2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			jedis.set(paraArray[0],paraArray[1]);
		} catch (Exception e) {
			sr.setResponseMessage("set异常");
			sr.setResponseData("set异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("set成功！");
		sr.setResponseData("set成功！",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult get(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {			
		//获取参数总个数
		String[] paraArray=cmd.split(" "); 	
		if (paraArray.length != 1) {
			sr.setResponseMessage("get的参数个数必须为1");
			sr.setResponseData("get的参数个数必须为1",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			String result = jedis.get(paraArray[0]);
			sr.setResponseMessage(String.format("%s",result));
			sr.setResponseData(String.format("%s",result),null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
			return sr;
		} catch (Exception e) {
			sr.setResponseMessage("get异常");
			sr.setResponseData("get异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	

	}
	
	
	private SampleResult del(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//获取参数总个数		
		String[] paraArray=cmd.split(" "); 
		if (paraArray.length == 0) {
			sr.setResponseMessage("del的参数个数必须>=1");
			sr.setResponseData("del的参数个数必须>=1",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {			
			for (int i = 0; i < paraArray.length; i++) {
				String key = paraArray[i];					
				jedis.del(key);		
			}
		} catch (Exception e) {
			sr.setResponseMessage("del异常");
			sr.setResponseData("del异常",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("del成功！");
		sr.setResponseData("del成功！",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg0) {
		
		SampleResult sr = new SampleResult();
	
		Jedis jedis = new Jedis(arg0.getParameter("redisIp"), Integer.parseInt(arg0.getParameter("redisPort")));
		jedis.select(Integer.parseInt(arg0.getParameter("redisDb")));

		
		String[] records = arg0.getParameter("cmd").trim().split(";");
		for(String r : records){
			String[] cmd = r.trim().split(",");	
			if (cmd[0].equals("set")) 
				sr = set(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("get"))
				sr = get(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("del"))
				sr = del(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("keys"))
				sr = keys(arg0, cmd[1], sr, jedis);	
			else if (cmd[0].equals("hget")) 
				sr = hget(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("hmget")) 
				sr = hmget(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("hgetall")) 
				sr = hgetall(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("hset")) 
				sr = hset(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("hmset")) 
				sr = hmset(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("hdel")) 
				sr = hdel(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("sadd")) 
				sr = sadd(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("smembers")) 
				sr = smembers(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("scard")) 
				sr = scard(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("srem")) 
				sr = srem(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("lpush")) 
				sr = lpush(arg0, cmd[1], sr, jedis);
			else if (cmd[0].equals("lrem")) 
				sr = lrem(arg0, cmd[1], sr, jedis);

		}
		

//		}	

		jedis.close();
//		sr.setSuccessful(true);
		return sr;
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
	public String getCommandParas(String paras) {
		String[] stringArray = paras.split(" ");
		String parasString = StringUtils.join(stringArray, ",");
		return parasString;
		
	}
	

	public Arguments getDefaultParameters()
	{
		 Arguments args = new Arguments();	 
		 args.addArgument("redisIp","192.168.16.140");
		 args.addArgument("redisPort","6379");
		 args.addArgument("redisDb","8");	
		 args.addArgument("cmd","");	
	     return args;
		
	}
 public static void main(String[] args) {
	
}
}
