import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class MetroWindow {
	BufferedImage content;
	BufferedImage extraImage;
	final static Color LIGHT_RED = new Color(255, 71, 71);
	final static Color LIGHTER_RED = new Color(255, 158, 158);
	String name;
	
	public MetroWindow(String name){
		this.name = name;
		try {
			this.content = ImageIO.read(getClass().getResource("/res/"+"metro_"+name+".jpg"));
		} catch (Exception e) {
		}
		
		if (name.equals("mail")){
			try {
				this.extraImage = ImageIO.read(getClass().getResource("/res/"+"divorce_papers_download.jpg"));
			} catch (Exception e) {
			}
		}
			
	}
	
	
	int quality;
	public void draw(Graphics2D g){
		
		if (quality == 0){
			quality++;
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}
		
		if (closing){
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_DEFAULT);
			close();
		}
		
		g.drawImage(content, 0, 0, WindowFrame.w, WindowFrame.h, null);
		
		
		
		if (name.equals("mail")){
			if (Desktop.mouse!=null && isTouchingDownload(Desktop.mouse))
				g.drawImage(extraImage, WindowFrame.w/2-5, WindowFrame.h-300-5, extraImage.getWidth()+10, extraImage.getHeight()+10, null);
			else
				g.drawImage(extraImage, WindowFrame.w/2, WindowFrame.h-300, null);
		}
		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WindowFrame.w, 30);
		
		if (Desktop.mouse !=null && isTouchingExit(Desktop.mouse))
			g.setColor(LIGHTER_RED);
		else
			g.setColor(LIGHT_RED);
		
		g.fillRect(WindowFrame.w-30, 0, 30, 30);
		
		
	}
	
	
	public boolean isTouchingExit(Point p){
		if (p.x>WindowFrame.w-30 && p.x<=WindowFrame.w && p.y>=0 && p.y<30)
			return true;
		else
			return false;
	}
	
	public boolean isTouchingDownload(Point p){
		if (p.x>WindowFrame.w/2 && p.x< WindowFrame.w/2 + extraImage.getWidth() && p.y>WindowFrame.h-300 && p.y< WindowFrame.h-300 + extraImage.getHeight())
			return true;
		else
			return false;
	}
	
	boolean closing;
	public void mouseClicked(MouseEvent e){
		

		if (name.equals("mail")){
			
			if (isTouchingDownload(e.getPoint())){
				AppData.divorcePapersDownloaded = true;
				for (Window w:Desktop.windows){
					if (w.name.equals("folder")){
						App a = new App("words", "Divorce", "Papers", 0, 0);
						a.setColor(Color.BLACK);
						w.apps.add(a);
					}
				}
				Desktop.popup = new PopupDialog("divorce", true);
			}

		}
		
		if (name.equals("contacts"))
			Desktop.popup = new PopupDialog("contacts",true);
		
		if (isTouchingExit(e.getPoint()))
			closing = true;
	}
	
	public void close(){
		MetroMenu.metroWindow = null;
	}

}
