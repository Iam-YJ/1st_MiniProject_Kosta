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
	 * 단어 전체 출력
	 */
	public static void printWordList(List<Word> list) {
		System.out.println("----- 단어 " + list.size() + "개 -------------");
		for (Word word : list) {
			System.out.println("레벨 : " + word.getWordLevel() + " | 영어 : " + word.getWordEng() + " | 한글 : "
					+ word.getWordKor() + " | 품사  :" + word.getWordPart());
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
	 * 개인 DB 보기
	 */
	public static void printOrderByUserId(List<UserWord> list) {
		System.out.println("========개인 단어장 =======");
		for (UserWord member : list) {
			System.out.println("레벨 : " + member.getUserLevel() + " | 영어 : " + member.getUserEng() + " | 한글 : "
					+ member.getUserKor() + " | 품사  :" + member.getUserPart());
			System.out.println();
		}

	}

	/**
	 * 랭킹 출력
	 */
	public static void printRankList(List<Member> list) {
		int rank = 1;
		System.out.println("----- 회원" + list.size() + "명 -------------");
		for (Member member : list) {
			System.out.println("rank :" + rank + " | userId : " + member.getUserId() + " | nickName : "
					+ member.getNickName() + " | points : " + member.getPoints());
			System.out.println();
			rank++;
		}
		System.out.println();
	}

	/**
	 * 단어 시험(전체 DB 참조)
	 */
	public static void wordTest(List<Word> list, int userNo) {

		run = 10 - run;
		List<Integer> orderList = EndView.makeRandom(run, list.size());

		for (Integer i : orderList) {
			System.out.println("문제" + count + "번");
			System.out.println(list.get(i).getWordEng() + "의 뜻은?");
			System.out.println(list.get(i).getWordKor());
			System.out.print("답 : ");
			String answer = sc.nextLine();

			if (WordController.getAnswer(list.get(i).getWordNo(), answer)) {
				System.out.println("정답입니다");
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
		System.out.println(score + "점 입니다");
	}

	/**
	 * 단어시험 (개인DB 참조)
	 */
	public static void userWordTest(List<UserWord> list) {
		count = 1;
		while (true) {
			run = random.nextInt(10) + 1;
			if (run <= list.size()) {
				break;
			}
		}

		List<Integer> orderList = EndView.makeRandom(run, list.size());

		for (Integer i : orderList) {
			System.out.println("문제" + count + "번");

			System.out.println(list.get(i).getUserEng() + "의 뜻은?");

			System.out.println("정답" + list.get(i).getUserKor());
			System.out.print("답 : ");
			String answer = sc.nextLine();
			if (UserWordController.getAnswer(list.get(i).getUserWordNo(), answer)) {
				System.out.println("정답입니다");
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
	 * 단어시험용 랜덤 10개 뽑기
	 */
	public static List<Integer> makeRandom(int run, int size) {
		System.out.println(size);
		for (int i = 0; i < run; i++) {
			System.out.println(number);
			int number = random.nextInt(size);
			if (!numList.contains(number)) {
				numList.add(number);
			} else {
				i--;
			}
		}

		return numList;
	}

	/**
	 * 단어 시험 (초, 중, 고)
	 */
	public static void wordTestByLevel(List<Word> list) {
		count = 1;
		run = random.nextInt(10);
		String[] level = { "L", "M", "H" };
		List<Integer> orderList = EndView.makeRandom(run, list.size());

		for (int k = 0; k < level.length; k++) {
			for (Integer i : orderList) {
				for (int j = 0; score < 8; j++) {
					System.out.println("문제" + count + "번");
					System.out.println(list.get(i).getWordEng());
					System.out.println(list.get(i).getWordKor());
					System.out.print("답 :");
					String answer = sc.nextLine();
					System.out.println("레벨:" + level[k]);
					WordController.getAnswerByLevel(list.get(i).getWordNo(), level[k], answer);

					if (WordController.getAnswerByLevel(list.get(i).getWordNo(), level[k], answer)) {
						System.out.println("정답입니다");
						score++;
					} else {
						continue;
					}
					count++;
					System.out.println();
				}
				System.out.println(score + "점 입니다");
			}
		}

	}

	/**
	 * 단어 게임
	 */
	public static void wordGame(List<Word> list) {
		// level 순으로 시험
		// 8개 이상 맞으면 level up
		// 3개 이상 틀리면 out

		// 레벨 순으로 불러오는 메소드 호출 (만들고 arr [l, m, h] 하나씩 돌기)
		// random 메소드 작성
		// 답 개수 저장 + 오답 개수 저장
		// 답 8개 이상이면 - > 다시

	}

}
