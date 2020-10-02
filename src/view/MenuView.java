package view;

import java.sql.SQLException;
import java.util.Scanner;

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
import session.Session;
import session.SessionSet;

//연속적으로 안돌아감 
public class MenuView {
	//git test
	private static Scanner sc = new Scanner(System.in);

	public static void menu() { // 첫 화면
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			MenuView.printMenu();
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				MenuView.register(); // 가입

				break;
			case 2:
				MenuView.login(); // 로그인
				break;

			case 3:
				MenuView.printAdminMenu();
				break;

			case 9:
				System.exit(0);
			}
		}
	}

	public static void printUserMenu(String userId, int userNo) { // 회원 메뉴
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			System.out.println("-----" + userId + " 로그인 중 -----");
			System.out.println("1.전체검색  |  2.단어시험  | 3. 단어추가  |  4.단어삭제  |  5.오답노트  | 6. 랭킹  | 7. 로그아웃");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				MenuView.printAllWord();
				UserWordController.selectMemberWord(userNo);
				return;

			case 2:
				// 단어시험
				MenuView.wordTest(userNo);
				return;
			case 3:
				// 단어추가
				MenuView.printInputWord(userNo);
				return;
			case 4:
				// 단어삭제
				MenuView.printDeleteWord(userNo);
				return;
			case 5:
				// 오답노트
				WordController.wordSelectByWordNo(userNo);
				return;

			case 6:
				// 랭킹
				MemberController.rank();
				return;

			case 7:
				MenuView.logout(userId);
				return;
			default:
				System.out.println("잘못된 입력입니다.");
				break;

			}
		}

	}

	public static void printNonUserMenu() { // 비회원 메뉴
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			System.out.println("----- 비회원 로그인 중 -----");
			System.out.println(" 1.전체검색 |  2.단어시험  ");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				// 전체검색
				MenuView.printAllWord();
				return;

			case 2:
				// 단어시험
				MenuView.wordTest(0);
				return;
			// break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}

	}

	public static void printAdminMenu() { // 관리자 메뉴
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			// System.out.println("----- 관리자 로그인 중 -----);
			System.out.println("1. 단어 추가 | 2. 단어 삭제 | 3. 회원 수정 | 4. 회원 삭제");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				MenuView.printInputAdminWord();
				return;

			case 2:
				MenuView.printDeleteAdminWord();
				return;

			case 3:

				MenuView.printUpdateAdminMember();
				return;

			case 4:

				MenuView.printDeleteAdminMember();
				return;

			}
		}

	}

	private static void printUpdateAdminMember() {
		System.out.println("수정 할 회원번호? ");
		int userNo = Integer.parseInt(sc.nextLine());

		System.out.println("수정 비밀번호는?");
		String password = sc.nextLine();

		System.out.println("수정 이름은?");
		String nickName = sc.nextLine();

		Member member = new Member(userNo, null, password, nickName);
		MemberController.memberUpdate(member);
	}

	private static void printDeleteAdminMember() {
		System.out.println("삭제할 회원번호? ");
		int userNo = Integer.parseInt(sc.nextLine());

		Member member = new Member(userNo);
		MemberController.memberDelete(member);

	}

	/**
	 * 관리자 단어 추가
	 */
	public static void printInputAdminWord() {

		System.out.print("추가할 단어 레벨(H,M,L)");
		String wordLevel = sc.nextLine();

		System.out.print("단어(영문) : ");
		String wordEng = sc.nextLine();

		System.out.print("단어(한글) : ");
		String wordKor = sc.nextLine();

		System.out.print("품사 : ");
		String wordPart = sc.nextLine();

		Word word = new Word(wordLevel, wordEng, wordKor, wordPart);
		WordController.wordInsert(word);
	}
	

	/**
	 * 관리자 단어 삭제
	 */

	public static void printDeleteAdminWord() {

		System.out.print("삭제할 단어(영문) : ");
		String wordEng = sc.nextLine();

		Word word = new Word(wordEng);
		WordController.wordDelete(word);
	}

	public static void printMenu() { // 입장 메뉴
		System.out.println("=== 잊혀질 단어장 3조 ===");
		System.out.println("1. 가입   |   2. 로그인 | 3. 관리자 |  9. 종료");
	}

	/**
	 * 전체 단어 검색 메뉴 (전체 순회 / 단어로 찾기 / 알파벳으로 찾기)
	 */
	public static void printAllWord() {
		System.out.println("어떤 방식으로 단어를 출력할까요? ");
		System.out.println("1. 전체 검색 | 2. 단어로 검색 | 3. 알파벳으로 검색");
		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("===== 전체 검색 합니다 =====");
			WordController.wordSelect();
			return;

		case 2:
			System.out.println("검색할 단어를 입력하세요 ");
			String word = sc.nextLine();
			System.out.println("===== 단어로 검색 합니다 =====");
			WordController.wordSelectByWord(word.toLowerCase());
			return;

		case 3:
			System.out.println("검색할 알파벳을 입력하세요 ");
			String alphabet = sc.nextLine();
			System.out.println("===== 알파벳으로 검색 합니다 =====");
			WordController.wordSelectByAlphabet(alphabet);
			return;

		default:
			System.out.println("잘못된 입력입니다.");
			break;
		}
	}

	/**
	 * 단어 시험 & 게임
	 */
	public static void wordTest(int userNo) {
		WordDAO wd = new WordDAOImpl();
		UserWordDAO uwd = new UserWordDAOImpl();
		System.out.println("단어 시험을 시작합니다. ");
		System.out.println("1. 랜덤 단어 시험 ");
		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("======= 랜덤 단어 시험을 시작합니다 =====");
			try {
				if (userNo != 0) {
					EndView.userWordTest(uwd.selectMemberWord(userNo));
				}
				 EndView.wordTest(wd.wordSelect(), userNo);

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return;

		default:
			System.out.println("잘못된 입력입니다.");
			break;

		}
	}

	/**
	 * 회원가입
	 */
	public static void register() {
		System.out.println("가입하시겠습니까? ");
		System.out.println("1. 네	| 2. 아니요 ");

		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("가입할 아이디 : ");
			String userId = sc.nextLine();

			System.out.print("비번 : ");
			String password = sc.nextLine();

			System.out.print("닉네임 : ");
			String nickName = sc.nextLine();

			MemberController.register(userId, password, nickName);
			return;

		case 2:
			System.out.println("비회원 입니다.");
			MenuView.printNonUserMenu(); // 비회원 전용 메뉴
			return;

		default:
			System.out.println("잘못된 입력입니다.");
			break;

		}

	}

	/**
	 * 로그인 메뉴
	 */
	public static void login() {
		System.out.print("아이디 : ");
		String userId = sc.nextLine();

		System.out.print("비번 : ");
		String password = sc.nextLine();

		MemberController.login(userId, password);
	}

	/**
	 * 로그아웃
	 */
	public static void logout(String userId) {
		SessionSet ss = SessionSet.getInstance();
		Session session = new Session(userId);
		ss.remove(session);
	}

	/**
	 * 단어추가
	 */
	public static void printInputWord(int userNo) {
		System.out.print("추가할 단어 레벨");
		String wordLev = sc.nextLine();

		System.out.print("단어(영문) : ");
		String wordEng = sc.nextLine();

		System.out.print("단어(한글) : ");
		String wordKor = sc.nextLine();

		System.out.print("품사 : ");
		String wordPart = sc.nextLine();

		UserWord memberWord = new UserWord(userNo, wordLev, wordEng, wordKor, wordPart);
		UserWordController.wordInsert(memberWord);
	}

	/**
	 * 단어삭제
	 */
	public static void printDeleteWord(int userNo) {
		System.out.print("삭제할 단어(영문) : ");
		String wordEng = sc.nextLine();

		UserWord userWord = new UserWord(userNo, wordEng);
		UserWordController.wordDelete(userWord);
	}

}
