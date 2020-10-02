package gui;

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
import dto.Member;

import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.graphics.Point;

public class GUIMainView {
	static boolean bSecondLayout = false;
	
    public static void main(String[] args) {
		
		// Main Display
		Display display = new Display();

		// Background Color
//		Color colorBackground = new Color(display, 242, 242, 242);
		Color colorTitle = new Color (display, 102, 153, 255);
		
		// System Font Data
		FontData systemFontData = display.getSystemFont().getFontData()[0];

		// Font Bold
		Font fontTitle = new Font(display, systemFontData.getName(), 22, SWT.BOLD);
		Font fontButton = new Font(display, systemFontData.getName(), 12, SWT.BOLD);

		// Icon Random Image
		Random rand = new Random();
		int randNum = rand.nextInt(2)+1;
		Image imageIcon = resize(new Image(display, "./src/img/icon." + randNum + ".png"), 128, 128);
		
		// Title Height
		int programWidth = 800;
		int programHeight = 1000;
		
		// Main Shell
		Shell mainShell = new Shell(display, SWT.CLOSE|SWT.TITLE|SWT.MIN|SWT.NO_REDRAW_RESIZE);
		mainShell.setText("잊혀질 단어장");
		mainShell.setBounds(40, 40, programWidth, programHeight);
		mainShell.setMinimumSize(new Point(programWidth, programHeight));
		mainShell.setImage(imageIcon);
//		mainShell.setBackground(colorBackground);
		
		int x = (display.getBounds().width - mainShell.getSize().x) / 2;
		int y = (display.getBounds().height - mainShell.getSize().y) / 2;
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
		labelTitleImage.setLayoutData(new RowData(programWidth, (mainShell.getSize().y+mainShell.getSize().y/3) - display.getBounds().height));
		labelTitleImage.setImage(imageIcon);

		final Label labelTitleText = new Label(mainShell, SWT.CENTER);
		labelTitleText.setLayoutData(new RowData(programWidth, (mainShell.getSize().y+mainShell.getSize().y/3) - display.getBounds().height));
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
		useridField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				String str = useridField.getText();
				if (str.contains("ID / E-mail"))
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
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("아이디는 공백을 포함할 수 없습니다 :(");
		            	dialogInfo.open();
	                    e.doit = false;
	                    return;
	                }
	            }
		    }
	    });
		useridField.addListener(SWT.Traverse, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event)
	        {
	            if (event.detail == SWT.TRAVERSE_RETURN)
	            {
	            	dialogInfo.setText("Login Information");
	            	dialogInfo.setMessage("TODO: Enter from USERID");
	            	dialogInfo.open();
	            }
	        }
	    });
		
		
		/*
		 * TEXT FIELD - PASSWORD
		 */
		Text passwordField = new Text(mainShell, SWT.BORDER | SWT.PASSWORD | SWT.CENTER | SWT.SINGLE);
		passwordField.setTextLimit(16);
		passwordField.setLayoutData(new RowData(352, 32));
		passwordField.setText("PASSWORD");
		passwordField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				String str = passwordField.getText();
				if (str.equals("PASSWORD"))
					passwordField.setText("");
			}
			public void focusLost(FocusEvent e) {}
		});
		passwordField.addListener(SWT.Traverse, new Listener()
	    {
	        @Override
	        public void handleEvent(Event event)
	        {
	            if (event.detail == SWT.TRAVERSE_RETURN)
	            {
	            	dialogInfo.setText("Login Information");
	            	dialogInfo.setMessage("TODO: Enter from PASSWORD");
	            	dialogInfo.open();
	            }
	        }
	    });
		
		/*
		 * Button - Login
		 */
		Button buttonLogin = new Button(mainShell, SWT.PUSH);
		buttonLogin.setText("Login");
		buttonLogin.setFont(fontButton);
		buttonLogin.setForeground(colorTitle);
		buttonLogin.setLayoutData(new RowData(200, 64));
		buttonLogin.addListener(SWT.Selection, new Listener() {
		      public void handleEvent(Event e) {
					String userid = useridField.getText();
					
					// User ID Length Check
					if (userid.length() < 3) {
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("아이디는 3자리 이상이어야 합니다.");
		            	dialogInfo.open();
		            	return;
					}
					
					// User ID Space bar Check
				    for(char c : userid.toCharArray()){
				        if (Character.isWhitespace(c)) {
			            	dialogInfo.setText("Login Information");
			            	dialogInfo.setMessage("아이디는 공백을 포함할 수 없습니다 :(");
			            	dialogInfo.open();
				        	return;
				        }
				    }
				    
					String password = passwordField.getText();

				    // User Password Length Check
					if (password.length() < 4){
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("비밀번호는 4자리 이상이어야 합니다.");
		            	dialogInfo.open();
		            	return;
					}

					// User ID & Password Check
					Member member = MemberController.login(userid, password);
					if (userid.equals("test") && password.equals("test")) {
		            	bSecondLayout = true;
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("Login: " + userid + "\nTODO: After Login UI");
		            	dialogInfo.open();
		            	return;
		      		}
					else {
		            	dialogInfo.setText("Login Information");
		            	dialogInfo.setMessage("일치하는 계정이 없습니다 " + userid);
		            	dialogInfo.open();
		            	return;
					}
				}
		      });

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
		      }
		});

		// Open Shell
		mainShell.open();
        while (! mainShell.isDisposed()) {
            if (! display.readAndDispatch()) {
                if (bSecondLayout) {
                	bSecondLayout = false;
                	buttonGuest.dispose();
                	buttonLogin.dispose();
                	labelTitleText.dispose();
                	labelTitleImage.dispose();
                	useridField.dispose();
                	passwordField.dispose();
                	
                	Button buttonReturn = new Button(mainShell, SWT.PUSH);
                	buttonReturn.setText("Main");
                	buttonReturn.setLayoutData(new RowData(160, 36));
                	buttonReturn.addListener(SWT.Selection, new Listener() {
            		      public void handleEvent(Event e) {
            	            	dialogQuestion.setText(":<");
            	            	dialogQuestion.setMessage("메인 메뉴로 돌아가시겠습니까?");
            	            	dialogQuestion.open();
            		      }
            		});

                	StyledText ConsoleField = new StyledText(mainShell, SWT.BORDER | SWT.WRAP);
            		ConsoleField.setLayoutData(new RowData(programWidth-37, programHeight-150));
            		ConsoleField.setText("Loading...");
            		ConsoleField.setLineAlignment(0, ConsoleField.getLineCount(), SWT.CENTER);
            		ConsoleField.setEditable(false);

            		Text InputField = new Text(mainShell, SWT.BORDER | SWT.FILL);
                	InputField.setLayoutData(new RowData(programWidth-80, 30));
                	InputField.setTextLimit(48);
                	InputField.setFocus();
                	
                	mainShell.requestLayout();
                }
                display.sleep();
            }
        }
        display.dispose();
    }

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
}