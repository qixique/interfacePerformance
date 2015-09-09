package interfacePerformance.util;



import java.sql.*;
import java.util.ArrayList;



/*
 * 数据库处理类
 */
public class DBUtil {
	
	//oracle驱动:oracle.jdbc.driver.OracleDriver
	//mysql驱动:com.mysql.jdbc.Driver
	//sql server驱动：com.microsoft.jdbc.sqlserver.SQLServerDriver
	//DB2驱动:com.ibm.db2.jdbc.app.DB2Driver
	//Informix驱动：com.informix.jdbc.IfxDriver
	//Sybase驱动：com.sybase.jdbc.SybDriver
	//PostgreSQL驱动：org.postgresql.Driver
	
	private String driver; //JDBC驱动
	private String url; //数据库地址
	private String user; //数据库用户名
	private String password; //数据库密码
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//配置mysql服务器信息
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.20.71:3306/jumei_cart";
		String user = "dev";
		String password = "jmdevcd";
		
		//实例化db对象
		DBUtil dbUtil = new DBUtil(driver, url, user, password);
		
		//创建要查询的sql语句
		String sql = "select * from batch_trade_orders where order_id = 2";
		//调用sql查询方法，获取结果
		ResultSet resultSet = dbUtil.executeQuery(dbUtil.connect(), sql);
		//解析结果。判断是否有下一行数，如果有，循环读取，并根据字段名取出对应的该字段值
		while (resultSet.next()) {
			System.out.println("batch_trade_number:" + resultSet.getString("batch_trade_number"));
			System.out.println("is_order_processed:" + resultSet.getString("is_order_processed"));
		}
		
	}
	
	/*
	 * 构造函数
	 */
	public  DBUtil(String driver,String url,String user,String password) {
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	/*
	 * 获得数据库连接对象
	 */
	public Statement connect() throws ClassNotFoundException, SQLException {
		
		//加载数据库驱动程序
		try {
			Class.forName(this.driver);			
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			throw new ClassNotFoundException("加载数据库驱动程序失败",e);
		}
		
		//建立连接对象
		try {
			Connection connection = DriverManager.getConnection(this.url, this.user, this.password);
			return connection.createStatement();			
		} catch (SQLException e) {
			// TODO: handle exception
			
			System.out.println("--"+url+"--"+user+"--"+password);
			throw new SQLException("创建连接对象失败",e);
		}
	}
	
	
	/*
	 * 执行SQL查询语句，返回查询结果
	 * @param statement: statement对象
	 * @param sql: sql查询语句
	 * @return ResultSet
	 */
	public ResultSet executeQuery(Statement statement,String sql) throws SQLException {
		try {
			ResultSet resultSet = statement.executeQuery(sql);
			
			return resultSet;			
		} catch (SQLException e) {
			// TODO: handle exception
			
			throw new SQLException("执行sql失败", e);
		}
		finally
		{
			statement.close();
		}
	}
	
	/*
	 * 提交update语句
	 * @param statement: statement对象
	 * @param sql: sql查询语句
	 * @return int
	 */
	public int executeUpdate(Statement statement,String sql) throws SQLException {
		try {
			int resultSet = statement.executeUpdate(sql);
			
			return resultSet;		
		} catch (SQLException e) {
			// TODO: handle exception
			throw new SQLException("执行sql失败", e);
		}
		finally
		{
			statement.close();
		}
	}
	
	/*
	 * 解析查询结果，转换成字符串数组进行返回。
	 * @param resultSet: 结果对象
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getSqlResult(ResultSet resultSet) throws SQLException{
		ArrayList<String> resultList = new ArrayList<String>();
		
		for(int i = 1;i < resultSet.getFetchSize(); i++){
			while (resultSet.next()) {
				resultList.add(resultSet.getString(i));
			}
		}
		return resultList;
	}
	
	
	/*
	 * 依次关闭ResultSet、Statement、Connection对象
	 * @param resultset: 
	 * @param statement: 
	 * @param connection:
	 */
	public void close(ResultSet resultSet,Statement statement,Connection connection) throws SQLException {
		if(resultSet != null){
			resultSet.close();
			resultSet = null;
		}
		
		if(statement != null){
			statement.close();
			statement = null;
		}
		
		if(connection != null){
			connection.close();
			statement = null;
		}
	}
}