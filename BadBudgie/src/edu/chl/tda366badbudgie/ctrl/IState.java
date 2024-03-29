package edu.chl.tda366badbudgie.ctrl;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * Interface for the game states.
 */
public interface IState {

	/**
	 * Tell the state to do all it's logic.
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
	 * Handle a mouse press/release.
	 * @param button the mouse button that was affected
	 * @param pressed true if the mouse button was pressed.
	 */
	public void mouseButtonAction(int button, boolean pressed);
	
	/**
	 * Handle mouse movements.
	 * @param x x-coordinate of the mouse pointer.
	 * @param y y-coordinate of the mouse pointer.
	 */
	public void mouseMoved(int x, int y);
	
}
