package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import controller.MemberController;
import controller.UserWordController;
import controller.WordController;
import dto.Member;
import dto.UserWord;
import dto.Word;

public class EndView {
	static Scanner sc = new Scanner(System.in);
	static Random random = new Random();
	static int number = 0;
	static int count = 1;
	static int score = 0;
	static int run = 0;
	static int wrongNum = 0;
	static List<Integer> numList = new ArrayList<Integer>();

	/**
	 * �ܾ� ��ü ���
	 */
	public static void printWordList(List<Word> list) {
		System.out.println("----- �ܾ� " + list.size() + "�� -------------");
		for (Word word : list) {
			System.out.println("���� : " + word.getWordLevel() + " | ���� : " + word.getWordEng() + " | �ѱ� : "
					+ word.getWordKor() + " | ǰ��  :" + word.getWordPart());
			System.out.println();
		}

		System.out.println();
	}

	public static void printMessage(String message) {
		System.out.println(message);
	}

	public static void printWord(Word word) {
		System.out.println(word);
	}

	/**
	 * ���� DB ����
	 */
	public static void printOrderByUserId(List<UserWord> list) {
		System.out.println("========���� �ܾ��� =======");
		for (UserWord member : list) {
			System.out.println("���� : " + member.getUserLevel() + " | ���� : " + member.getUserEng() + " | �ѱ� : "
					+ member.getUserKor() + " | ǰ��  :" + member.getUserPart());
			System.out.println();
		}

	}

	/**
	 * ��ŷ ���
	 */
	public static void printRankList(List<Member> list) {
		int rank = 1;
		System.out.println("----- ȸ��" + list.size() + "�� -------------");
		for (Member member : list) {
			System.out.println("rank :" + rank + " | userId : " + member.getUserId() + " | nickName : "
					+ member.getNickName() + " | points : " + member.getPoints());
			System.out.println();
			rank++;
		}
		System.out.println();
	}

	/**
	 * �ܾ� ����(��ü DB ����)
	 */
	public static void wordTest(List<Word> list, int userNo) {
		int run1 = 0;
		run1 = 10 - run;
		List<Integer> orderList = EndView.makeRandom(run1, list.size());

		for (int i = 0; i < run1; i++) {
			System.out.println("����" + count + "��");
			System.out.println(list.get(i).getWordEng() + "�� ����?");
			System.out.println(list.get(i).getWordKor());
			System.out.print("�� : ");
			String answer = sc.nextLine();

			if (WordController.getAnswer(list.get(i).getWordNo(), answer)) {
				System.out.println("�����Դϴ�");
				score++;
				MemberController.updatePoint(userNo, score);
			} else {
				wrongNum++;
				MemberController.insertTest(userNo, null, list.get(i).getWordLevel(), score, 10 - score,
						Integer.toString(list.get(i).getWordNo()));

			}
			count++;

			System.out.println();
		}

		System.out.println(score + "�� �Դϴ�");

	}

	/**
	 * �ܾ���� (����DB ����)
	 */
	public static void userWordTest(List<UserWord> list) {
		count = 1;
		while (true) {
			run = random.nextInt(10) + 1;
			if (run <= list.size() && run > 0) {
				break;
			}
		}

		List<Integer> orderList = EndView.makeRandom(run, list.size());

		for (Integer i : orderList) {
			System.out.println("����" + count + "��");

			System.out.println(list.get(i).getUserEng() + "�� ����?");

			System.out.println("����" + list.get(i).getUserKor());
			System.out.print("�� : ");
			String answer = sc.nextLine();
			if (UserWordController.getAnswer(list.get(i).getUserWordNo(), answer)) {
				System.out.println("�����Դϴ�");
				score++;
				MemberController.updatePoint(list.get(1).getUserNo(), score);
			} else {
				wrongNum++;
				MemberController.insertTest(list.get(i).getUserNo(), null, list.get(i).getUserLevel(), score,
						10 - score, Integer.toString(list.get(i).getUserWordNo()));

			}
			count++;

			System.out.println();
		}

	}

	/**
	 * �ܾ����� ���� 10�� �̱�
	 */
	public static List<Integer> makeRandom(int run, int size) {
		for (int i = 0; i < run; i++) {
			int number = random.nextInt(size);
			if (!numList.contains(number)) {
				numList.add(number);
			} else {
				i--;
			}
		}

		return numList;
	}

}
