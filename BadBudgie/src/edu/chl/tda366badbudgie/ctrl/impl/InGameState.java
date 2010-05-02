package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.GameRenderer;
import edu.chl.tda366badbudgie.physics.CollisionHandler;
import edu.chl.tda366badbudgie.physics.MovementHandler;
import edu.chl.tda366badbudgie.physics.Physics;

/**
 * InGameState
 * 
 * State of a running game.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class InGameState implements IState {

	private GameRound gameRound;
	private Physics physics;
	private boolean mouseDown;
	private double mX, mY;
	
	
	public InGameState(GameRound gr) {
		gameRound = gr;
		physics = new Physics(gr,new CollisionHandler(), new MovementHandler());
	}
	
	@Override
	public void logic() {
		
		gameRound.updateGameObjects();
		physics.doPhysics(gameRound);
	}
	
	@Override
	public void draw(IGraphics g) {
		GameRenderer.render(gameRound,g);
	}

	@Override
	public void keyboardAction(String id, boolean down) {
		
		gameRound.keyboardAction(id, down);
		
		if (down && id.equals("escape")) {
			StateContext.getInstance().setState(StateContext.getInstance().getMenuState());
			return;
		}
	}



	@Override
	public void mouseActionClick(boolean pressed) {
		gameRound.mouseAction(mX, mY, pressed);
		//new Projectile(mX, mY);
	}

	@Override
	public void mouseActionMoved(double x, double y) {
		mX = x;
		mY = y;
		//System.out.println("Aiming at x: " + x + " y: " + y);
	}

	
	public GameRound getGameRound() {
		return gameRound;
	}
	
}
