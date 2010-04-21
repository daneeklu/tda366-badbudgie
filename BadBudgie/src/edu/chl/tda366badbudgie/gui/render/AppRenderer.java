package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.content.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.graphics.IRenderer;

public class AppRenderer implements IRenderer{
	
	public void render(IGraphics g) {

		g.drawRect(new Rectangle(-0.8, -0.8, 0.8, 0.8));
	}
}
