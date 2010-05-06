package edu.chl.tda366badbudgie.core;

import java.util.Iterator;

import edu.chl.tda366badbudgie.core.AbstractGameObject.GameRoundMessage;


/**
 * GameRound
 * 
 * A session of the game. 
 * Keeps track of the current level and the players score.
 * 
 * @author kvarfordt, lumbo
 * 
 */
public class GameRound {

	private Level currentLevel;
	private int currentLevelNumber;
	private Player player;
	private int score;

	/**
	 * Makes a new GameRound
	 */
	public GameRound() {

		currentLevelNumber = 0;
		currentLevel = LevelManager.getInstance().getLevel(currentLevelNumber);
		player = currentLevel.getPlayer();
	}

	/**
	 * Returns the current level
	 * @return the current level
	 */
	public Level getLevel() {
		return currentLevel;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Delegates actions to the right objects.
	 * 
	 * @param id
	 *            a string identifying the action
	 * @param down
	 *            a boolean that's true if the key was pressed, and false if it
	 *            was released.
	 */
	public void keyboardAction(String id, boolean down) {

		if (id.equals("moveleft")) {
			player.moveLeft(down);
		}
		if (id.equals("moveright")) {
			player.moveRight(down);
		}
		if (id.equals("jump")) {
			player.jumpOrFlap(down);
		}

	}

	/**
	 * This method calls the updateState on all objects inheriting
	 * AbstractGameObject.
	 */
	public void updateGameObjects() {
		
		// The objects pass back events as strings
		GameRoundMessage gameEvent;
		
		// Using iterator so that an object can be removed in the loop
		for (Iterator<AbstractGameObject> i = currentLevel.getGameObjects().iterator(); i.hasNext(); ) {
			AbstractGameObject ago = i.next();
			
			// Update each object and receive the passed back GameRoundMessage
			gameEvent = ago.update();
			
			// See if an event requiring action from GameRound has occurred
			if (gameEvent == GameRoundMessage.LevelFinished) {
				currentLevel = LevelManager.getInstance().getLevel(++currentLevelNumber);
				player = currentLevel.getPlayer();
				break;
			}
			else if (gameEvent == GameRoundMessage.PlayerDied) {
				// TODO: Player dies
			}
			else if (gameEvent == GameRoundMessage.RemoveObject) {
				i.remove();
			}
		}
	}

	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	
	public void addGameObject(AbstractGameObject agb){
		getCurrentLevel().addGameObject(agb);
	}

	public void mouseAction(double x, double y, boolean mouseDown) {
		getPlayer().shootAt(x, y, mouseDown);
	}
	
	public boolean isPlayerAlive() {
		return (getPlayer().getHealth() > 0);
	}

	public void mouseMove(double x, double y) {
		getPlayer().setAim(x, y);
		
	}

}
