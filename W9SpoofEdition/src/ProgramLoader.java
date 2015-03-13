import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;


public class ProgramLoader {
	
	BufferedImage frame;
	int frameNum=0;
	String name;
	int time;
	
	public ProgramLoader(String name){
		this.name = name;
		nextFrame();
	}
	
	public void nextFrame(){
		if (frameNum==9)
			finish();
		else{
			frameNum++;
			try {
				this.frame = ImageIO.read(getClass().getResource("/res/"+"loader_"+name+"_"+frameNum+".jpg"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void finish(){
		Window w = new Window(name);
		w.x = 100;
		w.y = 80;
		Desktop.windows.add(w);
		Desktop.programLoader = null;
	}
	
	public void drawLoader(Graphics2D g){
		time ++;
		if (time%100==0)
			nextFrame();
		if (frame!=null)
			g.drawImage(frame, WindowFrame.w/2 - frame.getWidth()/2, WindowFrame.h/2 -frame.getHeight()/2, null);
		
	}

}
