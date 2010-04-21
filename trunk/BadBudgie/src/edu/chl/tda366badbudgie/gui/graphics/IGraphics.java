package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;
import java.awt.image.BufferedImage;

import edu.chl.tda366badbudgie.core.content.Rectangle;

/**
 * 
 * @author Andreas
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
	public boolean startRendering();
	
	/**
	 * Draws a rectangle 
	 * @param r the rectangle to be drawn
	 * 
	 */
	public void drawRect(Rectangle r);
	
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
	 * Draw text in the lower left corner
	 * @param text the text to be drawn
	 */
	public void drawText(String text);

	
}
