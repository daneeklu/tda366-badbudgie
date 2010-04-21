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
	 *  If a canvas doesn't exist, a canvas object will be made and returned.
	 *  If a canvas do exist, the canvas will just be returned.
	 */
	public Canvas getCanvas();
	
	/**
	 * Starts the rendering
	 */
	public void startRendering();
	
	/**
	 * 
	 * @param r the rectangle to be drawn
	 * Draws a rectangle with the specified parameters.
	 * 
	 */
	public void drawRect(Rectangle r);
	
	public void addTexture(String id, BufferedImage data);

	
	public void setActiveTexture(String id);
	public String getActiveTexture();

	
}
