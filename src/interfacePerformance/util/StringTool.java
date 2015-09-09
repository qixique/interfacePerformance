package interfacePerformance.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTool {
	/**
	 * 数据库结果
	 * @param list
	 * @param showFileds
	 * @return
	 */
	public static String showDbResult(List<Map<String,Object>> list,String[] showFileds){
		String result = "";
		String oneResult = "";
		for(int i = 0; i < list.size(); i++){
			oneResult = "";
			for(int j = 0 ; j < showFileds.length; j++){
				oneResult = String.format("%s,%s=%s",oneResult,showFileds[j],String.valueOf(list.get(i).get(showFileds[j].trim().replace("\"", ""))));
			}
			result = String.format("%s\n%d:%s",result,i+1,oneResult);
		}
		return result;
	}
	
	/**
	 * 返回排序字段的值
	 * @param result
	 * @return
	 */
	public static Map<String,List<Float>> parseOrderFields(String result,Set<String> fileds){
		if(result == null || result.trim().length() < 1 || fileds == null || fileds.size() == 0)
			return null;
		
		Pattern pattern = null;
		Matcher matcher = null;
		
		Map<String,List<Float>> res = new HashMap<String,List<Float>>();
		List<Float> value = null;
		for(String f : fileds){
			value = new ArrayList<Float>();			
			pattern = Pattern.compile(String.format("\"%s\":\"([0-9,.]+)\"",f));
			matcher = pattern.matcher(result);
			while(matcher.find()){
				value.add(Float.parseFloat(matcher.group(1)));
			}
			res.put(f, value);
		}
		return res;
	}
	
	/**
	 * 将字符串解析放入map中
	 * @param orgStr eg: discounted:desc,jumei_price:asc
	 * @param splitLevel1
	 * @param splitLevel2
	 * @return
	 */
	public static Map<String,String> paraseStrToMap(String orgStr,String splitLevel1, String splitLevel2){
		if(orgStr == null || orgStr.length() == 0)
			return null;
		String[] temp = null;
		Map<String,String> resultMap = null;
		if(splitLevel1 == null){
			temp = orgStr.split(splitLevel2);
			if(temp.length != 2){
				return null;
			}
			resultMap = new HashMap<String,String>();
			resultMap.put(temp[0].trim(), temp[1].trim());
		}else{
			temp = orgStr.split(splitLevel1);
			resultMap = new HashMap<String,String>();
			String[] str1;
			for(String s : temp){
				str1 =  s.trim().split(splitLevel2);
				if(str1.length != 2)
					continue;
				resultMap.put(str1[0].trim(), str1[1].trim());
			}
		}
		return resultMap;
	}
	
	/**
	 * 去掉字符串中的特定字符
	 * @param org
	 * @param chars
	 * @return
	 */
	public static String TrimSepcialChar(String org, String[] chars){
		String result = org;
		for(String c : chars){
			result = result.replace(c, "");
		}
		return result;
	}
	
	/**
	 * 将中文转换成unicode编码
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s) {
		String str = "";
		char c;
		int ch;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			ch = (int) c;
			if (ch > 255)
				str += "\\u" + Integer.toHexString(ch);
			else
				str += s.charAt(i);
		}
		return str;
	}
	
	public static void main(String[] args){
		String sortParam = "sales_volume:desc";
		String result = "[{\"deal_id\":\"2038551\",\"original_price\":\"2666\",\"discounted_price\":\"2652\",\"discount\":\"9.9\",\"hash_id\":\"df140508p1033650\",\"status\":\"0\",\"short_name\":\"asf\",\"product_id\":\"1033650\",\"category\":\"media\",\"sales_volume\":\"2652\",\"end_time\":\"1456761600\",\"start_time\":\"1425139200\",\"brand_id\":\"2\"},{\"deal_id\":\"2056310\",\"original_price\":\"588\",\"discounted_price\":\"288\",\"discount\":\"4.8\",\"hash_id\":\"df140821p1033903\",\"status\":\"0\",\"short_name\":\"\u9a8c\u8bc1\u6a71\u7a97\u72b6\u6001\",\"product_id\":\"1033903\",\"category\":\"media\",\"sales_volume\":\"288\",\"end_time\":\"1456761600\",\"start_time\":\"1425139200\",\"brand_id\":\"2\"},{\"deal_id\":\"2056313\",\"original_price\":\"500\",\"discounted_price\":\"255\",\"discount\":\"5.1\",\"hash_id\":\"df140922p1033904\",\"status\":\"2\",\"short_name\":\"\u9a8c\u8bc1\u6a71\u7a97\u72b6\u60012\",\"product_id\":\"1033904\",\"category\":\"media\",\"sales_volume\":\"255\",\"end_time\":\"1456761600\",\"start_time\":\"1425139200\",\"brand_id\":\"2\"},{\"deal_id\":\"2056225\",\"original_price\":\"310\",\"discounted_price\":\"123\",\"discount\":\"3.9\",\"hash_id\":\"df140804p1033873\",\"status\":\"0\",\"short_name\":\"\u5929\u5802\u7684\u9910\u996e\",\"product_id\":\"1033873\",\"category\":\"media\",\"sales_volume\":\"123\",\"end_time\":\"1456761600\",\"start_time\":\"1425139200\",\"brand_id\":\"2\"}]";
		System.out.println("sortParam : " + sortParam);
		Map<String,String> orderMap = null;
		Map<String,List<Float>> orderInfoMap = null;
		if(sortParam.length() > 0){
			orderMap = StringTool.paraseStrToMap(sortParam, null, ":");
			System.out.println(orderMap);
			orderInfoMap = StringTool.parseOrderFields(result, orderMap.keySet());
		}
	}
}
