package edu.chl.tda366badbudgie.gui;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import edu.chl.tda366badbudgie.core.App;
import edu.chl.tda366badbudgie.ctrl.FlowController;
import edu.chl.tda366badbudgie.gui.graphics.GLGraphics;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.AppRenderer;
import edu.chl.tda366badbudgie.io.FileManager;
import edu.chl.tda366badbudgie.io.IFileManager;

@SuppressWarnings("serial")
public class GraphicsFrame extends JFrame {
	private IGraphics ig;
	public GraphicsFrame(){
		ig = new GLGraphics(new AppRenderer());
		
		add(ig.getCanvas());
		setSize(500, 500);
		setVisible(true);
		

	
		Timer timer = new Timer(17, (ActionListener) (new FlowController(new App(), ig)));
		timer.start();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

}
