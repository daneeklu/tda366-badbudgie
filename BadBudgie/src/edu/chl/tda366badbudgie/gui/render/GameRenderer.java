package edu.chl.tda366badbudgie.gui.render;

import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * GameRenderer
 * 
 * Rendering class for drawing in-game graphics. 
 * 
 * @author d.skalle, kvarfordt
 *
 */
public class GameRenderer {

	/**
	 * Render a GameRound.
	 * @param gameRound the GameRound.
	 * @param g the Graphics object to do the rendering.
	 */
	public static void render(GameRound gameRound, IGraphics g) {
		if(!g.startRendering(gameRound.getPlayer().getPosition())) 
			return;
		
		g.drawBackgroundRect(new Rectangle(new Vector(-400, -300), new Vector(800, 600)), gameRound.getLevel().getBackgroundTexId());
		
		for (TerrainSection ts : gameRound.getLevel().getTerrainSections()) {
			drawTerrainSection(ts, g);
		}
		
		for (AbstractGameObject go : gameRound.getLevel().getGameObjects()) {
			drawGameObject(go, g);
		}
		
		DebugInfoRenderer.getInstance().drawDebugInfo(gameRound, g);
		
		g.stopRendering();
		
	}
	
	/**
	 * 
	 * Render a section of terrain.
	 * @param ts the terrain section.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void drawTerrainSection(TerrainSection ts, IGraphics g) {
		if (ts.getTexId() != null && !ts.getTexId().equals(""))
			g.drawTexturedQuad(ts.getQuad(), ts.getTexId(), ts.getTexRes());
		else 
			g.drawQuad(ts.getQuad());
	}
	
	/**
	 * Render a GameObject.
	 * @param go the GameObject to draw.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void drawGameObject(AbstractGameObject go, IGraphics g) {
		g.drawSprite(go.getSprite(), 
				new Vector(go.getX() - go.getWidth() / 2,go.getY() - go.getHeight() / 2),
				new Vector(go.getWidth(), go.getHeight()));
	}

}
