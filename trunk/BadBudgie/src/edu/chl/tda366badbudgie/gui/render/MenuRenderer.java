package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * MenuRenderer
 * 
 * Rendering class for menu graphics.
 * 
 * @author d.skalle
 *
 */
public class MenuRenderer {

	/**
	 * Render a menu.
	 * @param menu the menu to render.
	 * @param g the Graphics object to do the rendering. 
	 */
	public static void render(Menu menu, IGraphics g) {
		if(!g.startRendering(new Vector())) 
			return;
		
		
		// TODO: draw the background image of the menu, make this not hardcoded
		g.setActiveTexture("test");
		g.drawRect(new Rectangle(-400, -300, 800, 600));

		DebugInfoRenderer.getInstance().drawDebugInfo(menu, g);
		
		for (MenuItem item : menu.getMenuItems()) {
			renderMenuItem(item, g);
		}
		g.stopRendering();
	}
	
	/**
	 * Render a menu item.
	 * @param item the menu item.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void renderMenuItem(MenuItem item, IGraphics g) {
		g.setActiveTexture(item.getSprite().getId());
		g.drawSprite(item.getSprite(), item.getPosition(), item.getSize());
	}
}
