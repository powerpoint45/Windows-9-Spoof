import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class MetroMenu{
		static boolean open = true;
		BufferedImage background;
		BufferedImage start;
		BufferedImage userBadge;
		BufferedImage powerButton;;
		public static int animValue;
		ArrayList<Tile> tiles;
		public static MetroWindow metroWindow;
		
		public void openMainMenu(){
			open = true;
		}
		
		public static void toggleMainMenu(){
			open = !open;
		}
		
		public boolean isOpen(){
			return open;
		}
		
		public MetroMenu(){
			tiles = new ArrayList<Tile>();
			try {
				background = ImageIO.read(getClass().getResource("/res/"+"main_menu_bg.png"));
				start = ImageIO.read(getClass().getResource("/res/"+"start.png"));
				userBadge = ImageIO.read(getClass().getResource("/res/"+"user_badge.png"));
				powerButton =  ImageIO.read(getClass().getResource("/res/"+"power_button.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			tiles.add(new Tile("contacts",10,WindowFrame.h/2-190));
			tiles.add(new Tile("maps",400,WindowFrame.h/2-190));
			tiles.add(new Tile("desktop",10,WindowFrame.h/2+20));
			tiles.add(new Tile("mail",400,WindowFrame.h/2+20));
			tiles.add(new Tile("sports",790,WindowFrame.h/2+20));
			tiles.add(new Tile("nothing",590,WindowFrame.h/2-190));
			tiles.add(new Tile("camera",790,WindowFrame.h/2-190));
			
			
		}
		
		public void drawMainMenu(Graphics2D g){
			if (metroWindow!=null){
				metroWindow.draw(g);
			}else{
				if (open && animValue!=0 && animValue<0)
					animValue+=70;
				if (!open && animValue!=-WindowFrame.w && animValue>-WindowFrame.w)
					animValue -=70;
				
				if (animValue>-WindowFrame.w){
					g.drawImage(background, animValue, 0, WindowFrame.w, WindowFrame.h, null);
					g.drawImage(start, 50+animValue, 50, null);
					g.drawImage(userBadge, WindowFrame.w -250 +animValue, 40, null);
					
					if (Desktop.mouse!=null && isTouchingPowerButton(Desktop.mouse)){
						g.drawImage(powerButton, WindowFrame.w -100 +animValue -5, 40-5,powerButton.getWidth()+10 , powerButton.getHeight()+10,  null);
					}else
						g.drawImage(powerButton, WindowFrame.w -100 +animValue, 40, null);
					for (Tile t:tiles)
						t.draw(g);
				}
			}
			if (shutdown)
				updateShutDown(g);
		}
		
		public boolean isTouchingPowerButton(Point p){
			if (p.x>WindowFrame.w-100 && p.x<WindowFrame.w && p.y>40 && p.y<40 + powerButton.getHeight())
				return true;
			else
				return false;
		}
		
		public void mouseClicked(MouseEvent e){
			if (metroWindow==null){
				for (Tile t: tiles){
					if (t.isTileSelected(e.getPoint()))
						t.tileClicked();
				}
				
				if (isTouchingPowerButton(e.getPoint())){
					shutdown();
				}
			}else{
				metroWindow.mouseClicked(e);
			}
		}
		
		public void updateShutDown(Graphics2D g){
			ShutdownSequence.check(shutDownMusic);
		}
		
		boolean shutdown;
		Clip shutDownMusic;
		public void shutdown(){
			shutdown = true;
			try{
				shutDownMusic = AudioSystem.getClip();
				shutDownMusic.open(AudioSystem.getAudioInputStream(getClass().getResource("/res/"+"error_song.wav")));
				shutDownMusic.loop(0);
			}catch(Exception e){
				
			}
		}
	}