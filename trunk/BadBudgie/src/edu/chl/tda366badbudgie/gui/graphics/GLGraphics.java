package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

import edu.chl.tda366badbudgie.core.content.Rectangle;
import edu.chl.tda366badbudgie.io.FileManager;
import edu.chl.tda366badbudgie.io.IFileManager;


public class GLGraphics implements GLEventListener, IGraphics{
	private GL gl;
	private GLCanvas canvas;
	private IRenderer rend;
	private TextureManager textureManager;
	public GLGraphics(IRenderer r){
		rend = r;

	}
	
	@Override
	public Canvas getCanvas() {
		if(canvas != null) return canvas;
		
		canvas = new GLCanvas();
		canvas.addGLEventListener(this);
		canvas.setAutoSwapBufferMode(false);
		return (Canvas)canvas;
	}
	
	@Override
	public void startRendering() {
		canvas.repaint();
	}

	

	@Override
	public void init(GLAutoDrawable glDraw) {

		
		gl = glDraw.getGL();
		textureManager = new TextureManager(gl);
		
		IFileManager fm = new FileManager(this);
		fm.loadData();
		
	}
	
	@Override
	public void display(GLAutoDrawable glDraw) {

		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

		rend.render(this);
		canvas.swapBuffers();

	}

	@Override
	public void displayChanged(GLAutoDrawable arg0, boolean arg1, boolean arg2) {
		//Nothing to be implemented
		
	}
	
	@Override
	public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
			int arg4) {
		//Nothing to be implemented
		
	}

	@Override
	public void drawRect(Rectangle r) {
		double x = r.getX(), y = r.getY();
		double w = r.getWidth(), h = r.getHeight();
		gl.glBegin(GL.GL_QUADS);
		
		gl.glTexCoord2d(0.0, 1.0);
		gl.glVertex2d(x, y );
		
		gl.glTexCoord2d(0.0, 0.0);
		gl.glVertex2d(x, y + h);
		
		gl.glTexCoord2d(1.0, 0.0);
		gl.glVertex2d(x + w, y + h);
		
		gl.glTexCoord2d(1.0, 1.0);
		gl.glVertex2d(x + w, y);
		
		gl.glEnd();		
	}

	@Override
	public String getActiveTexture() {
		return textureManager.getActiveTexture();
	}

	@Override
	public void setActiveTexture(String id) {
		textureManager.setActiveTexture(id);
		
	}
	
	public void addTexture(String id, BufferedImage data) {
		textureManager.addTexture(id, data);
	}
	
}
