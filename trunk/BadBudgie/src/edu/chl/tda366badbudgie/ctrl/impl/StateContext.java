package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * This class is responsible for the state of the game, 
 * whether it's in the menu or in a running game.
 * 
 * @author dkvarfordt
 *
 */
public class StateContext implements IStateContext {
	
	private IState menuState;
	private IState inGameState;
	
	// The current state of the game.
	private IState currentState;
	
	private static StateContext instance;
	
	private StateContext() {
		menuState = new MenuState();
		inGameState = new InGameState(new GameRound());
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
	public void setState(String stateid) {
		if (stateid.equals("startGame")) {
			currentState = inGameState;
		} else if (stateid.equals("menu")) {
			currentState = menuState;
		}
	}


	public void shutDown() {
		
	}
	
	
}
