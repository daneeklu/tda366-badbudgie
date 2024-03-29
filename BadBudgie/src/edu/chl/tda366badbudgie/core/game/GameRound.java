package edu.chl.tda366badbudgie.core.game;

import java.util.Iterator;
import edu.chl.tda366badbudgie.util.*;


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
	
	/**
	 * Enum containing possible signals from an 
	 * AbstractGameObject to the GameRound.
	 */
	public enum GameRoundMessage{NO_EVENT, LEVEL_FINISHED, PLAYER_DIED, 
		REMOVE_OBJECT};

	private Level currentLevel;
	private Player player;
	private int score;

	/**
	 * Makes a new GameRound
	 */
	public GameRound() {
		player = new Player(new Vector(0,0));
		player.setCollisionData(new RoundedRectangle(40, 80, 15));
	}

	/**
	 * Returns the current level
	 * @return the current level
	 */
	public Level getLevel() {
		return currentLevel;
	}
	
	/**
	 * Set the current level
	 * @param level the level to set as active
	 */
	public void setLevel(Level level) {
		currentLevel = level;
		player.setPosition(level.getStartPosition());
		currentLevel.setPlayer(player);
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
			getPlayer().moveLeft(down);
		}
		if (id.equals("moveright")) {
			getPlayer().moveRight(down);
		}
		if (id.equals("jump")) {
			getPlayer().jumpOrFlap(down);
		}

	}

	/**
	 * This method calls the updateState on all objects inheriting
	 * AbstractGameObject.
	 */
	public GameRoundMessage updateGameObjects() {
		
		// The objects pass back events as strings
		GameRoundMessage gameEvent;
		
		// Using iterator so that an object can be removed in the loop
		for (Iterator<AbstractGameObject> i = 
			currentLevel.getGameObjects().iterator(); i.hasNext(); ) {
			
			AbstractGameObject ago = i.next();
			
			// Update each object and receive the passed back GameRoundMessage
			gameEvent = ago.update();
			
			if (gameEvent == GameRoundMessage.REMOVE_OBJECT) {
				i.remove();
			}
			
			/*
			 *  Forward some of the messages through return value to the 
			 *  InGameState object.
			 */
			if (gameEvent == GameRoundMessage.LEVEL_FINISHED
				|| gameEvent == GameRoundMessage.PLAYER_DIED) {
				return gameEvent;
			}

		}
		
		/*
		 *  If any objects were scheduled for addition to the level, add them.
		 *  Removing them mid-iteration raises a concurrent modification 
		 *  exception.
		 */
		for (AbstractGameObject ago : currentLevel.getScheduledForAddition()) {
			currentLevel.addGameObject(ago);
		}
		currentLevel.getScheduledForAddition().clear();
		
		return GameRoundMessage.NO_EVENT;
	}

	/**
	 * Adds an AbstractGameObject to the current level.
	 * @param agb the AbstractGameObject to be added.
	 */
	public void addGameObject(AbstractGameObject agb){
		getLevel().addGameObject(agb);
	}

	/**
	 * Delegates mouse-press events to game logic.
	 * @param x the x-coordinate of the mouse position.
	 * @param y the y-coordinate of the mouse position.
	 * @param mouseDown denotes whether the mouse is pressed.
	 */
	public void mouseAction(int button, double x, double y, boolean mouseDown) {
		if (button == 1)
			getPlayer().shootToggle(mouseDown);
		else if (button == 3) {
			getPlayer().jumpOrFlap(mouseDown);
		}
	}
	
	/**
	 * Delegates mouse-movement events to game logic.
	 * @param x the x-coordinate of the mouse position.
	 * @param y the y-coordinate of the mouse position.
	 */
	public void mouseMove(double x, double y) {
		
		// Convert from screen coordinates to world coordinates
		Vector worldAim = 
			Screen.screenToWorldCoordinates(new Vector(x, y), 
					getPlayer().getPosition());
		
		getPlayer().setAim(worldAim.getX(), worldAim.getY());
		
	}
	
	/**
	 * Returns the score.
	 * @return the score.
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Set the score.
	 * @param score
	 */
	public void setScore(int score){
		this.score = score;
	}
	
}
