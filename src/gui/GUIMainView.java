package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import controller.MemberController;
import controller.UserWordController;
import controller.WordController;
import dto.Admin;
import dto.Member;
import dto.UserWord;
import dto.Word;
import view.MenuView;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.graphics.Point;

public class GUIMainView {
	// Title Height
	static private final int programWidth = 800;
	static private final int programHeight = 1000;
	
	// Word Test Variables
	static private int count = 0;
	static private int score = 0;
	static private int run = 0;
	static private int life = 0;
	static private int bonus = 0;
	static Random rand = new Random();

	static boolean bSecondLayout = false;
	
	static Member loginMember;
	static Button buttonLogin;

	private static String passwordText;
	private static String useridText;

	static List<Integer> numList = new ArrayList<Integer>();
	static List<Integer> orderList = new ArrayList<Integer>();

	// Main Display and Shell
	private static Display mainDisplay = new Display();
	private static Shell mainShell = new Shell(mainDisplay, SWT.CLOSE|SWT.TITLE|SWT.MIN|SWT.NO_REDRAW_RESIZE);
	private static StyledText ConsoleField = new StyledText(mainShell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	private static void initConsoleField(){
		ConsoleField.setLayoutData(new RowData(programWidth-37, programHeight-150));
		ConsoleField.setEditable(false);
	}

	public static void ConsoleFieldVisible(boolean visible) {
		getConsoleField().setVisible(visible);
	}
	
	public static Display getMainDisplay() {
		return mainDisplay;
	}

	public static Shell getMainShell() {
		return mainShell;
	}

	public static String getPasswordText() {
		return passwordText;
	}

	public static void setPasswordText(String password) {
		passwordText = password;
	}

	public static String getUseridText() {
		return useridText;
	}

	public static void setUseridText(String userid) {
		useridText = userid;
	}

	public static StyledText getConsoleField() {
		return ConsoleField;
	}

	public static void main(String[] string) {
		ConsoleFieldVisible(false);
		// Background Color
//		Color colorBackground = new Color(display, 242, 242, 242);
		Color colorTitle = new Color (mainDisplay, 102, 153, 255);
		
		// System Font Data
		FontData systemFontData = mainDisplay.getSystemFont().getFontData()[0];

		// Font Bold
		Font fontTitle = new Font(mainDisplay, systemFontData.getName(), 22, SWT.BOLD);
		Font fontButton = new Font(mainDisplay, systemFontData.getName(), 12, SWT.BOLD);

		// Icon Random Image
		Random rand = new Random();
		int randNum = rand.nextInt(2)+1;
		Image imageIcon = resize(new Image(mainDisplay, "./src/img/icon." + randNum + ".png"), 128, 128);
		
		// Main Shell
		mainShell.setText("잊혀질 단어장");
		mainShell.setBounds(40, 40, programWidth, programHeight);
		mainShell.setMinimumSize(new Point(programWidth, programHeight));
		mainShell.setImage(imageIcon);
//		mainShell.setBackground(colorBackground);
		
		int x = (mainDisplay.getBounds().width - mainShell.getSize().x) / 2;
		int y = (mainDisplay.getBounds().height - mainShell.getSize().y) / 2;
		mainShell.setLocation(x, y);

		// Dialog
		MessageBox dialogInfo = new MessageBox(mainShell, SWT.ICON_QUESTION | SWT.OK);
		MessageBox dialogQuestion = new MessageBox(mainShell, SWT.ICON_WARNING | SWT.OK | SWT.CANCEL);
		
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		rowLayout.wrap = false;
		rowLayout.fill = false;
		rowLayout.justify = false;
		rowLayout.center = true;
		mainShell.setLayout(rowLayout);

		final Label labelTitleImage = new Label(mainShell, SWT.CENTER);
		labelTitleImage.setLayoutData(new RowData(programWidth, (mainShell.getSize().y+mainShell.getSize().y/3) - mainDisplay.getBounds().height));
		labelTitleImage.setImage(imageIcon);

		final Label labelTitleText = new Label(mainShell, SWT.CENTER);
		labelTitleText.setLayoutData(new RowData(programWidth, (mainShell.getSize().y+mainShell.getSize().y/3) - mainDisplay.getBounds().height));
		labelTitleText.setFont(fontTitle);
		labelTitleText.setForeground(colorTitle);
		labelTitleText.setImage(imageIcon);
		labelTitleText.setText("Welcome back :)");


		/*
		 * TEXT FIELD - USERID
		 */

		Text useridField = new Text(mainShell, SWT.BORDER | SWT.CENTER | SWT.SINGLE);
		useridField.setTextLimit(16);
		useridField.setLayoutData(new RowData(352, 32));
		useridField.setText("ID / E-mail");
		useridField.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) { }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (useridField.getText().isEmpty()) {
		        	useridField.setText("ID / E-mail");
		        }
		    }
	    });
		useridField.addListener(SWT.Traverse, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event) {
	            if (event.detail == SWT.TRAVERSE_RETURN){
	            	loginCheck();
	            }
	        }
	    });
		useridField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String str = useridField.getText();
				if (str.equals("ID / E-mail"))
					useridField.setText("");
			}
		});
		useridField.addVerifyListener(new VerifyListener() {
		    @Override
		    public void verifyText(VerifyEvent e) {
		    	if (e.text.length() > 0) {
		    		String str = useridField.getText();
		    		if (str.contains("ID / E-mail"))
		    			useridField.setText("");
		    	}
	            for (int i = 0; i < e.text.length(); i++) {
	                if (Character.isWhitespace(e.text.charAt(i))) {
	                	if (e.text.equals("ID / E-mail")) break;
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("아이디는 공백을 포함할 수 없습니다 :(");
		            	dialogInfo.open();
	                    e.doit = false;
	                    return;
	                }
	            }
	            setUseridText(useridField.getText() + e.text);
		    }
	    });
		
		
		/*
		 * TEXT FIELD - PASSWORD
		 */
		Text passwordField = new Text(mainShell, SWT.BORDER | SWT.PASSWORD | SWT.CENTER | SWT.SINGLE);
		passwordField.setTextLimit(16);
		passwordField.setLayoutData(new RowData(352, 32));
		passwordField.setText("PASSWORD");
		passwordField.addVerifyListener(new VerifyListener() {
		    @Override
		    public void verifyText(VerifyEvent e) {
	            setPasswordText(passwordField.getText() + e.text);
		    }
	    });
		passwordField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String str = passwordField.getText();
				if (str.equals("PASSWORD"))
					passwordField.setText("");
			}
			public void focusLost(FocusEvent e) {
				if (passwordField.getText().isEmpty()) {
					passwordField.setText("PASSWORD");
				}
			}
		});
		passwordField.addListener(SWT.Traverse, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event){
	            if (event.detail == SWT.TRAVERSE_RETURN){
	            	loginCheck();
	            }
	        }
	    });
		
		/*
		 * Button - Login
		 */
		buttonLogin = new Button(mainShell, SWT.PUSH);
		buttonLogin.setText("Login");
		buttonLogin.setFont(fontButton);
		buttonLogin.setForeground(colorTitle);
		buttonLogin.setLayoutData(new RowData(200, 64));
		buttonLogin.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
	            	loginCheck();
				}
		      }
		);

       	/*
       	 * Button - Guest
       	 */
		Button buttonGuest = new Button(mainShell, SWT.PUSH);
		buttonGuest.setText("Guest");
		buttonGuest.setLayoutData(new RowData(200, 40));
		buttonGuest.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
	            	bSecondLayout = true;
	            	dialogInfo.setText("Login Information");
	            	dialogInfo.setMessage("Guest Login");
	            	dialogInfo.open();
        			MenuView.printNonUserMenu();
		      }
		});

       	/*
       	 * Button - Guest
       	 */
		Button buttonRegister = new Button(mainShell, SWT.PUSH);
		buttonRegister.setText("Register");
		buttonRegister.setLayoutData(new RowData(200, 40));
		buttonRegister.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
		    	  registerCheck();
		      }
		});

		// Open Shell
		mainShell.open();
        while (! mainShell.isDisposed() && ! mainDisplay.isDisposed()) {
            if (! mainDisplay.readAndDispatch()) {
                if (bSecondLayout) {
                	bSecondLayout = false;
                	buttonRegister.dispose();
                	buttonGuest.dispose();
                	buttonLogin.dispose();
                	labelTitleText.dispose();
                	labelTitleImage.dispose();
                	useridField.dispose();
                	passwordField.dispose();
            		initConsoleField();
                	
                	Button buttonReturn = new Button(mainShell, SWT.PUSH);
                	buttonReturn.moveAbove(ConsoleField);
                	buttonReturn.setText("Main");
                	buttonReturn.setLayoutData(new RowData(160, 36));
                	buttonReturn.addListener(SWT.Selection, new Listener() {
            		      public void handleEvent(Event e) {
            		    	  if (MenuView.CurrentMenu > 2 && MenuView.CurrentMenu != 80 && MenuView.CurrentMenu != 90) {
            	            	dialogQuestion.setText(":<");
            	            	dialogQuestion.setMessage("메인 메뉴로 돌아가시겠습니까?");
            	            	int result = dialogQuestion.open();
            	            	if (result == SWT.OK) {
            	            		if (loginMember == null)
            	            			MenuView.printNonUserMenu();
            	            		else if (Admin.getUserNo() == loginMember.getUserNo())
            	            			MenuView.printAdminMenu();
            	            		else
            	            			MenuView.printUserMenu(loginMember.getUserId(), loginMember.getUserNo());
            	            		}
            		    	  }
            		      }
            		});

            		ConsoleFieldVisible(true);

            		Text InputField = new Text(mainShell, SWT.BORDER | SWT.FILL);
                	InputField.setLayoutData(new RowData(300, 30));
                	InputField.setTextLimit(48);
                	InputField.setFocus();
                	InputField.addListener(SWT.Traverse, new Listener()
            	    {
            	        @Override
            	        public void handleEvent(Event event) {
            	            if (event.detail == SWT.TRAVERSE_RETURN && InputField.getText().length() > 0){
            	            	if (MenuView.CurrentMenu > 0 && MenuView.CurrentMenu != 20) appendConsoleField("\"" + InputField.getText() + "\"");
            	            	else {
            	            	}
            	            	String text = InputField.getText();
            	            	InputField.setText("");
            	            	checkEvent(text);
            	            }
            	        }
            	    });

                	mainShell.requestLayout();
                }
                mainDisplay.sleep();
            }
        }
        mainDisplay.dispose();
    }

    private static void registerCheck() {
		MessageBox dialogInfo = new MessageBox(mainShell, SWT.ICON_QUESTION | SWT.OK);

		if (useridText == null || passwordText == null || useridText.equals("ID / E-mail") || passwordText.equals("PASSWORD")) {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("아이디와 비밀번호를 입력해주세요.");
        	dialogInfo.open();
        	return;
		}
		// User ID Length Check
		if (useridText.length() < 3) {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("아이디는 3자리 이상이어야 합니다.");
        	dialogInfo.open();
        	return;
		}

	    // User Password Length Check
		if (passwordText.length() < 4){
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("비밀번호는 4자리 이상이어야 합니다.");
        	dialogInfo.open();
        	return;
		}

		// User ID & Password Check
		if (registerUser(useridText, passwordText)) {
        	dialogInfo.setText("Registration Successful");
        	dialogInfo.setMessage("User: " + useridText + "\n등록하신 아이디로 로그인하실 수 있습니다.");
        	dialogInfo.open();
        	return;
		}
		else {
        	dialogInfo.setText("Registration Failed");
        	dialogInfo.setMessage("User: " + useridText + "\n이미 존재하는 아이디 입니다.");
        	dialogInfo.open();
        	return;
		}
	}

    private static void loginCheck() {
		MessageBox dialogInfo = new MessageBox(mainShell, SWT.ICON_QUESTION | SWT.OK);

		if (useridText == null || passwordText == null || useridText.equals("ID / E-mail") || passwordText.equals("PASSWORD")) {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("아이디와 비밀번호를 입력해주세요.");
        	dialogInfo.open();
        	return;
		}
		// User ID Length Check
		if (useridText.length() < 3) {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("아이디는 3자리 이상이어야 합니다.");
        	dialogInfo.open();
        	return;
		}

	    // User Password Length Check
		if (passwordText.length() < 4){
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("비밀번호는 4자리 이상이어야 합니다.");
        	dialogInfo.open();
        	return;
		}

		// User ID & Password Check
		loginUser(useridText, passwordText);
		if (loginMember != null) {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("User: " + useridText + "\n로그인 되었습니다.");
        	dialogInfo.open();
        	bSecondLayout = true;
        	return;
  		}
		else {
        	dialogInfo.setText("Login Information");
        	dialogInfo.setMessage("아이디 또는 비밀번호가 틀렸습니다");
        	dialogInfo.open();
        	return;
		}
	}

	private static void checkEvent(String text) {
		if (MenuView.CurrentMenu == 1) { // MenuView.printUserMenu - 일반 유저 메뉴
			try {
				int SelectedMenu = Integer.parseInt(text);
				switch(SelectedMenu) {
					case 1: // 전체 검색
						MenuView.CurrentMenu = 10;
						MenuView.printAllWord();
						break;
						
					case 2: // 단어 시험
						count = 1;
						score = 0;
						run = 0;
						MenuView.CurrentMenu = 20;
						MenuView.wordTest(loginMember.getUserNo());
						break;
						
					case 3: // 단어 게임
						count = 0;
						score = 0;
						run = 0;
						life = 5;
						bonus = 0;
						MenuView.CurrentMenu = 21;
						MenuView.wordGame(loginMember.getUserNo());
						break;
	
					case 4: // 랭킹
						MenuView.CurrentMenu = 70;
						MemberController.rank();
						break;
						
					case 5: // 단어 추가
						MenuView.CurrentMenu = 30;
						MenuView.printInputWord(loginMember.getUserNo());
						break;
						
					case 6: // 단어 삭제
						MenuView.CurrentMenu = 40;
						MenuView.printDeleteWord(loginMember.getUserNo());
						break;
						
					case 7: // 오답 노트
						MenuView.CurrentMenu = 50;
						WordController.wordSelectByWordNo(loginMember.getUserNo());
						break;
						
					case 8: // 오답 노트 초기화
						MenuView.CurrentMenu = 60;
						setConsoleField("===== 오답 노트 초기화 =====");
						WordController.resetTestNote(loginMember.getUserNo());
						break;
		
					default: appendConsoleField("일치하는 메뉴가 없습니다.");
				}
			} catch (NumberFormatException e) {
				appendConsoleField("숫자만 입력 가능합니다.");
			}
		} else if (MenuView.CurrentMenu == 10) { // MenuView.printAllWord - 전체 출력 선택지 메뉴
			try {
				int SelectedMenu = Integer.parseInt(text);
				switch(SelectedMenu) {
					case 1:
						MenuView.CurrentMenu = 11;
						setConsoleField("===== 전체 검색 =====");
						WordController.wordSelect();
						return;
		
					case 2:
						MenuView.CurrentMenu = 12;
						appendConsoleField("검색할 영단어를 입력하세요 ");
						return;
						
					case 3:
						MenuView.CurrentMenu = 13;
						appendConsoleField("검색할 알파벳을 입력하세요 ");
						return;
						
					case 4:
						MenuView.CurrentMenu = 14;
						appendConsoleField("검색할 한글을 입력하세요 ");
						return;	
						
					case 5:
						MenuView.CurrentMenu = 15;
						setConsoleField("===== 개인 단어 검색 =====");
						WordController.wordSelectByUser(loginMember.getUserNo());
						return;
		
					default:
						appendConsoleField("일치하는 메뉴가 없습니다.");
						break;
				}
			} catch (NumberFormatException e) {
				appendConsoleField("숫자만 입력 가능합니다.");
			}
		} else if (MenuView.CurrentMenu == 12) { // WordController.wordSelectByWord - 영단어 검색
			if (text.matches("^[a-zA-Z]*$")) {
				setConsoleField("===== \"" + text + "\" 에 대한 영단어 검색 =====");
				WordController.wordSelectByWord(text.toLowerCase());
			}
			else {
				appendConsoleField("영문만 입력 가능합니다");
			}
		} else if (MenuView.CurrentMenu == 13) { // WordController.wordSelectByAlphabet - 알파벳 검색
			if (text.length() == 1 && text.matches("^[a-zA-Z]*$")) {
				setConsoleField("===== \"" + text + "\" 에 대한 알파벳 검색 =====");
				WordController.wordSelectByAlphabet(text);
			}
			else {
				appendConsoleField("한 글자의 알파벳만 입력 가능합니다");
			}
		} else if (MenuView.CurrentMenu == 14) { // WordController.wordSelectByWordKor - 한글 검색
			if (!text.matches("^[a-zA-Z0-9]*$")) {
				setConsoleField("===== \"" + text + "\" 에 대한 한글 검색 =====");
				WordController.wordSelectByWordKor(text);
			}
			else {
				appendConsoleField("영문과 숫자는 입력할 수 없습니다");
			}
		} else if (MenuView.CurrentMenu == 80) { // MenuView.printNonUserMenu - Guest 메뉴
			try {
				int SelectedMenu = Integer.parseInt(text);
				switch(SelectedMenu) {
					case 1: // 전체 검색
						MenuView.CurrentMenu = 81;
						MenuView.printAllWordGuest();
						break;
						
					case 2: // 단어 시험
						count = 1;
						score = 0;
						run = 0;
						MenuView.CurrentMenu = 20;
						MenuView.wordTest(0);
						break;
						
					case 3: // 단어 게임
						count = 0;
						score = 0;
						run = 0;
						life = 5;
						bonus = 0;
						MenuView.CurrentMenu = 21;
						MenuView.wordGame(0);
						break;
		
					default: appendConsoleField("일치하는 메뉴가 없습니다.");
				}
			} catch (NumberFormatException e) {
				appendConsoleField("숫자만 입력 가능합니다.");
			}
		} else if (MenuView.CurrentMenu == 81) { // MenuView.printAllWordGuest - Guest 전체 출력 선택지 메뉴
			try {
				int SelectedMenu = Integer.parseInt(text);
				switch(SelectedMenu) {
					case 1:
						MenuView.CurrentMenu = 11;
						setConsoleField("===== 전체 검색 =====");
						WordController.wordSelect();
						return;
		
					case 2:
						MenuView.CurrentMenu = 12;
						appendConsoleField("검색할 영단어를 입력하세요 ");
						return;
						
					case 3:
						MenuView.CurrentMenu = 13;
						appendConsoleField("검색할 알파벳을 입력하세요 ");
						return;
						
					case 4:
						MenuView.CurrentMenu = 14;
						appendConsoleField("검색할 한글을 입력하세요 ");
						return;	
						
					case 5:
						MenuView.CurrentMenu = 15;
						setConsoleField("===== 개인 단어 검색 =====");
						WordController.wordSelectByUser(loginMember.getUserNo());
						return;
		
					default:
						appendConsoleField("일치하는 메뉴가 없습니다.");
						break;
				}
			} catch (NumberFormatException e) {
				appendConsoleField("숫자만 입력 가능합니다.");
			}
		} else if (MenuView.CurrentMenu == 90) { // MenuView.printAdminMenu - Administrator 메뉴
			try {
				int SelectedMenu = Integer.parseInt(text);
				switch(SelectedMenu) {
				case 1:
					MenuView.CurrentMenu = 81;
					MenuView.printInputAdminWord();
					break;
	
				case 2:
					MenuView.CurrentMenu = 82;
					MenuView.printDeleteAdminWord();
					break;
	
				case 3:
					MenuView.CurrentMenu = 83;
					MemberController.rank();
					break;
	
				case 4:
					MenuView.CurrentMenu = 84;
					MenuView.printUpdateAdminMember();
					break;
	
				case 5:
					MenuView.CurrentMenu = 85;
					MenuView.printDeleteAdminMember();
					break;
		
					default: appendConsoleField("일치하는 메뉴가 없습니다.");
				}
			} catch (NumberFormatException e) {
				appendConsoleField("숫자만 입력 가능합니다.");
			}
		}
	}

	public static void resetConsoleField() {
    	ConsoleField.setText("");
    }

	public static void appendConsoleField(String text) {
		text = "\n" + text;
    	ConsoleField.append(text);
    	ConsoleField.setTopIndex(ConsoleField.getLineCount() - 1);
		ConsoleField.setLineAlignment(0, ConsoleField.getLineCount(), SWT.CENTER);
    }

	public static void setConsoleField(String text) {
    	ConsoleField.setText(text);
		ConsoleField.setLineAlignment(0, ConsoleField.getLineCount(), SWT.CENTER);
    }

	public static boolean registerUser(String userid, String password) {
    	return MemberController.register(userid, password, userid);
    }

	public static void loginUser(String userid, String password) {
    	loginMember = MemberController.login(userid, password);
    }

	/** 이미지 리사이징
	 * 
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
    static Image resize(Image image, int width, int height) {
    	Image scaled = new Image(Display.getDefault(), width, height);
    	GC gc = new GC(scaled);
    	gc.setAntialias(SWT.ON);
    	gc.setInterpolation(SWT.HIGH);
    	gc.drawImage(image, 0, 0,
    	image.getBounds().width, image.getBounds().height,
    	0, 0, width, height);
    	gc.dispose();
    	image.dispose(); // don't forget about me!
    	return scaled;
	}
    
    public static String getInputDialog(String title, String desc) {
    	String result = "";
        final Shell shell = new Shell(mainDisplay, SWT.TITLE | SWT.BORDER | SWT.APPLICATION_MODAL | SWT.CENTER);
        shell.setText(title);
		shell.setLocation(800, 400);

        shell.setLayout(new GridLayout(2, true));
        shell.setLayoutData(new GridData(300, 300));

        Label label = new Label(shell, SWT.NULL);
        label.setText(desc);

        final Text text = new Text(shell, SWT.SINGLE | SWT.BORDER | SWT.CENTER);
        text.setTextLimit(16);
        text.setLayoutData(new GridData(200, 30));
        text.addListener(SWT.Traverse, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event) {
	            if (event.detail == SWT.TRAVERSE_RETURN && text.getText().length() > 0){
	            	appendConsoleField("\"" + text.getText() + "\"");
	            	shell.dispose();
	            }
	        }
	    });

        shell.pack();
        shell.open();

        while (!shell.isDisposed()) {
          if (!mainDisplay.readAndDispatch()) {
            result = text.getText();
            mainDisplay.sleep();
          }
        }
        shell.dispose();
        return result;
    }

	/** 
	 * 단어 시험 (전체 DB 참조)
	 * @param list
	 * @param userNo
	 */
	public static void wordTest(List<Word> list, int userNo) {
		List<Integer> orderList = GUIMainView.makeRandom(10-run, list.size());

		for (Integer i : orderList) {
			GUIMainView.appendConsoleField("문제 " + count + "번");
			GUIMainView.appendConsoleField(list.get(i).getWordEng() + "의 뜻은?");
			System.out.println("[DEBUG] 정답 >> " + list.get(i).getWordKor());

			if (WordController.getAnswer(list.get(i).getWordNo(), getInputDialog("전체 단어 [" + count + " / 10]", list.get(i).getWordEng() + "의 뜻은?"))) {
				GUIMainView.appendConsoleField("\n정답입니다\n____________________________________________________________________________________\n\n");
				score++;
			} else {
				MemberController.insertTest(userNo, null, list.get(i).getWordLevel(), score, 10 - score,
																		Integer.toString(list.get(i).getWordNo()));
				GUIMainView.appendConsoleField("\n틀렸습니다\n____________________________________________________________________________________\n\n");
			}
			count++;
		}
		if (userNo > 0) {
			MemberController.updatePoint(userNo, score);
			loginMember.setPoints(loginMember.getPoints() + score);
			GUIMainView.appendConsoleField("고생하셨습니다.\n총 " + score + " 문제 맞추셨으며 누적 점수는 " + loginMember.getPoints() + " 포인트 입니다");
		}
		else GUIMainView.appendConsoleField("고생하셨습니다.\n총 " + score + " 문제 맞추셨습니다");
	}

	/**
	 * 단어 시험 (개인DB 참조)
	 * @param list
	 */
	public static void userWordTest(List<UserWord> list) {
		if (list.size() < 1) return;
		while (true) {
			run = rand.nextInt(10) + 1;
			if (run <= list.size() && run > 0) {
				break;
			}
		}

		List<Integer> orderList = GUIMainView.makeRandom(run, list.size());

		for (Integer i : orderList) {
			GUIMainView.appendConsoleField("(개인 단어) 문제 " + count + "번");

			GUIMainView.appendConsoleField(list.get(i).getUserEng() + "의 뜻은?");

			System.out.println("[DEBUG] 정답 >> " + list.get(i).getUserKor());
			
			if (UserWordController.getAnswer(list.get(i).getUserWordNo(), getInputDialog("개인 단어 [" + count + " / 10]", list.get(i).getUserEng() + "의 뜻은?"))) {
				GUIMainView.appendConsoleField("\n정답입니다\n____________________________________________________________________________________\n\n");
				score++;
			} else {
				MemberController.insertTest(list.get(i).getUserNo(), null, list.get(i).getUserLevel(), score,
						10 - score, Integer.toString(list.get(i).getUserWordNo()));
				GUIMainView.appendConsoleField("\n틀렸습니다\n____________________________________________________________________________________\n\n");
			}
			count++;
		}
	}

	/** 
	 * 단어 게임 (전체 DB 참조)
	 * @param list
	 * @param userNo
	 */
	public static int wordGame(List<Word> list, int userNo) {
		List<Integer> orderList = GUIMainView.makeRandom(1, list.size());

		for (Integer i : orderList) {
			count++;
			GUIMainView.appendConsoleField("문제 " + count + "번  [라이프 " + life + " / 5]");
			GUIMainView.appendConsoleField(list.get(i).getWordEng() + "의 뜻은?");
			System.out.println("[DEBUG] 정답 >> " + list.get(i).getWordKor());

			if (WordController.getAnswer(list.get(i).getWordNo(), getInputDialog("전체 단어 [" + count + "]", list.get(i).getWordEng() + "의 뜻은?"))) {
				GUIMainView.appendConsoleField("\n정답입니다\n____________________________________________________________________________________\n\n");
				score++;
				bonus++;
				if (bonus >= 5) {
					bonus = 0;
					if (life < 5)
						GUIMainView.appendConsoleField("5문제 라이프 보너스 !\n" + "[라이프 " + ++life + " / 5]\n\n____________________________________________________________________________________\n\n");
				}
			} else {
				life--;
				if (userNo > 0) MemberController.insertTest(userNo, null, list.get(i).getWordLevel(), score, count - score,
																		Integer.toString(list.get(i).getWordNo()));
				GUIMainView.appendConsoleField("\n틀렸습니다\n____________________________________________________________________________________\n\n");
			}
			break;
		}
		if (life == 0) {
			if (userNo > 0) {
				loginMember.setPoints(loginMember.getPoints() + score);
				MemberController.updatePoint(loginMember.getUserNo(), score);
				GUIMainView.appendConsoleField("고생하셨습니다.\n총 " + score + " 문제 맞추셨으며 누적 점수는 " + loginMember.getPoints() + " 포인트 입니다");
			}
			else GUIMainView.appendConsoleField("고생하셨습니다.\n총 " + score + " 문제 맞추셨습니다");
		}
		return life;
	}

	/**
	 * 단어 게임 (개인DB 참조)
	 * @param list
	 */
	public static int userWordGame(List<UserWord> list) {
		if (list.size() < 1) return 0;

		List<Integer> orderList = GUIMainView.makeRandom(1, list.size());

		for (Integer i : orderList) {
			count++;
			GUIMainView.appendConsoleField("(개인 단어) 문제 " + count + "번  [라이프 " + life + " / 5]");
			GUIMainView.appendConsoleField(list.get(i).getUserEng() + "의 뜻은?");
			System.out.println("[DEBUG] 정답 >> " + list.get(i).getUserKor());
			
			if (UserWordController.getAnswer(list.get(i).getUserWordNo(), getInputDialog("개인 단어 [" + count + "]", list.get(i).getUserEng() + "의 뜻은?"))) {
				GUIMainView.appendConsoleField("\n정답입니다\n____________________________________________________________________________________\n\n");
				score++;
				bonus++;
				if (bonus >= 5) {
					bonus = 0;
					if (life < 5)
						GUIMainView.appendConsoleField("5문제 라이프 보너스 !\n" + "[라이프 " + ++life + " / 5]\n\n____________________________________________________________________________________\n\n");
				}
			} else {
				life--;
				MemberController.insertTest(list.get(i).getUserNo(), null, list.get(i).getUserLevel(), score,
						count - score, Integer.toString(list.get(i).getUserWordNo()));
				GUIMainView.appendConsoleField("\n틀렸습니다\n____________________________________________________________________________________\n\n");
			}
			if (life == 0) {
				loginMember.setPoints(loginMember.getPoints() + score);
				MemberController.updatePoint(loginMember.getUserNo(), score);
				GUIMainView.appendConsoleField("고생하셨습니다.\n총 " + score + " 문제 맞추셨으며 누적 점수는 " + loginMember.getPoints() + " 포인트 입니다");
			}
			break;
		}
		return life;
	}

	/**
	 * 단어시험용 랜덤 10개 뽑기
	 * @param run
	 * @param size
	 * @return
	 */
	public static List<Integer> makeRandom(int run, int size) {
		numList.clear();
		for (int i = 0; i < run; i++) {
			int number = rand.nextInt(size);
			if (!numList.contains(number)) {
				numList.add(number);
			} else {
				i--;
			}
		}

		return numList;
	}
}