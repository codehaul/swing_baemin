import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "sqlid";
	String pw = "sqlpw";

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public StoreDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // 생성자

	public ArrayList<StoreBean> getAllStore(String id) {
		ArrayList<StoreBean> lists = new ArrayList<StoreBean>();

		try {
			String sql = "select * from store where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();
			while (rs.next()) {
				StoreBean sb = new StoreBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getString(8));

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
	
	public ArrayList<StoreBean> getAllStore() {
		ArrayList<StoreBean> lists = new ArrayList<StoreBean>();

		try {
			String sql = "select * from store";
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			while (rs.next()) {
				StoreBean sb = new StoreBean(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6), rs.getInt(7), rs.getString(8));

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

	public int insertData(StoreBean sb) {
		int cnt = -1;

		try {
			String sql = "insert into store values(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sb.getId());
			ps.setString(2, sb.getStoreName());
			ps.setString(3, sb.getFoodType());
			ps.setInt(4, sb.getPriceAvg());
			ps.setString(5, sb.getStoreLocation());
			ps.setString(6, sb.getCallNum());
			ps.setInt(7, sb.getReviewCnt());
			ps.setString(8, sb.getImg());

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

	public int deleteData(StoreBean sb) {

		int cnt = -1;
		try {
			String sql = "delete store where id = ? and storeName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sb.getId());
			ps.setString(2, sb.getStoreName());

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

	public void exit() {
		if (conn != null) {
			try {
				System.out.println("DB접속 끊기");
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}


	public int updateStoreImage(StoreBean sb) {
		int cnt = -1;
		try {
			String sql = "update store set img = ? where id = ? and storeName = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sb.getImg());
			ps.setString(2, sb.getId());
			ps.setString(3, sb.getStoreName());
			

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

	public int updateData(StoreBean sb, String storeName) {
		int cnt = -1;

		try {
			String sql = "update store set storeName = ?, foodType = ?, priceAvg = ?, storeLocation = ?,  callNum = ?, reviewCnt = ? where id = ? and storeName =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, sb.getStoreName());
			ps.setString(2, sb.getFoodType());
			ps.setInt(3, sb.getPriceAvg());
			ps.setString(4, sb.getStoreLocation());
			ps.setString(5, sb.getCallNum());
			ps.setInt(6, sb.getReviewCnt());
			ps.setString(7, sb.getId());
			ps.setString(8, storeName);

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
