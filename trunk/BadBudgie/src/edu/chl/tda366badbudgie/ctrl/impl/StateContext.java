package edu.chl.tda366badbudgie.ctrl.impl;

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
	
	public StateContext() {
		menuState = new MenuState();
		currentState = menuState;
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

	
	public void buttonStateChanged(String id, boolean down) {
		if (down) {
			System.out.println("button pressed:" + id);
		}
		else {
			System.out.println("button released:" + id);
		}
	}


}
