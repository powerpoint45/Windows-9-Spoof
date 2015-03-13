import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class PopupDialog{
	BufferedImage popup;
	BufferedImage OK;
	BufferedImage OK_pressed;
	String name;
	boolean showOKButton =true;
	boolean streach = true;
	
	public PopupDialog(String name, boolean sound){
		this.name = name;
		try {
			popup = ImageIO.read(getClass().getResource("/res/"+"popup_"+name+".jpg"));
			OK = ImageIO.read(getClass().getResource("/res/"+"ok.jpg"));
			OK_pressed = ImageIO.read(getClass().getResource("/res/"+"ok_pressed.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (sound)
			noise();
	}
	
	public void showOKButton(boolean b){
		showOKButton = b;
	}
	
	public void setStreached(boolean b){
		streach = b;
	}
	
	public void drawPopup(Graphics2D g){
		if (popup!=null){
			
			if (streach)
				g.drawImage(popup , 0, WindowFrame.h/2 - 200, WindowFrame.w, popup.getHeight(), null);
			else{
				g.drawImage(popup , WindowFrame.w/2 - popup.getWidth()/2, WindowFrame.h/2 - popup.getHeight()/2, null);
			}
			
			if (showOKButton){
				if (Desktop.mouse!=null && isTouchingOK(Desktop.mouse))
					g.drawImage(OK_pressed , WindowFrame.w -120, WindowFrame.h/2 +70, null);
				else
					g.drawImage(OK , WindowFrame.w -120, WindowFrame.h/2 +70, null);
			}
		}
	}
	
	public void noise(){
		try{
			Clip music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"error.wav")));
			music.loop(0);
		}catch (Exception e){
			
		}
	}
	
	public boolean isTouchingOK(Point p){
		
		if (!streach){
			if (p.x>WindowFrame.w/2 - popup.getWidth()/2 && p.x< WindowFrame.w/2 - popup.getWidth()/2 + popup.getWidth()
					&& p.y>WindowFrame.h/2 - popup.getHeight()/2 && p.y< WindowFrame.h/2 - popup.getHeight()/2+popup.getHeight())
				return true;
			else
				return false;
		}
		
		if (p.x > (WindowFrame.w -120) && p.x<(WindowFrame.w -120)+OK.getWidth() && p.y>(WindowFrame.h/2 +70) && p.y<(WindowFrame.h/2 +70)+OK.getHeight())
			return true;
		else
			return false;
	}
	
	public void okClicked(){
		if (name.equals("chrome")){
			for (Window w:Desktop.windows){
				if (w.name.equals("chrome")){
					BufferedImage img =null;
					try {
						img = ImageIO.read(getClass().getResource("/res/"+"window_chrome_dead.jpg"));
					} catch (Exception e) {
						e.printStackTrace();
					}
					w.contents = img;
							
				}
			}
		}
		
		if (name.equals("desktop"))
			MetroMenu.toggleMainMenu();
		
		if (name.equals("word")||name.equals("words")){
			Desktop.popup = new PopupDialog(name+"2",true);
		}
		else
			Desktop.popup = null;
	}
	
}
