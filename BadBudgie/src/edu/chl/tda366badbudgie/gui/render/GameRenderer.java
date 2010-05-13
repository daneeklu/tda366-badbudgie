package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.game.AbstractGameObject;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
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
		
		double x, y, h, w;
		w = go.getWidth()*go.getScale();
		h = go.getHeight()*go.getScale();
		x = go.getX() - w/2;
		y = go.getY() - h/2;
		
		g.drawSprite(go.getSprite(), new Rectangle(x, y, w, h));
				
		// Draw the ago's children if any
		for (AbstractGameObject child : go.getChildren()) {
			drawGameObject(child, g);
		}
	}
	
	/**
	 * Render heads up display showing score and health among 
	 * other things about the players status.
	 */
	private static void renderHUD() {
		
	}

}
