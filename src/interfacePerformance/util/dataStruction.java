package interfacePerformance.util;

import java.io.FileInputStream;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;






import org.json.JSONArray;

public class dataStruction {
	private static int num;
	public static void main(String[] args) throws Exception {
//		final String driver = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.20.71:9001/tuanmei";
		final String user = "dev";
		final String password = "jmdevcd";
		final String filePath = "E:/file/2000007260.xls";
		
//		dataStruction.deleteDateByXls(url, user, password,"user_hp_address_map_1", filePath, "Sheet1", "Sheet1");
		int num=dataStruction.creatTestData(url, user, password,
				"tuanmei_user_wish_deals", filePath, "Sheet1", "Sheet2");
//		ArrayList<String> num1=dataStruction.deleteSqlAccurate("user_hp_address_map_1", filePath, "Sheet1", "Sheet1");
//		System.out.println(num1);
	}

	public static int creatTestData(String url, String user, String password,
			String tableName, String filePath, String dataSheetName,
			String deleteSheetName) {
//		System.out.println(System.currentTimeMillis());
		try {

			DBUtil1 dbUtil1 = new DBUtil1(url, user, password);
 
			Statement statement = dbUtil1.statement(dbUtil1.connect());

			// 读取excel文件，获取insert sql
			String createSql = createSql(tableName, filePath, dataSheetName);// "TestCaseData/promoCard.xls"

			// 定义插入数据前要清理的sql语句
			ArrayList<String> sqlLists = new ArrayList<String>();
			sqlLists = deleteSqlAccurate(tableName, filePath, dataSheetName,
					deleteSheetName);
			// 先执行清理操作
			for (String sql : sqlLists) {

				dbUtil1.executeUpdate(statement, sql);

			}

			// 再执行插入操作
			num=dbUtil1.executeUpdate(statement, createSql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//		System.out.println(System.currentTimeMillis());
		return num;
	}

	public static void deleteDateByXls(String url, String user,
			String password, String tableName, String filePath,
			String dataSheetName, String deleteSheetName) throws Exception {
		DBUtil1 dbUtil1 = new DBUtil1(url, user, password);

		Statement statement = dbUtil1.statement(dbUtil1.connect());
		// 获取要清理的sql语句列表，可能有多条
		ArrayList<String> sqlLists = new ArrayList<String>();
		sqlLists = deleteSql(tableName, filePath, dataSheetName,
				deleteSheetName);

		// 执行清理操作
		for (String sql : sqlLists) {
			dbUtil1.executeUpdate(statement, sql);
		}
	}

	/*
	 * 读取数据文件excel的第二个sheet，得到多个列名，然后读取第一个sheet的这些列下的所有内容，生成delete语句。
	 * 比如得到了两个列名id,uid.那么返回的delete语句包含两条，一条是按id删除，一条是按uid删除
	 * 
	 * @param tableName:要插入的表名
	 * 
	 * @param file:excel文件路径，这里我们约定放在工程的TestCaseData目录下
	 * 
	 * @return ArrayList<String>:拼装好的删除语句的List
	 */
	public static ArrayList<String> deleteSql(String tabelName, String file,
			String dataSheetName, String deleteSheetName) throws Exception {
		// 初始化返回的sql语句列表
		ArrayList<String> sqlLists = new ArrayList<String>();
		// 获取文件路径，将文件内容以流的形式输出
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(file));
		// 获取sheet对象
		Sheet sheet1 = workbook.getSheet(deleteSheetName);
		// 主键个数
		int numOfKey = sheet1.getColumns();
		String primaryKey = "";
		for (int k = 0; k < numOfKey; k++) {
			Cell cellOne = sheet1.getCell(k, 0);
			primaryKey = cellOne.getContents();
			// 拼装delete语句开头部分
			StringBuilder sb = new StringBuilder();
			sb.append("delete from " + tabelName + " where " + primaryKey
					+ " in (");
			// 读取excel中主键内容
			JSONArray jsonArray = getcolumns(file, dataSheetName, primaryKey);

			for (int i = 0; i < jsonArray.length(); i++) {
				String value = jsonArray.getString(i);
				// 是数字不需要加引号
				if (isDouble(value) || isInteger(value)) {
					sb.append(value + ",");
				}
				// 非数字加引号
				else {
					sb.append("'" + value + "',");
				}
			}
			// 去掉最后的逗号
			if (jsonArray.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			sb.append(")");
			// System.out.println(sb.toString());
			sqlLists.add(sb.toString());

		}
		return sqlLists;
	}

	public static ArrayList<String> deleteSqlAccurate(String tabelName, String file,
			String dataSheetName, String deleteSheetName) throws Exception {
		// 初始化返回的sql语句列表 精准
//		System.out.println(System.currentTimeMillis());
		ArrayList<String> sqlLists = new ArrayList<String>();
		// 获取文件路径，将文件内容以流的形式输出
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(file));
		// 获取sheet对象
		Sheet sheet1 = workbook.getSheet(deleteSheetName);
		// 主键个数
		int numOfKey = sheet1.getColumns();
		String primaryKey = "";
		
		
		List<String> keys=new ArrayList<String>();
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//		Sheet sheet = workbook.getSheet(dataSheetName);
//		int rows=sheet.getRows();
//		Map<String,String> map=new HashMap<String, String>();
		Map<String,JSONArray> cont=new HashMap<String,JSONArray>();
		JSONArray jsonArray =null;
		//获取key
		//把数据完整put到cont里面
		for (int k = 0; k < numOfKey; k++) {
			Cell cellOne = sheet1.getCell(k, 0);
			primaryKey = cellOne.getContents();
			if(primaryKey.equals(""))
				continue;
			jsonArray=getcolumns(file, dataSheetName, primaryKey);
			keys.add(primaryKey);
			cont.put(primaryKey, jsonArray);
		}
		
		//把数据数列变成横列
		for(int i=0;i<cont.get(keys.get(0)).length();i++)
		{
			Map<String,String> map1=new HashMap<String, String>();
			for(int j=0;j<cont.size();j++)
			{
				String a=cont.get(keys.get(j)).get(i).toString();
				map1.put(keys.get(j), a);
			}
			list.add(map1);
		}
		//sql construction
		for(int i=0;i<list.size();i++)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("delete from " + tabelName + " where ");
			for(int j=0;j<keys.size();j++)
			{
			if(j>0)
				sb.append(" and "+keys.get(j)+ " in ('"+list.get(i).get(keys.get(j))+"')");
			else
				sb.append(keys.get(j)+ " in ('"+list.get(i).get(keys.get(j))+"')");
			}
			sqlLists.add(sb.toString());
		}
		System.out.println(sqlLists);
		return sqlLists;
	}
	
	
	
