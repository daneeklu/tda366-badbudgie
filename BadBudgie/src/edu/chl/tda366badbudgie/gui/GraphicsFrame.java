package edu.chl.tda366badbudgie.gui;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.graphics.impl.GLGraphics;

/**
 * GraphicsFrame
 * 
 * @author d.skalle
 *
 */
@SuppressWarnings("serial")
public class GraphicsFrame extends JFrame {
	
	private transient IGraphics ig;
	
	public GraphicsFrame(){
		ig = new GLGraphics(800, 600);
		
		add(ig.getCanvas());

		setSize(800, 600);
		setLocationRelativeTo(null);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}
	
	public IGraphics getIGraphics() {
		return ig;
	}
	
	/**
	 * Shuts down the program.
	 */
    public void shutdown() {
        WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
    }
    
	
}
