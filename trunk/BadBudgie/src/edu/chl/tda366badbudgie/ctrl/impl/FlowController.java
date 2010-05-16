package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
import edu.chl.tda366badbudgie.gui.SplashScreen;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.io.IFileManager;
import edu.chl.tda366badbudgie.io.impl.FileManager;

/**
 * FlowController
 * 
 * The game update controller. Inits the game and all its
 * parts, and later calls on the state context 
 * to update the game at a regular interval.
 * 
 * @author d.skalle, kvarfordt
 *
 */
public class FlowController implements ActionListener {
	
	public IStateContext stateContext;
	public IGraphics graphics;
	
	/**
	 * Flowcontroller constructor.
	 * Inits the flowcontroller and all of it's parts.
	 * 
	 * @throws InterruptedException If the thread was interrupted
	 */
	public FlowController() throws InterruptedException {
		
		SplashScreen splash = new SplashScreen();
		
		//Load assets
		IFileManager fileManager = new FileManager();
		fileManager.loadData("config/config.xml");
		
		//1.5 second delay to avoid flashing
		Thread.sleep(1500);
		
		//Remove splash
		splash.setVisible(false);
		splash = null;
		
		//Create graphicsframe instance
		GraphicsFrame frame = new GraphicsFrame();
		frame.setVisible(true);
		Canvas canvas = frame.getIGraphics().getCanvas();
		graphics = frame.getIGraphics();
		
		//Create a statecontext for the app, and forward
		//the graphicsframe to it (for rendering calls)
		IStateContext app = StateContext.getInstance();
		app.setFrame(frame);

		
		//Create listeners for input and add them as observers
		//to the graphicsframe and it's canvas
		InputController kc = new InputController(app);
		
		frame.addKeyListener(kc);
		frame.addMouseListener(kc);
		frame.addMouseMotionListener(kc);
		
		canvas.addKeyListener(kc);
		canvas.addMouseListener(kc);
		canvas.addMouseMotionListener(kc);
		

		// Create a timer calling the loop method of this
		// FlowController, at a rate of 60 frames / second.
		//
		// An interval of 1000 / 60 ms corresponds to 60 FPS
		int interval = 1000 / 60;
		Timer timer = new Timer(interval, (ActionListener) (this));
		timer.start();
		
	}
	
	public void loop() {
		StateContext.getInstance().doLogic();
		StateContext.getInstance().draw(graphics);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		loop();
	}

}
