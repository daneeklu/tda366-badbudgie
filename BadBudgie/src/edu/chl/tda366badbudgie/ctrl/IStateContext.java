package edu.chl.tda366badbudgie.ctrl;
import javax.swing.JFrame;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public interface IStateContext {

	/**
	 * Returns the current state object.
	 * 
	 * @return the current state
	 */
	public IState getState();
	
	/**
	 * Calls doLogic on the current state.
	 */
	public void doLogic();
	
	/**
	 * Tells the current state to draw itself.
	 * 
	 * @param g
	 */
	public void draw(IGraphics g);
	
	/**
	 * Returns the game state object.
	 * 
	 * @return the game state
	 */
	public IState getGameState();
	
	/**
	 * Returns the menu state object.
	 * 
	 * @return the menu state
	 */
	public IState getMenuState();
	
	/**
	 * Set the game state
	 * @param state the state to set the game state to
	 */
	public void setGameState(IState state);
	
	/**
	 * Set the menu state
	 * @param state the state to set the menu state to
	 */
	public void setMenuState(IState state);
	

	/**
	 * Switch to the menu state. If the menu state is
	 * currently null, instantiate it then switch to it.
	 */
	public void setMenuState();
	
	/**
	 * Switch to the game state. If the game state is
	 * currently null, instantiate it then switch to it.
	 */
	public void setGameState();
	
	/**
	 * Tells the GraphicsFrame to shut down the program.
     * Requres that setFrame has been called.
	 * 
	 * @throws an IllegalStateException if setFrame() has not been called.
	 */
	public void shutDown();
	
    /**
     * Toggles the frame between fullscreen and windowed mode.
     * Requres that setFrame has been called.
     * 
     * @throws an IllegalStateException if setFrame() has not been called.
     */
	public void toggleFullscreen();

	/**
	 * Sets the GUI frame
	 *  
	 * @param frame the frame to set
	 */
	public void setFrame(JFrame frame);

		
}
