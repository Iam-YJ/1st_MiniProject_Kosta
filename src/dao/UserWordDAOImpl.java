package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dto.UserWord;
import util.DbUtil;

public class UserWordDAOImpl implements UserWordDAO {

	@Override
	public int wordInsert(UserWord userWord) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO user_word(user_no, user_word_no,user_word_level,user_eng,user_kor,user_part) VALUES(?,WORD_NO_SEQ.nextval,?,?,?,?)";
		int result = 0;
		try {

			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userWord.getUserNo());
			ps.setString(2, userWord.getUserLevel());
			ps.setString(3, userWord.getUserEng());
			ps.setString(4, userWord.getUserKor());
			ps.setString(5, userWord.getUserPart());

			result = ps.executeUpdate();

		} finally {
			DbUtil.close(con, ps, null);
		}
		return result;
	}

	@Override
	public int wordDelete(UserWord userWord) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "delete from user_word where user_no=? and user_eng =?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, userWord.getUserNo());
			ps.setString(2, userWord.getUserEng());
			result = ps.executeUpdate();

		} finally {
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public List<UserWord> selectMemberWord(int userNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<UserWord> list = new ArrayList<UserWord>();
		UserWord userWord = null;

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement("select * from user_word where user_no = " +userNo);
			rs = ps.executeQuery();

			while (rs.next()) {
				userWord = new UserWord(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6));
				list.add(userWord);
			}

		} finally {
			DbUtil.dbClose(con, ps);
		}
		return list;
	}

	@Override
	public boolean wordTest(int wordNo, String answer) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String sql = "select user_kor from user_word where user_word_no = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, wordNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				list = UserWordDAOImpl.korList(rs.getString("user_kor"));
				for (String s : list) {
					if (s.equals(answer)) {
						flag = true;
					}
				}

			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return flag;

	}

	public static List<String> korList(String wordKor) {

		List<String> list = new ArrayList<String>();
		// 뜻이 여러개 일 결우 각각 분리
		StringTokenizer token = new StringTokenizer(wordKor, ",");
		while (token.hasMoreTokens()) {
			list.add(token.nextToken().trim());
		}

		return list;

	}

}
