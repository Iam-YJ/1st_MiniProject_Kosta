package view;

import java.util.List;
import dto.Member;
import dto.Word;
import gui.GUIMainView;

public class EndView {
	/**
	 * 단어 전체 출력
	 */
	public static void printWordList(List<Word> list) {
		GUIMainView.appendConsoleField("------------- 단어 " + list.size() + "개 -------------\n\n");
		String text = "____________________________________________________________________________________\n\n";
		int num = 0;
		for (Word word : list) {
			text = text + ++num + ".\n\n" + word.getWordEng() + "\n\""
					+ word.getWordKor() + "\"\n\n (레벨 : " + word.getWordLevel() + ")  (품사 : " + word.getWordPart() + ")\n____________________________________________________________________________________\n\n";
		}
		GUIMainView.appendConsoleField(text);
	}

	public static void printMessage(String message) {
		GUIMainView.appendConsoleField(message);
	}

	public static void printWord(Word word) {
		System.out.println(word);
	}

	/**
	 * 랭킹 출력
	 */
	public static void printRankList(List<Member> list) {
		int rank = 1;
		GUIMainView.setConsoleField("------------- 등록된 총 인원 " + list.size() + "명 -------------\n\n");
		String text = "____________________________________________________________________________________\n\n";
		for (Member member : list) {
			text = text + rank++ + ".  " + member.getNickName() + "\n(ID: "
					+ member.getUserNo() + "  /  " + member.getUserId() + ")\n\nPoints : " + member.getPoints() + "\n____________________________________________________________________________________\n\n";
		}
		GUIMainView.appendConsoleField(text);
	}
}
