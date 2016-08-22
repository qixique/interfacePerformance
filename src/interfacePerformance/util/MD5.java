package interfacePerformance.util;


import java.security.*;



/*
 * MD5类
 */
public class MD5 {
	
  public MD5() {}
  
  public static void main(String[] args) {
//	    System.out.print(MD5.MD5Crypt("四方"));
	    
	    String[][] strings = {{"1","2","3"},{"a","b"}};
	    
	    System.out.println(strings.length);
	    for(int i=0;i<1000;i++)
	    {
	    	System.out.println(i);
	    	System.out.println(MD5Crypt(i));
	    }
	  }
  
  public static String byte2hex(byte[] b) {
    String hs = "";
    String stmp = "";
    for (int n = 0; n < b.length; n++) {
      stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
      if (stmp.length() == 1)
        hs = hs + "0" + stmp;
      else
        hs = hs + stmp;
    }
   
    return hs.toUpperCase();
  }

  public static String MD5Crypt(String s) {
    try {
      byte[] strTemp = s.getBytes("utf8");
     
      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
      mdTemp.update(strTemp);
      byte[] md = mdTemp.digest();
      
      return byte2hex(md).toLowerCase();
    }
    catch (Exception e) {
      return null;
    }
  }
  public static String MD5Crypt(Object s) {
	    try {
	      byte[] strTemp = s.toString().getBytes("utf8");
	     
	      MessageDigest mdTemp = MessageDigest.getInstance("MD5");
	      mdTemp.update(strTemp);
	      byte[] md = mdTemp.digest();
	      
	      return byte2hex(md).toLowerCase();
	    }
	    catch (Exception e) {
	      return null;
	    }
	  }
  
  
  


}