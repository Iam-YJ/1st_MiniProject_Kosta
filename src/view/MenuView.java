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

//���������� �ȵ��ư� 
public class MenuView {
	//git test
	private static Scanner sc = new Scanner(System.in);

	public static void menu() { // ù ȭ��
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			MenuView.printMenu();
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				MenuView.register(); // ����

				break;
			case 2:
				MenuView.login(); // �α���
				break;

			case 3:
				MenuView.printAdminMenu();
				break;

			case 9:
				System.exit(0);
			}
		}
	}

	public static void printUserMenu(String userId, int userNo) { // ȸ�� �޴�
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			System.out.println("-----" + userId + " �α��� �� -----");
			System.out.println("1.��ü�˻�  |  2.�ܾ����  | 3. �ܾ��߰�  |  4.�ܾ����  |  5.�����Ʈ  | 6. ��ŷ  | 7. �α׾ƿ�");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				MenuView.printAllWord();
				UserWordController.selectMemberWord(userNo);
				return;

			case 2:
				// �ܾ����
				MenuView.wordTest(userNo);
				return;
			case 3:
				// �ܾ��߰�
				MenuView.printInputWord(userNo);
				return;
			case 4:
				// �ܾ����
				MenuView.printDeleteWord(userNo);
				return;
			case 5:
				// �����Ʈ
				WordController.wordSelectByWordNo(userNo);
				return;

			case 6:
				// ��ŷ
				MemberController.rank();
				return;

			case 7:
				MenuView.logout(userId);
				return;
			default:
				System.out.println("�߸��� �Է��Դϴ�.");
				break;

			}
		}

	}

	public static void printNonUserMenu() { // ��ȸ�� �޴�
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			System.out.println("----- ��ȸ�� �α��� �� -----");
			System.out.println(" 1.��ü�˻� |  2.�ܾ����  ");
			int menu = Integer.parseInt(sc.nextLine());
			switch (menu) {
			case 1:
				// ��ü�˻�
				MenuView.printAllWord();
				return;

			case 2:
				// �ܾ����
				MenuView.wordTest(0);
				return;
			// break;
			default:
				System.out.println("�߸��� �Է��Դϴ�.");
				break;
			}
		}

	}

	public static void printAdminMenu() { // ������ �޴�
		while (true) {
			SessionSet ss = SessionSet.getInstance();
			// System.out.println("----- ������ �α��� �� -----);
			System.out.println("1. �ܾ� �߰� | 2. �ܾ� ���� | 3. ȸ�� ���� | 4. ȸ�� ����");
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
		System.out.println("���� �� ȸ����ȣ? ");
		int userNo = Integer.parseInt(sc.nextLine());

		System.out.println("���� ��й�ȣ��?");
		String password = sc.nextLine();

		System.out.println("���� �̸���?");
		String nickName = sc.nextLine();

		Member member = new Member(userNo, null, password, nickName);
		MemberController.memberUpdate(member);
	}

	private static void printDeleteAdminMember() {
		System.out.println("������ ȸ����ȣ? ");
		int userNo = Integer.parseInt(sc.nextLine());

		Member member = new Member(userNo);
		MemberController.memberDelete(member);

	}

	/**
	 * ������ �ܾ� �߰�
	 */
	public static void printInputAdminWord() {

		System.out.print("�߰��� �ܾ� ����(H,M,L)");
		String wordLevel = sc.nextLine();

		System.out.print("�ܾ�(����) : ");
		String wordEng = sc.nextLine();

		System.out.print("�ܾ�(�ѱ�) : ");
		String wordKor = sc.nextLine();

		System.out.print("ǰ�� : ");
		String wordPart = sc.nextLine();

		Word word = new Word(wordLevel, wordEng, wordKor, wordPart);
		WordController.wordInsert(word);
	}
	

	/**
	 * ������ �ܾ� ����
	 */

	public static void printDeleteAdminWord() {

		System.out.print("������ �ܾ�(����) : ");
		String wordEng = sc.nextLine();

		Word word = new Word(wordEng);
		WordController.wordDelete(word);
	}

	public static void printMenu() { // ���� �޴�
		System.out.println("=== ������ �ܾ��� 3�� ===");
		System.out.println("1. ����   |   2. �α��� | 3. ������ |  9. ����");
	}

	/**
	 * ��ü �ܾ� �˻� �޴� (��ü ��ȸ / �ܾ�� ã�� / ���ĺ����� ã��)
	 */
	public static void printAllWord() {
		System.out.println("� ������� �ܾ ����ұ��? ");
		System.out.println("1. ��ü �˻� | 2. �ܾ�� �˻� | 3. ���ĺ����� �˻�");
		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("===== ��ü �˻� �մϴ� =====");
			WordController.wordSelect();
			return;

		case 2:
			System.out.println("�˻��� �ܾ �Է��ϼ��� ");
			String word = sc.nextLine();
			System.out.println("===== �ܾ�� �˻� �մϴ� =====");
			WordController.wordSelectByWord(word.toLowerCase());
			return;

		case 3:
			System.out.println("�˻��� ���ĺ��� �Է��ϼ��� ");
			String alphabet = sc.nextLine();
			System.out.println("===== ���ĺ����� �˻� �մϴ� =====");
			WordController.wordSelectByAlphabet(alphabet);
			return;

		default:
			System.out.println("�߸��� �Է��Դϴ�.");
			break;
		}
	}

	/**
	 * �ܾ� ���� & ����
	 */
	public static void wordTest(int userNo) {
		WordDAO wd = new WordDAOImpl();
		UserWordDAO uwd = new UserWordDAOImpl();
		System.out.println("�ܾ� ������ �����մϴ�. ");
		System.out.println("1. ���� �ܾ� ���� ");
		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("======= ���� �ܾ� ������ �����մϴ� =====");
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
			System.out.println("�߸��� �Է��Դϴ�.");
			break;

		}
	}

	/**
	 * ȸ������
	 */
	public static void register() {
		System.out.println("�����Ͻðڽ��ϱ�? ");
		System.out.println("1. ��	| 2. �ƴϿ� ");

		int menu = Integer.parseInt(sc.nextLine());
		switch (menu) {
		case 1:
			System.out.println("������ ���̵� : ");
			String userId = sc.nextLine();

			System.out.print("��� : ");
			String password = sc.nextLine();

			System.out.print("�г��� : ");
			String nickName = sc.nextLine();

			MemberController.register(userId, password, nickName);
			return;

		case 2:
			System.out.println("��ȸ�� �Դϴ�.");
			MenuView.printNonUserMenu(); // ��ȸ�� ���� �޴�
			return;

		default:
			System.out.println("�߸��� �Է��Դϴ�.");
			break;

		}

	}

	/**
	 * �α��� �޴�
	 */
	public static void login() {
		System.out.print("���̵� : ");
		String userId = sc.nextLine();

		System.out.print("��� : ");
		String password = sc.nextLine();

		MemberController.login(userId, password);
	}

	/**
	 * �α׾ƿ�
	 */
	public static void logout(String userId) {
		SessionSet ss = SessionSet.getInstance();
		Session session = new Session(userId);
		ss.remove(session);
	}

	/**
	 * �ܾ��߰�
	 */
	public static void printInputWord(int userNo) {
		System.out.print("�߰��� �ܾ� ����");
		String wordLev = sc.nextLine();

		System.out.print("�ܾ�(����) : ");
		String wordEng = sc.nextLine();

		System.out.print("�ܾ�(�ѱ�) : ");
		String wordKor = sc.nextLine();

		System.out.print("ǰ�� : ");
		String wordPart = sc.nextLine();

		UserWord memberWord = new UserWord(userNo, wordLev, wordEng, wordKor, wordPart);
		UserWordController.wordInsert(memberWord);
	}

	/**
	 * �ܾ����
	 */
	public static void printDeleteWord(int userNo) {
		System.out.print("������ �ܾ�(����) : ");
		String wordEng = sc.nextLine();

		UserWord userWord = new UserWord(userNo, wordEng);
		UserWordController.wordDelete(userWord);
	}

}
