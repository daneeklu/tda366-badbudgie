package edu.chl.tda366badbudgie.gui.graphics;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;

import com.sun.opengl.util.FPSAnimator;

import edu.chl.tda366badbudgie.gui.GraphicsFrame;


public class GLGraphics implements GLEventListener, IGraphics{
	private GL gl;
	private GLCanvas canvas;
	private final FPSAnimator animator;
	private GraphicsFrame gf;
	public GLGraphics(GraphicsFrame gf){
		this.gf = gf;
		createCanvas();
		
		canvas.addGLEventListener(this);
		animator = new FPSAnimator(canvas, 30);
		animator.start();
		

	}
	
	@Override
	public void createCanvas() {
		canvas = new GLCanvas();
		gf.add(canvas);
		
	}
	
	@Override
	public void startRendering() {
		animator.start();
	}

	@Override
	public void stopRendering() {
		canvas.swapBuffers();
	}
	

	@Override
	public void init(GLAutoDrawable glDraw) {
		gl = glDraw.getGL();
		
	}
	
	@Override
	public void display(GLAutoDrawable glDraw) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		//gl.glLoadIdentity();
		gl.glBegin(GL.GL_QUADS);
		
		gl.glVertex2f(0.1f, 0.1f);
		gl.glVertex2f(0.1f, 0.3f);
		gl.glVertex2f(0.3f, 0.3f);
		gl.glVertex2f(0.3f, 0.1f);
		
		gl.glEnd();
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
	
}
