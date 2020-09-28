package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dao.MemberDAO;
import dao.MemberDAOImpl;
import dto.Member;
import dto.Word;
import exception.DuplicatedException;
import exception.NotFoundException;
import session.Session;
import session.SessionSet;
import util.DbUtil;

public class MemberService {
	static MemberDAO mdao = new MemberDAOImpl();

	public Member login(String userId, String password) throws SQLException, NotFoundException {
		Member member = mdao.login(userId, password);
		if (member == null) {
			throw new NotFoundException("일치하는 회원 정보가 없습니다.");
		}

		SessionSet sessionSet = SessionSet.getInstance();
		Session session = new Session(userId);
		sessionSet.add(session);

		return member;

	}

	public static Member register(String userId, String password, String nickName)
			throws SQLException, DuplicatedException {
		Member member = mdao.register(userId, password, nickName);

		if (member != null) {
			throw new DuplicatedException("이미 존재하는 회원입니다.");
		}
		SessionSet sessionSet = SessionSet.getInstance();
		Session session = new Session(userId);
		sessionSet.add(session);
		return member;
	}

	/**
	 * 회원 수정
	 */
	public int update(Member member) throws SQLException {
		int result = mdao.update(member);
		if (result == 0) {
			throw new SQLException("회원을 수정하지 못했습니다");
		}
		return result;
	}

	/**
	 * 회원 삭제
	 */
	public int delete(Member member) throws SQLException {
		int result = mdao.delete(member);
		if (result == 0) {
			throw new SQLException("삭제되지 않았습니다..");
		}
		return result;
	}

	public int updatePoint(int userNo, int points) throws SQLException {
		int result = mdao.updatePoint(userNo, points);
		if (result == 0) {
			throw new SQLException("POINTS가 업데이트되지 않았습니다.");
		}
		return result;
	}

	public List<Member> rank() throws SQLException, NotFoundException {
		List<Member> list = mdao.rank();
		if (list.size() == 0 || list == null) {
			throw new NotFoundException("회원 랭킹을 조회할 수 없습니다.");
		}

		return list;
	}

	public int insertTest(int userNo, Object o, String wordLevel, int corNum, int wrongNum, String wordNo)
			throws SQLException {
		int result = mdao.insertTest(userNo, o, wordLevel, corNum, wrongNum, wordNo);
		if (result == 0) {
			throw new SQLException("오답노트가 업데이트되지 않았습니다.");
		}
		return result;

	}

}
