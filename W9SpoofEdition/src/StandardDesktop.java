import java.awt.Graphics2D;
import java.util.ArrayList;


public class StandardDesktop {
	
	ArrayList <App> apps = new ArrayList<App>();
	
	public StandardDesktop(){
		apps.add(new App("chrome", "Chrome", null, 25, 20));
		apps.add(new App("ie", "Internet","Exploder", 25, 120));
		apps.add(new App("word", "Microsoft","  Word", 25, 220));
		apps.add(new App("words", "Microsoft","  Words", 25, 320));
		apps.add(new App("office", "Microsoft's","   Office", 25, 420));
		apps.add(new App("my_computer", "     My","Computer", WindowFrame.w - 25 -80, 20));
		apps.add(new App("folder", "     My","Documents", WindowFrame.w - 25 -80, 120));
	}
	
	public void draw(Graphics2D g){
		
		if (MetroMenu.animValue != 0){
			for (App a:apps)
				a.drawApp(g);
		}
			//g.setXORMode(Color.BLUE);
		
		
	}
	

}
