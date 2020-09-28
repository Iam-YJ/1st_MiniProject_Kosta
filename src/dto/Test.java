package dto;

public class Test {
	private int userNo;
	private int testRound;
	private String testDate;
	private String testLevel;
	private int correctNumber;
	private int wrongNumber;
	private String wrongWord;

	public Test() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Test(int userNo, int testRound, String testDate, String testLevel, int correctNumber, int wrongNumber,
			String wrongWord) {
		super();
		this.userNo = userNo;
		this.testRound = testRound;
		this.testDate = testDate;
		this.testLevel = testLevel;
		this.correctNumber = correctNumber;
		this.wrongNumber = wrongNumber;
		this.wrongWord = wrongWord;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getTestRound() {
		return testRound;
	}

	public void setTestRound(int testRound) {
		this.testRound = testRound;
	}

	public String getTestDate() {
		return testDate;
	}

	public void setTestDate(String testDate) {
		this.testDate = testDate;
	}

	public String getTestLevel() {
		return testLevel;
	}

	public void setTestLevel(String testLevel) {
		this.testLevel = testLevel;
	}

	public int getCorrectNumber() {
		return correctNumber;
	}

	public void setCorrectNumber(int correctNumber) {
		this.correctNumber = correctNumber;
	}

	public int getWrongNumber() {
		return wrongNumber;
	}

	public void setWrongNumber(int wrongNumber) {
		this.wrongNumber = wrongNumber;
	}

	public String getWrongWord() {
		return wrongWord;
	}

	public void setWrongWord(String wrongWord) {
		this.wrongWord = wrongWord;
	}

}
