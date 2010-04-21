package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.graphics.IRenderer;

public class AppRenderer implements IRenderer{
	
	public void render(IGraphics g) {
		
		g.drawRect(0.0, 0.0, 0.2, 0.5);
		g.drawRect(-0.1, -0.7, 0.2, 0.5);
		g.drawRect(0.6, 0.0, 0.2, 0.5);
		g.drawRect(0.2, 0.4, 0.2, 0.5);
	}
}
