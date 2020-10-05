package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.Admin;
import dto.Member;
import util.DbUtil;

public class MemberDAOImpl implements MemberDAO {
	Member member = new Member();
	int realPoint = 0;

	@Override
	public Member login(String userId, String password) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Member member = null;

		String sql = "select * from member where user_id=? and password =?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			rs = ps.executeQuery();

			if (rs.next()) {
				member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));

				// 로그인 됨...
				adminCheck(member);
				// 관리자인지여부를 체크한다. ( select * from admin where user_no = ? )
			}

		} finally {
			DbUtil.close(con, ps, rs);
		}

		return member;
	}

	public Admin adminCheck(Member member) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Admin admin = null;

		String sql = "select * from admin where user_no = ?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, member.getUserNo());
			rs = ps.executeQuery();

			if (rs.next()) {
				admin.addAdmin(member);
			}

		} finally {
			DbUtil.dbClose(con, ps);
		}

		return admin;
	}

	@Override
	public void register(String userId, String password, String nickName) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into member(user_no, user_id, password, nickname,points) values(USER_NO_SEQ.nextval,?,?,?,0)";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, userId);
			ps.setString(2, password);
			ps.setString(3, nickName);
			ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps);
		}
	}

	@Override
	public int update(Member member) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "update member set password = ?, nickName = ?, points = ? where user_No=?";
		int result = 0;

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, member.getPassword());
			ps.setString(2, member.getNickName());
			ps.setInt(3, member.getPoints());
			ps.setInt(4, member.getUserNo());

			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps);

		}

		return result;
	}

	@Override
	public int delete(Member member) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "delete from member where user_no=?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, member.getUserNo());

			result = ps.executeUpdate();

		} finally {
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public int updatePoint(int userNo, int points) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		points = getPoint(userNo) + points;
		String sql = "update member set points = ? where user_no = ?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, points);
			ps.setInt(2, userNo);

			result = ps.executeUpdate();

		} finally {
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	public int getPoint(int userNo) {
		int point = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select points from member where user_no = ?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userNo);

			rs = ps.executeQuery();
			if (rs.next()) point = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtil.dbClose(con, ps);
		}

		return point;
	}

	@Override
	public List<Member> rank() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Member> list = new ArrayList<Member>();

		String sql = "select * from member order by points desc";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Member member = new Member(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5));
				list.add(member);
			}

		} finally {
			DbUtil.close(con, ps, rs);
		}

		return list;

	}

	@Override
	public int insertTest(int userNo, Object o, String wordLevel, int corNum, int wrongNum, String wordNo)
			throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "insert into test(user_no, test_round,test_date, test_level, correct_number, wrong_number, wrong_word) values(?,1,sysdate,?,?,?,?)";
		int result = 0;

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userNo);
			// ps.setInt(2, o);
			ps.setString(2, wordLevel);
			ps.setInt(3, corNum);
			ps.setInt(4, wrongNum);
			ps.setString(5, wordNo);

			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps);

		}

		return result;
	}

}
