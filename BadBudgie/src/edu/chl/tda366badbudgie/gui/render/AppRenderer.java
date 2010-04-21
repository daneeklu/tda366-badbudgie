package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.App;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class AppRenderer {
	
	MenuRenderer mr;
	
	public AppRenderer() {
		mr = new MenuRenderer();
	}
	
	public static void render(App app, IGraphics g) {

		if (app.getState() == App.AppState.Menu) {
			MenuRenderer.render(app.getMenu(),g);
		}
	}

}
