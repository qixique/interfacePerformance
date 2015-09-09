package interfacePerformance.util;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/*
 * 文本协议格式：%d\n%s\n%d\n%s\n,commandlength,command,datalength,data
 */
public class Package {
	/*
	 * 封装发送数据体。文本协议就4行：命令长度\n命令\n数据长度\n数据
	 * @param command: 命令
	 * @param databody: 数据体
	 * @return data: 返回数据
	 */
	public  JSONObject getDataBody(String command,String user,String password,String className,
			String methodName,JSONArray value) throws JSONException{
		 
//		String pkg = "{\"data\":\"{\\\"version\\\":\\\"2.0\\\",\\\"user\\\":\\\"" + user + "\\\",\\\"password\\\":\\\"" + password
//						+ "\\\",\\\"timestamp\\\":\\\"" + System.currentTimeMillis() + "\\\",\\\"class\\\":\\\"RpcClient_" + className + "\\\",\\\"method\\\":\\\"" + methodName 
//						+ "\\\",\\\"params\\\":" + value + "}\"}";
		
		//初始化jsonObject
		JSONObject jsonObject = new JSONObject();
		JSONObject dataObject = new JSONObject();
		//添加json
		jsonObject.put("data",dataObject);
		dataObject.put("params", value);
		dataObject.put("method", methodName);
		dataObject.put("class", className);
		dataObject.put("timestamp", System.nanoTime());
		dataObject.put("password", password);
		dataObject.put("user", user);
		dataObject.put("version", "2.0");
		return jsonObject;
	}
	
	
	/*
	 * 格式化pkg
	 * @param command: 协议命令
	 * @param pkg: 协议中数据体
	 * @return data: 格式化后的字符串
	 */
	public static String formatPkg(String command,String pkg){
//		String data = String.format("%d\n%s\n%d\n%s",command.length(),command,pkg.length(),pkg);
		String data1=command.length()+"\n"+command+"\n"+pkg.length()+"\n"+pkg;
		return data1;
	}
	
	/*
	 * 将pkg转换成jsonObject
	 * @param pkg: 协议中数据体
	 * @return: json对象
	 */
	public static JSONObject getJsonObject(String pkg) throws JSONException{
	JSONObject jsonObject = new JSONObject(pkg);
	
	return jsonObject;
	}

	}
