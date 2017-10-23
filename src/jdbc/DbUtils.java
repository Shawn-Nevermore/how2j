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
	 * �������
	 */
	private static void transactionManagement() {
		try(
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
			){
			
			// �������ǰ����
            // �������еĶ��������Ҫô���ɹ���Ҫô��ʧ��
  
            conn.setAutoCommit(false);
  
            // ��Ѫ��SQL
            String sql1 = "update hero set hp = hp +1 where id = 22";
            stmt.execute(sql1);
  
            // ��Ѫ��SQL
            // ��С��д��д���� updata(����update)
  
            String sql2 = "updata hero set hp = hp -1 where id = 22";
            stmt.execute(sql2);
  
            // �ֶ��ύ
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
			System.out.println("���ݿ��Ʒ���ƣ�" + dbmd.getDatabaseProductName());
			System.out.println("���ݿ��Ʒ�汾��" + dbmd.getDatabaseProductVersion());
			System.out.println("�����汾��" + dbmd.getDriverName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * execute��executeUpdate
	 * 
	 * ��ͬ�㣺������ɾ��
	 */
	private static void execute_executeUpdate() {
		try(
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt = conn.createStatement();
				){
			// ��ͬ1��execute����ִ�в�ѯ���
            // Ȼ��ͨ��getResultSet���ѽ����ȡ����
			String sqlSelect = "select * from hero limit 20, 10";
			stmt.executeQuery(sqlSelect);
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				int id = rs.getInt("id"); // ����ʹ���ֶ���
				String name = rs.getString(2);// Ҳ����ʹ���ֶ����
				float hp = rs.getFloat("hp");
				int damage = rs.getInt("damage");

//				System.out.printf("%d\t%-15s%-6.2f\t%d%n", id, name, hp, damage);
			}				
			
			// executeUpdate����ִ�в�ѯ���
            // s.executeUpdate(sqlSelect);

            // ��ͬ2:
            // execute����boolean���ͣ�true��ʾִ�е��ǲ�ѯ��䣬false��ʾִ�е���insert,delete,update�ȵ�
			System.out.println(stmt.execute(sqlSelect));
			
			// executeUpdate���ص���int����ʾ�ж����������ܵ���Ӱ��
            String sqlUpdate = "update Hero set hp = 300 where id < 100";
            int number = stmt.executeUpdate(sqlUpdate);
            System.out.println(number);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ʹ��PreparedStatement��ִ��sql��䣬��������
	 * 
	 * �ŵ㣺
	 * 	1.ʹ�ò������ã��ɶ��Ժã����׷���
	 * 	2.����Ԥ������ƣ����ܱ�Statement����
	 * 		1)Statementִ��n�Σ���Ҫn�ΰ�SQL��䴫�䵽���ݿ�ˣ����ݿ�Ҫ��ÿһ������SQL�����б��봦��
	 * 		2)PreparedStatement ִ��n�Σ�ֻ��Ҫ1�ΰ�SQL��䴫�䵽���ݿ�ˣ����ݿ�Դ�?��SQL����Ԥ����
            // ÿ��ִ�У�ֻ��Ҫ������������ݿ��
            // 1. ���紫������Statement��С
            // 2. ���ݿⲻ��Ҫ�ٽ��б��룬��Ӧ����
	 * 	3.���Է�ֹSQLע��ʽ����
	 * 		PreparedStatement��һЩ����������ŵ�sql��������ת�壬
	 * 		select * from hero where name='����\'or1=\'1'
	 * 		�����or 1 = 1 �ͻ���Ч
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
	 * ʹ��Statement��ִ��sql��䣬�������ӡ�
	 * 
	 * ȱ�㣺��Ҫsql����ַ���ƴ�ӣ��ȽϷ���
	 */
	private static void stmtConn() {
		try (Connection c = DriverManager.getConnection(DB_URL, USER, PASS); Statement s = c.createStatement();) {

			// String sql = "select * from hero";
			String sql = list(3, 2);

			addRandomHero(s);

			// ִ�в�ѯ��䣬���ѽ�������ظ�ResultSet

			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id"); // ����ʹ���ֶ���
				String name = rs.getString(2);// Ҳ����ʹ���ֶ����
				float hp = rs.getFloat("hp");
				int damage = rs.getInt("damage");

				System.out.printf("%d\t%-15s%-6.2f\t%d%n", id, name, hp, damage);
			}

			// ��һ��Ҫ������ر�ReultSet����ΪStatement�رյ�ʱ�򣬻��Զ��ر�ResultSet
			// rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������50��Ӣ��
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
	 * ��ҳ��ѯ����
	 * @param start	��ʼֵ
	 * @param count	��Ŀ��
	 * @return 
	 */
	public static String list(int start, int count) {
		return "select * from hero limit " + start + "," + count;
	}

}
