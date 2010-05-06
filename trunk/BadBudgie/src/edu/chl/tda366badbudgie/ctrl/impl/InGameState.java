package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.ai.impl.EnemyAI;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;
import edu.chl.tda366badbudgie.gui.render.GameRenderer;
import edu.chl.tda366badbudgie.physics.impl.Physics;

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
	private IAI enemyAi;
	private int mX, mY;
	
	
	public InGameState(GameRound gr) {
		gameRound = gr;
		physics = new Physics();
		enemyAi = new EnemyAI();
	}
	
	@Override
	public void logic() {
		
		enemyAi.doAI(gameRound);
		physics.doPhysics(gameRound);		
		gameRound.updateGameObjects();
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
		
		if (down && id.equals("debug")) {
			DebugInfoRenderer.getInstance().setDebugInfoEnabled(!DebugInfoRenderer.getInstance().isDebugInfoEnabled());
		}
	}



	@Override
	public void mouseActionClick(boolean pressed) {
		gameRound.mouseAction(mX, mY, pressed);
		//new Projectile(mX, mY);
	}

	@Override
	public void mouseActionMoved(int x, int y) {
		mX = x;
		mY = y;
		gameRound.mouseMove(x,y);
		//System.out.println("Aiming at x: " + x + " y: " + y);
	}

	
	public GameRound getGameRound() {
		return gameRound;
	}
	
}
