package userinf.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHelper {
	/**
	 * 从文件中获取要执行的sql语句
	 * @param filePath
	 * @return Map<List<String>>
	 */
	public static Map<String,List<String>> getSqlFromFile(String filePath){
		Map<String,List<String>> sqlMap = new HashMap<String,List<String>>();
		sqlMap.put("delete", new ArrayList<String>());
		sqlMap.put("insert", new ArrayList<String>());
		try {
			String path = System.getProperty("user.dir");
			String confPath = path.concat(File.separator).concat("bin").concat(File.separator).concat("userinf").concat(File.separator).concat("test").concat(File.separator).concat(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(confPath)));
			String sql = null;
			while((sql = br.readLine()) != null){
				sql = sql.trim();
				if(sql.length() > 0 && !sql.startsWith("#")){
					if(sql.startsWith("delete")){
						sqlMap.get("delete").add(sql);
					}else{
						sqlMap.get("insert").add(sql);
					}
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlMap;
	}
	
	public static void main(String[] args){
		FileHelper.getSqlFromFile("sql.txt");
	}

}
