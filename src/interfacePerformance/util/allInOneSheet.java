package interfacePerformance.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;






import jxl.read.biff.BiffException;

import org.json.JSONArray;

/**
 * @author Administrator
 *
 */
public class allInOneSheet {


	private static int num;
	public static void main(String[] args) throws Exception {
//		final String driver = "com.mysql.jdbc.Driver";
		final String url = "jdbc:mysql://192.168.20.71:9001/tuanmei";
		final String user = "dev";
		final String password = "jmdevcd";
		final String filePath = "E:/file/2000007260.xls";
		
//		int num=dataStruction.creatTestData(url, user, password,
//				"tuanmei_user_wish_deals", filePath, "Sheet1", "Sheet2");
		getRow(filePath, "Sheet1", "1");
		getKeyRow(filePath, "Sheet1");
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
	
	/**
	 * get the table key in all over the sheet
	 * @param file
	 * @param sheetname
	 * @return
	 * @throws BiffException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<Integer> getKeyRow(String file,String sheetname) throws BiffException, FileNotFoundException, IOException
	{
		ArrayList<Integer> arr=new ArrayList<>();
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(file));
		Sheet sheet1 = workbook.getSheet(sheetname);
		for(int i=0;i<sheet1.getRows();i++)
		{
			String table=sheet1.getCell(0, i).getContents();
			if(table.equals("table"))
			{
				System.out.println(table+"row:"+i);
				arr.add(i+1);
			}
		}
		return arr;
	}

	
	public static void getRow(String file,String sheetname)
	{
		
	}
	
	/**
	 * @param filePath
	 * @param dataSheet
	 * @param row
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws BiffException 
	 */
	public static String getRow(String filePath,String dataSheet,String row) throws BiffException, FileNotFoundException, IOException
	{
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		Sheet sheet = workbook.getSheet(dataSheet);
		int row1=sheet.getRows();
		int table= sheet.findCell("table").getRow();
		String content=sheet.getCell(1, table).getContents();
		System.out.println(sheet.getColumns());
		System.out.println("rows:"+row1);
		System.out.println("table:"+table);
		System.out.println("content:"+content);
		return null;
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



}
