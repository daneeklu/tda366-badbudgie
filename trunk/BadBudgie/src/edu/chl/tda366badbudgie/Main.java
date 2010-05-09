package edu.chl.tda366badbudgie;

import edu.chl.tda366badbudgie.ctrl.impl.FlowController;

/**
 * Bad Budgie
 * 
 * @author tda366-badbudgie
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		new FlowController();
		/*
		//Create splash screen
		SplashScreen splash = new SplashScreen();
		
		//Load assets
		IFileManager fileManager = new FileManager();
		fileManager.loadData();
		
		//1.5 second delay to avoid flashing
		Thread.sleep(1500);d
		//Remove splash
		splash.setVisible(false);
		splash = null;
		
		//Create graphicsframe instance
		(new GraphicsFrame()).setVisible(true);
		*/
	}
}