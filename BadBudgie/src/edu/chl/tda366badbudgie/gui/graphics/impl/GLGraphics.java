package edu.chl.tda366badbudgie.gui.graphics.impl;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.Map;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import com.sun.opengl.util.GLUT;

import edu.chl.tda366badbudgie.core.ImageDataHandler;
import edu.chl.tda366badbudgie.core.Polygon;
import edu.chl.tda366badbudgie.core.Quad;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.Sprite;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.io.impl.FileManager;
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
	
	private final int fullwidth;
	private final int fullheight;
	//The preferred ratio = preferred width / preferred height
	private final double ratio;
	private double width;
	private double height;
	
	private Vector cameraPosition;
	
	//Has the program been properly inited? (resources etc)
	private boolean ready;
	
	private TextureManager textureManager;
	
	public GLGraphics(int width, int height) {
		canvas = new GLCanvas();
		canvas.createContext(null).makeCurrent();
		canvas.addGLEventListener(this);
		canvas.setAutoSwapBufferMode(false);
		fullwidth = width;
		fullheight = height;
		ratio = (double)fullwidth / (double)fullheight;
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
		
		this.cameraPosition = cameraPosition;
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		

		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluOrtho2D(0, width, 0, height);
		gl.glTranslatef((float)(width/2 - ( cameraPosition.getX())),(float)(height/2 - ( cameraPosition.getY())), 0);

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

		Map<String, BufferedImage> imgMap = ImageDataHandler.getInstance().getData();
		Iterator<String> it = imgMap.keySet().iterator();
		
		while(it.hasNext()){
			String key = it.next();
			addTexture(key, imgMap.get(key));
		}
		
		ready = true;
		GL gl = glDraw.getGL();
		glu = new GLU();
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		
		
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		//Don't render, rendering is done elsewhere
	}

	@Override
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
		//Nothing to be implemented
		
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y,
			int width, int height) {

		// Refit the GL width and height mappings so the ratio of the
		// screen stays intact
		
		
		// If the screen is currently too wide...
		if ((double)width / (double)height >  ratio) {
			
			//Height decides the size
			this.height = fullheight;
			this.width = (fullwidth * width) / (height * ratio);
			
			//If the screen is very small, then make it
			//crop rather than zoom
			if (height < fullheight / 2.0) {
				this.height = 2.0 * height;
				this.width = (fullwidth * (double)width ) / (ratio * ((double)fullheight / 2.0));
			}
			
		} else {
			//The screen is too high
			//Width decides the size
			this.width = fullwidth;
			this.height =  (fullheight * height) * ratio / (width);
			
			//If the screen is very small, then make it
			//crop rather than zoom
			if (width < fullwidth / 2.0) {
				this.width = 2.0 * width;
				this.height = (fullheight * height * ratio) / (fullwidth / 2.0);
			}
		}
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
	public void drawBackgroundRect(Rectangle r, String texId) {
		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}
		
		double xr = r.getX() + cameraPosition.getX();
		double yr = r.getY() + cameraPosition.getY();
		double w = r.getWidth();
		double h = r.getHeight();
		
		
		setActiveTexture(texId);
		
		gl.glBegin(GL.GL_QUADS);
		gl.glEnable(GL.GL_TEXTURE_2D);

		gl.glTexCoord2d(0, 1);	
		gl.glVertex2d(xr, yr);
		gl.glTexCoord2d(1, 1);
		gl.glVertex2d(xr + w, yr);
		gl.glTexCoord2d(1, 0);
		gl.glVertex2d(xr + w, yr + h);
		gl.glTexCoord2d(0, 0);
		gl.glVertex2d(xr, yr + h);
		
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
	public void drawTexturedQuad(Quad q, String textureId, double texRes) {
		
		GL gl = canvas.getGL();
		GLContext con = canvas.getContext();
		if (GLContext.getCurrent() != con) {
			return;
		}

		gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
	    gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
		setActiveTexture(textureId);

		
		gl.glBegin(GL.GL_QUADS);
		
		for (Vector v : q.getVertices()) {
			
			gl.glTexCoord2d(v.getX() * texRes, v.getY() * texRes);
			gl.glVertex2d(v.getX(), v.getY() );
			
		}
		
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
		gl.glRasterPos2d(x - width/2 + ((float) cameraPosition.getX()), height/2 - y - 30 + ((float) cameraPosition.getY()));
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
		
		gl.glColor3d(0.5, 0.5, 0.5);
		
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
	public void drawRectSection(Rectangle rectangle, int section, int hres,
			int vres) {
		
		double tx, ty;
		double frameWidth = 1.0 / hres;
		double frameHeight = 1.0 / vres;
		
		tx = (double)(section % hres ) * frameWidth;
		ty = (double)(section / hres ) * frameHeight;
		
		drawRect(rectangle, new Rectangle(tx, ty, frameWidth, frameHeight));
		
	}

	@Override
	public void drawSprite(Sprite sprite, Rectangle bounds) {
		String texId = sprite.getId();
		
		double tx, ty;
		double frameWidth = 1.0 / sprite.getHorFrames();
		double frameHeight = 1.0 / sprite.getVerFrames();
		
		int frameIndex = sprite.getFrameIndex();
		
		tx = (double)(frameIndex % sprite.getHorFrames() ) * frameWidth;
		ty = (double)(frameIndex / sprite.getHorFrames() ) * frameHeight;
		
		setActiveTexture(texId);
		drawRect(bounds, new Rectangle(tx, ty, frameWidth, frameHeight));
		
	}

	
}
