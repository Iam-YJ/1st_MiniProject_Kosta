
package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.WordDAO;
import dao.WordDAOImpl;
import dto.Word;
import exception.NotFoundException;
import util.DbUtil;

public class WordService {
	static WordDAO wd = new WordDAOImpl();

	/**
	 * 단어 추가 
	 * */
	public void wordInsert(Word word) throws SQLException {
		int result = wd.wordInsert(word);
		if (result == 0)
			throw new SQLException("등록되지 않았습니다..");

	}
	/**
	 * 단어 삭제
	 */
	public void wordDelete(Word word) throws SQLException {
		int result = wd.wordDelete(word);
		if (result == 0)
			throw new SQLException("삭제되지 않았습니다..");
	}
	
	
	/**
	 * 단어 검색 (전체 단어 순회)
	 */
	public List<Word> wordSelect() throws SQLException	{ //전체단어 검색
		List<Word> list = wd.wordSelect();
		if (list.size() == 0 || list == null) {
			throw new SQLException("단어장을 불러오지 못했습니다");
		}
		return list;
	}

	/**
	 * 단어 검색 (단어로 검색 )
	 */
	public List<Word> wordSelectByWord(String eng) throws SQLException { //특정단어 검색
		List<Word> list = wd.wordSelectByWord(eng);
		if (list.size() == 0 || list == null) {
			throw new SQLException("단어장을 불러오지 못했습니다");
		}
		return list;
	}

	/**
	 * 단어 검색 (알파벳 검색)
	 */
	public List<Word> wordSelectByAlphabet(String alphabet) throws SQLException { //알파벳검색
		List<Word> list = wd.wordSelectByAlphabet(alphabet);
		if (list.size() == 0 || list == null) {
			throw new SQLException("단어장을 불러오지 못했습니다");
		}
		return list;
	}
	

	
	/**
	 * 단어 시험(주관식 랜덤 10개)
	 */
	public static boolean wordTest(int wordNo, String answer) throws SQLException {
		boolean flag = wd.wordTest(wordNo, answer);
		if (flag == false) {
			throw new SQLException("오답입니다");
		}
		return flag;
	}
	
	/**
	 * 단어시험(레벨별)
	 */
	public static boolean wordTestByLevel(int wordNo, String wordLevel, String answer) throws SQLException{
		boolean flag = wd.wordTestByLevel(wordNo, wordLevel, answer);
		if (flag == false) {
			throw new SQLException("오답입니다");
		}
		return flag;
	}
	
	/**
	 * 단어 검색 (단어 넘버로)
	 */
	public static List<Word> wordSelectByWordNo(int userNo) throws SQLException {
		List<Word> list = wd.wordSelectByWordNo(userNo);
		
		if (list.size() == 0 || list == null) {
			throw new SQLException("단어장을 불러오지 못했습니다");
		}
		return list;
	}
}

