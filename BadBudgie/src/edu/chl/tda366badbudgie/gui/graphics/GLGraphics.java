package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;

import edu.chl.tda366badbudgie.core.content.Rectangle;
import edu.chl.tda366badbudgie.io.FileManager;
import edu.chl.tda366badbudgie.io.IFileManager;


public class GLGraphics implements GLEventListener, IGraphics{
	private GLCanvas canvas;
	private GLContext con;
	private GL gl;
	
	//Has the program been properly inited? (resources etc)
	private boolean ready;
	
	private TextureManager textureManager;
	
	public GLGraphics(){
		canvas = new GLCanvas();
		canvas.createContext(null).makeCurrent();
		canvas.addGLEventListener(this);
		canvas.setAutoSwapBufferMode(false);
		ready = false;
	}
	
	@Override
	public Canvas getCanvas() {
		return (Canvas)canvas;
	}
	
	@Override
	public boolean startRendering() {
		
		gl = canvas.getGL();
		con = canvas.getContext();

		if(con.makeCurrent() == GLContext.CONTEXT_NOT_CURRENT) {
				return false;
		}
		
		if(!ready) {
			init(canvas);
		}
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		return true;
	}

	@Override
	public void stopRendering() {
		// TODO Auto-generated method stub
		


		canvas.swapBuffers();
		canvas.getContext().release();
		con = null;
		gl = null;
	}
	

	@Override
	public void init(GLAutoDrawable glDraw) {
		
		System.out.println("OpenGL init");
		textureManager = new TextureManager();
		IFileManager fm = new FileManager(this);
		fm.loadData();
		ready = true;
	}
	
	@Override
	public void display(GLAutoDrawable glDraw) {
		//Don't render, rendering is done elsewhere
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
		
		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
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
		if(textureManager == null) return null;
		return textureManager.getActiveTexture();
	}

	@Override
	public void setActiveTexture(String id) {
		if(textureManager == null) return;
		textureManager.setActiveTexture(id);
		
	}
	
	public void addTexture(String id, BufferedImage data) {
		if(textureManager == null) { return;} 
		textureManager.addTexture(id, data);
	}


	
}
