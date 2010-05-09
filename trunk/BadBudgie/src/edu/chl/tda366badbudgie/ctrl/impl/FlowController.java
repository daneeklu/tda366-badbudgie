package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import edu.chl.tda366badbudgie.core.SplashScreen;
import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
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

	public static FlowController ctrl;
	
	public IStateContext stateContext;
	public IGraphics graphics;
	
	/**
	 * Flowcontroller constructor.
	 * Inits the flowcontroller and all of it's parts.
	 * 
	 * @throws InterruptedException If the thread was interuppted
	 */
	public FlowController() throws InterruptedException {
		
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
		GraphicsFrame frame = new GraphicsFrame();
		frame.setVisible(true);
		Canvas canvas = frame.getIGraphics().getCanvas();
		
		//Create a statecontext for the app, and forward
		//the graphicsframe to it (for rendering calls)
		IStateContext app = StateContext.getInstance();
		app.setFrame(frame);

		
		//Create listeners for input and add them as observers
		//to the graphicsframe and it's canvas
		KeyController kc = new KeyController(app);
		MouseController mc = new MouseController(app);
		
		frame.addKeyListener(kc);
		frame.addMouseListener(mc);
		frame.addMouseMotionListener(mc);
		
		canvas.addKeyListener(kc);
		canvas.addMouseListener(mc);
		canvas.addMouseMotionListener(mc);
		

		// TODO: Change 17 to some defined constant or add a comment saying that 17 ms = x fps
		Timer timer = new Timer(17, (ActionListener) (new FlowController(app, frame.getIGraphics())));
		timer.start();
		
	}
	
	public FlowController(IStateContext app, IGraphics graphics) {
		super();
		this.stateContext = app;
		this.graphics = graphics;
	}
	
	public void loop() {
		stateContext.doLogic();
		stateContext.draw(graphics);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		loop();
	}

}
