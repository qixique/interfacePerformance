package interfacePerformance.util;



public class JmMD5 {
	
	/*
	   * 密码生成MD5算法
	   * @param user: 服务的用户名
	   * @param rpcSecret: 
	   * @return 返回md5值
	   */
	  public static String getPasswordMD5(String user, String rpcSecret){
		  //md5加密算法
		  String md5string = user + ":" + rpcSecret;
		  
		  return MD5.MD5Crypt(md5string);
	  }
	  
	  /*
	   * 数字签名生成MD5算法
	   */
	  public static String getSignatureMD5(String data, String rpc_secret_key){
		  //md5加密算法
		  String md5string = data + "&" + rpc_secret_key;
		  
		  return MD5.MD5Crypt(md5string);
	  }

}
