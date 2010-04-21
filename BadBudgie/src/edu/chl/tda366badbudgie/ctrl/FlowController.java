package edu.chl.tda366badbudgie.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.chl.tda366badbudgie.core.App;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.AppRenderer;

public class FlowController implements ActionListener {

	public static FlowController ctrl;
	
	public App app;
	public IGraphics graphics;
	
	public FlowController(App app, IGraphics graphics) {
		super();
		this.app = app;
		this.graphics = graphics;
	}
	
	public void loop() {
		app.doLogic();
		AppRenderer.render(app,graphics);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		loop();
	}

}
