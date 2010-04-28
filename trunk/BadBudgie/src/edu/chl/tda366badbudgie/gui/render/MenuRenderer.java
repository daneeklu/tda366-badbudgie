package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.core.Menu.ConfirmDialog;
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
		
		
		for (MenuItem item : menu.getMenuItems()) {
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
		g.drawSprite(item.getSprite(), item.getPosition(), item.getSize());
	}
	
	private static void renderConfirmDialog(ConfirmDialog dialog, IGraphics g) {
		g.setActiveTexture(dialog.getTexId());
		g.drawRect(new Rectangle(-200, -200, 400, 400));
		
		g.setActiveTexture("yesno");
		
		if(dialog.getValue()) {
			g.drawRectSection(new Rectangle(-170, -150, 100, 100), 2, 2, 2);
			g.drawRectSection(new Rectangle(20, -150, 100, 100), 1, 2, 2);
		} else {
			g.drawRectSection(new Rectangle(-170, -150, 100, 100), 0, 2, 2);
			g.drawRectSection(new Rectangle(20, -150, 100, 100), 3, 2, 2);
		}

	}
}
