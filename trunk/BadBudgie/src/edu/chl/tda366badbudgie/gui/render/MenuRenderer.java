package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.Menu.ConfirmDialog;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Vector;

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
		if(!g.startRendering(menu.getBounds())) 
			return;
		
		
		// TODO: draw the background image of the menu, make this not hardcoded
		g.setActiveTexture("test");
		g.drawRect(menu.getBounds());
		
		
		for (MenuItem item : menu.getMenuItems()) {
			if(item.getEnabled())
				renderMenuItem(item, g);
		}

		if (menu.getConfirmDialog() != null) {
			renderConfirmDialog(menu.getConfirmDialog(), g);
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
		g.drawSprite(item.getSprite(), item.getBounds());
	}
	
	private static void renderConfirmDialog(ConfirmDialog dialog, IGraphics g) {
		g.setActiveTexture(dialog.getTexId());
		g.drawRect(new Rectangle(200, 100, 400, 400));
		
		g.setActiveTexture("yesno");
		
		if(dialog.getValue()) {
			g.drawRectSection(null, new Rectangle(230, 150, 100, 100), 2, 2, 2);
			g.drawRectSection(null, new Rectangle(420, 150, 100, 100), 1, 2, 2);
		} else {
			g.drawRectSection(null, new Rectangle(230, 150, 100, 100), 0, 2, 2);
			g.drawRectSection(null, new Rectangle(420, 150, 100, 100), 3, 2, 2);
		}

	}
}
