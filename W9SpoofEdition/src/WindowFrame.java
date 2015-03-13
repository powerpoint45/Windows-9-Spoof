import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial") 
public class WindowFrame extends JFrame implements Runnable,KeyListener, MouseListener, MouseMotionListener {
	Graphics2D offGraphics; //used for DB
	static Image offImage;      //used for DB
	Point locationOfArcs;
	boolean animatingClear;
	static int displayMode;
	static StartScreen startScreen;
	static Desktop desktop;
	static JPanel plane;
	
	static int w;
	static int h;

	//Image unicornImage;
	
	public WindowFrame(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	    final int screen_Width = dim.width;
	    final int screen_Height = dim.height;
	    
	    //Create a JFrame
	    JFrame frame = new JFrame();

	    frame.setSize(screen_Width , screen_Height);

	    //set properties for the JFrame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	    frame.setUndecorated(true);
	    frame.setLayout( null );
	    frame.setVisible(true);
		w = screen_Width;
		h =screen_Height;
		
		displayMode = DisplayMode.MODE_MENU;
		offImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		plane = new JPanel();
		frame.setContentPane(plane);
		frame.requestFocus();//adding focus to enable key and mouse events
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		new Thread(this).start();
		plane.setSize(dim);
		plane.invalidate();
		frame.invalidate();
		//System.out.println("program is not stuck running loop. Main thread can still be used");
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (offGraphics ==null && offImage.getGraphics() != null)
			offGraphics = (Graphics2D) offImage.getGraphics();
		
	do{
		if (plane.getGraphics()!=null){//graphics not initialized
			if (offGraphics!=null){
				switch(displayMode){
				case DisplayMode.MODE_MENU:
					if (desktop==null)
						desktop = null;
					
					if (startScreen==null)
						startScreen = new StartScreen();
					break;
					
				case DisplayMode.MODE_DESKTOP:
					if (startScreen!=null)
						startScreen = null;
					
					if (desktop==null)
						desktop = new Desktop();
					
					break;
					
				}
				
				onDraw((Graphics2D)plane.getGraphics());
				updateGameData();
			}
		}else{}
		}while (true);
	}
	
	// called from thread
	
	public void onDraw(Graphics2D g){
		
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			if (startScreen!=null)
				startScreen.draw(offGraphics);
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (desktop!=null)
				desktop.draw(offGraphics);
			break;
			
		}
		
		g.drawImage(offImage, 0, 0, this);
	}
	
	

	public void updateGameData(){
		
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			if (startScreen!=null)
				startScreen.update();
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (desktop!=null)
				desktop.update();
			break;
			
		}
		
	}
	
	
	
	
	@Override
	public void keyPressed(KeyEvent ev) {
		
		if (ev.getKeyCode()==KeyCodes.ESC_KEY)
			System.exit(0);
		
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			if (startScreen!=null)
				startScreen.keyPressed(ev);
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (desktop!=null)
				desktop.keyPressed(ev);
			break;
		}
	}


	@Override
	public void keyReleased(KeyEvent ev) {
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			startScreen.keyReleased(ev);
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (desktop!=null)
				desktop.keyReleased(ev);
			break;
		}
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			if (startScreen!=null && e!=null)
				startScreen.mouseClicked(e);
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (e!=null && e.getPoint()!=null)
				desktop.mouseClicked(e);
			break;
		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (e!=null && e.getPoint()!=null)
				desktop.mousePressed(e);
			break;
		}
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			break;
			
		case DisplayMode.MODE_DESKTOP:
			desktop.mouseReleased(e);
			break;
		}
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			break;
			
		case DisplayMode.MODE_DESKTOP:
			desktop.mouseMoved(e);
			break;
		}
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		switch(displayMode){
		case DisplayMode.MODE_MENU:
			if (startScreen!=null && e!=null)
				startScreen.mouseMoved(e);
			break;
			
		case DisplayMode.MODE_DESKTOP:
			if (desktop!=null)
				desktop.mouseMoved(e);
			break;
		}
	}
	
	
	
	


}
