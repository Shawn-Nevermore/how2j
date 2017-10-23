package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.how2j.character.Hero;

/**
 * 常规try-catch-finally 方式的jdbc工具
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
	 * 经典连接方式
	 * 
	 * 使用这种方式存在的问题：
		（1）数据库连接，使用时建立连接，不使用就释放，对数据连接释放频繁，造成数据库资源浪费，影响性能    
			解决办法：数据库连接池
		（2）sql语句硬编码，不利于sql语句修改的维护，   设想：将sql语句配置到xml文件中，sql变化，不需要重新编译java代码
		（3）设置参数硬编码，设想，将sql语句占位符和参数全部配置到xml中
		（4）从resultSet中比那里结果集数据，存在硬编码  设想，将查询的结果集自动映射为java对象
		（5）对程序员来讲，更加关心的是sql语句编写和参数设置这部分内容，连接的建立和释放并不需要每次都关心， 设想：程序员可以更多的关注sql语句编写和参数设置问题
	 */
	private static void connClassic() {
		Connection c = null;
		Statement s = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 建立与数据库的Connection连接
			// 这里需要提供：
			// 数据库所处于的ip:127.0.0.1 (本机)
			// 数据库的端口号： 3306 （mysql专用端口号）
			// 数据库名称 how2java
			// 编码方式 UTF-8
			// 账号 root
			// 密码 admin

			c = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/how2java?characterEncoding=UTF-8", "root",
					"admin");

			s = c.createStatement();

			// 准备sql语句
			// 注意： 字符串要用单引号'
			String sql = "insert into hero values(null," + "'Timo'" + "," + 313.0f + "," + 50 + ")";
			s.execute(sql);

			System.out.println("执行插入语句成功");

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
