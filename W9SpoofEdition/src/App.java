import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import javax.imageio.ImageIO;


public class App {

	String title;
	String title2;
	String name;
	BufferedImage icon;
	BufferedImage iconPressed;
	int x,y;
	Color color;
	
	public App(String name, String title, String title2, int x, int y){
		this.x = x;
		this.y = y;
		this.title = title;
		this.title2 = title2;
		this.name = name;
		try {
			icon = ImageIO.read(getClass().getResource("/res/"+"app_"+name+".png"));
		} catch (Exception e) {
		}
		ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		iconPressed = op.filter(icon, null);
		setColor(Color.WHITE);
	}
	
	public void setColor(Color c){
		color =c;
	}
	
	public void drawApp(Graphics2D g){
		
		if (Desktop.mouse!=null && isTouchingApp(Desktop.mouse))
			g.drawImage(iconPressed, x, y, null);
		else
			g.drawImage(icon, x, y, null);
		g.setColor(color);
		g.drawString(title, x-13, y+icon.getHeight()+20);
		if (title2!=null)
			g.drawString(title2, x-15, y+icon.getHeight()+40);
	}
	
	public boolean isTouchingApp(Point p){
		if (p.x>x && p.x<x+icon.getWidth() && p.y>y && p.y<y+icon.getHeight()+40)
			return true;
		else
			return false;
	}
	
	public void clicked(){
		
		if (!name.contains("word") && !name.equals("ie")){
			Window.newWindow(name);
			if(Desktop.windows.size()==3)
				Desktop.popup = new PopupDialog("improve_performance",true);
		}
		
		if (name.equals("chrome")){
			Desktop.popup = new PopupDialog(name,true);
		}
		
		if (name.equals("ie")){
			PopupDialog p = new PopupDialog("ie", true);
			p.setStreached(false);
			p.showOKButton(false);
			Desktop.popup = p;
		}
		
		if (name.equals("word")){
			Desktop.programLoader = new ProgramLoader(name);
		}
		
		if (name.equals("words")){
			Desktop.programLoader = new ProgramLoader(name);
		}
	}
	
}
