

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Desktop {
	public static Point mouse;
	Clip music;
	BufferedImage wallpaper;
	public static ArrayList<Window> windows = new ArrayList<>();
	public static PopupDialog popup;
	public static Notification notification;
	public static ProgramLoader programLoader;
	public static boolean BSODTime;
	
	public Desktop(){
		try {
			wallpaper = ImageIO.read(getClass().getResource("/res/"+"wallpaper.jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playLogin();
	}
	
	public void playLogin(){
		try {
			music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"login.wav")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		music.loop(0);
	}
	
	public void mouseMoved(MouseEvent e){
		if (e!=null)
			mouse = e.getPoint();
		
		if (windowSelected!=-1){
			windows.get(windowSelected).x = windowPressedXY.x - (windowPressedPoint.x-e.getPoint().x);
			windows.get(windowSelected).y = windowPressedXY.y - (windowPressedPoint.y-e.getPoint().y);
		}
	}
	
	boolean mouseDown;
	int windowSelected = -1;
	Point windowPressedPoint;
	Point windowPressedXY;
	public void mousePressed(MouseEvent e){
			mouseDown = true;
			
			for (int win =windows.size(); win>0; win--){
				if (windows.get(win-1).isTouchingTopBar(e.getPoint())){
					touchingWindow = true;
					windowSelected = win-1;
					windowPressedPoint = e.getPoint();
					windowPressedXY = new Point(windows.get(win-1).x, windows.get(win-1).y);
					break;
				}
			}
	}
	
	public void mouseReleased(MouseEvent e){
		mouseDown = false;
		windowSelected = -1;
	}
	
	boolean touchingWindow;
	public void mouseClicked(MouseEvent e){
		touchingWindow = false;
		if (pannel.isStartButtonFocused())
			MetroMenu.toggleMainMenu();
		
		if (notification!=null && notification.isTouching(e.getPoint()))
			notification = null;
		
		if (popup==null){
			if (!metroMenu.isOpen()){
				for (int win =windows.size(); win>0; win--){
					if (windows.get(win-1).isTouchingExit(e.getPoint())){
						windows.get(win-1).closing();
						windows.remove(win-1);
						touchingWindow = true;
						break;
					}
					
					if (windows.get(win-1).isTouchingWindow(e.getPoint())){
						Collections.swap(windows, win-1, windows.size()-1);
						touchingWindow = true;
						break;
					}
				}
				
				if (!touchingWindow){
				
					for (App a:standardDesktop.apps){
						if (a.isTouchingApp(e.getPoint()))
							a.clicked();
					}
				
				}else{
					if (windows.size()>0)
						windows.get(windows.size()-1).mouseClicked(e);
				}
			}else{
				metroMenu.mouseClicked(e);
				
			}
		}else{
			if (popup.isTouchingOK(e.getPoint()))
				popup.okClicked();
		}
				
	}
	
	public void keyPressed(KeyEvent ev){
		
		if (!metroMenu.isOpen()){
			
			if (windows.size()>0){
				windows.get(windows.size()-1).keyPressed(ev);
			}
			
		}
		
		System.out.println(ev.getKeyCode());
		
		if (BSODTime){
			if (ev.getKeyCode() == KeyCodes.ENTER_KEY){
				popup = null;
				metroMenu.shutdown = false;
				metroMenu.shutDownMusic = null;
				BSODTime = false;
				ShutdownSequence.reset();
				playLogin();
			}
		}
	}
		
	public void keyReleased(KeyEvent ev) {

	}
	
	public void update() {
	}

	int size =50;
	BasicStroke stroke;
	Pannel pannel = new Pannel();
	MetroMenu metroMenu = new MetroMenu();
	StandardDesktop standardDesktop = new StandardDesktop();
	
	public void draw(Graphics2D g) {
		
		if (AppData.notifiedMalware == false && Math.random()>.999){
			notification = new Notification("malware");
			AppData.notifiedMalware = true;
		}
		
		if (AppData.notifiedVirus == false && notification == null && Math.random()>.999){
			notification = new Notification("virus");
			AppData.notifiedVirus = true;
		}
		
		if (AppData.notifiedVirus && AppData.notifiedVirus2 == false && notification == null && Math.random()>.999){
			notification = new Notification("virus2");
			AppData.notifiedVirus2 = true;
		}
		
		if (stroke==null){
			stroke = new BasicStroke(0);
			g.setStroke(stroke);//set line width
			
			g.setRenderingHint(
			        RenderingHints.KEY_ANTIALIASING,
			        RenderingHints.VALUE_ANTIALIAS_ON); // Antialias to make graphics look nicer
			g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
			
		}
		
		
		if (BSODTime){
			ShutdownSequence.drawBSOD(g, Desktop.this);
		}else{
		
			if (!metroMenu.isOpen()){
				g.drawImage(wallpaper, 0, 0, WindowFrame.w, WindowFrame.h, null);
				standardDesktop.draw(g);
				for (int win = 0; win<windows.size(); win++){
					windows.get(win).draw(g);
				}
				if (programLoader!=null){
					programLoader.drawLoader(g);
				}
			}
				
			metroMenu.drawMainMenu(g);
			
			if (MetroMenu.metroWindow==null)
				pannel.drawPannel(g);
			
			if (popup!=null){
				popup.drawPopup(g);
			}
			
			if (notification!=null)
				notification.drawNotification(g);
		
		}
		
	}
	
	
	
	class Pannel{
		BufferedImage startButton;
		BufferedImage startButtonFocused;
		BufferedImage pannel;
		
		public Pannel(){
			try {
				startButton = ImageIO.read(getClass().getResource("/res/"+"start_menu.png"));
				startButtonFocused = ImageIO.read(getClass().getResource("/res/"+"start_menu_pressed.png"));
				pannel = ImageIO.read(getClass().getResource("/res/"+"pannel_bg.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void drawPannel(Graphics2D g){
			
			g.drawImage(pannel,0, WindowFrame.h-startButton.getHeight(), WindowFrame.w, startButton.getHeight(),null);
			
			if (isStartButtonFocused())
				g.drawImage(startButtonFocused, 0, WindowFrame.h-startButton.getHeight(), null);
			else
				g.drawImage(startButton, 0, WindowFrame.h-startButton.getHeight(), null);
		}
		
		public boolean isStartButtonFocused(){
			if (mouse!=null){
				if (mouse.x>=0 && mouse.x<startButton.getWidth() && mouse.y>WindowFrame.h-startButton.getHeight() && mouse.y<WindowFrame.h)
						return true;
					else
						return false;
				
			}
			return false;
			
		}
		
	}
	
	public Point getMousePos(){
		return mouse;
	}
	
	
	
}
