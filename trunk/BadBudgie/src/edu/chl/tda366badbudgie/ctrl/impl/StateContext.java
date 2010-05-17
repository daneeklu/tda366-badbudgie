package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * StateContext
 * 
 * This class is responsible for the state of the game, 
 * whether it's in the menu or in a running game.
 * 
 * @author kvarfordt
 *
 */
public class StateContext implements IStateContext {
	
	private JFrame graphicsFrame;
	
	private IState menuState;
	private IState inGameState;
	
	// The current state of the game.
	private IState currentState;
	
	// Singleton instance
	private static StateContext instance;
	
	private StateContext() {
		menuState = new MenuState();
		currentState = menuState;
	}
	
	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the instance
	 */
	public static IStateContext getInstance() {
		if (instance == null) {
			instance = new StateContext();
		}
		return instance;
	}
	
	/**
	 * Returns the current state object.
	 */
	public IState getState() {
		return currentState;
	}
	
	/**
	 * Calls doLogic on the current state.
	 */
	public void doLogic() {
		currentState.logic();
	}
	
	/**
	 * Tells the current state to draw itself.
	 */
	public void draw(IGraphics g) {
		currentState.draw(g);
	}
	
	public void setGameState(IState state) {
		inGameState = state;
		if (state != null)
			currentState = state;
	}
	
	public void setMenuState(IState state) {
		menuState = state;
		if (state != null)
			currentState = state;
	}
	
	@Override
	public void setFrame(JFrame graphicsFrame) {
		this.graphicsFrame = graphicsFrame;
	}

	@Override
	public IState getGameState() {
		return inGameState;
	}

	@Override
	public IState getMenuState() {
		return menuState;
	}

	@Override
	public void shutDown() {
		if (graphicsFrame != null) {
			WindowEvent wev = 
				new WindowEvent(graphicsFrame, WindowEvent.WINDOW_CLOSING);
	        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}
		else {
			throw new IllegalStateException("The GraphicsFrame " +
					"has not been set.");
		}
	}
	
	@Override
	public void setFullscreen(boolean fullscreen) {
    	if (fullscreen) {
    		graphicsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    	}
    	else {
    		graphicsFrame.setExtendedState(JFrame.NORMAL);
    	}
	}

	@Override
	public boolean getFullscreen() {
		return (graphicsFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH);
	}

	@Override
	public void setMenuState() {
		if (menuState == null)
			menuState = new MenuState();
		currentState = menuState;
	}

	@Override
	public void setGameState() {
		if (inGameState == null)
			inGameState = new InGameState(new GameRound());
		currentState = inGameState;
		
	}
	
	
}