	/*
	 * 根据字段名找出该列的值，放进数组
	 * 
	 * @param filePath:文件路径
	 * 
	 * @param sheetIndex:sheet索引
	 * 
	 * @param columnValue:字段
	 * 
	 * @return : JSONArray
	 */
	public static JSONArray getcolumns(String filePath, String dataSheetName,
			String columnValue) throws Exception {
		// 获取文件路径，将文件内容以流的形式输出
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		// 获取sheet对象
		Sheet sheet = workbook.getSheet(dataSheetName);

		JSONArray jsonArray = new JSONArray();

		try {
			for (int i = 0; i < sheet.getColumns(); i++) {

				if (sheet.getCell(i, 0).getContents().equals(columnValue)) {
					for (int j = 1; j < sheet.getRows(); j++) {
						if (sheet.getCell(i, j).getContents() == null
								|| sheet.getCell(i, j).getContents().equals("")) {
							;
						}
						jsonArray.put(sheet.getCell(i, j).getContents());
					}
					break;
				} else {
					continue;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("找不到该字段");
		}
		return jsonArray;
	}

	/*
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是浮点数返回true,否则返回false
	 */
	// public static boolean isDouble(String str) {
	// Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
	// return pattern.matcher(str).matches();
	// }

	/*
	 * 去掉IP字符串前后所有的空格
	 */
	// public static String removeSpace(String IP) {
	// while (IP.startsWith(" ")) {
	// IP = IP.substring(1, IP.length()).trim();
	// }
	// while (IP.endsWith(" ")) {
	// IP = IP.substring(0, IP.length() - 1).trim();
	// }
	// return IP;
	// }

	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	// public static boolean isInteger(String str) {
	// Pattern pattern = Pattern.compile("[0-9]+");
	// return pattern.matcher(str).matches();
	// }

	/*
	 * 读取数据文件excel，生成insert语句
	 * 
	 * @param tableName:要插入的表名
	 * 
	 * @param filepath:excel文件路径，这里我们约定放在工程的TestCaseData目录下
	 * 
	 * @return string:拼装好的插入语句
	 * 
	 * !!!!!!!!拼装的sql语句中，每个字段名是加了``的，和程洪提供的不同，所以从jar包中单独提出来放在了自己的工具包中
	 */
	public static String createSql(String tableName, String filePath,
			String dataSheetName) throws Exception {
		StringBuilder sb = new StringBuilder();

		// 拼装insert语句开头部分
		sb.append("insert into " + tableName + " (");

		// 获取文件路径，将文件内容以流的形式输出
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		// 获取sheet对象
		Sheet sheet = workbook.getSheet(dataSheetName);

		// 获取该excel的总行数和总列数
		if (sheet == null) {
			throw new Exception("没有找到指定的sheet");
		}
		int rows = sheet.getRows();
		int columns = sheet.getColumns();

		// 获取第一行表的字段名
		for (int j = 0; j < columns; j++) {
			Cell cellOne = sheet.getCell(j, 0);
			// 判断该单元格是否是最后一个，如果不是，则添加逗号。
			// 这里的字段名都用“`”包起来的，如`id`
			// 改动，如果遇到空cell，跳出循环不继续读字段，并修正columns的实际值
			if (cellOne.getContents() == null
					|| cellOne.getContents().equals("")) {
				columns = j;
				sb.setLength(sb.length() - 1);
				break;
			}

			if (j != (columns - 1)) {
				sb.append("`" + cellOne.getContents() + "`" + ",");
			} else {
				sb.append("`" + cellOne.getContents() + "`");
			}
		}
		// 拼装列名部分完毕，并添加值的关键字values
		sb.append(")" + " values");

		// 获取values。从第一行开始遍历读取
		for (int i = 1; i < rows; i++) {
			// 如果第i行的第一个数据为空，则跳出循环不继续读取该行和以后行的数据，并修正rows的实际值
			if (sheet.getCell(0, i).getContents() == null
					|| sheet.getCell(0, i).getContents().equals("")) {
				rows = i;
				sb.setLength(sb.length() - 1);
				break;
			}
			sb.append("(");
			// 遍历每一行的列并获取该值
			for (int j = 0; j < columns; j++) {
				Cell cell = sheet.getCell(j, i);
				String cellValue = cell.getContents();

				// 判断该列的值是否是最后一列，如果不是，则添加逗号
				if (j != (columns - 1)) {
					// 判断是否是数字还是字符串。如果是字符串，则添加引号
					if ((isDouble(cellValue) || isInteger(cellValue))
							&& !(cellValue.isEmpty())) {
						sb.append(cellValue + ",");
					} else {
						sb.append("\'" + cellValue + "\',");
					}
				} else {
					if ((isDouble(cellValue) || isInteger(cellValue))
							&& !(cellValue.isEmpty())) {
						sb.append(cellValue);
					} else {
						sb.append("\'" + cellValue + "\'");
					}
				}
			}
			// 判断该行数据是否是最后一行数据，如果不是，则添加逗号
			if (i != (rows - 1)) {
				sb.append("),");
			} else {
				sb.append(")");
			}
			
		}
		// System.out.println("create的SQL为：" + sb.toString());

		return sb.toString();
	}

	/*
	 * 判断是否为整数
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

	/*
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str 传入的字符串
	 * 
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}

	/*
	 * 调用DBResources封装的方法来连接数据库，删除数据
	 */
//	public static void deleteDateByXls(String DBPort, String DBName,
//			String tableName, String filePath, String dataSheetName,
//			String deleteSheetName) throws Exception {
//
//		Connection con = DBResources.connect(DBPort, DBName);
//		// 获取要清理的sql语句列表，可能有多条
//		ArrayList<String> sqlLists = new ArrayList<String>();
//		sqlLists = deleteSql(tableName, filePath, dataSheetName,
//				deleteSheetName);
//
//		// 执行清理操作
//		for (String sql : sqlLists) {
//			DBResources.executeUpdate(con, sql);
//		}
//		con.close();
//	}
//
//	/*
//	 * 调用DBResources封装的方法来连接数据库,创建测试数据
//	 */
//
//	public static void creatTestData(String DBPort, String DBName,
//			String tableName, String filePath, String dataSheetName,
//			String deleteSheetName) {
//		try {
//			Connection con = DBResources.connect(DBPort, DBName);
//			// 读取excel文件，获取insert sql
//			String createSql = createSql(tableName, filePath, dataSheetName);// "TestCaseData/promoCard.xls"
//
//			// 定义插入数据前要清理的sql语句
//			ArrayList<String> sqlLists = new ArrayList<String>();
//			sqlLists = deleteSql(tableName, filePath, dataSheetName,
//					deleteSheetName);
//			// 先执行清理操作
//			for (String sql : sqlLists) {
//				DBResources.executeUpdate(con, sql);
//			}
//
//			// 再执行插入操作
//			DBResources.executeUpdate(con, createSql);
//			con.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}

}