package jdbc;

import java.sql.*;

import com.how2j.character.Hero;

public class DbUtils {

	/**
	 * JDBC driver name and database URL
	 */
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String DB_URL = "jdbc:mysql://localhost/how2java?characterEncoding=UTF-8&autoReconnect=true&useSSL=false";

	/**
	 * Database credentials
	 */
	public static final String USER = "root";
	public static final String PASS = "admin";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

//		stmtConn();
		
//		pstmtConn();
		
//		execute_executeUpdate();
		
//		metaData();

//		transactionManagement();
		
		try(
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
			){
		
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Goodbye.");
	}

		
	/**
	 * 事务管理
	 */
	private static void transactionManagement() {
		try(
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
			){
			
			// 有事务的前提下
            // 在事务中的多个操作，要么都成功，要么都失败
  
            conn.setAutoCommit(false);
  
            // 加血的SQL
            String sql1 = "update hero set hp = hp +1 where id = 22";
            stmt.execute(sql1);
  
            // 减血的SQL
            // 不小心写错写成了 updata(而非update)
  
            String sql2 = "updata hero set hp = hp -1 where id = 22";
            stmt.execute(sql2);
  
            // 手动提交
            conn.commit();		
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void metaData() {
		try(
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			){
			DatabaseMetaData dbmd = conn.getMetaData();
			System.out.println("数据库产品名称：" + dbmd.getDatabaseProductName());
			System.out.println("数据库产品版本：" + dbmd.getDatabaseProductVersion());
			System.out.println("驱动版本：" + dbmd.getDriverName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * execute和executeUpdate
	 * 
	 * 相同点：都能增删改
	 */
	private static void execute_executeUpdate() {
		try(
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				){
			// 不同1：execute可以执行查询语句
            // 然后通过getResultSet，把结果集取出来
			String sqlSelect = "select * from hero limit 20, 10";
			stmt.executeQuery(sqlSelect);
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				int id = rs.getInt("id"); // 可以使用字段名
				String name = rs.getString(2);// 也可以使用字段序号
				float hp = rs.getFloat("hp");
				int damage = rs.getInt("damage");

//				System.out.printf("%d\t%-15s%-6.2f\t%d%n", id, name, hp, damage);
			}				
			
			// executeUpdate不能执行查询语句
            // s.executeUpdate(sqlSelect);

            // 不同2:
            // execute返回boolean类型，true表示执行的是查询语句，false表示执行的是insert,delete,update等等
			System.out.println(stmt.execute(sqlSelect));
			
			// executeUpdate返回的是int，表示有多少条数据受到了影响
            String sqlUpdate = "update Hero set hp = 300 where id < 100";
            int number = stmt.executeUpdate(sqlUpdate);
            System.out.println(number);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用PreparedStatement来执行sql语句，建立连接
	 * 
	 * 优点：
	 * 	1.使用参数设置，可读性好，不易犯错
	 * 	2.具有预编译机制，性能比Statement更快
	 * 		1)Statement执行n次，需要n次把SQL语句传输到数据库端，数据库要对每一次来的SQL语句进行编译处理
	 * 		2)PreparedStatement 执行n次，只需要1次把SQL语句传输到数据库端，数据库对带?的SQL进行预编译
            // 每次执行，只需要传输参数到数据库端
            // 1. 网络传输量比Statement更小
            // 2. 数据库不需要再进行编译，相应更快
	 * 	3.可以防止SQL注入式攻击
	 * 		PreparedStatement对一些含有特殊符号的sql语句进行了转义，
	 * 		select * from hero where name='盖伦\'or1=\'1'
	 * 		这里的or 1 = 1 就会无效
	 */
	private static void pstmtConn() {
		String sql = "insert into hero values(null, ?, ?, ?)";
		try (
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			pstmt.setString(1, "Lion");
			pstmt.setFloat(2, 480);
			pstmt.setInt(3, 66);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 使用Statement来执行sql语句，建立连接。
	 * 
	 * 缺点：需要sql语句字符串拼接，比较繁琐
	 */
	private static void stmtConn() {
		try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS); Statement s = c.createStatement();) {

			// String sql = "select * from hero";
			String sql = list(3, 2);

			addRandomHero(s);

			// 执行查询语句，并把结果集返回给ResultSet

			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id"); // 可以使用字段名
				String name = rs.getString(2);// 也可以使用字段序号
				float hp = rs.getFloat("hp");
				int damage = rs.getInt("damage");

				System.out.printf("%d\t%-15s%-6.2f\t%d%n", id, name, hp, damage);
			}

			// 不一定要在这里关闭ReultSet，因为Statement关闭的时候，会自动关闭ResultSet
			// rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 随机添加50个英雄
	 * @param s
	 * @throws SQLException
	 */
	private static void addRandomHero(Statement s) throws SQLException {
		String sql = null;
		for (int i = 1; i <= 50; i++) {
			sql = "insert into hero values("
					+ "null, "
					+ "'DemoHero" + i + "', "
					+ (float) Math.round(Math.random() * (1000 - 380) + 380) + ", "
					+ (int) Math.round(Math.random() * (130 - 60) + 60) + ")";
			s.execute(sql);
		}
	}

	/**
	 * 分页查询功能
	 * @param start	起始值
	 * @param count	条目数
	 * @return 
	 */
	public static String list(int start, int count) {
		return "select * from hero limit " + start + "," + count;
	}

}
