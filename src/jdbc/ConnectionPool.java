package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库连接池
 * 步骤：
 * 	1. ConnectionPool() 构造方法约定了这个连接池一共有多少连接
	2. 在init() 初始化方法中，创建了size条连接。 注意，这里不能使用try-with-resource这种
		自动关闭连接的方式，因为连接恰恰需要保持不关闭状态，供后续循环使用
	3. getConnection， 判断是否为空，如果是空的就wait等待，否则就借用一条连接出去
	4. returnConnection， 在使用完毕后，归还这个连接到连接池，并且在归还完毕后，
		调用notifyAll，通知那些等待的线程，有新的连接可以借用了。
 * @author Shawn-Nevermore
 *
 */
public class ConnectionPool {

	private List<Connection> cs = new ArrayList<>();

	private int size;

	public ConnectionPool(int size) {
		this.size = size;
		init();
	}

	private void init() {
		try {

			Class.forName(DbUtils.JDBC_DRIVER);

			for (int i = 0; i < size; i++) {
				Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				cs.add(conn);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized Connection getConnection() {
		if (cs.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 借用池中最近的一条连接
		Connection conn = cs.remove(0);

		return conn;

	}
	
	public synchronized void returnConnection(Connection c) {
		cs.add(c);
		this.notifyAll();
	}
}
