package edu.chl.tda366badbudgie.ctrl;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public interface IState {

	/**
	 * Perform logical operations.
	 */
	public void logic();
	
	/**
	 * Perform graphical operations.
	 * @param g IGraphics object handling the drawing.
	 */
	public void draw(IGraphics g);

	/**
	 * Handle a keyboard press/release.
	 * @param id id of the pressed/released key.
	 * @param down true if the key was pressed.
	 */
	public void keyboardAction(String id, boolean down);
	
	/**
	 * Handle a mouse click/release.
	 * @param pressed true if the mouse button was pressed.
	 */
	public void mouseActionClick(boolean pressed);
	
	/**
	 * Handle mouse movements.
	 * @param x x-coordinate of the mouse pointer.
	 * @param y y-coordinate of the mouse pointer.
	 */
	public void mouseActionMoved(int x, int y);
	
}
