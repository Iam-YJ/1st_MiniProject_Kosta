package session;

import java.util.HashMap;
import java.util.Map;

/**
 * 사용자 객체
 */
public class Session {
	private String sessionId;
	private int sessionNo;
	private int count;
	private Map<String, Object> attributes; // 장바구니

	public Session() {
	}

	public Session(String sessionId) {
		this.sessionId = sessionId;
		attributes = new HashMap<>();
	}

	public Session(int sessionNo) {
		this.sessionNo = sessionNo;
		attributes = new HashMap<>();
	}

	public String getSessionId() {
		return sessionId;
	}

	// 추가
	public void setAttribute(String name, Object value) {
		attributes.put(name, value);
	}

	// 조회(Map에 Key에 해당하는 value 찾기)
	public Object getAttribute(String name) {// cart
		return attributes.get(name);
	}

	// 제거(장바구니를 비울 때 사용한다)
	public void removeAttribute(String name) {
		attributes.remove(name);
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getSessionNo() {
		return sessionNo;
	}

	public void setSessionNo(int sessionNo) {
		this.sessionNo = sessionNo;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", attributes=" + attributes + "]" + "\n";
	}

	@Override
	public int hashCode() {
		return sessionId.hashCode();
	}

	/**
	 * 같은 객체라는 뜻은 hashCode가 같아야하고, equals의 결과가 true 여야 한다.
	 * 
	 * hashCode가 다르면 무조건 다른 객체 hashCode가 같으면 같은 객체일수도, 다른 객체일수도 있다.
	 */

	@Override
	public boolean equals(Object obj) {
		Session other = (Session) obj;
		if (sessionId.equals(other.sessionId)) {
			return true;
		} else {
			return false;
		}

	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
//		return result;
//	}
//	
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Session other = (Session) obj;
//		if (sessionId == null) {
//			if (other.sessionId != null)
//				return false;
//		} else if (!sessionId.equals(other.sessionId))
//			return false;
//		return true;
//	}

}
