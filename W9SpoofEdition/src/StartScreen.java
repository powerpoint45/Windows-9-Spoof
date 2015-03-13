import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class StartScreen {
	final static int STEP_HI = 0;
	final static int STEP_WELCOME = 1;
	final static int STEP_SSN = 2;
	final static int STEP_LANGUAGE = 3;
	final static int STEP_ENTER_NAME = 4;
	final static int STEP_HELLO = 5;
	
	final static int STEP_SKIP = 5;
	
	int curImage = -1;
	
	BufferedImage setupFrame;

	
	public StartScreen(){
		nextStep();
	}
	
	public void nextStep(){
		if (curImage==STEP_HELLO)
			done();
		else{
			try {
				setupFrame = ImageIO.read(getClass().getResource("/res/"+""+(curImage+2)+".jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (curImage == STEP_WELCOME){
				loader.extraOffset = 0;
				loader.rot = 0;
			}
			curImage ++;
		}
	}

	public void done(){
		WindowFrame.displayMode = DisplayMode.MODE_DESKTOP;
		setupFrame = null;
		nexButton = null;
		loader = null;
		social = null;
		langInput = null;
		name = null;
	}
	
	
	void keyPressed(KeyEvent ev){
		System.out.println(ev.getKeyCode());
		if (curImage == STEP_ENTER_NAME){
			if (ev.getKeyCode() == KeyCodes.BACKSPACE_KEY)
				name.backspace();
			else{
				if (Character.isLetter(ev.getKeyChar()))
					name.putChar(ev.getKeyChar());
			}
		}else{
			switch (ev.getKeyCode()){
			case KeyCodes.LEFT_ARROW:
				break;
				
			case KeyCodes.RIGHT_ARROW:
				break;
				
			case KeyCodes.UP_ARROW:
				break;
				
			case KeyCodes.DOWN_ARROW:
				break;
				
			case KeyCodes.BACKSPACE_KEY:
				if (curImage == STEP_SSN)
					social.backspace();
				break;
				
			case KeyCodes.KEY_0:
			case KeyCodes.KEY_1:
			case KeyCodes.KEY_2:
			case KeyCodes.KEY_3:
			case KeyCodes.KEY_4:
			case KeyCodes.KEY_5:
			case KeyCodes.KEY_6:
			case KeyCodes.KEY_7:
			case KeyCodes.KEY_8:
			case KeyCodes.KEY_9:
				if (curImage == STEP_SSN)
					social.putChar(ev.getKeyChar());
				break;
			}
		}
	}
		
	void keyReleased(KeyEvent ev) {
		switch (ev.getKeyCode()){
		case KeyCodes.LEFT_ARROW:
			break;
			
		case KeyCodes.RIGHT_ARROW:
			break;
			
		case KeyCodes.UP_ARROW:
			break;
			
		case KeyCodes.DOWN_ARROW:
			break;
		}
	}
	
	public void mouseClicked(MouseEvent e){
		if (e!=null && e.getPoint()!=null){
			if (nexButton.isTouching(e.getPoint()))
				nextStep();
			
			if (curImage==STEP_LANGUAGE && langInput.isTouching(e.getPoint()))
				langInput.drop();
			else if (curImage==STEP_LANGUAGE)
				langInput.dropped = false;
			
			if (curImage == STEP_LANGUAGE && langInput.isTouchingEnglish(e.getPoint()))
				langInput.englishSelected();
			
			if (curImage == STEP_LANGUAGE && langInput.isTouchingOK(e.getPoint()))
				langInput.nextPopup();
		}
			
	}
	
	public void mouseMoved(MouseEvent e){
		if (e!=null && nexButton!=null && e.getPoint()!=null){
			if (nexButton.isTouching(e.getPoint()))
				nexButton.focused = true;
			else
				nexButton.focused = false;
			
			if (langInput.isTouching(e.getPoint()))
				langInput.focused=true;
			else
				langInput.focused=false;
			
			if (langInput.dropped && langInput.isTouchingEnglish(e.getPoint()))
				langInput.englishFocused = true;
			else
				langInput.englishFocused = false;
			
			if (curImage == STEP_LANGUAGE && langInput.isTouchingOK(e.getPoint()))
				System.getenv();
		}
	}
	
	NextButton nexButton = new NextButton();
	Loader loader = new Loader();
	SSNInput social = new SSNInput();
	LangInput langInput = new LangInput();
	TextInput name = new TextInput();
	int i = 0;
	public void draw(Graphics2D g){
		
		if (i == 0){
			i = 1;
			g.setRenderingHint(
			        RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
		}
		
		
		
		g.drawImage(setupFrame, 0, 0, WindowFrame.w,WindowFrame.h,null);
		
		if (curImage!=STEP_WELCOME && curImage !=STEP_SSN && curImage!=STEP_LANGUAGE && curImage !=STEP_ENTER_NAME  && curImage !=STEP_HELLO )
			nexButton.drawButton(g);
		
		if (curImage == STEP_SSN&& social.SSN.length()>8)
			nexButton.drawButton(g);
		
		if (curImage == STEP_ENTER_NAME && name.text.length()>0)
			nexButton.drawButton(g);
			
		
		if (curImage == STEP_WELCOME || curImage == STEP_HELLO){
			if (loader!=null)
				loader.draw(g);
		}
		
		if (curImage == STEP_SSN)
			social.draw(g);
		
		if (curImage == STEP_LANGUAGE)
			langInput.draw(g);
		
		if (curImage == STEP_ENTER_NAME)
			name.draw(g);
		
	}
	
	class TextInput{
		String text = "";
		
		public void putChar(char c){
			text+=c;
		}
		
		public void backspace(){
			if (text.length()>0){
				StringBuilder b = new StringBuilder(text);
				b.deleteCharAt(text.length()-1);
				text = b.toString();
			}
		}
		
		int x = WindowFrame.w/2 -250;
		int y = WindowFrame.h - 300;
		
		public void draw(Graphics2D g){
			g.setColor(Color.WHITE);
			g.fillRect(x, y, 500, 60);
			g.setColor(Color.BLACK);
			g.drawString(text, x, y+50);
		}
	}
	
	class LangInput{
		int x = WindowFrame.w/2 -100;
		int y = WindowFrame.h - 400;
		BufferedImage dropDown;
		BufferedImage dropDownPressed;
		BufferedImage english;
		BufferedImage englishPressed;
		BufferedImage chinese;
		BufferedImage spanish;
		boolean focused;
		boolean englishFocused;
		boolean dropped;
		int w;
		int h;
		
		public LangInput(){
			try {
				dropDown = ImageIO.read(getClass().getResource("/res/"+"drop_button.jpg"));
				dropDownPressed = ImageIO.read(getClass().getResource("/res/"+"drop_button_pressed.jpg"));
				english = ImageIO.read(getClass().getResource("/res/"+"english.jpg"));
				englishPressed = ImageIO.read(getClass().getResource("/res/"+"english_pressed.jpg"));
				chinese = ImageIO.read(getClass().getResource("/res/"+"chinese.jpg"));
			    spanish = ImageIO.read(getClass().getResource("/res/"+"spanish.jpg"));
				w = english.getWidth();
				h = english.getHeight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int selected = 0;
		
		public void englishSelected(){
			selected = 1;
			popup();
		}
		
		BufferedImage popup;
		BufferedImage OK;
		BufferedImage OK_pressed;
		int popupNum = 0;
		boolean okSelected;
		int dlProgress = 0;
		public void popup(){
			try {
				OK = ImageIO.read(getClass().getResource("/res/"+"ok.jpg"));
				OK_pressed = ImageIO.read(getClass().getResource("/res/"+"ok_pressed.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			nextPopup();
			
		}
		
		Clip dial;
		public void nextPopup(){
			if (popupNum == 10)
				nextStep();
			else{
				popupNum++;
				try {
					popup = ImageIO.read(getClass().getResource("/res/"+"language_select_popup"+popupNum+".jpg"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (popupNum!=2){
					try{
						Clip music = AudioSystem.getClip();
						music.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"error.wav")));
						music.loop(0);
					}catch (Exception e){
						
					}
				}else{
					try{
					dial = AudioSystem.getClip();
					dial.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"dialup.wav")));
					dial.loop(0);
					}catch (Exception e){
						
					}
				}
			
			}
		}
		
		public void draw(Graphics2D g){
			if (selected == 0)
				g.drawImage(chinese, x, y, w, h, null);
			else
				g.drawImage(english, x, y, w, h, null);
			
			if (dropped){
				g.drawImage(spanish, x, y+h, w, h, null);
				if (englishFocused)
					g.drawImage(englishPressed, x, y+h*2, w, h, null);
				else
					g.drawImage(english, x, y+h*2, w, h, null);
			}
			
			if (focused)
				g.drawImage(dropDownPressed, x+w, y, dropDown.getWidth(), dropDown.getHeight(), null);
			else
				g.drawImage(dropDown, x+w, y, dropDown.getWidth(), dropDown.getHeight(), null);
			
			if (popupNum>0){
				if (popup!=null){
					g.drawImage(popup , 0, WindowFrame.h/2 - 200, WindowFrame.w, popup.getHeight(), null);
				
					if (popupNum == 2){
						if (dial!=null && dial.getMicrosecondLength()>1000 && dial.getMicrosecondPosition()>dial.getMicrosecondLength()-1000000){
							System.out.println("DONE");
							nextPopup();
							
						}
					}
					
					if (popupNum!=3 ){
						if (popupNum!=2){
							if (okSelected)
								g.drawImage(OK_pressed , WindowFrame.w -120, WindowFrame.h/2 +70, null);
							else
								g.drawImage(OK , WindowFrame.w -120, WindowFrame.h/2 +70, null);
						}
					}else{
						dlProgress +=4;
						g.setColor(Color.WHITE);
						g.fillRect(0, WindowFrame.h/2 , dlProgress, 60);
						
						if (dlProgress>=WindowFrame.w)
							nextPopup();
					}
				}
			}
		}
		
		public void drop(){
			dropped = true;
		}
		
		public boolean isTouchingOK(Point p){
			if (popupNum>0){
				if (p.x > (WindowFrame.w -120) && p.x<(WindowFrame.w -120)+OK.getWidth() && p.y>(WindowFrame.h/2 +70) && p.y<(WindowFrame.h/2 +70)+OK.getHeight())
					okSelected = true;
				else
					okSelected = false;
			}
			
			return okSelected;
		}
		
		public boolean isTouching(Point p){
			
			
			if (p.x > x+w && p.x<x+w+dropDown.getWidth() && p.y>y && p.y<y+h)
				return true;
			else
				return false;
		}
		
		public boolean isTouchingEnglish(Point p){
			
			if (p.x > x && p.x<x+w && p.y>y+h*2 && p.y<y+h*3)
				return true;
			else
				return false;
		}
		
		
	}
	
	class SSNInput{
		int x = WindowFrame.w/2 -400;
		int y = WindowFrame.h - 200;
		
		String SSN = "";
		
		public void putChar(char c){
			if (social.SSN.length()<9)
				SSN+=c;
		}
		
		public void backspace(){
			if (SSN.length()>0){
				StringBuilder b = new StringBuilder(SSN);
				b.deleteCharAt(SSN.length()-1);
				SSN = b.toString();
			}
		}
		
		
		public void draw(Graphics2D g){
			g.setColor(Color.WHITE);
			g.fillRect(x, y, 50, 60);
			g.fillRect(x+60, y, 50, 60);
			g.fillRect(x+120, y, 50, 60);
			
			g.fillRect(x+220, y, 50, 60);
			g.fillRect(x+280, y, 50, 60);
			
			g.fillRect(x+380, y, 50, 60);
			g.fillRect(x+440, y, 50, 60);
			g.fillRect(x+500, y, 50, 60);
			g.fillRect(x+560, y, 50, 60);
			
			g.setColor(Color.BLACK);
			if (SSN.length()>0)
				g.drawString(""+SSN.charAt(0), x+10, y+49);
			if (SSN.length()>1)
				g.drawString(""+SSN.charAt(1), x+60+10, y+49);
			if (SSN.length()>2)
				g.drawString(""+SSN.charAt(2), x+120+10, y+49);
			
			if (SSN.length()>3)
				g.drawString(""+SSN.charAt(3), x+220+10, y+49);
			if (SSN.length()>4)
				g.drawString(""+SSN.charAt(4), x+280+10, y+49);
			
			if (SSN.length()>5)
				g.drawString(""+SSN.charAt(5), x+380+10, y+49);
			if (SSN.length()>6)
				g.drawString(""+SSN.charAt(6), x+440+10, y+49);
			if (SSN.length()>7)
				g.drawString(""+SSN.charAt(7), x+500+10, y+49);
			if (SSN.length()>8)
				g.drawString(""+SSN.charAt(8), x+560+10, y+49);
		
		}
		
	}
	
	class NextButton{
		BufferedImage nextButton;
		BufferedImage nextButtonFocused;
		boolean focused;
		int w;
		int h;
		
		public NextButton(){
			try {
				nextButton = ImageIO.read(getClass().getResource("/res/"+"next_button.jpg"));
				nextButtonFocused = ImageIO.read(getClass().getResource("/res/"+"next_button_pressed.jpg"));
				w = nextButton.getWidth();
				h = nextButton.getHeight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int x = WindowFrame.w-250;
		int y = WindowFrame.h-150;
		
		public void drawButton(Graphics2D g){
			if (focused)
				g.drawImage(nextButtonFocused,x ,y , w,h ,null);
			else
				g.drawImage(nextButton,x ,y , w,h ,null);
		}
		
		public boolean isTouching(Point p){
			
			if (p.x > x && p.x<x+w && p.y>y && p.y<y+h)
				return true;
			else
				return false;
		}
	}
	
	class Loader{
		double rot = 0.0;
		double extraOffset = 0.0;
		
		public void draw(Graphics2D g){
			g.setColor(Color.WHITE);
			if (rot>30)
				extraOffset+=5;
			
			if (extraOffset>1000)
				nextStep();
			
			rot+=.1;
			double xOffset = (40+extraOffset)*Math.cos(rot);
			double yOffset = (40+extraOffset)*Math.sin(rot);
			g.fillOval(WindowFrame.w/2 - 20 +(int)xOffset, WindowFrame.h/2 +100 +(int)yOffset, 10, 10);
			double xOffset1 = (40+extraOffset)*Math.cos(rot*.4);
			double yOffset1 = (40+extraOffset)*Math.sin(rot*.4);
			g.fillOval(WindowFrame.w/2 - 20 +(int)xOffset1, WindowFrame.h/2 +100 +(int)yOffset1, 10, 10);
		}
		
		
		
	}
	
	
	
	
	
	public void update(){
	}
	
	
	
	float r;
	float g;
	float b;
	Random rand = new Random(10);
	Float colorint=0f;
	Color newRandomColor(){
		//colorint+=.001f;
		r = (rand.nextFloat()/2)+.5f;
		g = (rand.nextFloat()/2)+.5f;
		b = (rand.nextFloat()/2)+.5f;
		return new Color(r,g,b);
	}
	
}
