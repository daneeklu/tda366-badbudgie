package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.ai.impl.EnemyAI;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.LevelManager;
import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
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
	
	// The current mouse position on the screen
	private int mX, mY;
	
	/**
	 * Constructor
	 * @param gameRound
	 */
	public InGameState(GameRound gameRound) {
		
		this.gameRound = gameRound;
		gameRound.setLevel(LevelManager.getInstance().getLevel(1));
		physics = new Physics();
		enemyAi = new EnemyAI();		
	}
	
	@Override
	public void logic() {
		
		enemyAi.doAI(gameRound);
		physics.doPhysics(gameRound);		
		
		// Update the game world, and get a signal if something happened
		GameRoundMessage gameAction = gameRound.updateGameObjects();
		 
		
		// If a level is finished, switch to the next
		if (gameAction == GameRoundMessage.LEVEL_FINISHED) {
			gameRound.setLevel(LevelManager.getInstance().getLevel(
					gameRound.getLevel().getNumber() + 1));
			gameRound.getPlayer()
					.setPosition(gameRound.getLevel().getStartPosition());
			
		// If the player died, terminate the game state and return to menu
		} else if (gameAction == GameRoundMessage.PLAYER_DIED) {
			StateContext.getInstance().setGameState(null);
			StateContext.getInstance().setMenuState();
		}
	}
	
	@Override
	public void draw(IGraphics g) {
		GameRenderer.render(gameRound,g);
	}

	@Override
	public void keyboardAction(String id, boolean down) {
		
		gameRound.keyboardAction(id, down);
		
		if (down && id.equals("escape")) {
			StateContext.getInstance().setMenuState();
			return;
		}
		
		if (down && id.equals("debug")) {
			DebugInfoRenderer.getInstance().setDebugInfoEnabled(
					!DebugInfoRenderer.getInstance().isDebugInfoEnabled());
		}
	}



	@Override
	public void mouseButtonAction(int button, boolean pressed) {
		gameRound.mouseAction(button, mX, mY, pressed);
	}

	@Override
	public void mouseMoved(int x, int y) {
		mX = x;
		mY = y;
		gameRound.mouseMove(x,y);
	}

	/**
	 * Get the active gameRound object for this state.
	 * @return gameRound
	 */
	public GameRound getGameRound() {
		return gameRound;
	}
	
}
