package dto;

public class UserWord {
	private int userNo;
	private int userWordNo;
	private String userEng;
	private String userKor;
	private String userLevel;
	private String userPart;

	public UserWord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserWord(int userNo, String userEng) {
		this.userNo = userNo;
		this.userEng = userEng;

	}

	public UserWord(int userNo, String userLevel, String userEng, String userKor, String userPart) {
		super();
		this.userNo = userNo;
		this.userEng = userEng;
		this.userKor = userKor;
		this.userLevel = userLevel;
		this.userPart = userPart;
	}

	public UserWord(int userNo, int userWordNo, String userLevel, String userEng, String userKor, String userPart) {
		super();
		this.userNo = userNo;
		this.userWordNo = userWordNo;
		this.userEng = userEng;
		this.userKor = userKor;
		this.userLevel = userLevel;
		this.userPart = userPart;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getUserWordNo() {
		return userWordNo;
	}

	public void setUserWordNo(int userWordNo) {
		this.userWordNo = userWordNo;
	}

	public String getUserEng() {
		return userEng;
	}

	public void setUserEng(String userEng) {
		this.userEng = userEng;
	}

	public String getUserKor() {
		return userKor;
	}

	public void setUserKor(String userKor) {
		this.userKor = userKor;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getUserPart() {
		return userPart;
	}

	public void setUserPart(String userPart) {
		this.userPart = userPart;
	}

}
