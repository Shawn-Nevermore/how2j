package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.ConnectionPool;

public class TestConnectionPool {

	public static void main(String[] args) {
		ConnectionPool pool = new ConnectionPool(3);
		for (int i = 0; i < 100; i++) {
			new WorkThread("Working Thread " + i, pool).start();
		}
	}
	
	static class WorkThread extends Thread {
		private ConnectionPool pool;
		
		public WorkThread(String name, ConnectionPool pool) {
			super(name);
			this.pool = pool;
		}
		
		public void run() {
			Connection conn = pool.getConnection();
			System.out.println(getName() + "\t 获取了一个连接，并开始工作");
			try(Statement stmt = conn.createStatement();){
				
				Thread.sleep(1000);
				stmt.execute("select * from hero");
			} catch (SQLException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pool.returnConnection(conn);
		}
	}
}
