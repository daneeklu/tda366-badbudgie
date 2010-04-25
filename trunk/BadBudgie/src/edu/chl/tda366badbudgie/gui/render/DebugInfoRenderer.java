package edu.chl.tda366badbudgie.gui.render;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * DebugInfoRenderer
 * 
 * Singleton used for drawing debug info to screen.
 * Graphical lines and text can be added to the buffer anywhere in the code, 
 * and are drawn by calling drawDebugInfo() from the game's render method.
 * 
 * @author kvarfordt
 *
 */
public class DebugInfoRenderer {

	private List<DebugLine> debugLines;
	private List<String> debugText;
	private boolean debugInfoEnabled = true;
	
	private static DebugInfoRenderer instance;
	
	private DebugInfoRenderer() {
		debugLines = new LinkedList<DebugLine>();
		debugText = new LinkedList<String>();
	}
	
	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the instance
	 */
	public static DebugInfoRenderer getInstance() {
		if (instance == null) {
			instance = new DebugInfoRenderer();
		}
		return instance;
	}
	
	/**
	 * Adds a line to the class' buffer, to be 
	 * drawn on the next call to drawDebugInfo().
	 * 
	 * @param start the start point of the line
	 * @param end the end point of the line
	 * @param c the color of the line
	 */
	public void addDebugLine(Vector start, Vector end, Color c) {
		debugLines.add(new DebugLine(start, end, c));
	}
	
	/**
	 * Adds a line of text to the class' buffer, to be 
	 * drawn on the next call to drawDebugInfo().
	 * 
	 * @param string the text to be drawn
	 */
	public void addDebugText(String string) {
		debugText.add(string);
	}

	/**
	 * Call this method from the game's render 
	 * method, between the calls to startRender() and stopRender()
	 * to draw the contents of the class' buffer to screen.
	 * 
	 * @param g the graphics object to use.
	 */
	public void drawDebugInfo(IGraphics g) {
		if (isDebugInfoEnabled()) {
			for (DebugLine d : debugLines) {
				g.drawLine(d.start, d.end, d.c);
			}
			int i = 0;
			for (String s : debugText) {
				g.drawText(s, 10, 25 * i++);
			}
			
			debugLines.clear();
			debugText.clear();
		}
	}
	
	/**
	 * Enable or disable drawing of debug info.
	 * 
	 * @param debugInfoEnabled the boolean telling the class to draw or not
	 */
	public void setDebugInfoEnabled(boolean debugInfoEnabled) {
		this.debugInfoEnabled = debugInfoEnabled;
	}

	/**
	 * Returns true if debug drawing is enabled.
	 * 
	 * @return true if enabled
	 */
	public boolean isDebugInfoEnabled() {
		return debugInfoEnabled;
	}


	/**
	 * Private inner class for a line in the buffer.
	 * 
	 * @author Daniel
	 *
	 */
	private class DebugLine {

		private Vector start;
		private Vector end;
		private Color c;
		
		public DebugLine(Vector start2, Vector end2, Color c2) {
			start = start2;
			end = end2;
			c = c2;
		}
	}

	
}
