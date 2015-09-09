package interfacePerformance.util;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;



public class QueryDB {
	
	private String url="jdbc:mysql://192.168.20.71:9001/jumei_product?useUnicode=true&characterEncoding=UTF-8";
//	private String url="jdbc:mysql://192.168.20.71:9001/tuanmei?useUnicode=true&characterEncoding=GBK";
//	private String url="jdbc:mysql://192.168.20.71:9002/tuanmei?useUnicode=true&characterEncoding=GBK";
	public int updateDb(String url,String sql,String user,String password) throws ClassNotFoundException, SQLException
	{
		String driver = "com.mysql.jdbc.Driver";
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
//		byte[] bytesql=sql.getBytes();
//		String sql1=new String(bytesql,"utf8");
		int r=dbUtil.executeUpdate(dbUtil.connect(), sql);
		return r;
	}
	public String queryDb(String sql, String parm)
			throws ClassNotFoundException, SQLException {
		// 配置mysql服务器信息
		String driver = "com.mysql.jdbc.Driver";
		String user = "dev";
		String password = "jmdevcd";
		String sqlResult = "";
		// 实例化db对象
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
		// 创建要查询的sql语句
		// sql = "select * from batch_trade_orders where order_id = 2";
		// 调用sql查询方法，获取结果
		
		ResultSet resultSet = dbUtil.executeQuery(dbUtil.connect(), sql);
		// 解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
		while (resultSet.next()) {
			sqlResult = resultSet.getString(parm);
		}
		return sqlResult;
	}

	public String queryDb1(String sql, String parm)
			throws ClassNotFoundException, SQLException {
		// 配置mysql服务器信息
		String driver = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://192.168.20.71:9001/jumei_product?useUnicode=true&characterEncoding=UTF-8";
		String user = "dev";
		String password = "jmdevcd";
		String sqlResult = "";
		// 实例化db对象
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
		// 创建要查询的sql语句
		// sql = "select * from batch_trade_orders where order_id = 2";
		// 调用sql查询方法，获取结果
		ResultSet resultSet = dbUtil.executeQuery(dbUtil.connect(), sql);
		// 解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
		while (resultSet.next()) {
			sqlResult += resultSet.getString(parm) + ",";
		}
		return sqlResult;
	}

	public String executeQueryByColumn(String sql, String parm)
			throws ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		// 配置mysql服务器信息
		String driver = "com.mysql.jdbc.Driver";
//		String url = "jdbc:mysql://192.168.20.71:9001/jumei_product?useUnicode=true&characterEncoding=UTF-8";
		String user = "dev";
		String password = "jmdevcd";
		// 实例化db对象
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
		// 创建要查询的sql语句
		// sql = "select * from batch_trade_orders where order_id = 2";
		// 调用sql查询方法，获取结果
		ResultSet resultSet = dbUtil.executeQuery(dbUtil.connect(), sql);
		// 解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
		String result = "";
		while (resultSet.next()) {
			// sqlResult +=resultSet.getString(parm)+",";
			byte[] tempString = resultSet.getBytes(parm);
			// 转换成utf-8
			if (result != "")
				result += "," + new String(tempString, "utf-8");
			else
				result = new String(tempString, "utf-8");
		}
		return result;
	}

	public String executeQueryByColumn(String sql, String parm,
			String hostandport) throws ClassNotFoundException, SQLException,
			UnsupportedEncodingException {
		// 配置mysql服务器信息
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://" + hostandport
				+ "/tuanmei?useUnicode=true&characterEncoding=UTF-8";
		String user = "dev";
		String password = "jmdevcd";
		// 实例化db对象
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
		// 创建要查询的sql语句
		// sql = "select * from batch_trade_orders where order_id = 2";
		// 调用sql查询方法，获取结果
		ResultSet resultSet = dbUtil.executeQuery(dbUtil.connect(), sql);
		// 解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
		String result = "";
		while (resultSet.next()) {
			// sqlResult +=resultSet.getString(parm)+",";
			byte[] tempString = resultSet.getBytes(parm);
			// 转换成utf-8
			result += new String(tempString, "utf-8") + ",";
		}
		resultSet.close();
		return result;
	}
}