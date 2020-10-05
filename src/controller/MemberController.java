package controller;

import java.sql.SQLException;
import java.util.List;

import dto.Admin;
import dto.Member;
import gui.GUIMainView;
import service.MemberService;
import view.EndView;
import view.FailView;
import view.MenuView;

public class MemberController {
	static MemberService memberService = new MemberService();

	/**
	 * 로그인
	 */
	public static Member login(String userId, String password) {
		try {
			Member member = memberService.login(userId, password);

    		if (Admin.getUserNo() == member.getUserNo())
    			MenuView.printAdminMenu();
    		else
    			MenuView.printUserMenu(userId, member.getUserNo());
			
			return member;

		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());

		}
		return null;
	}

	/**
	 * 회원가입
	 */
	public static boolean register(String userId, String password, String nickName) {
		try {
			MemberService.register(userId, password, nickName);
//			MenuView.printUserMenu(userId, member.getUserNo());
			return true;
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
			return false;
		}
	}

	/**
	 * 회원 수정
	 */
	public static void memberUpdate(Member member) {
		try {
			memberService.update(member);
			GUIMainView.appendConsoleField("____________________________________________________________________________________\n\n사용자 " + member.getUserNo() + ".  \"" + member.getNickName() + "\" 의 정보가 수정되었습니다.");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	/**
	 * 회원 삭제
	 */

	public static void memberDelete(Member member) {
		try {
			memberService.delete(member);
			GUIMainView.appendConsoleField("____________________________________________________________________________________\n\n" + member.getUserNo() + " 번의 사용자 아이디가 삭제되었습니다.");
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void updatePoint(int userNo, int points) {
		try {
			memberService.updatePoint(userNo, points);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
	}

	public static void rank() {

		try {
			List<Member> list = memberService.rank();
			EndView.printRankList(list);
		} catch (Exception e) {
			FailView.errorMessage(e.getMessage());
		}

	}

	public static void insertTest(int userNo, Object o, String wordLevel, int corNum, int wrongNum, String wordNo) {
		try {
			memberService.insertTest(userNo, o, wordLevel, corNum, wrongNum, wordNo);
		} catch (SQLException e) {
			FailView.errorMessage(e.getMessage());
		}
		
	}




}
