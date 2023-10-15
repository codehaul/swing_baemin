import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	String id = "sqlid";
	String pw = "sqlpw";

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	public MemberDao() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // 생성자

	public int insertData(MemberBean mb) {

		int cnt = -1;

		try {
			String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, mb.getId());
			ps.setString(2, mb.getPw());
			ps.setString(3, mb.getName());
			ps.setString(4, mb.getBirth());
			ps.setString(5, mb.getGender());
			ps.setString(6, mb.getEmail());
			ps.setString(7, mb.getPhone());
			ps.setString(8, mb.getMem_role());

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

	} // insertData

	public MemberBean checkLogin(String id, String pw) {
		MemberBean mb = null;

		try {
			String sql = "select * from member where id = ? and pw = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);

			rs = ps.executeQuery();
			if (rs.next()) {
				mb = new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPw(rs.getString("pw"));
				mb.setMem_role(rs.getString("mem_role"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mb;
	} // checkData

}
