package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.chl.tda366badbudgie.core.Polygon;
import edu.chl.tda366badbudgie.core.Quad;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.Sprite;
import edu.chl.tda366badbudgie.core.Vector;

/**
 * 
 * @author lumbo, d.skalle
 *
 */
public interface IGraphics {
	/**
	 *  Get the canvas for the Graphics object
	 */
	public Canvas getCanvas();
	
	/**
	 * Start the rendering
	 * @return true if you may proceed using the other drawing methods. Do not use them otherwise
	 */
	public boolean startRendering(Vector cameraPosition);

	/**
	 * Draws a rectangle 
	 * @param r the rectangle to be drawn
	 * 
	 */
	public void drawRect(Rectangle r);
	
	/**
	 * Draws a quad 
	 * @param r the rectangle to be drawn
	 * 
	 */
	public void drawQuad(Quad q);
	
	/**
	 * Draws a polygon
	 * @param p the polygon to draw
	 * 
	 */
	public void drawPolygon(Polygon p);
	
	/**
	 * Draws a line
	 * @param start
	 * @param end
	 */
	public void drawLine(Vector start, Vector end, Color c);
	
	/**
	 * 
	 * Draw a sprite as a rectangle
	 * @param s the sprite to be drawn
	 * @param pos the position of the sprite
	 * @param size the size of the sprite
	 */
	public void drawSprite(Sprite s, Vector pos, Vector size);
	
	
	/**
	 * Add a texture to the texture library
	 * @param id the id for the texture
	 * @param data a bufferedimage containing the image
	 */
	
	public void addTexture(String id, BufferedImage data);

	/**
	 * Set the active texture to the one in the library with
	 * corresponding id
	 * @param id the id for the texture
	 */
	public void setActiveTexture(String id);
	
	/**
	 * Get which texture is currently set in the library
	 * as the active texture
	 * @return the id for the texture, or null if no texture is set
	 */
	public String getActiveTexture();

	/**
	 * Call stopRendering when you are done drawing to
	 * tell the system you're finished
	 */
	public void stopRendering();
	
	/**
	 * Draw text at the specified pixel position from the upper left corner.
	 * @param text the text to be drawn
	 */
	public void drawText(String text, int x, int y);

	
}
