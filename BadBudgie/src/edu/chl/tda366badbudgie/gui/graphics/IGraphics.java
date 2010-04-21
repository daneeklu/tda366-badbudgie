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
	 * Stops the rendering process, will not shut it down completely.
	 */
	
	public void drawRect(double x, double y, double w, double h);

	
}
