package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.util.Rectangle;

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
		
		g.drawBackgroundRect(new Rectangle(0, 0, 800, 600), gameRound.getLevel().getBackgroundTexId());
		
		for (TerrainSection ts : gameRound.getLevel().getTerrainSections()) {
			drawTerrainSection(ts, g);
		}
		
		for (AbstractGameObject go : gameRound.getLevel().getGameObjects()) {
			drawGameObject(go, g);
		}
		
		renderHUD();
		
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
		if (ts.getSprite().getId() != null && !ts.getSprite().getId().equals(""))
			g.drawTexturedPolygon(ts.getPosition(),ts.getSurface(), ts.getSprite().getId(), ts.getTexRes());
		else 
			g.drawPolygon(ts.getPosition(),ts.getSurface());
	}
	
	/**
	 * Render a GameObject.
	 * @param go the GameObject to draw.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void drawGameObject(AbstractGameObject go, IGraphics g) {
		g.drawSprite(go.getSprite(), new Rectangle(
				go.getX() - go.getWidth() / 2,go.getY() - go.getHeight() / 2,
				go.getWidth(), go.getHeight()));
	}
	
	/**
	 * Render heads up display showing score and health among 
	 * other things about the players status.
	 */
	private static void renderHUD() {
		
	}

}
