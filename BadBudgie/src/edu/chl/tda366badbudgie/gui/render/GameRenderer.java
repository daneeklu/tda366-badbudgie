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
		
		g.drawBackgroundRect(new Rectangle(0, 0, 800, 600), gameRound.getLevel()
				.getBackgroundTexId());
		
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
		if (ts.getSprite().getId() != null 
				&& !ts.getSprite().getId().equals(""))
			g.drawTexturedPolygon(ts.getPosition(),ts.getSurface(), 
					ts.getSprite().getId(), ts.getTexRes());
		else 
			g.drawPolygon(ts.getPosition(),ts.getSurface());
	}
	
	/**
	 * Render a GameObject and it's children.
	 * @param go the GameObject to draw.
	 * @param g the Graphics object to do the rendering. 
	 */
	private static void drawGameObject(AbstractGameObject go, IGraphics g) {
		double x, y, h, w;
		w = go.getSize().getX()*go.getScale();
		h = go.getSize().getY()*go.getScale();
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
	private static void renderHUD(IGraphics g, GameRound gameRound) {
		
		int health = gameRound.getPlayer().getHealth();
		
		
		/*
		 * Coordinates for the HUD position.
		 */
		double hudXPos = 30;
		double hudYPos = -50;
		
		
		/*
		 * Health converted to a multiple of 2.55 to make the 
		 * color of the health bar right.
		 * Also adds 1 to all values to secure that no division with zero
		 * will happen.
		 */
		
		if(health<0){
			health = 0;
		}
		int healthHigh = (int)(health*2.55)+1;
		int healthLow = (int)(((100-health)*2.55)+1);
		
		/*
		 * Depending on how much health the player has, it will change color.
		 */ 
		
		Color c1 = new Color(healthLow, healthHigh, 1);

		
		//Draw the health bar Background
		g.drawColoredRect(
				new Rectangle(400, 55), 
				Color.BLACK, 
				hudXPos,
				hudYPos);		
		
		//Draw the health bar
		g.drawColoredRect(
				new Rectangle(health*4, 55), 
				c1, 
				hudXPos,
				hudYPos);
		
		//Draws Health text
		g.drawText("Health", hudXPos, 1, 6);
		g.drawText(health + "/100", hudXPos, 15, 6);
		
		
		//To get the Y distance between the Health and Energy bars
		hudYPos -= 100;
		
		//Draws the energy bar Background
		g.drawColoredRect(
				new Rectangle(400, 55),
				Color.BLACK,
				hudXPos,
				hudYPos);
		
		//Draws the energy bar
		g.drawColoredRect(
				new Rectangle(gameRound.getPlayer().getFlyingEnergy()*4, 55),
				Color.GRAY,
				hudXPos,
				hudYPos);

		//Draws Energy text
		g.drawText("Energy", hudXPos, 101, 6);
		g.drawText((int)gameRound.getPlayer().getFlyingEnergy() + "/100", hudXPos, 117, 6);
		
	}

}
