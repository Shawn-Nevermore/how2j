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
public class HeroDAO {

	public HeroDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
	}

	/**
	 * ORM-�����е�Hero���ݲ�ѯ������ת��ΪHero����󣬷���һ�������з���
	 * @return
	 */
	public List<Hero> list(int start, int count) {
		String sql = "select * from Hero order by id desc limit ?,?";
		List<Hero> heros = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, start);
			pstmt.setInt(2, count);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Hero h = new Hero();
				h.id = rs.getInt("id");
				h.setName(rs.getString("name"));
				h.setHp(rs.getFloat("hp"));
				h.setDamage(rs.getInt("damage"));
				heros.add(h);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return heros;
	}

	/**
	 * ORM-����id����һ��Hero����
	 * @param id
	 * @return
	 */
	public Hero get(int id) {
		Hero h = new Hero(id, null, 0.0f, 0);

		try (Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				Statement stmt = conn.createStatement();) {
			String sql = "select * from hero where id=" + id;
			ResultSet rs = stmt.executeQuery(sql);

			// ��Ϊid��Ψһ�ģ�ResultSet���ֻ����һ����¼
			// ����ʹ��if����while
			if (rs.next()) {
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
	public void add(Hero h) {
		String sql = "insert into hero values(null,?,?,?)";

		try (Connection conn = DriverManager.getConnection(DbUtils.DB_URL, DbUtils.USER, DbUtils.PASS);
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setString(1, h.getName());
			pstmt.setFloat(2, h.getHp());
			pstmt.setInt(3, h.getDamage());

			pstmt.execute();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				int id = rs.getInt(1);
				h.id = id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������Ϣ
	 * @param hero
	 */
	public void update(Hero hero) {

		String sql = "update hero set name= ?, hp = ? , damage = ? where id = ?";
		try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);) {

			ps.setString(1, hero.getName());
			ps.setFloat(2, hero.getHp());
			ps.setInt(3, hero.getDamage());
			ps.setInt(4, hero.id);

			ps.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

}
