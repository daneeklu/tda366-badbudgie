package edu.chl.tda366badbudgie.gui.render;

import java.awt.Color;


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
		
		renderHUD(g, gameRound);
		
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
	private static void renderHUD(IGraphics g, GameRound gameRound) {
		
		int health = gameRound.getPlayer().getHealth();
		
		int xPos;
		xPos = (int)(g.getCanvas().getWidth()*0.12);
		
		
		//This is health converted to a multiple of 2.55 to make the color of the health bar right
		int healthHigh = (int)(health*2.55);
		int healthLow = (int)((100-health)*2.55);

		//Depending on how much health the player has, it will change color. 
		//Also adds 1 (one) to all values to secure that no division with zero will happen. 
		Color c1 = new Color(healthLow+1, healthHigh+1, 1);
		
		
		//Draw the health bar Background
		g.drawColoredRect(
				new Rectangle(400, 55), 
				Color.BLACK, 
				gameRound.getPlayer().getX()-400,
				gameRound.getPlayer().getY()+200);
		//Draws the energy bar Background
		g.drawColoredRect(
				new Rectangle(400, 55),
				Color.BLACK,
				gameRound.getPlayer().getX()-400,
				gameRound.getPlayer().getY()+120);

		
		//Draw the health bar
		g.drawColoredRect(
				new Rectangle(health*4, 55), 
				c1, 
				gameRound.getPlayer().getX()-400,
				gameRound.getPlayer().getY()+200);
		//Draws the energy bar
		g.drawColoredRect(
				new Rectangle(gameRound.getPlayer().getFlyingEnergy()*4, 55),
				Color.GRAY,
				gameRound.getPlayer().getX()-400,
				gameRound.getPlayer().getY()+120);
		
		
		//Draws Health text
		g.drawText("Health", xPos, 53, 6);
		g.drawText(health + "/100", xPos, 68, 6);
		
		
		//Draws Energy text
		g.drawText("Energy", xPos, 133, 6);
		g.drawText((int)gameRound.getPlayer().getFlyingEnergy() + "/100", xPos, 148, 6);
		
		//Draws the score points
		//g.drawText("Current score " + gameRound.getScore(), 600, 50, 5);
		
	}

}
