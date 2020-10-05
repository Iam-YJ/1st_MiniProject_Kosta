 package controller;

import dto.UserWord;
import gui.GUIMainView;
import service.UserWordService;
import view.FailView;

public class UserWordController {
	private static UserWordService service = new UserWordService();

	public static void wordInsert(UserWord userWord) {
		try {
			if (service.wordInsert(userWord) > 0) {
				GUIMainView.appendConsoleField("____________________________________________________________________________________\n\n개인 단어 \"" + userWord.getUserEng() + "\"  /  \"" + userWord.getUserKor() + "\" 가  추가되었습니다.");
			}
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}
	
	public static void wordDelete(UserWord userWord) {
		try {
			int result = service.wordDelete(userWord);
			if (result > 0) {
				GUIMainView.appendConsoleField("____________________________________________________________________________________\n\n개인 단어 \"" + userWord.getUserEng() + "\" 가 " + result + "개 삭제되었습니다.");
			}
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
}
