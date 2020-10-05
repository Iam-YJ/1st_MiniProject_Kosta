package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Member;

public interface MemberDAO {

	/**
	 * 로그인하기
	 */
	Member login(String userId, String password) throws SQLException;

	/**
	 * 회원가입하기
	 */
	void register(String userId, String password, String nickName) throws SQLException;

	/**
	 * 관리자 - 회원 수정
	 */
	int update(Member member) throws SQLException;

	/**
	 * 관리자 - 회원 삭제
	 */
	int delete(Member member) throws SQLException;

	/**
	 * 포인트 추가
	 */
	int updatePoint(int userNo, int points) throws SQLException;

	/**
	 * 랭킹
	 */
	List<Member> rank() throws SQLException;

	int insertTest(int userNo, Object o, String wordLevel, int corNum, int wrongNum, String wordNo) throws SQLException;

}
