package view;

import java.sql.SQLException;
import java.util.Random;

import controller.MemberController;
import controller.UserWordController;
import controller.WordController;
import dao.UserWordDAO;
import dao.UserWordDAOImpl;
import dao.WordDAO;
import dao.WordDAOImpl;
import dto.Member;
import dto.UserWord;
import dto.Word;
import gui.GUIMainView;
import session.SessionSet;

//연속적으로 안돌아감 
public class MenuView {
	public static int CurrentMenu = 0;
	public static void printUserMenu(String userId, int userNo) { // 회원 메뉴
		CurrentMenu = 1;
		SessionSet ss = SessionSet.getInstance();
		GUIMainView.setConsoleField("-----" + userId + " 로그인 중 -----\n\n");
		GUIMainView.appendConsoleField("1. 단어 검색  |  2. 단어 시험  |  3. 단어 게임 |  4. 랭킹/회원 목록\n\n____________________________________________________________________________________\n\n5. 개인 단어 추가  |  6. 개인 단어 삭제\n\n____________________________________________________________________________________\n\n7. 오답 노트  |  8. 오답 노트 초기화 ");
	}

	public static void printNonUserMenu() { // 비회원 메뉴
		CurrentMenu = 80;
		SessionSet ss = SessionSet.getInstance();
		GUIMainView.setConsoleField("----- Guest 로그인 중 -----\n\n");
		GUIMainView.appendConsoleField(" 1. 단어 검색  |  2. 단어 시험  |  3. 단어 게임");
	}

	public static void printAdminMenu() { // 관리자 메뉴
		CurrentMenu = 90;
			SessionSet ss = SessionSet.getInstance();
			GUIMainView.setConsoleField("----- Administrator 로그인 중 -----\n\n");
			GUIMainView.appendConsoleField("1. 단어 추가  |  2. 단어 삭제  |  3. 회원 목록  |  4. 회원 수정  |  5. 회원 삭제");
	}

