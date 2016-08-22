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
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("keys�Ĳ��������������1");
			sr.setResponseData("keys�Ĳ��������������1", null);
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
			sr.setResponseMessage("hgetall�쳣");
			sr.setResponseData("hgetall�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult lpush(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���				
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length<2) {
			sr.setResponseMessage("lpush�Ĳ��������������2");
			sr.setResponseData("lpush�Ĳ�����������",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			//��һ��������keyֵ
			String LpushKey = paraArray[0];					
			//�ӵڶ���������ʼ��valueֵ
			for (int i = 1; i < paraArray.length; i++) {
				String lpushValue = paraArray[i];					
				System.out.println("exc: lpush "+ LpushKey + " "+ lpushValue);
				jedis.lpush(LpushKey, lpushValue);
				System.out.println(jedis.lrange(LpushKey, 0, -1).toString());		
			}
		} catch (Exception e) {
			sr.setResponseMessage("lpush�쳣");
			sr.setResponseData("lpush�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("lpush�ɹ�");
		sr.setResponseData("lpush�ɹ�",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	
	private SampleResult lrem(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 3) {
			sr.setResponseMessage("lrem�Ĳ�����������Ϊ3");
			sr.setResponseData("lrem�Ĳ�����������",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {
			//��һ��������keyֵ					
			String lremKey = paraArray[0];
			//�ڶ���������countֵ				
			long count = Long.parseLong(paraArray[1]);	
			//������������valueֵ					
			String value = paraArray[2];	
			jedis.lrem(lremKey, count, value);
		} catch (Exception e) {
			System.out.println("lrem false!");
			sr.setResponseMessage("lrem�쳣");
			sr.setResponseData("lrem�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("lrem�ɹ�");
		sr.setResponseData("lrem�ɹ�",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult hset(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 3) {
			sr.setResponseMessage("hset�Ĳ�����������Ϊ3");
			sr.setResponseData("hset�Ĳ�����������Ϊ3",null);
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
			sr.setResponseMessage("hset�쳣");
			sr.setResponseData("hset�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("hset�ɹ�");
		sr.setResponseData("hset�ɹ�",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult hget(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length != 2) {
			sr.setResponseMessage("hget�Ĳ�����������Ϊ2");
			sr.setResponseData("hget�Ĳ�����������Ϊ2",null);
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
			sr.setResponseMessage("hget�쳣");
			sr.setResponseData("hget�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	private SampleResult hmset(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" ");  
		if (paraArray.length % 2 == 0) {
			sr.setResponseMessage("hmset�Ĳ�����������Ϊ����");
			sr.setResponseData("hmset�Ĳ�����������",null);
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
			sr.setResponseMessage("hmset�쳣");
			sr.setResponseData("hmset�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("hmset�ɹ�");
		sr.setResponseData("hmset�ɹ�",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult hmget(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length < 2) {
			sr.setResponseMessage("hmget�Ĳ�����������>=2");
			sr.setResponseData("hmget�Ĳ�����������>=2",null);
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
			sr.setResponseMessage("hmget�쳣");
			sr.setResponseData("hmget�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	private SampleResult hgetall(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("hgetall�Ĳ��������������1");
			sr.setResponseData("hgetall�Ĳ��������������1", null);
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
			sr.setResponseMessage("hgetall�쳣");
			sr.setResponseData("hgetall�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult hdel(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���				
		String[] paraArray = cmd.split(" "); 
		if (paraArray.length<2) {
			sr.setResponseMessage("hdel�Ĳ�����������>=2");
			sr.setResponseData("hdel�Ĳ�����������>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//�ӵڶ���������ʼ��fieldֵ
			long j = 0;
			for (int i = 1; i < paraArray.length; i++) {
				String field = paraArray[i];					
				j += jedis.hdel(key, field);
			    					
			}
			sr.setResponseMessage("hdel�ɹ�ɾ��"+j+"���ֶ�");
			sr.setResponseData("hdel�ɹ�ɾ��"+j+"���ֶ�",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(true);
		} catch (Exception e) {
			sr.setResponseMessage("hdel�쳣");
			sr.setResponseData("hdel�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		return sr;
	}
	
	
	private SampleResult sadd(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���				
		String[] paraArray = cmd.split(" ");
		if (paraArray.length<2) {
			sr.setResponseMessage("sadd�Ĳ�����������>=2");
			sr.setResponseData("sadd�Ĳ�����������>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//�ӵڶ���������ʼ��memberֵ
			for (int i = 1; i < paraArray.length; i++) {
				String member = paraArray[i];					
				jedis.sadd(key,member);		
			}
		} catch (Exception e) {
			sr.setResponseMessage("sadd�쳣");
			sr.setResponseData("sadd�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("sadd�ɹ�!");
		sr.setResponseData("sadd�ɹ�!",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult smembers(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("smembers�Ĳ��������������1");
			sr.setResponseData("smembers�Ĳ��������������1", null);
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
			sr.setResponseMessage("smembers�쳣");
			sr.setResponseData("smembers�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}

	private SampleResult scard(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis){
		//��ȡ�����ܸ���
		String[] paraArray = cmd.split(" ");
		if(paraArray.length!=1){
			sr.setResponseMessage("scard�Ĳ��������������1");
			sr.setResponseData("scard�Ĳ��������������1", null);
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
			sr.setResponseMessage("smembers�쳣");
			sr.setResponseData("smembers�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;			
		}
		return sr;
	}
	
	private SampleResult srem(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���				
		String[] paraArray=cmd.split(" ");
		if (paraArray.length<2) {
			sr.setResponseMessage("srem�Ĳ�����������>=2");
			sr.setResponseData("srem�Ĳ�����������>=2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}				
		try {
			String key = paraArray[0];					
			//�ӵڶ���������ʼ��memberֵ
			for (int i = 1; i < paraArray.length; i++) {
				String member = paraArray[i];					
				jedis.srem(key, member);					
			}
		} catch (Exception e) {
			sr.setResponseMessage("srem�쳣");
			sr.setResponseData("srem�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("srem�ɹ���");
		sr.setResponseData("srem�ɹ���",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}

	private SampleResult set(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {			
		//��ȡ�����ܸ���
		String[] paraArray=cmd.split(" "); 
		if (paraArray.length != 2) {
			sr.setResponseMessage("set�Ĳ�����������Ϊ2");
			sr.setResponseData("set�Ĳ�����������Ϊ2",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}
		try {					
			jedis.set(paraArray[0],paraArray[1]);
		} catch (Exception e) {
			sr.setResponseMessage("set�쳣");
			sr.setResponseData("set�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("set�ɹ���");
		sr.setResponseData("set�ɹ���",null);
		sr.setDataType(SampleResult.TEXT);
		sr.setSuccessful(true);
		return sr;
	}
	
	private SampleResult get(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {			
		//��ȡ�����ܸ���
		String[] paraArray=cmd.split(" "); 	
		if (paraArray.length != 1) {
			sr.setResponseMessage("get�Ĳ�����������Ϊ1");
			sr.setResponseData("get�Ĳ�����������Ϊ1",null);
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
			sr.setResponseMessage("get�쳣");
			sr.setResponseData("get�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	

	}
	
	
	private SampleResult del(JavaSamplerContext arg0, String cmd, SampleResult sr, Jedis jedis) {
		//��ȡ�����ܸ���		
		String[] paraArray=cmd.split(" "); 
		if (paraArray.length == 0) {
			sr.setResponseMessage("del�Ĳ�����������>=1");
			sr.setResponseData("del�Ĳ�����������>=1",null);
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
			sr.setResponseMessage("del�쳣");
			sr.setResponseData("del�쳣",null);
			sr.setDataType(SampleResult.TEXT);
			sr.setSuccessful(false);
			jedis.close();
			return sr;
		}	
		sr.setResponseMessage("del�ɹ���");
		sr.setResponseData("del�ɹ���",null);
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
