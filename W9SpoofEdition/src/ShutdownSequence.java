import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;


public class ShutdownSequence {
	public static int shutDownPos;

	public static void check(Clip shutDownMusic){
		if (shutDownPos==0){
			if (shutDownMusic!=null){
				PopupDialog p = new PopupDialog("shutdown0",false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
			
		}
		
		if (shutDownPos==1){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/4){
				PopupDialog p = new PopupDialog("shutdown1",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
			
		}
		
		if (shutDownPos == 2){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/3){
				Desktop.popup = null;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 3){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/2.2){
				PopupDialog p = new PopupDialog("shutdown2",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 4){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.9){
				PopupDialog p = new PopupDialog("shutdown3",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 5){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.7){
				PopupDialog p = new PopupDialog("shutdown2",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 6){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.6){
				PopupDialog p = new PopupDialog("shutdown1",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 7){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.5){
				PopupDialog p = new PopupDialog("shutdown4",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		
		if (shutDownPos == 8){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.3){
				PopupDialog p = new PopupDialog("shutdown2",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 9){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.3){
				PopupDialog p = new PopupDialog("shutdown1",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 10){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.2){
				PopupDialog p = new PopupDialog("shutdown2",false);
				p.setStreached(false);
				p.showOKButton(false);
				Desktop.popup = p;
				shutDownPos++;
			}
		}
		
		if (shutDownPos == 11){
			if (shutDownMusic!=null && shutDownMusic.getMicrosecondPosition()>shutDownMusic.getMicrosecondLength()/1.1){
				finishShutdown();
			}
		}
		
	}
	
	public static void finishShutdown(){
		Desktop.windows.clear();
		Desktop.BSODTime = true;
	}
	
	
	static BufferedImage BSOD;
	static int BSODTimer;
	static int BSODImage;
	
	public static void drawBSOD(Graphics2D g, Desktop d){
		if (BSOD == null){
			try {
				BSOD = ImageIO.read(d.getClass().getResource("/res/"+"bsod0.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}else{
			BSODTimer++;
			if (BSODTimer>100 && BSODImage == 0){
				BSODImage = 1;
				try {
					BSOD = ImageIO.read(d.getClass().getResource("/res/"+"bsod1.jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			g.drawImage(BSOD, 0, 0, WindowFrame.w, WindowFrame.h, null);
		}
		
		
	}

	public static void reset() {
		shutDownPos = 0;
		BSOD = null;
		BSODImage = 0;
		BSODTimer =0;
	}
	
}
