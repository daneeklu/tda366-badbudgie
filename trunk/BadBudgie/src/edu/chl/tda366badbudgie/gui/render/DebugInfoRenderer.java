package edu.chl.tda366badbudgie.gui.render;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class DebugInfoRenderer {

	private List<DebugLine> debugLines;
	private boolean debugInfoEnabled = true;
	
	private static DebugInfoRenderer instance;
	
	private DebugInfoRenderer() {
		debugLines = new LinkedList<DebugLine>();
	}
	
	public static DebugInfoRenderer getInstance() {
		if (instance == null) {
			instance = new DebugInfoRenderer();
		}
		return instance;
	}
	
	public void addDebugLine(Vector start, Vector end, Color c) {
		debugLines.add(new DebugLine(start, end, c));
	}
	

	public void drawDebugInfo(IGraphics g) {
		if (isDebugInfoEnabled()) {
			for (DebugLine d : debugLines) {
				g.drawLine(d.start, d.end, d.c);
			}
			debugLines.clear();
		}
	}
	
	
	public void setDebugInfoEnabled(boolean debugInfoEnabled) {
		this.debugInfoEnabled = debugInfoEnabled;
	}

	public boolean isDebugInfoEnabled() {
		return debugInfoEnabled;
	}



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
