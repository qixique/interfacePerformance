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

			// ��ȡexcel�ļ�����ȡinsert sql
			String createSql = createSql(tableName, filePath, dataSheetName);// "TestCaseData/promoCard.xls"

			// �����������ǰҪ�����sql���
			ArrayList<String> sqlLists = new ArrayList<String>();
			sqlLists = deleteSqlAccurate(tableName, filePath, dataSheetName,
					deleteSheetName);
			// ��ִ���������
			for (String sql : sqlLists) {

				dbUtil1.executeUpdate(statement, sql);

			}

			// ��ִ�в������
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
		// ��ȡҪ�����sql����б������ж���
		ArrayList<String> sqlLists = new ArrayList<String>();
		sqlLists = deleteSql(tableName, filePath, dataSheetName,
				deleteSheetName);

		// ִ���������
		for (String sql : sqlLists) {
			dbUtil1.executeUpdate(statement, sql);
		}
	}

	/*
	 * ��ȡ�����ļ�excel�ĵڶ���sheet���õ����������Ȼ���ȡ��һ��sheet����Щ���µ��������ݣ�����delete��䡣
	 * ����õ�����������id,uid.��ô���ص�delete������������һ���ǰ�idɾ����һ���ǰ�uidɾ��
	 * 
	 * @param tableName:Ҫ����ı���
	 * 
	 * @param file:excel�ļ�·������������Լ�����ڹ��̵�TestCaseDataĿ¼��
	 * 
	 * @return ArrayList<String>:ƴװ�õ�ɾ������List
	 */
	public static ArrayList<String> deleteSql(String tabelName, String file,
			String dataSheetName, String deleteSheetName) throws Exception {
		// ��ʼ�����ص�sql����б�
		ArrayList<String> sqlLists = new ArrayList<String>();
		// ��ȡ�ļ�·�������ļ�������������ʽ���
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(file));
		// ��ȡsheet����
		Sheet sheet1 = workbook.getSheet(deleteSheetName);
		// ��������
		int numOfKey = sheet1.getColumns();
		String primaryKey = "";
		for (int k = 0; k < numOfKey; k++) {
			Cell cellOne = sheet1.getCell(k, 0);
			primaryKey = cellOne.getContents();
			// ƴװdelete��俪ͷ����
			StringBuilder sb = new StringBuilder();
			sb.append("delete from " + tabelName + " where " + primaryKey
					+ " in (");
			// ��ȡexcel����������
			JSONArray jsonArray = getcolumns(file, dataSheetName, primaryKey);

			for (int i = 0; i < jsonArray.length(); i++) {
				String value = jsonArray.getString(i);
				// �����ֲ���Ҫ������
				if (isDouble(value) || isInteger(value)) {
					sb.append(value + ",");
				}
				// �����ּ�����
				else {
					sb.append("'" + value + "',");
				}
			}
			// ȥ�����Ķ���
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
		// ��ʼ�����ص�sql����б� ��׼
//		System.out.println(System.currentTimeMillis());
		ArrayList<String> sqlLists = new ArrayList<String>();
		// ��ȡ�ļ�·�������ļ�������������ʽ���
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(file));
		// ��ȡsheet����
		Sheet sheet1 = workbook.getSheet(deleteSheetName);
		// ��������
		int numOfKey = sheet1.getColumns();
		String primaryKey = "";
		
		
		List<String> keys=new ArrayList<String>();
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//		Sheet sheet = workbook.getSheet(dataSheetName);
//		int rows=sheet.getRows();
//		Map<String,String> map=new HashMap<String, String>();
		Map<String,JSONArray> cont=new HashMap<String,JSONArray>();
		JSONArray jsonArray =null;
		//��ȡkey
		//����������put��cont����
		for (int k = 0; k < numOfKey; k++) {
			Cell cellOne = sheet1.getCell(k, 0);
			primaryKey = cellOne.getContents();
			if(primaryKey.equals(""))
				continue;
			jsonArray=getcolumns(file, dataSheetName, primaryKey);
			keys.add(primaryKey);
			cont.put(primaryKey, jsonArray);
		}
		
		//���������б�ɺ���
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
	 * �����ֶ����ҳ����е�ֵ���Ž�����
	 * 
	 * @param filePath:�ļ�·��
	 * 
	 * @param sheetIndex:sheet����
	 * 
	 * @param columnValue:�ֶ�
	 * 
	 * @return : JSONArray
	 */
	public static JSONArray getcolumns(String filePath, String dataSheetName,
			String columnValue) throws Exception {
		// ��ȡ�ļ�·�������ļ�������������ʽ���
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		// ��ȡsheet����
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
			throw new Exception("�Ҳ������ֶ�");
		}
		return jsonArray;
	}

	/*
	 * �ж��Ƿ�Ϊ������������double��float
	 * 
	 * @param str ������ַ���
	 * 
	 * @return �Ǹ���������true,���򷵻�false
	 */
	// public static boolean isDouble(String str) {
	// Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
	// return pattern.matcher(str).matches();
	// }

	/*
	 * ȥ��IP�ַ���ǰ�����еĿո�
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
	 * �ж��Ƿ�Ϊ����
	 * 
	 * @param str ������ַ���
	 * 
	 * @return ����������true,���򷵻�false
	 */
	// public static boolean isInteger(String str) {
	// Pattern pattern = Pattern.compile("[0-9]+");
	// return pattern.matcher(str).matches();
	// }

	/*
	 * ��ȡ�����ļ�excel������insert���
	 * 
	 * @param tableName:Ҫ����ı���
	 * 
	 * @param filepath:excel�ļ�·������������Լ�����ڹ��̵�TestCaseDataĿ¼��
	 * 
	 * @return string:ƴװ�õĲ������
	 * 
	 * !!!!!!!!ƴװ��sql����У�ÿ���ֶ����Ǽ���``�ģ��ͳ̺��ṩ�Ĳ�ͬ�����Դ�jar���е���������������Լ��Ĺ��߰���
	 */
	public static String createSql(String tableName, String filePath,
			String dataSheetName) throws Exception {
		StringBuilder sb = new StringBuilder();

		// ƴװinsert��俪ͷ����
		sb.append("insert into " + tableName + " (");

		// ��ȡ�ļ�·�������ļ�������������ʽ���
		Workbook workbook = Workbook.getWorkbook(new FileInputStream(filePath));
		// ��ȡsheet����
		Sheet sheet = workbook.getSheet(dataSheetName);

		// ��ȡ��excel����������������
		if (sheet == null) {
			throw new Exception("û���ҵ�ָ����sheet");
		}
		int rows = sheet.getRows();
		int columns = sheet.getColumns();

		// ��ȡ��һ�б���ֶ���
		for (int j = 0; j < columns; j++) {
			Cell cellOne = sheet.getCell(j, 0);
			// �жϸõ�Ԫ���Ƿ������һ����������ǣ�����Ӷ��š�
			// ������ֶ������á�`���������ģ���`id`
			// �Ķ������������cell������ѭ�����������ֶΣ�������columns��ʵ��ֵ
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
		// ƴװ����������ϣ������ֵ�Ĺؼ���values
		sb.append(")" + " values");

		// ��ȡvalues���ӵ�һ�п�ʼ������ȡ
		for (int i = 1; i < rows; i++) {
			// �����i�еĵ�һ������Ϊ�գ�������ѭ����������ȡ���к��Ժ��е����ݣ�������rows��ʵ��ֵ
			if (sheet.getCell(0, i).getContents() == null
					|| sheet.getCell(0, i).getContents().equals("")) {
				rows = i;
				sb.setLength(sb.length() - 1);
				break;
			}
			sb.append("(");
			// ����ÿһ�е��в���ȡ��ֵ
			for (int j = 0; j < columns; j++) {
				Cell cell = sheet.getCell(j, i);
				String cellValue = cell.getContents();

				// �жϸ��е�ֵ�Ƿ������һ�У�������ǣ�����Ӷ���
				if (j != (columns - 1)) {
					// �ж��Ƿ������ֻ����ַ�����������ַ��������������
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
			// �жϸ��������Ƿ������һ�����ݣ�������ǣ�����Ӷ���
			if (i != (rows - 1)) {
				sb.append("),");
			} else {
				sb.append(")");
			}
			
		}
		// System.out.println("create��SQLΪ��" + sb.toString());

		return sb.toString();
	}

	/*
	 * �ж��Ƿ�Ϊ����
	 * 
	 * @param str ������ַ���
	 * 
	 * @return ����������true,���򷵻�false
	 */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]+");
		return pattern.matcher(str).matches();
	}

	/*
	 * �ж��Ƿ�Ϊ������������double��float
	 * 
	 * @param str ������ַ���
	 * 
	 * @return �Ǹ���������true,���򷵻�false
	 */
	public static boolean isDouble(String str) {
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}

	/*
	 * ����DBResources��װ�ķ������������ݿ⣬ɾ������
	 */
//	public static void deleteDateByXls(String DBPort, String DBName,
//			String tableName, String filePath, String dataSheetName,
//			String deleteSheetName) throws Exception {
//
//		Connection con = DBResources.connect(DBPort, DBName);
//		// ��ȡҪ�����sql����б������ж���
//		ArrayList<String> sqlLists = new ArrayList<String>();
//		sqlLists = deleteSql(tableName, filePath, dataSheetName,
//				deleteSheetName);
//
//		// ִ���������
//		for (String sql : sqlLists) {
//			DBResources.executeUpdate(con, sql);
//		}
//		con.close();
//	}
//
//	/*
//	 * ����DBResources��װ�ķ������������ݿ�,������������
//	 */
//
//	public static void creatTestData(String DBPort, String DBName,
//			String tableName, String filePath, String dataSheetName,
//			String deleteSheetName) {
//		try {
//			Connection con = DBResources.connect(DBPort, DBName);
//			// ��ȡexcel�ļ�����ȡinsert sql
//			String createSql = createSql(tableName, filePath, dataSheetName);// "TestCaseData/promoCard.xls"
//
//			// �����������ǰҪ�����sql���
//			ArrayList<String> sqlLists = new ArrayList<String>();
//			sqlLists = deleteSql(tableName, filePath, dataSheetName,
//					deleteSheetName);
//			// ��ִ���������
//			for (String sql : sqlLists) {
//				DBResources.executeUpdate(con, sql);
//			}
//
//			// ��ִ�в������
//			DBResources.executeUpdate(con, createSql);
//			con.close();
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}

}