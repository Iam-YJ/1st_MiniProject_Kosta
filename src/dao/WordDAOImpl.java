package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dto.Admin;
import dto.Member;
import dto.Word;
import util.DbUtil;

public class WordDAOImpl implements WordDAO {
	static TestDAO tdao = new TestDAOImpl();

	@Override
	public List<Word> wordSelect() throws SQLException { // 전체단어 검색
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Word> list = new ArrayList<>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(
					"select*from WORD order by CASE WHEN WORD_LEVEL = 'H' THEN WORD_ENG end asc , CASE WHEN WORD_LEVEL = 'M' THEN WORD_ENG end asc");
//"select*from WORD order by CASE WHEN WORD_LEVEL = 'H' THEN WORD_ENG end asc , CASE WHEN WORD_LEVEL = 'M' THEN WORD_ENG end asc"
			rs = ps.executeQuery();

			while (rs.next()) {
				Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(word);
			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<Word> wordSelectByWord(String eng) throws SQLException { // 특정단어 검색
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Word> list = new ArrayList<>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement("select*from WORD where WORD_ENG = ? order by WORD_ENG asc");
			ps.setString(1, eng);
			rs = ps.executeQuery();

			if (rs.next()) {
				Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(word);
			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<Word> wordSelectByAlphabet(String alphabet) throws SQLException { // 알파벳검색
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Word> list = new ArrayList<>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement("select * from WORD order by CASE WHEN WORD_LEVEL LIKE ?% THEN WORD_ENG end asc");
			ps.setString(1, alphabet);
			rs = ps.executeQuery();

			while (rs.next()) {
				Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(word);
			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

	@Override
	public int wordInsert(Word word) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "insert into word (word_no, word_level, word_eng, word_kor, word_part) values (WORD_NO_SEQ.nextval,?,?,?,?)";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, word.getWordLevel());
			ps.setString(2, word.getWordEng());
			ps.setString(3, word.getWordKor());
			ps.setString(4, word.getWordPart());

			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps);

		}
		return result;
	}

	@Override
	public int wordDelete(Word word) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "delete from word where word_eng = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, word.getWordEng());

			result = ps.executeUpdate();
		} finally {
			DbUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public boolean wordTest(int wordNo, String answer) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String sql = "select word_kor from word where word_no = ?";
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, wordNo);
			rs = ps.executeQuery();
			if (rs.next()) {
				list = WordDAOImpl.korList(rs.getString("word_kor"));
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

	/**
	 * 뜻 분리 메소드
	 */
	public static List<String> korList(String wordKor) {
		List<String> list = new ArrayList<String>();
		// 뜻이 여러개 일 결우 각각 분리
		StringTokenizer token = new StringTokenizer(wordKor, ",");
		while (token.hasMoreTokens()) {
			list.add(token.nextToken().trim());
		}

		return list;

	}

	@Override
	public boolean wordTestByLevel(int wordNo, String wordLevel, String answer) throws SQLException {
		boolean flag = false;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> list = new ArrayList<>();
		String sql = "select word_kor from word where word_level = ? and word_no = ?";

		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, wordLevel);
			ps.setInt(2, wordNo);
			rs = ps.executeQuery();
			System.out.println(rs.getString("word_level"));
			System.out.println(rs.getString("word_no"));

			if (rs.next()) {
				list = WordDAOImpl.korList(rs.getString("word_kor"));
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

	//
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
	public List<Word> wordSelectByLevel(String level) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Word> list = new ArrayList<>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement("select * from WORD where word_level = ?");
			ps.setString(1, level);
			rs = ps.executeQuery();

			while (rs.next()) {
				Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				list.add(word);
			}
		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

	@Override
	public List<Word> wordSelectByWordNo(int userNo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "select*from WORD where word_no =? ";
		List<Word> list = new ArrayList<>();
		List<String> noList = new ArrayList<String>();
		try {
			con = DbUtil.getConnection();
			ps = con.prepareStatement(sql);

			noList = tdao.testSelectByUserNo(userNo);

			for (String s : noList) {
				ps.setInt(1, Integer.parseInt(s));
				rs = ps.executeQuery();

				while (rs.next()) {
					Word word = new Word(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
							rs.getString(5));
					list.add(word);
				}

			}

		} finally {
			DbUtil.close(con, ps, rs);
		}
		return list;
	}

}
