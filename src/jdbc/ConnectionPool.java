package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * ���ݿ����ӳ�
 * ���裺
 * 	1. ConnectionPool() ���췽��Լ����������ӳ�һ���ж�������
	2. ��init() ��ʼ�������У�������size�����ӡ� ע�⣬���ﲻ��ʹ��try-with-resource����
		�Զ��ر����ӵķ�ʽ����Ϊ����ǡǡ��Ҫ���ֲ��ر�״̬��������ѭ��ʹ��
	3. getConnection�� �ж��Ƿ�Ϊ�գ�����ǿյľ�wait�ȴ�������ͽ���һ�����ӳ�ȥ
	4. returnConnection�� ��ʹ����Ϻ󣬹黹������ӵ����ӳأ������ڹ黹��Ϻ�
		����notifyAll��֪ͨ��Щ�ȴ����̣߳����µ����ӿ��Խ����ˡ�
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

		// ���ó��������һ������
		Connection conn = cs.remove(0);

		return conn;

	}
	
	public synchronized void returnConnection(Connection c) {
		cs.add(c);
		this.notifyAll();
	}
}
