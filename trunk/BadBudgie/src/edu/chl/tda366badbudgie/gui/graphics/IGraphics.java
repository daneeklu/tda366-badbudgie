package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;

/**
 * 
 * @author Andreas
 *
 */
public interface IGraphics {
	/**
	 *  Creates a canvas for the GL graphics
	 */
	public Canvas getCanvas();
	
	/**
	 * Starts the rendering
	 */
	public void startRendering();
	
	/**
	 * 
	 * @param x - x coordinate for the Quad
	 * @param y - y coordinate for the Quad
	 * @param w - width of the Quad
	 * @param h - height of the Quad
	 * Draws a rectangle with the specified parameters.
	 * 
	 */
	public void drawRect(double x, double y, double w, double h);

	
}
