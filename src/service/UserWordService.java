package service;

import java.sql.SQLException;
import java.util.List;
import dao.UserWordDAO;
import dao.UserWordDAOImpl;
import dto.UserWord;

public class UserWordService {
	static UserWordDAO uw = new UserWordDAOImpl();

	public int wordInsert(UserWord userWord) throws SQLException {
		int result = uw.wordInsert(userWord);
		if (result == 0) {
			throw new SQLException("단어를 추가하지 못했습니다");
		}
		return result;
	}

	public int wordDelete(UserWord userWord) throws SQLException {
		int result = uw.wordDelete(userWord);
		if (result == 0) {
			throw new SQLException("단어를 삭제하지 못했습니다");
		}
		return result;
	}

	public List<UserWord> selectMemberWord(int userNo) throws SQLException {
		List<UserWord> list = uw.selectMemberWord(userNo);
		if (list.size() == 0 || list == null) {
			throw new SQLException("개인 단어장을 불러오지 못했습니다");
		}
		return list;
	}
	
	/**
	 * 단어 시험(주관식 랜덤 10개)
	 */
	public static boolean wordTest(int wordNo, String answer) throws SQLException {
		boolean flag = uw.wordTest(wordNo, answer);
/*		if (flag == false) { // GUI에서 오답 출력되므로 주석 처리
			throw new SQLException("오답입니다");
		}	*/
		return flag;
	}
}
