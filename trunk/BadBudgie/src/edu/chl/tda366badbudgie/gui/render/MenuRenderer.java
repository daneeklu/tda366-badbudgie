package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class MenuRenderer {

	public static void render(Menu menu, IGraphics g) {
		if(!g.startRendering()) 
			return;
		g.setActiveTexture("test");
		g.drawRect(new Rectangle(-1.0, -1.0, 2.0, 2.0));
		g.drawText("AppState: menu. Item: " + menu.getSelected());
		g.stopRendering();
	}
}
