package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.Canvas;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

//import edu.chl.tda366badbudgie.gui.render.AppRenderer;


public class GLGraphics implements GLEventListener, IGraphics{
	private GL gl;
	private GLCanvas canvas;
	private IRenderer rend;
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
	public void drawRect(double x, double y, double w, double h) {
		gl.glBegin(GL.GL_QUADS);
		
		gl.glVertex2d(x, y );
		gl.glVertex2d(x, y + h);
		gl.glVertex2d(x + w, y + h);
		gl.glVertex2d(x + w, y);
		
		gl.glEnd();		
	}
	
}
