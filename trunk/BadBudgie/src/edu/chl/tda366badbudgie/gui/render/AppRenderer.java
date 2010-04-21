package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.content.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.graphics.IRenderer;

public class AppRenderer implements IRenderer{
	
	public void render(IGraphics g) {

		if(!g.startRendering()) 
			return;
		g.setActiveTexture("test");
		g.drawRect(new Rectangle(-1.0, -1.0, 2.0, 2.0));
		g.stopRendering();
	}
}
