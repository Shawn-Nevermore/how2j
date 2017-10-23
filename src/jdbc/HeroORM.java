package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.how2j.character.Hero;


/**
 * һ����Hero�������ݿ��¼��ӳ��
 * 
 * ˼����
 * 	������Connection��PreparedStatement������Դ�Ĳ������븴��
 * @author Shawn-Nevermore
 *
 */
public class HeroORM {
	public static void main(String[] args) {
		
		
		
	}
	
	/**
	 * ORM-�����е�Hero���ݲ�ѯ������ת��ΪHero����󣬷���һ�������з���
	 * @return
	 */
	public static List<Hero> list(){
		String sql = "select * from Hero";
		List<Hero> list = new ArrayList<>();
		try(
				Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Hero h = new Hero();
				h.id = rs.getInt("id");
				h.setName(rs.getString("name"));
				h.setHp(rs.getFloat("hp"));
				h.setDamage(rs.getInt("damage"));
				list.add(h);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		
		return list;
	}
	
	/**
	 * ORM-����id����һ��Hero����
	 * @param id
	 * @return
	 */
	public static Hero get(int id) {
		Hero h = new Hero(id, null, 0.0f, 0);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(
				Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				Statement stmt = conn.createStatement();
			){
			String sql = "select * from hero where id="+id;
			ResultSet rs = stmt.executeQuery(sql);

            // ��Ϊid��Ψһ�ģ�ResultSet���ֻ����һ����¼
            // ����ʹ��if����while
			if(rs.next()) {
				h.setName(rs.getString("name"));
				h.setHp(rs.getFloat("hp"));
				h.setDamage(rs.getInt("damage"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return h;
		
	}
	
	/**
	 * ORM-���һ��Hero�������ݿ�
	 * @param h
	 */
	public static void add(Hero h) {
		String sql = "insert into hero values(?,?,?,?)";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(
				Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql);
			){
			
			pstmt.setInt(1, h.id);
			pstmt.setString(2, h.getName());
			pstmt.setFloat(3, h.getHp());
			pstmt.setInt(4, h.getDamage());
			
			pstmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
