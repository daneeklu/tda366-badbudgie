package edu.chl.tda366badbudgie;

import edu.chl.tda366badbudgie.core.SplashScreen;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
import edu.chl.tda366badbudgie.io.IFileManager;
import edu.chl.tda366badbudgie.io.impl.FileManager;

/**
 * Bad Budgie
 * 
 * @author tda366-badbudgie
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		//Create splash screen
		SplashScreen splash = new SplashScreen();
		
		//Load assets
		IFileManager fileManager = new FileManager();
		fileManager.loadData();
		
		//1.5 second delay to avoid flashing
		Thread.sleep(1500);
		
		//Remove splash
		splash.setVisible(false);
		splash = null;
		
		//Create graphicsframe instance
		(new GraphicsFrame()).setVisible(true);
	}
}