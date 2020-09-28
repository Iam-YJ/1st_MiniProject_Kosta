package dto;

public class Member {
	private int userNo;
	private String userId;
	private String password;
	private String nickName;
	private int points;

	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(int userNo, String userId, String password, String nickName, int points) {
		super();
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.nickName = nickName;
		this.points = points;
	}

	public Member(int userNo, String userId, String password, String nickName) {
		this.userNo = userNo;
		this.userId = userId;
		this.password = password;
		this.nickName = nickName;
	}
	
	
	public Member(int userNo, String password, String nickName) {
		this.userNo = userNo;
		this.password = password;
		this.nickName = nickName;
	}
	
	public Member(int userNo) {
		
		this.userNo = userNo;;
	}
	

	
	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
