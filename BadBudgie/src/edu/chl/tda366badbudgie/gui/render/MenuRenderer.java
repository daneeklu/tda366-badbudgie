package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * MenuRenderer
 * 
 * Rendering class for menu graphics.
 * 
 * @author 
 *
 */
public class MenuRenderer {

	public static void render(Menu menu, IGraphics g) {
		if(!g.startRendering()) 
			return;
		g.setActiveTexture("test");
		
		g.drawRect(new Rectangle(-1.0, -1.0, 2.0, 2.0));
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
			DebugInfoRenderer.getInstance().drawDebugInfo(g);
		}
		
		
		for (MenuItem item : menu.getMenuItems()) {
			renderMenuItem(item, g);
		}
		g.stopRendering();
	}
	
	private static void renderMenuItem(MenuItem item, IGraphics g) {
		g.setActiveTexture(item.getSprite().getId());
		g.drawSprite(item.getSprite(), item.getPosition(), item.getSize());
	}
}
