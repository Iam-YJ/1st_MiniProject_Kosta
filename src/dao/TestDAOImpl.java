package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.DbUtil;

public class TestDAOImpl implements TestDAO {

	@Override
	public List<String> testSelectByUserNo(int userNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select wrong_word from test where user_no = ?";
		List<String> list = new ArrayList<String>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, userNo);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(rs.getString("wrong_word"));
			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

}
