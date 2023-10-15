import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "sqlid";
	String pw = "sqlpw";

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MenuDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // 생성자

	public ArrayList<MenuBean> getAllMenu(String id, String storeName) {
		ArrayList<MenuBean> lists = new ArrayList<MenuBean>();

		try {
			String sql = "select * from menu where id = ? and storeName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, storeName);

			rs = ps.executeQuery();
			while (rs.next()) {
				MenuBean sb = new MenuBean(rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6),
						rs.getString(6));

				lists.add(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	public ArrayList<MenuBean> getAllMenu(String storeName) {
		ArrayList<MenuBean> lists = new ArrayList<MenuBean>();

		try {
			String sql = "select * from menu where storeName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, storeName);

			rs = ps.executeQuery();
			while (rs.next()) {
				MenuBean sb = new MenuBean(rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6),
						rs.getString(6));

				lists.add(sb);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}

	public int insertData(MenuBean meb) {
		int cnt = -1;

		try {
			String sql = "insert into menu values(?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, meb.getId());
			ps.setString(2, meb.getStoreName());
			ps.setString(3, meb.getMenuName());
			ps.setInt(4, meb.getPrice());
			ps.setInt(5, meb.getSidedishCnt());
			ps.setString(6, meb.getTaste());
			ps.setString(7, meb.getMainIngredient());

			cnt = ps.executeUpdate();
			System.out.println("cnt:" + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cnt;
	}

	public int deleteData(MenuBean meb) {
		int cnt = -1;
		try {
			String sql = "delete menu where id = ? and storeName = ? and menuName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, meb.getId());
			ps.setString(2, meb.getStoreName());
			ps.setString(3, meb.getMenuName());

			cnt = ps.executeUpdate();
			System.out.println("cnt:" + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cnt;
	}

	public int updateData(MenuBean meb, String menuName) {
		int cnt = -1;

		try {
			String sql = "update menu set menuName = ?, price = ?, sidedishCnt = ?, taste = ?,  mainIngredient =? where id = ? and storeName =? and menuName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, meb.getMenuName());
			ps.setInt(2, meb.getPrice());
			ps.setInt(3, meb.getSidedishCnt());
			ps.setString(4, meb.getTaste());
			ps.setString(5, meb.getMainIngredient());
			ps.setString(6, meb.getId());
			ps.setString(7, meb.getStoreName());
			ps.setString(8, menuName);

			cnt = ps.executeUpdate();
			System.out.println("cnt:" + cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cnt;
	}

}
