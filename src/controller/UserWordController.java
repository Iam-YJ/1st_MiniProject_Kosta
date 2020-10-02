 package controller;

import java.util.List;

import dto.UserWord;
import service.UserWordService;
import view.EndView;
import view.FailView;

public class UserWordController {
	private static UserWordService service = new UserWordService();

	public static void wordInsert(UserWord userWord) {
		try {
			service.wordInsert(userWord);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}
	
	public static void wordDelete(UserWord userWord) {
		try {
			service.wordDelete(userWord);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}
	}
	public static void selectMemberWord(int userNo) {
		try {
			List<UserWord> list = service.selectMemberWord(userNo);
			EndView.printOrderByUserId(list);
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
