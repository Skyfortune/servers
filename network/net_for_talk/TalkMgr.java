package net_for_talk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TalkMgr {

	private DBConnectionMgr pool;

	public TalkMgr() {
		pool = DBConnectionMgr.getInstance();
	}

	// login - 성공(ture)
	public boolean loginChk(String id, String pwd) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		// flag는 제어권을 의미, 불이 들어온다? 그럼 제어가능하다 라는 뜻으로 해석하면 된다.
		boolean flag = false;

		try {
			con = pool.getConnection();
			// sql = "select id from tblRegister where id = ? and pwd = ?";
			sql = "select count(id) from tblRegister where id = ? and pwd = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pwd);
			rs = pstmt.executeQuery();
			// flag = rs.next();
			if (rs.next() && rs.getInt(1) == 1)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.freeConnection(con, pstmt, rs);
		}
		return flag;

	}
}
