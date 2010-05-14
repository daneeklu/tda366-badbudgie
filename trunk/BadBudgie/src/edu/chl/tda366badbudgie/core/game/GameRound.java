package edu.chl.tda366badbudgie.core.game;

import java.util.Iterator;

import edu.chl.tda366badbudgie.core.game.AbstractGameObject.GameRoundMessage;


/**
 * GameRound
 * 
 * A session of the game. 
 * Keeps track of the current level and the players score.
 * Delegates input-events to game logic.
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

	/**
	 * Returns the reference to the Player associated with the GameRound.
	 * @return the Player reference.
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Delegates actions to the right objects.
	 * 
	 * @param id a string identifying the action
	 * @param down a boolean that's true if the 
	 * key was pressed, and false if it was released.
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
			if (gameEvent == GameRoundMessage.LEVEL_FINISHED) {
				currentLevel = LevelManager.getInstance().getLevel(++currentLevelNumber);
				player = currentLevel.getPlayer();
				break;
			}
			else if (gameEvent == GameRoundMessage.PLAYER_DIED) {
				// TODO: Player dies
			}
			else if (gameEvent == GameRoundMessage.REMOVE_OBJECT) {
				i.remove();
			}
		}
		
		// If any objects were scheduled for addition to the level, add them
		for (AbstractGameObject ago : currentLevel.getScheduledForAddition()) {
			currentLevel.addGameObject(ago);
		}
		currentLevel.getScheduledForAddition().clear();
		
	}

	/**
	 * Retrives a reference to the current level.
	 * @return the Level object currently used by the GameRound.
	 */
	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	/**
	 * Adds an AbstractGameObject to the current level.
	 * @param agb the AbstractGameObject to be added.
	 */
	public void addGameObject(AbstractGameObject agb){
		getCurrentLevel().addGameObject(agb);
	}

	/**
	 * Delegates mouse-press events to game logic.
	 * @param x the x-coordinate of the mouse position.
	 * @param y the y-coordinate of the mouse position.
	 * @param mouseDown denotes whether the mouse is pressed.
	 */
	public void mouseAction(double x, double y, boolean mouseDown) {
		getPlayer().shootToggle(mouseDown);
	}
	
	/**
	 * Retrieves the players status with regards to being alive.
	 * @return true if the player is alive.
	 */
	public boolean isPlayerAlive() {
		return (getPlayer().getHealth() > 0);
	}

	/**
	 * Delegates mouse-movement events to game logic.
	 * @param x the x-coordinate of the mouse position.
	 * @param y the y-coordinate of the mouse position.
	 */
	public void mouseMove(double x, double y) {
		getPlayer().setAimScreenCoords(x, y);
		
	}
	
	/**
	 * Returns the score.
	 * @return the score.
	 */
	public int getScore(){
		return score;
	}
	
}
