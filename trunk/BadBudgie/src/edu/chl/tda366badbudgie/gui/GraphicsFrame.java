package edu.chl.tda366badbudgie.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import edu.chl.tda366badbudgie.gui.graphics.GLGraphics;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.AppRenderer;

public class GraphicsFrame extends JFrame {
	private IGraphics ig;
	public GraphicsFrame(){
		ig = new GLGraphics(new AppRenderer());
		
		add(ig.getCanvas());
		setSize(500, 500);
		setVisible(true);
	
		ig.startRendering();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

}