	public static void printUpdateAdminMember() {
		GUIMainView.setConsoleField("======= 회원 수정 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("수정 할 대상의 회원 번호");
		String userStrNo = GUIMainView.getInputDialog("수정 할 회원 번호", "회원 번호 입력");
		while (userStrNo.length() == 0 || !userStrNo.matches("^[0-9]*$")) {
			GUIMainView.appendConsoleField("숫자만 입력 가능합니다.");
			userStrNo = GUIMainView.getInputDialog("수정 할 회원 번호", "회원 번호 입력");
		}
		int userNo = Integer.parseInt(userStrNo);

		GUIMainView.appendConsoleField("\n새 비밀번호");
		String password = GUIMainView.getInputDialog("새 비밀번호", "새 비밀번호 입력");
		while(password.length() < 4) {
			GUIMainView.appendConsoleField("비밀번호는 최소 4자 이상이여야 합니다.");
			password = GUIMainView.getInputDialog("새 비밀번호", "새 비밀번호 입력");
		}

		GUIMainView.appendConsoleField("\n새 닉네임");
		String nickName = GUIMainView.getInputDialog("새 닉네임", "새 닉네임 입력");
		while(nickName.length() < 3) {
			GUIMainView.appendConsoleField("닉네임은 최소 3자 이상이여야 합니다.");
			password = GUIMainView.getInputDialog("새 닉네임", "새 닉네임 입력");
		}
		
		GUIMainView.appendConsoleField("포인트 값 수정");
		userStrNo = GUIMainView.getInputDialog("포인트 값 수정", "포인트 값 입력");
		while (userStrNo.length() == 0 || !userStrNo.matches("^[0-9]*$")) {
			GUIMainView.appendConsoleField("숫자만 입력 가능합니다.");
			userStrNo = GUIMainView.getInputDialog("포인트 값 수정", "포인트 값 입력");
		}
		int points = Integer.parseInt(userStrNo);
		
		Member member = new Member(userNo, null, password, nickName, points);
		MemberController.memberUpdate(member);
	}

	public static void printDeleteAdminMember() {
		GUIMainView.setConsoleField("======= 회원 삭제 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("삭제 될 회원 번호");
		String userStrNo = GUIMainView.getInputDialog("삭제 될 회원 번호", "회원 번호 입력");
		while (userStrNo.length() == 0 || !userStrNo.matches("^[0-9]*$")) {
			GUIMainView.appendConsoleField("숫자만 입력 가능합니다.");
			userStrNo = GUIMainView.getInputDialog("삭제 될 회원 번호", "회원 번호 입력");
		}
		int userNo = Integer.parseInt(userStrNo);
		
		Member member = new Member(userNo);
		MemberController.memberDelete(member);

	}

	/**
	 * 관리자 단어 추가
	 */
	public static void printInputAdminWord() {
		GUIMainView.setConsoleField("======= 단어 추가 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("단어 레벨");
		String wordLev = GUIMainView.getInputDialog("추가할 단어 레벨", "L    (Low)\nM  (Medium)\nH   (High)");
		while (wordLev.length() != 1 || !"LMH".contains(wordLev.toUpperCase())) {
			GUIMainView.appendConsoleField("L  M  H 중에 입력하세요.");
			wordLev = GUIMainView.getInputDialog("추가할 단어 레벨", "L    (Low)\nM  (Medium)\nH   (High)");
		}

		GUIMainView.appendConsoleField("\n영단어");
		String wordEng = GUIMainView.getInputDialog("추가할 영단어", "영단어 입력");
		while(wordEng.length() == 0 || !wordEng.matches("^[a-zA-Z]*$")) {
			GUIMainView.appendConsoleField("알파벳만 입력 가능합니다.");
			wordEng = GUIMainView.getInputDialog("추가할 영단어", "영단어 입력");
		}

		GUIMainView.appendConsoleField("\n한글 뜻");
		String wordKor = GUIMainView.getInputDialog("추가할 한글 뜻", "한글 뜻 입력");
		while (wordKor.length() == 0 || wordKor.matches("^[a-zA-Z0-9]*$")) {
			GUIMainView.appendConsoleField("영문과 숫자는 입력할 수 없습니다");
			wordKor = GUIMainView.getInputDialog("추가할 한글 뜻", "한글 뜻 입력");
		}

		GUIMainView.appendConsoleField("\n품사");
		String wordPart = GUIMainView.getInputDialog("품사", "품사 입력");
		while (wordPart.length() == 0 || !wordPart.matches("^[a-z]*$")) {
			GUIMainView.appendConsoleField("영어 소문자만 입력 가능합니다");
			wordPart = GUIMainView.getInputDialog("품사", "품사 입력");
		}

		Word word = new Word(wordLev, wordEng, wordKor, wordPart);
		WordController.wordInsert(word);
	}
	

	/**
	 * 관리자 단어 삭제
	 */

	public static void printDeleteAdminWord() {
		GUIMainView.setConsoleField("======= 단어 삭제 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("영단어");
		String wordEng = GUIMainView.getInputDialog("삭제할 영단어", "영단어 입력");
		while(wordEng.length() == 0 || !wordEng.matches("^[a-zA-Z]*$")) {
			GUIMainView.appendConsoleField("알파벳만 입력 가능합니다.");
			wordEng = GUIMainView.getInputDialog("삭제할 영단어", "영단어 입력");
		}

		Word word = new Word(wordEng);
		WordController.wordDelete(word);
	}

	/**
	 * 전체 단어 검색 메뉴 (전체 순회 / 단어로 찾기 / 알파벳으로 찾기)
	 */
	public static void printAllWord() {
		GUIMainView.setConsoleField("어떤 방식으로 단어를 검색할까요? ");
		GUIMainView.appendConsoleField("1. 전체 검색  |  2. 단어 검색  |  3. 알파벳 검색  |  4. 한글 검색  |  5. 개인 단어 검색");
	}

	/**
	 * 전체 단어 검색 메뉴 (전체 순회 / 단어로 찾기 / 알파벳으로 찾기) - Guest
	 */
	public static void printAllWordGuest() {
		GUIMainView.setConsoleField("어떤 방식으로 단어를 검색할까요? ");
		GUIMainView.appendConsoleField("1. 전체 검색  |  2. 단어 검색  |  3. 알파벳 검색  |  4. 한글 검색");
	}

	/**
	 * 단어 시험
	 */
	public static void wordTest(int userNo) {
		WordDAO wd = new WordDAOImpl();
		UserWordDAO uwd = new UserWordDAOImpl();
		GUIMainView.setConsoleField("======= 단어 시험을 시작합니다 =====\n\n____________________________________________________________________________________\n\n");
		try {
			if (userNo != 0) {
				GUIMainView.userWordTest(uwd.selectMemberWord(userNo));
			}
			GUIMainView.wordTest(wd.wordSelect(), userNo);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;
	}

	/**
	 * 단어 게임
	 */
	public static void wordGame(int userNo) {
		WordDAO wd = new WordDAOImpl();
		UserWordDAO uwd = new UserWordDAOImpl();
		GUIMainView.setConsoleField("======= 단어 게임을 시작합니다 =====\n\n____________________________________________________________________________________\n\n");
		try {
			int life = 5;
			int userWordCount = 0;
			if (userNo != 0) {
				userWordCount = uwd.selectMemberWord(userNo).size();
			}
			Random rand = new Random();
			while (life > 0){
				if (userNo != 0 && userWordCount > 0 && rand.nextInt(3) == 0) {
					life = GUIMainView.userWordGame(uwd.selectMemberWord(userNo));
					userWordCount--;
				}
				else life = GUIMainView.wordGame(wd.wordSelect(), userNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return;
	}

	/**
	 * 단어추가
	 */
	public static void printInputWord(int userNo) {
		GUIMainView.setConsoleField("======= 개인 단어 추가 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("단어 레벨");
		String wordLev = GUIMainView.getInputDialog("추가할 단어 레벨", "L    (Low)\nM  (Medium)\nH   (High)");
		while (wordLev.length() != 1 || !"LMH".contains(wordLev.toUpperCase())) {
			GUIMainView.appendConsoleField("L  M  H 중에 입력하세요.");
			wordLev = GUIMainView.getInputDialog("추가할 단어 레벨", "L    (Low)\nM  (Medium)\nH   (High)");
		}

		GUIMainView.appendConsoleField("\n영단어");
		String wordEng = GUIMainView.getInputDialog("추가할 영단어", "영단어 입력");
		while(wordEng.length() == 0 || !wordEng.matches("^[a-zA-Z]*$")) {
			GUIMainView.appendConsoleField("알파벳만 입력 가능합니다.");
			wordEng = GUIMainView.getInputDialog("추가할 영단어", "영단어 입력");
		}

		GUIMainView.appendConsoleField("\n한글 뜻");
		String wordKor = GUIMainView.getInputDialog("추가할 한글 뜻", "한글 뜻 입력");
		while (wordKor.length() == 0 || wordKor.matches("^[a-zA-Z0-9]*$")) {
			GUIMainView.appendConsoleField("영문과 숫자는 입력할 수 없습니다");
			wordKor = GUIMainView.getInputDialog("추가할 한글 뜻", "한글 뜻 입력");
		}

		GUIMainView.appendConsoleField("\n품사");
		String wordPart = GUIMainView.getInputDialog("품사", "품사 입력");
		while (wordPart.length() == 0 || !wordPart.matches("^[a-z]*$")) {
			GUIMainView.appendConsoleField("영어 소문자만 입력 가능합니다");
			wordPart = GUIMainView.getInputDialog("품사", "품사 입력");
		}

		UserWord memberWord = new UserWord(userNo, wordLev, wordEng, wordKor, wordPart);
		UserWordController.wordInsert(memberWord);
	}

	/**
	 * 단어삭제
	 */
	public static void printDeleteWord(int userNo) {
		GUIMainView.setConsoleField("======= 개인 단어 삭제 =====\n\n____________________________________________________________________________________\n\n");
		GUIMainView.appendConsoleField("영단어");
		String wordEng = GUIMainView.getInputDialog("삭제할 영단어", "영단어 입력");
		while(wordEng.length() == 0 || !wordEng.matches("^[a-zA-Z]*$")) {
			GUIMainView.appendConsoleField("알파벳만 입력 가능합니다.");
			wordEng = GUIMainView.getInputDialog("삭제할 영단어", "영단어 입력");
		}

		UserWord userWord = new UserWord(userNo, wordEng);
		UserWordController.wordDelete(userWord);
	}

}
