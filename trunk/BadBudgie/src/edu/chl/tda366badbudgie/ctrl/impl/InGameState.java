package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.GameRenderer;

/**
 * This class represents a running game state.
 * 
 * @author dkvarford
 *
 */
public class InGameState implements IState {

	private GameRound gameRound;
	
	public InGameState(GameRound gr) {
		gameRound = gr;
	}
	
	@Override
	public void logic() {
		
	}
	
	@Override
	public void draw(IGraphics g) {
		GameRenderer.render(gameRound,g);
	}

}
