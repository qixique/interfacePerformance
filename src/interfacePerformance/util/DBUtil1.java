package interfacePerformance.util;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;

/*
 * 数据库处理类
 */
public class DBUtil1 {

	// oracle驱动:oracle.jdbc.driver.OracleDriver
	// mysql驱动:com.mysql.jdbc.Driver
	// sql server驱动：com.microsoft.jdbc.sqlserver.SQLServerDriver
	// DB2驱动:com.ibm.db2.jdbc.app.DB2Driver
	// Informix驱动：com.informix.jdbc.IfxDriver
	// Sybase驱动：com.sybase.jdbc.SybDriver
	// PostgreSQL驱动：org.postgresql.Driver

	private final String driver = "com.mysql.jdbc.Driver"; // JDBC驱动
	private String url; // 数据库地址
	private String user; // 数据库用户名
	private String password; // 数据库密码

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException, UnsupportedEncodingException {
		// 配置mysql服务器信息
		String url = "jdbc:mysql://192.168.20.71:9001/tuanmei";
		String user = "dev";
		String password = "jmdevcd";
		// 实例化db对象
		DBUtil1 dbUtil1 = new DBUtil1( url, user, password);

		// 创建要查询的sql语句
		String sql = " SELECT CONCAT(\"http://cart.jumeicd.com/i/cart/new_items/\",b.sku_no,\",\",a.hash_id,\",1\")AS \"url\","
				+ "a.category,a.site,b.sku_no,a.hash_id,a.stocks,a.buyer_number,a.real_buyer_number,a.start_time,a.end_time,"
				+ "a.tipped_time FROM tuanmei.tuanmei_deals a,inventory.inventory_warehouse_company b "
				+ "WHERE a.end_time > UNIX_TIMESTAMP()AND a.start_time < UNIX_TIMESTAMP() "
				+ "AND a.buyer_number + a.real_buyer_number < a.stocks AND a.site = ''AND a.product_id = b.product_id "
				+ "AND a.category = \"product\" LIMIT 80;";

		try {
			// 调用sql查询方法，获取结果
			ResultSet resultSet = dbUtil1.executeQuery(
					dbUtil1.statement(dbUtil1.connect()), sql);
			// 解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
			while (resultSet.next()) {
				// 转换成字节
				byte[] tempString = resultSet.getBytes("url");
				// 转换成utf-8
				String resultString = new String(tempString, "utf-8");
				System.out.println(resultString + "\n");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/*
	 * 构造函数
	 */
	public DBUtil1( String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	/*
	 * 获得数据库连接对象
	 */
	public Connection connect() throws ClassNotFoundException, SQLException {

		// 加载数据库驱动程序
		try {
			Class.forName(this.driver);
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new ClassNotFoundException("加载数据库驱动程序失败", e);
		}

		// 建立连接对象
		try {
			return DriverManager.getConnection(this.url, this.user,
					this.password);
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException("创建连接对象失败", e);
		}
	}

	/*
	 * 获取statement对象
	 */
	public Statement statement(Connection connection) throws SQLException {
		return connection.createStatement();
	}

	/*
	 * 执行SQL查询语句，返回查询结果
	 * 
	 * @param statement: statement对象
	 * 
	 * @param sql: sql查询语句
	 * 
	 * @return ResultSet
	 */
	public ResultSet executeQuery(Statement statement, String sql)
			throws SQLException {
		try {
			ResultSet resultSet = statement.executeQuery(sql);

			return resultSet;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException("执行sql失败", e);
		}
	}

	/*
	 * 提交update语句
	 * 
	 * @param statement: statement对象
	 * 
	 * @param sql: sql查询语句
	 * 
	 * @return int
	 */
	public int executeUpdate(Statement statement, String sql)
			throws SQLException {
		try {
			int resultSet = statement.executeUpdate(sql);

			return resultSet;
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException("执行sql失败", e);
		}
	}

	/*
	 * 解析查询结果，转换成字符串数组进行返回。
	 * 
	 * @param resultSet: 结果对象
	 * 
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getSqlResult(ResultSet resultSet)
			throws SQLException {
		ArrayList<String> resultList = new ArrayList<String>();

		for (int i = 1; i < resultSet.getFetchSize(); i++) {
			while (resultSet.next()) {
				resultList.add(resultSet.getString(i));
			}
		}
		return resultList;
	}

	/*
	 * 依次关闭ResultSet、Statement、Connection对象
	 * 
	 * @param resultset:
	 * 
	 * @param statement:
	 * 
	 * @param connection:
	 */
	public void close(ResultSet resultSet, Statement statement,
			Connection connection) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
			resultSet = null;
		}

		if (statement != null) {
			statement.close();
			statement = null;
		}

		if (connection != null) {
			connection.close();
			statement = null;
		}
	}
}