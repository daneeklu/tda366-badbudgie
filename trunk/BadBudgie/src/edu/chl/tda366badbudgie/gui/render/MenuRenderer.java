package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

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
		//if(item == null) return;
		//if(g == null) return;
		//System.out.println("tex:" + item.getSprite().getId());
		g.setActiveTexture(item.getSprite().getId());
		//System.out.println("???");
		g.drawSprite(item.getSprite(), item.getPosition(), item.getSize());//new Rectangle(item.getPosition(),item.getSize()));
	}
}
