package interfacePerformance.util;

//import org.json.JSONObject;

public class StrToJson{

	public static void main(String[] args) throws InterruptedException {

		String jsonStr = "a,b,c";

		String resultStr = null;

		resultStr = "[\""+jsonStr.replace(",","\",\"")+"\"]";
		System.out.printf(resultStr);
//		"{\"a\":1\,\"b\":2\,\"c\":3\,\"d\":4
	}
	
	public static String StrJson(String value) {
		// TODO Auto-generated method stub
		String jsonStr = value;
		String resultStr = null;

		resultStr = "[\""+jsonStr.replace(",","\",\"")+"\"]";
		return resultStr;
	}

}