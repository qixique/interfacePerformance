package interfacePerformance.util;

import java.io.IOException;
import java.net.UnknownHostException;








import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONValue;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BasicPortal {
	
	public  String exe(String classname,String function,JSONArray values,String host,String port) throws UnknownHostException, IOException, JSONException
	{
		BasicController bc=new BasicController(host, port);
//		bc.getSocketStream(command, user, password, className, methodName, value, Rpc_secret_key);
		return bc.getSocketStream("RPC","Optool","82bb6a906a4d69faa7010c4eb28aa4cf","RpcClient_"+classname,function,values,"769af463a39f077a0340a189e9c1ec28");
	}
	public  String exe(String classname,String function,String values,String host,String port) throws UnknownHostException, IOException, JSONException
	{
		BasicPortal bp=new BasicPortal();
		JSONArray ja=new JSONArray();
		ja.put(values);
		return bp.exe( classname, function,ja , host, port) ;
		
	}
	@Test
	public static void main1() throws UnknownHostException, IOException, JSONException {
		
		
		BasicPortal bp=new BasicPortal();
		
//		String result=bp.exe("Get_Nearby","getNearbyStations",new JSONArray("['\\u5fb7\\u9633','31.0651','104.384','500']"),"192.168.16.140","2110");
		String result=bp.exe("Get_Nearby","getNearbyStations","\\u5fb7\\u9633","192.168.16.140","2110");
		Object obj=JSONValue.parse(result);
		System.out.println(System.currentTimeMillis());
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    String json = gson.toJson(obj);
		    System.out.println(System.currentTimeMillis());
//		System.out.println(result);
		System.out.println(json);
	}
	public static String toUnicode(String str)
	{
		char[] ch=str.toCharArray();
	     String ss="";
	     for (int i = 0; i < ch.length; i++) 
	     {
	       ss +="\\u"+Integer.toHexString(ch[i]);
	     
	     }
	     System.out.println(ss);
	     return ss;
	}
}
