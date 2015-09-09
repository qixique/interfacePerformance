package interfacePerformance.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ���ݿ��ѯ������
 * @author Lily Hu
 *
 */
public class QueryHelper {
	private static final String driver = "com.mysql.jdbc.Driver";
	
	/**
	 * 
	 * @param configMap �������Ӳ�����map����
	 * @return һ���µ����ݿ�����
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
	 * ִ�в�ѯ��������¼��sql
	 * @param sql 
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> getManyRecordsFromSQL(String url, String user,String pwd,String sql){
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		try {
			Connection conn = getPrivateConn(url,user,pwd);
			if(conn == null){
				return datas;
			}
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int columns = meta.getColumnCount();
			
			Map<String,Object> data = null;
			while(rs.next()){
				data = new HashMap<String,Object>();
				for(int i = 1; i <= columns; i++){
					data.put(meta.getColumnLabel(i), readFiled(rs,meta.getColumnLabel(i)));
				}
				datas.add(data);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * �����ݿ��������
	 * @param url
	 * @param user
	 * @param pwd
	 * @param sql
	 * @return ��������
	 */
	public static int insertDb(String url, String user,String pwd,String sql){
		if(sql == null || sql.trim().length() == 0)
			return 0;
		String[] insert_sqls = sql.split(";");
		try{
			Connection conn = getPrivateConn(url,user,pwd);
			if(conn == null){
				return 0;
			}
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			for(String s : insert_sqls){
				st.addBatch(s.trim());
			}
			int[] res = st.executeBatch();
			conn.commit();
			st.close();
			conn.close();
			return res.length;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * �����ݿ��������
	 * @param conn
	 * @param sql
	 * @return
	 */
	public int insertDb(Connection conn,String sql){
		if(sql == null || sql.trim().length() == 0)
			return -1;
		String[] insert_sqls = sql.split(";");
		try{
			if(conn == null){
				return -1;
			}
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			for(String s : insert_sqls){
				st.addBatch(s.trim());
			}
			int[] res = st.executeBatch();
			conn.commit();
			st.close();
			return res.length;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * �������������ļ�¼��
	 * @param conn
	 * @param sql
	 * @return
	 */
	public int queryRecordCount(Connection conn, String sql){
		if(sql == null || sql.trim().length() == 0)
			return -1;
		if(conn == null)
			return -1;
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				return rs.getInt(1);
			}
			st.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	/**
	 * �������������ļ�¼��
	 * @param url
	 * @param user
	 * @param pwd
	 * @param sql
	 * @return
	 */
	public static int queryRecordCount(String url, String user,String pwd,String sql){
		if(sql == null || sql.trim().length() == 0)
			return -1;
		try {
			Connection conn = getPrivateConn(url,user,pwd);
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				return rs.getInt(1);
			}
			st.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * �ر����ݿ�����
	 * @param conn
	 * @return
	 */
	public boolean closeConn(Connection conn){
		try{
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * ��ȡ���ݿ��е��ֶΣ�ת����utf-8��ʽ
	 * @param rs
	 * @param fieldName
	 * @return
	 */
	public static String readFiled(ResultSet rs, String fieldName){
		String result = "";
		try {
			byte[] result_byte= rs.getBytes(fieldName);
			if(result_byte == null){
				result = "null";
			}else{
				result = new String(result_byte, "utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
