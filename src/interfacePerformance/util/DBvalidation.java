package interfacePerformance.util;

import interfacePerformance.db.QueryHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class DBvalidation {
	 private String  url="jdbc:mysql://192.168.20.71:9001/jumei_product?useUnicode=true&characterEncoding=UTF-8";
	 private String  user="dev";
	 private String  pwd="jmdevcd";
	 private String  sql="select deal_id,short_name as sn from tuanmei_deals where hash_id in('hl','sana')";
//	 private String  actualResult="product_id:1213,original_price:150,discounted_price:39.9,deal_id:36,sn:ttt,deal_id:20481915,sn:SANA豆乳水乳套装未找到";
	public Map<String, List<String>> com()
	 {
		 Map<String,List<String>> compareMap = new HashMap<String,List<String>>();
			
			List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql);
			List<String> list = new ArrayList<String>();
			String key = "";
			String value = "";
			Object[] fields= records.get(0).keySet().toArray();
			for(Object s : fields){
				key = s.toString().trim().replace("\"", "");
				for(int i = 0; i < records.size(); i++){
					value = records.get(i).get(key).toString();
						list.add(String.format("\"%s\": \"%s\"", key,value)); //非sql中的字段，将返回null
				}
				compareMap.put(key, list);
			}
			return compareMap;
	 }
	public String com1(String actualResult,String sql)
	{
		String re="比对成功";
//		Map<String,List<String>> compareMap = new HashMap<String,List<String>>();
//		List<String> list = new ArrayList<String>();
//		System.out.println(sql);
		List<Map<String,Object>> records = QueryHelper.getManyRecordsFromSQL(url, user, pwd, sql.replace("\"", ""));
		String key = "";
		String value = "";
		Object[] fields= records.get(0).keySet().toArray();
		for(Object s:fields)
		{
			key=s.toString().trim().replace("\"", "");
			for(int i = 0; i < records.size(); i++)
			{
				value = records.get(i).get(key).toString();
				if(actualResult.trim().replace("\"", "").indexOf(key+":"+value)<0)
				re="---"+key+":"+value+"---未找到"; 
			}
		}
		return re;
	}
	 public static void main(String[] args) {
		DBvalidation dv=new DBvalidation();
		System.out.println(dv.com1("deal_id:13","select deal_id,short_name,hash_id from tuanmei_deals where hash_id='qblotion'"));;
	}
	
}
