package interfacePerformance.util;

//import org.json.JSONObject;

public class jsonToStr{

	public static void main(String[] args) throws InterruptedException {

		String jsonStr = "{\"a\":1,\"b\":2,\"c\":3,\"d\":4,\"e\":\"test\",\"f\":8}";
		String resultStr = null;

		resultStr = jsonStr.replace(",","\\,");
		System.out.printf(resultStr);
//		"{\"a\":1\,\"b\":2\,\"c\":3\,\"d\":4
	}
	
	public static String jsonStr(String value) {
		// TODO Auto-generated method stub
		String jsonStr = value;
		String resultStr = null;

		resultStr = jsonStr.replace(",","\\,");
		return resultStr;
	}

}