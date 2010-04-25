package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;
import edu.chl.tda366badbudgie.gui.render.GameRenderer;
import edu.chl.tda366badbudgie.physics.CollisionHandler;
import edu.chl.tda366badbudgie.physics.MovementHandler;
import edu.chl.tda366badbudgie.physics.Physics;

/**
 * InGameState
 * 
 * State of a running game.
 * 
 * @author kvarfordt
 *
 */
public class InGameState implements IState {

	private GameRound gameRound;
	private Physics physics;
	
	
	public InGameState(GameRound gr) {
		gameRound = gr;
		physics = new Physics(gr,new CollisionHandler(), new MovementHandler());
	}
	
	@Override
	public void logic() {
		
		physics.doPhysics(gameRound);
		
		gameRound.updateGameObjects();
		
	}
	
	@Override
	public void draw(IGraphics g) {
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled())
			DebugInfoRenderer.getInstance().addDebugText("AppState: ingame");
			
		GameRenderer.render(gameRound,g);
	}

	@Override
	public void keyboardAction(String id, boolean down) {
		
		gameRound.keyboardAction(id, down);
		
		if (down && id.equals("escape")) {
			StateContext.getInstance().setState("menu");
			return;
		}
		
		System.out.println("In game keypress: " + id + " " + down);
	}

}
