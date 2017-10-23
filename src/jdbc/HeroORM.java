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
 * 一个从Hero对象到数据库记录的映射
 * 
 * 思考：
 * 	怎样将Connection和PreparedStatement连接资源的操作代码复用
 * @author Shawn-Nevermore
 *
 */
public class HeroORM {
	public static void main(String[] args) {
		
		
		
	}
	
	/**
	 * ORM-把所有的Hero数据查询出来，转换为Hero对象后，放在一个集合中返回
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
	 * ORM-根据id返回一个Hero对象
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

            // 因为id是唯一的，ResultSet最多只能有一条记录
            // 所以使用if代替while
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
	 * ORM-添加一个Hero对象到数据库
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
