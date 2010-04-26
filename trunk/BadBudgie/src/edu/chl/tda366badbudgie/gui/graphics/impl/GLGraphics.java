package edu.chl.tda366badbudgie.gui.graphics.impl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import edu.chl.tda366badbudgie.core.Polygon;
import edu.chl.tda366badbudgie.core.Quad;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.Sprite;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.io.FileManager;
import edu.chl.tda366badbudgie.io.IFileManager;

/**
 * 
 * A graphics class implementing the IGraphics
 * interface, using OpenGL.
 * 
 * @author d.skalle
 *
 */

public class GLGraphics implements GLEventListener, IGraphics{
	private GLCanvas canvas;
	private GLContext con;
	private GL gl;
	private GLU glu;
	
	private int width;
	private int height;
	
	//Has the program been properly inited? (resources etc)
	private boolean ready;
	
	private TextureManager textureManager;
	
	public GLGraphics(int width, int height) {
		canvas = new GLCanvas();
		canvas.createContext(null).makeCurrent();
		canvas.addGLEventListener(this);
		canvas.setAutoSwapBufferMode(false);
		this.width = width;
		this.height = height;
		ready = false;
	}
	
	@Override
	public Canvas getCanvas() {
		return (Canvas)canvas;
	}
	
	@Override
	public boolean startRendering(Vector cameraPosition) {
		
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
		gl.glMatrixMode(GL.GL_PROJECTION);
		glu.gluOrtho2D(0, width, 0, height);
		gl.glTranslatef(width/2 - ((float) cameraPosition.getX()), height/2 - ((float) cameraPosition.getY()), 0);

		return true;
	}

	@Override
	public void stopRendering() {

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
		GL gl = glDraw.getGL();
		glu = new GLU();
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		
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
	
	public void drawRect(Rectangle r, Rectangle tex) {
		double x = r.getX(), y = r.getY();
		double w = r.getWidth(), h = r.getHeight();
		
		double tx1 = tex.getX();
		double ty1 = tex.getY();
		double tx2 = tx1 + tex.getWidth(); 
		double ty2 = ty1 + tex.getHeight();
		
		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
		gl.glBegin(GL.GL_QUADS);
		
		gl.glTexCoord2d(tx1, ty2);
		gl.glVertex2d(x, y );
		
		gl.glTexCoord2d(tx1, ty1);
		gl.glVertex2d(x, y + h);
		
		gl.glTexCoord2d(tx2, ty1);
		gl.glVertex2d(x + w, y + h);
		
		gl.glTexCoord2d(tx2, ty2);
		gl.glVertex2d(x + w, y);
		
		gl.glEnd();		
	}


	@Override
	public void drawQuad(Quad q) {
		
		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
		
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_QUADS);
		
		gl.glColor3d(0.5, 0.5, 0.5);
		for (Vector v : q.getVertices()) {
			gl.glVertex2d(v.getX(), v.getY() );
		}
		
		gl.glColor3d(1.0, 1.0, 1.0);
		gl.glEnable(GL.GL_TEXTURE_2D);
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

	@Override
	public void drawText(String text, int x, int y) {
		
		GLUT glut = new GLUT();
		
		gl.glColor3d(1.0,1.0,1.0);
		gl.glRasterPos2d(x - width, height - y - 30);
		gl.glDisable(GL.GL_TEXTURE_2D);
		glut.glutBitmapString(7, text);
		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glRasterPos2d(0, 0);
		
	}

	@Override
	public void drawPolygon(Polygon p) {

		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
		
		
		gl.glBegin(GL.GL_POLYGON);
		
		gl.glColor3d(1.0, 0.5, 1.0);
		
		for (Vector v : p.getVertices()) {
			gl.glVertex2d(v.getX(), v.getY() );
		}
		
		gl.glEnd();	
		
	}

	@Override
	public void drawLine(Vector start, Vector end, Color c) {

		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
		

		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glBegin(GL.GL_LINE_STRIP);
		gl.glColor3d(((double) c.getRed()) / 256, ((double) c.getGreen()) / 256, ((double) c.getBlue()) / 256);
		
		gl.glLineWidth(2f);
		
		gl.glVertex2d(start.getX(), start.getY());
		gl.glVertex2d(end.getX(), end.getY());

		gl.glEnable(GL.GL_TEXTURE_2D);
		
		gl.glEnd();	
		
	}

	@Override
	public void drawSprite(Sprite s, Vector pos, Vector size) {
		String texId = s.getId();
		
		double tx, ty;
		double frameWidth = 1.0 / s.getHorFrames();
		double frameHeight = 1.0 / s.getVerFrames();
		
		int frameIndex = s.getFrameIndex();
		
		tx = (double)(frameIndex % s.getHorFrames() ) * frameWidth;
		ty = (double)(frameIndex / s.getHorFrames() ) * frameHeight;
		
		setActiveTexture(texId);
		drawRect(new Rectangle(pos, size), new Rectangle(tx, ty, frameWidth, frameHeight));
		
	}

	
}
