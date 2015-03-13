import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class Window {
	int x,y;
	int w,h;
	BufferedImage contents;
	String name;
	final static Color LIGHT_BLUE = new Color(135, 205, 255);
	final static Color LIGHT_RED = new Color(255, 71, 71);
	final static Color LIGHTER_RED = new Color(255, 158, 158);
	String s = "";
	boolean blink;
	
	ArrayList<App> apps;
	
	public Window(String contents){
		this.name = contents;
		try {
			this.contents = ImageIO.read(getClass().getResource("/res/"+"window_"+contents+".jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		w = this.contents.getWidth();
		h = this.contents.getHeight();
		
		if (name.equals("folder")){
			apps = new ArrayList<>();
			apps.add(new App("words", "NSA Docs", "  to Leak", 0, 0));
			if (AppData.divorcePapersDownloaded)
				apps.add(new App("words", "Divorce", "Papers", 0, 0));
			
			for (App a:apps)
				a.setColor(Color.BLACK);
		}
		
	}
	
	public void draw(Graphics2D g){
		g.setColor(LIGHT_BLUE);
		g.fillRect(x-5, y-30, w+10, h+35);
		
		if (Desktop.mouse!=null && isTouchingExit(Desktop.mouse))
			g.setColor(LIGHTER_RED);
		else
			g.setColor(LIGHT_RED);
		g.fillRect(x+w-30, y-30, 35, 30);
		g.drawImage(contents, x, y,w, h, null);
		
		
		
		if (name.equals("word")){
			g.setColor(Color.BLACK);
			blink = !blink;
			if (blink)
				g.drawString(s+"|", x+140, y+150);
			else
				g.drawString(s, x+140, y+150);
		}
		
		if (name.equals("words")){
			g.setColor(Color.BLACK);
			blink = !blink;
			if (blink)
				g.drawString(s+ "|", x+150, y+300);
			else
				g.drawString(s, x+150, y+300);
		}
		
		
		
		if (name.equals("folder")){
			for (int app =0; app<apps.size(); app++){
				apps.get(app).x = x + 200 + 100*app;
				apps.get(app).y = y + 90;
				apps.get(app).drawApp(g);
			}
			
		}
	}
	
	public void keyPressed(KeyEvent ev){
		
		if (ev.getKeyCode() == KeyCodes.SPACE_KEY){
			if (name.equals("word"))
				Desktop.popup = new PopupDialog("word",true);
			
			
			if (name.equals("words")){
				int numSpaces = 0;
				for (int c =0 ; c <s.length();c++){
					if (s.toCharArray()[c] == ' ')
						numSpaces++;
				}
				
				if (numSpaces>0){
					Desktop.popup = new PopupDialog("words",true);
				}else
					s+=' ';
				
			}

		}
		
		if (ev.getKeyCode() == KeyCodes.BACKSPACE_KEY){
			if (name.contains("word"))
				backspace();
		}
			
		
		if (Character.isLetter(ev.getKeyChar())||Character.isDigit(ev.getKeyChar())){
			
			if (name.contains("word")){
				
				s+= ev.getKeyChar();
			}
		}
		
	}
	
	public void mouseClicked(MouseEvent e){
		if (name.equals("folder")){
			if (apps.get(0).isTouchingApp(e.getPoint())){
				newWindow("folder_nsa");
			}
			
			if (apps.size()>1 && apps.get(1).isTouchingApp(e.getPoint())){
				newWindow("folder_divorce");
			}
				
			
		}
	}
	
	public static void newWindow(String name){
		Window w = new Window(name);
		w.x = 100+(int)(50*Math.random());
		w.y = 80+(int)(50*Math.random());
		Desktop.windows.add(w);
	}
	
	public void closing(){
		if (name.equals("words"))
			Desktop.popup = new PopupDialog("words3",true);
		
		if (name.equals("folder_divorce"))
			Desktop.popup = new PopupDialog(name,true);
	}
	
	public void backspace(){
		if (s.length()>0){
			StringBuilder b = new StringBuilder(s);
			b.deleteCharAt(s.length()-1);
			s = b.toString();
		}
	}
	
	public boolean isTouchingWindow(Point p){
		
		if (p.x>x-5 && p.x<x+w+10 && p.y>y-30 && p.y<y+h+35)
			return true;
		else
			return false;
		
	}
	
	public boolean isTouchingTopBar(Point p){
		if (p.y>y-30 && p.y<y && p.x>x && p.x<x+w)
			return true;
		else
			return false;
	}
	
	public boolean isTouchingExit(Point p){
		if (p.y>y-30 && p.y<y && p.x>x+w-30 && p.x<x+w+5)
			return true;
		else
			return false;
	}

}
