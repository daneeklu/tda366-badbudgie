package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * StateContext
 * 
 * This class is responsible for the state of the game, 
 * whether it's in the menu or in a running game.
 * 
 * @author dkvarfordt
 *
 */
public class StateContext implements IStateContext {
	
	private GraphicsFrame graphicsFrame;
	
	private IState menuState;
	private IState inGameState;
	
	// The current state of the game.
	private IState currentState;
	
	private static StateContext instance;
	
	private StateContext() {
		menuState = new MenuState();
		currentState = menuState;
	}
	
	public static IStateContext getInstance() {
		if (instance == null) {
			instance = new StateContext();
		}
		return instance;
	}
	
	public IState getState() {
		return currentState;
	}
	
	public void doLogic() {
		currentState.logic();
	}
	
	public void draw(IGraphics g) {
		currentState.draw(g);
	}

	@Override
	public void setState(IState state) {
		
		if (state instanceof InGameState)
			inGameState = state;
		else if (state instanceof MenuState)
			menuState = state;
		
		currentState = state;
	}

	/**
	 * Tells the GraphicsFrame to shut down the program.
	 * @throws an IllegalStateException if graphicsframe has not been set.
	 */
	public void shutDown() {
		if (graphicsFrame != null) {
			graphicsFrame.shutdown();
		}
		else {
			throw new IllegalStateException("The GraphicsFrame has not been set.");
		}
	}

	@Override
	public void setFrame(GraphicsFrame graphicsFrame) {
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
	
	
}
