import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class Notification {
	
	BufferedImage notification;
	
	public Notification(String name){
		
		try {
			notification = ImageIO.read(getClass().getResource("/res/"+"notification_"+name+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		noise();
		
	}
	
	public void drawNotification(Graphics2D g){
		
		if (Desktop.mouse!=null && isTouching(Desktop.mouse))
			g.drawImage(notification, WindowFrame.w-notification.getWidth()-10, 50-10,notification.getWidth()+10, notification.getHeight()+5,  null);
		else
			g.drawImage(notification, WindowFrame.w-notification.getWidth(), 50, null);
		
	}
	
	public boolean isTouching(Point p){
		if (p.x>WindowFrame.w-notification.getWidth() && p.x<=WindowFrame.w && p.y>50 && p.y<50+notification.getHeight())
			return true;
		else
			return false;
	}
	
	public void noise(){
		try{
			Clip music = AudioSystem.getClip();
			music.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"notification.wav")));
			music.loop(0);
		}catch (Exception e){
			
		}
	}

}
