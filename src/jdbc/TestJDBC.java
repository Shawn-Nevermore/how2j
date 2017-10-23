package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.how2j.character.Hero;

/**
 * ����try-catch-finally ��ʽ��jdbc����
 * @author Shawn-Nevermore
 *
 */
public class TestJDBC {

	public static void main(String[] args) {

//		connClassic();
		
/*		Hero h = new Hero(100, "Riki", 540, 96);
		HeroORM.add(h);
		
		Hero getH = HeroORM.get(100);
		System.out.printf("%d\t%-12s%.2f\t%d", getH.id, getH.getName(), getH.getHp(), getH.getDamage());
*/		
		
		List<Hero> list = HeroORM.list();
		for(Hero h : list ) {
			System.out.printf("%d\t%-12s%.2f\t%d%n", h.id, h.getName(), h.getHp(), h.getDamage());
		}
	}

	/**
	 * �������ӷ�ʽ
	 * 
	 * ʹ�����ַ�ʽ���ڵ����⣺
		��1�����ݿ����ӣ�ʹ��ʱ�������ӣ���ʹ�þ��ͷţ������������ͷ�Ƶ����������ݿ���Դ�˷ѣ�Ӱ������    
			����취�����ݿ����ӳ�
		��2��sql���Ӳ���룬������sql����޸ĵ�ά����   ���룺��sql������õ�xml�ļ��У�sql�仯������Ҫ���±���java����
		��3�����ò���Ӳ���룬���룬��sql���ռλ���Ͳ���ȫ�����õ�xml��
		��4����resultSet�б������������ݣ�����Ӳ����  ���룬����ѯ�Ľ�����Զ�ӳ��Ϊjava����
		��5���Գ���Ա���������ӹ��ĵ���sql����д�Ͳ��������ⲿ�����ݣ����ӵĽ������ͷŲ�����Ҫÿ�ζ����ģ� ���룺����Ա���Ը���Ĺ�עsql����д�Ͳ�����������
	 */
	private static void connClassic() {
		Connection c = null;
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// ���������ݿ��Connection����
			// ������Ҫ�ṩ��
			// ���ݿ������ڵ�ip:127.0.0.1 (����)
			// ���ݿ�Ķ˿ںţ� 3306 ��mysqlר�ö˿ںţ�
			// ���ݿ����� how2java
			// ���뷽ʽ UTF-8
			// �˺� root
			// ���� admin

			c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8", "root",
					"admin");

			s = c.createStatement();

			// ׼��sql���
			// ע�⣺ �ַ���Ҫ�õ�����'
			String sql = "insert into hero values(null," + "'Timo'" + "," + 313.0f + "," + 50 + ")";
			s.execute(sql);

			System.out.println("ִ�в������ɹ�");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
