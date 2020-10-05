package dto;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Word {

	private int wordNo;
	private String wordLevel;
	private String wordEng;
	private String wordKor;
	private String wordPart;


	public Word() {

	}

	public Word(String wordEng) {
		this.wordEng = wordEng;
	}

	public Word(String wordLevel, String wordEng, String wordKor, String wordPart) {
		super();
		this.wordLevel = wordLevel;
		this.wordEng = wordEng;
		this.wordKor = wordKor;
		// wordKor.add(mean);
		this.wordPart = wordPart;

	}

	public Word(int wordNo, String wordLevel, String wordEng, String wordKor, String wordPart) {
		super();
		this.wordNo = wordNo;
		this.wordLevel = wordLevel;
		this.wordEng = wordEng;
		this.wordKor = wordKor;
		// wordKor.add(mean);
		this.wordPart = wordPart;
	}

	public Word(int wordNo, String wordEng) {
		this.wordNo = wordNo;
		this.wordEng = wordEng;
	}

	public int getWordNo() {
		return wordNo;
	}

	public void setWordNo(int wordNo) {
		this.wordNo = wordNo;
	}

	public String getWordLevel() {
		return wordLevel;
	}

	public void setWordLevel(String wordLevel) {
		this.wordLevel = wordLevel;
	}

	public String getWordEng() {
		return wordEng;
	}

	public void setWordEng(String wordEng) {
		this.wordEng = wordEng;
	}

	public String getWordKor() {
		return wordKor;
	}

	public void setWordKor(String wordKor) {
		this.wordKor = wordKor;
	}

	public String getWordPart() {
		return wordPart;
	}

	public void setWordPart(String wordPart) {
		this.wordPart = wordPart;
	}



	public List<String> wordSeperate(String str) {
		// 뜻이 여러개 일 결우 각각 분리
		List<String> wordMean = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(str, ",");
		while (token.hasMoreTokens()) {
			wordMean.add(token.nextToken().trim());
		}
		return wordMean;
	}

	/*
	 * public Word(int wordNO, String wordLevel, String wordEng, String wordKor,
	 * String) {
	 * 
	 * }
	 */

}
