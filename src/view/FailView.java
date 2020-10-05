package view;

import gui.GUIMainView;

public class FailView {
	
	/**
	 * 예외가 발생했을때 예외메세지 출력
	 */
	public static void errorMessage(String message){
		GUIMainView.appendConsoleField(message);
	}
	
}
