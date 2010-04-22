package edu.chl.tda366badbudgie.ctrl;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public interface IState {

	public void logic();
	
	public void draw(IGraphics g);
	
}
