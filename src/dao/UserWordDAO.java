package dao;

import java.sql.SQLException;
import java.util.List;
import dto.UserWord;

public interface UserWordDAO {

	/**
	 * 단어 추가하기 (개인용)
	 */
	int wordInsert(UserWord userWord) throws SQLException;

	/**
	 * 단어 삭제하기 (개인용)
	 */
	int wordDelete(UserWord userWord) throws SQLException;

	/**
	 * 개인 단어 전체  보기
	 */
	List<UserWord> selectMemberWord(int userNo) throws SQLException;

	/**
	 * 단어 시험 (주관식 랜덤 10문)
	 */
	public boolean wordTest(int wordNo, String answer) throws SQLException;
}
