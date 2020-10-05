
package dao;

import java.sql.SQLException;
import java.util.List;

import dto.Word;

public interface WordDAO {

	/**
	 * 검색기능 (전체검색)
	 */
	public List<Word> wordSelect() throws SQLException; // 전체검색(전체단어검색)

	/**
	 * 검색기능 (단어로 검색)
	 */
	public List<Word> wordSelectByWord(String eng) throws SQLException; // 검색(단어검색)

	/**
	 * 검색기능 (알파벳으로 검색)
	 */
	public List<Word> wordSelectByAlphabet(String alphabet) throws SQLException; // 검색(알파벳검색)

	/**
	 * 단어 추가하기 (관리자)
	 */
	int wordInsert(Word word) throws SQLException;

	/**
	 * 단어 삭제하기 (관리자)
	 */
	int wordDelete(Word word) throws SQLException;

	/**
	 * 단어 시험 (주관식 랜덤 10문)
	 */
	public boolean wordTest(int wordNo, String answer) throws SQLException;

	/**
	 * 단어 시험(레벨별)
	 */
	public boolean wordTestByLevel(int wordNo, String wordLevel, String answer) throws SQLException;

	/**
	 * 단어 불러오기 (레벨별)
	 */
	public List<Word> wordSelectByLevel(String level) throws SQLException;
	
	/**
	 * 단어 불러오기 (단어 넘버로)
	 */
	public List<Word> wordSelectByWordNo(int userNo) throws SQLException;

	/**
	 * 검색 기능 (한글)
	 */
	public List<Word> wordSelectByWordKor(String kor) throws SQLException;

	/**
	 * 검색 기능 (개인 단어)
	 */
	public List<Word> wordSelectByUserNo(int userNo) throws SQLException;

	/**
	 * 오답 노트 초기화
	 */
	public void wordResetByUserNo(int userNo) throws SQLException;


}
