package edu.chl.tda366badbudgie.gui.render;

import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
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
		if(!g.startRendering()) 
			return;
		
		drawTerrain(gameRound.getLevel().getTerrainSections(), g);
		
		for (AbstractGameObject go : gameRound.getLevel().getGameObjects()) {
			drawGameObject(go, g);
		}
		
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
			DebugInfoRenderer.getInstance().drawDebugInfo(g);
		}
		
		g.stopRendering();
		
	}
	
	/**
	 * 
	 * Render a section of terrain.
	 * @param ts the terrain section.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void drawTerrain(List<TerrainSection> ts, IGraphics g) {
		for (TerrainSection t : ts) {
			g.drawQuad(t.getQuad());
		}
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
