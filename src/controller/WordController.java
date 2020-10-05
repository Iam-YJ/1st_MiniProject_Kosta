
package controller;

import java.sql.SQLException;
import java.util.List;

import dto.Word;
import gui.GUIMainView;
import service.WordService;
import view.EndView;
import view.FailView;

public class WordController {

	private static WordService service = new WordService();

	/**
	 * 단어 추가하기
	 */
	public static void wordInsert(Word word) {
		try {
			service.wordInsert(word);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());

		}

	}

	/**
	 * 단어 삭제하기
	 */

	public static void wordDelete(Word word) {
		try {
			service.wordDelete(word);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void wordSelect() { // 전체단어 검색
		try {
			List<Word> list = service.wordSelect();
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void wordSelectByWord(String eng) { // 영단어 검색
		try {
			List<Word> list = service.wordSelectByWord(eng);
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void wordSelectByWordKor(String kor) { // 한글 검색
		try {
			List<Word> list = service.wordSelectByWordKor(kor);
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void wordSelectByAlphabet(String alphabet) { // 알파벳검색
		try {
			List<Word> list = service.wordSelectByAlphabet(alphabet);
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void wordSelectByUser(int userNo) { // 전체단어 검색
		try {
			List<Word> list = service.selectUserWord(userNo);
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static boolean getAnswer(int wordNo, String answer) {
		boolean flag = false;
		try {
			flag = service.wordTest(wordNo, answer);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}

		return flag;
	}

	public static boolean getAnswerByLevel(int wordNo, String wordLevel, String answer) {
		boolean flag = false;
		try {
			flag = service.wordTestByLevel(wordNo, wordLevel, answer);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}

		return flag;
	}

	/**
	 * 단어 가져오기 (단어 넘버로)
	 * @return 
	 */
	public static void wordSelectByWordNo(int userNo) {
		try {
			List<Word> list = service.wordSelectByWordNo(userNo);
			EndView.printWordList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void resetTestNote(int userNo) {
		try {
			service.wordResetByUserNo(userNo);
			GUIMainView.appendConsoleField("오답 노트를 초기화 하였습니다.");
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
		
	}

}
