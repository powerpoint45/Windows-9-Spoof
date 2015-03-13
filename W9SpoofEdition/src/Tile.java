import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class Tile {
	
	String name;
	BufferedImage icon;
	int x,y;
	
	public Tile(String content, int x, int y){
		name = content;
		this.x = x;
		this.y = y;
		try {
			icon = ImageIO.read(getClass().getResource("/res/"+"tile_"+name+".jpg"));
		} catch (Exception e) {
		}
	}
	
	public void draw (Graphics2D g){
		
		if (Desktop.mouse!=null && isTileSelected(Desktop.mouse))
			g.drawImage(icon, MetroMenu.animValue + x-5, y-5, icon.getWidth()+10, icon.getHeight()+10, null);
		else
			g.drawImage(icon, MetroMenu.animValue + x, y, null);
	}
	
	public void tileClicked(){
		if (name.equals("desktop"))
			Desktop.popup = new PopupDialog("desktop",true);
		else{
			MetroMenu.metroWindow = new MetroWindow(name);
		}
		
	}
	
	public boolean isTileSelected(Point p){
		if (p.x>x && p.x<x+icon.getWidth() && p.y>y && p.y<y+icon.getHeight())
			return true;
		else
			return false;
	}

}
