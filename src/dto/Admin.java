package dto;

import java.util.ArrayList;
import java.util.List;

public class Admin {
	private int userNo;
	private static List<Member> adminList = new ArrayList<Member>();

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(int userNo) {
		super();
		this.userNo = userNo;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public static List<Member> getAdmin() {
		return adminList;
	}

	public static void setAdmin(List<Member> admin) {
		Admin.adminList = admin;
	}
	public static void addAdmin(Member member) {
		adminList.add(member);
	}
	
	
	
	
}
