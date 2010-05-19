package edu.chl.tda366badbudgie.gui.render;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.core.game.AbstractCollidable;
import edu.chl.tda366badbudgie.core.game.AbstractGameObject;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.Player;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * DebugInfoRenderer
 * 
 * Singleton used for drawing debug info to screen.
 * Graphical lines and text can be added to the buffer anywhere in the code, 
 * and are drawn by calling drawDebugInfo() from the game's render pipe.
 * 
 * @author kvarfordt
 *
 */
public class DebugInfoRenderer {

	private List<DebugLine> debugLines;
	private List<String> debugText;
	private boolean debugInfoEnabled = false;
	private static DebugInfoRenderer instance = new DebugInfoRenderer();
	
	/**
	 * Private constructor
	 */
	private DebugInfoRenderer() {
		debugLines = new LinkedList<DebugLine>();
		debugText = new LinkedList<String>();
	}
	
	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the instance
	 */
	public static DebugInfoRenderer getInstance() {
		return instance;
	}
	
	/**
	 * Adds a line to the class' buffer, to be 
	 * drawn on the next call to drawDebugInfo().
	 * 
	 * @param start the start point of the line
	 * @param end the end point of the line
	 * @param c the color of the line
	 */
	public void addDebugLine(Vector start, Vector end, Color c) {
		debugLines.add(new DebugLine(start, end, c));
	}
	
	/**
	 * Adds a line of text to the class' buffer, to be 
	 * drawn on the next call to drawDebugInfo().
	 * 
	 * @param string the text to be drawn
	 */
	public void addDebugText(String string) {
		debugText.add(string);
	}

	/**
	 * Gets the objects of a gameRounds level and visualizes various information
	 * about them, such as velocities, forces and collision data.
	 * 
	 * @param gameRound the gameRound
	 * @param g the IGraphics to use
	 */
	public void drawDebugInfo(GameRound gameRound, IGraphics g) {
		
		if (isDebugInfoEnabled()) {
			
			// Player info
			Player p = gameRound.getPlayer();
			addDebugText("Player");
			addDebugText("x:" + p.getX() + " y:" + p.getY());
			addDebugText("vx:" + p.getVelocity().getX() 
					+ " vy:" + p.getVelocity().getY());
			addDebugText("fx:" + p.getForce().getX() 
					+ " fy:" + p.getForce().getY());
			addDebugText("FlyingEnergy:" + p.getFlyingEnergy());
			addDebugText("Health:" + p.getHealth());
			
			
			for (AbstractGameObject ago : gameRound.getLevel()
					.getGameObjects()) {
				
				// Draw collision data of collidables
				if (ago instanceof AbstractCollidable) {
					AbstractCollidable ac = (AbstractCollidable) ago;
					List<Vector> verts = ac.getCollisionData(true)
							.getVertices();
					for (int i = 0; i < verts.size(); i++) {
						Vector v1 = verts.get(i);
						Vector v2 = verts.get((i + 1) % verts.size());
						addDebugLine(v1, v2, Color.darkGray);
					}
				}
				
				addDebugLine(ago.getPosition(), ago.getPosition()
						.add(ago.getForce().scalarMultiplication(100)), 
						Color.red);
				addDebugLine(ago.getPosition(), ago.getPosition()
						.add(ago.getVelocity().scalarMultiplication(7)), 
						Color.blue);
				
				
			}
			
			// Terrain sections
			for (TerrainSection t : gameRound.getLevel().getTerrainSections()) {
				List<Vector> cdvs = t.getCollisionData(true).getVertices();
				for (int i = 0; i < cdvs.size(); i++) {
					Vector v1 = cdvs.get(i);
					Vector v2 = cdvs.get((i + 1) % cdvs.size());

					addDebugLine(v1, v2, Color.black);
				}
			}
			
			renderDebugInfo(g);
		}
		
	}
	
	/**
	 * Displays info about the menu when called.
	 * 
	 * @param menu
	 * @param g
	 */
	public void drawDebugInfo(Menu menu, IGraphics g) {
		if (isDebugInfoEnabled()) {
			addDebugText("AppState: menu. Item: " + menu.getSelected());
			renderDebugInfo(g);
		}
	}
	
	/**
	 * The method that draws the lines and text in the buffer.
	 * @param g
	 */
	private void renderDebugInfo(IGraphics g) {
		for (DebugLine d : debugLines) {
			g.drawLine(d.start, d.end, d.c);
		}
		int i = 0;
		for (String s : debugText) {
			g.drawText(s, 10, 30 * i++, 7);
		}
		debugLines.clear();
		debugText.clear();
	}
	
	/**
	 * Enable or disable drawing of debug info.
	 * 
	 * @param debugInfoEnabled the boolean telling the class to draw or not
	 */
	public void setDebugInfoEnabled(boolean debugInfoEnabled) {
		this.debugInfoEnabled = debugInfoEnabled;
	}

	/**
	 * Returns true if debug drawing is enabled.
	 * 
	 * @return true if enabled
	 */
	public boolean isDebugInfoEnabled() {
		return debugInfoEnabled;
	}

	/**
	 * Private inner class for a line in the buffer.
	 * 
	 * @author kvarfordt
	 *
	 */
	private static class DebugLine {

		private Vector start;
		private Vector end;
		private Color c;
		
		public DebugLine(Vector start2, Vector end2, Color c2) {
			start = start2;
			end = end2;
			c = c2;
		}
	}

	
}
