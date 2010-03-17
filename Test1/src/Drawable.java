import javax.media.opengl.*;
/**
 * Allows an object of the implementing class to draw 
 * graphic content to a canvas through a GL object.
 * 
 * @author jesper
 * 
 */
public interface Drawable {
	/**
	 * Draws graphical shapes or textures using OpenGL.
	 * @param gl the GL object which will handle the calls to the primitive functions.
	 */
	public void draw(GL gl);
}
