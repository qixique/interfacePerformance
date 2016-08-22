package interfacePerformance.util;

import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/*
 * 根据文本协议，封装socket通信
 */
public class BasicController {
	
	RpcTextClient rpcTextClient = null;
	
	/*
	 * 构造函�
	 */
	public BasicController(String host,String port) throws UnknownHostException, IOException {
		rpcTextClient = new RpcTextClient(host, port);
//		rpcTextClient = new RpcTextClient(host, "3202");
		rpcTextClient.connect();
		
	}
	
	/*
	 * 根据文本协议构造数据，调用socket客户端，发送数据流，并接收输出数据
	 */
	public String getSocketStream(String command,String user,String password,
			String className,String methodName,JSONArray value,String Rpc_secret_key) throws IOException, JSONException {
//		System.out.println(System.currentTimeMillis());
		JSONObject jsonObject = new Package().getDataBody(command, user, password, className, methodName, value);
		
		//生成signature的MD5
		String dataString = jsonObject.get("data").toString();
		String signature = JmMD5.getSignatureMD5(dataString.replace("\\\\", "\\"),Rpc_secret_key);
		jsonObject.put("signature", signature);
		
		//将字符串进行转义，添加到jsonObject
		jsonObject.put("data", jsonObject.getString("data"));
		
		//将jsonObject转化为string，然后进行协议格式化
		String data = Package.formatPkg(command,jsonObject.toString().replace("\\\\", "\\"));
//		System.out.println(System.currentTimeMillis());
//		System.out.println(data);
		rpcTextClient.send(data);

		//读取第一行数据，即返回值长
		rpcTextClient.getResponse(50000);
		//返回第二行数据，即data
		String context = rpcTextClient.getResponse(50000);
		rpcTextClient.close();
		
		return context;
	}
	
	

}
