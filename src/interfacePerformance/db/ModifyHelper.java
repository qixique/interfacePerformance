package interfacePerformance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库修改辅助类
 * @author Lily Hu
 *
 */
public class ModifyHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	
	/**
	 * 
	 * @param configMap 包含连接参数的map对象
	 * @return 一个新的数据库连接
	 */
	public static Connection getPrivateConn(String url, String user,String pwd){
		try {
			Class.forName(driver);
			return DriverManager.getConnection(url,user,pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行数据库修改
	 * @param url
	 * @param user
	 * @param pwd
	 * @param sql
	 * @return
	 */
	public static boolean update(String url, String user,String pwd,String sql){
		boolean result = true;
		String[] sqlArray = sql.split(";");
		if(sqlArray.length <= 0){
			return false;
		}
		Connection conn = getPrivateConn(url, user, pwd);
		if(conn == null) return false;
		try {
			Statement st = conn.createStatement();
			for(String s : sqlArray)
				st.addBatch(s);
			st.executeBatch();
//			System.out.println(resultSet[0]);
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return result;
	}
}
