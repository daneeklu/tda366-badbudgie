package edu.chl.tda366badbudgie.gui;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.ctrl.impl.FlowController;
import edu.chl.tda366badbudgie.ctrl.impl.KeyController;
import edu.chl.tda366badbudgie.ctrl.impl.StateContext;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.graphics.impl.GLGraphics;

@SuppressWarnings("serial")
public class GraphicsFrame extends JFrame {
	private IGraphics ig;
	public GraphicsFrame(){
		ig = new GLGraphics();
		
		add(ig.getCanvas());
		setSize(500, 500);
		setVisible(true);
		
		IStateContext app = new StateContext();
		KeyController kc = new KeyController(app);
		
		addKeyListener(kc);
		ig.getCanvas().addKeyListener(kc);
		
		Timer timer = new Timer(17, (ActionListener) (new FlowController(app, ig)));
		timer.start();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

}
