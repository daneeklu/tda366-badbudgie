package edu.chl.tda366badbudgie.gui.graphics;

import javax.media.opengl.GLCanvas;

/**
 * 
 * @author Andreas
 *
 */
public interface IGraphics {
	/**
	 *  Creates a canvas for the GL graphics
	 */
	public void createCanvas();
	
	/**
	 * Starts the rendering
	 */
	public void startRendering();
	
	/**
	 * Stops the rendering process, will not shut it down completely.
	 */
	public void stopRendering();
	
}
