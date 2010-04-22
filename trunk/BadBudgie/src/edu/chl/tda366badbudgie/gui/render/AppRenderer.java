package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.ctrl.impl.StateContext;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class AppRenderer {
	
	MenuRenderer mr;
	
	public static void render(StateContext app, IGraphics g) {
		app.getState().draw(g);
	}

}
