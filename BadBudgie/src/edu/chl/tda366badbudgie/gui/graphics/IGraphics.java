package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Quad;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

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
	 * 
	 * @param cameraPosition the center position of the camera
	 * @return true if you may proceed using the other drawing methods. Do not use them otherwise
	 */
	public boolean startRendering(Vector cameraPosition);
	
	/**
	 * Start the rendering
	 * 
	 * @param bounds the boundary rectangle for the camera
	 * @return true if you may proceed using the other drawing methods. Do not use them otherwise
	 */
	public boolean startRendering(Rectangle bounds);

	/**
	 * Draws a rectangle 
	 * 
	 * @param r the rectangle to be drawn
	 * 
	 */
	public void drawRect(Rectangle r);
	
	/**
	 * Draws a quad 
	 * 
	 * @param position the reference position of the quad.
	 * @param r the rectangle to be drawn
	 * 
	 */
	public void drawQuad(Vector position, Quad q);
	
	/**
	 * Draws a polygon
	 * 
	 * @param position the reference position of the rectangle.
	 * @param p the polygon to draw
	 * 
	 */
	public void drawPolygon(Vector position, Polygon p);
	
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
	 * @param bounds the boundary rectangle
	 */
	public void drawSprite(Sprite sprite, Rectangle bounds);
	
	
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
	public void drawText(String text, double x, double y, int size);

	
	/**
	 * Draw a rectangle, using a section of a texture
	 * @param position the reference position of the rectangle.
	 * @param rectangle the rectangle
	 * @param section the number of the section to draw
	 * @param hres the horisontal section resolution of the texture
	 * @param vres the vertical section resolution of the texture
	 */
	public void drawRectSection(Vector position, Rectangle rectangle, int section, int hres, int vres);
	
	
	/**
	 * Draws a quad with the specified texture id
	 * 
	 * @param position the reference position of the quad
	 * @param q the quad
	 * @param texureId the texture id
	 */
	public void drawTexturedQuad(Vector position, Quad q, String textureId, double textureResolution);

	/**
	 * Draws a quad with the specified texture id
	 * 
	 * @param position the reference position of the quad
	 * @param q the quad
	 * @param texureId the texture id
	 */
	public void drawTexturedPolygon(Vector position, Polygon p, String textureId, double textureResolution);

	
	/**
	 * Draws a textured rectangle with the specified texture id
	 * 
	 * @param r the rectangle
	 * @param texId the texture id
	 */
	public void drawBackgroundRect(Rectangle r, String texId);

	/**
	 * Draws a polygon with texture coordinates
	 * 
	 * @param p the polygon
	 * @param t the texture coordinates
	 */
	void drawPolygon(Polygon p, Polygon t);

	/**
	 * Draws a rectangle which has a given color 
	 * @param r - Declares the size of the Rectangle
	 * @param color - Sets the color of the rectangle
	 * @param xPos - Sets the x coordinate for the rectangle position
	 * @param yPos - Sets the y coordinate for the rectangle position
	 */
	public void drawColoredRect(Rectangle r, Color color, double xPos, double yPos);


	
}
