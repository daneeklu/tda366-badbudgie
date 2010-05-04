package edu.chl.tda366badbudgie.core;


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

	public GameRound() {

		currentLevelNumber = 0;
		currentLevel = LevelManager.getInstance().getLevel(currentLevelNumber);
		player = currentLevel.getPlayer();
		
	}

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
		String gameEvent;
		for (AbstractGameObject ago : currentLevel.getGameObjects()) {
			// Update each object
			gameEvent = ago.update();
			
			// See if an event requiring action from GameRound has occured
			if (gameEvent.equals("nextlevel")) {
				currentLevel = LevelManager.getInstance().getLevel(++currentLevelNumber);
				player = currentLevel.getPlayer();
				break;
			}
			else if (gameEvent.equals("playerdied")) {
				// TODO: Player dies
			}
		}
	}

	public Level getCurrentLevel(){
		return currentLevel;
	}
	
	
	public void addGameObject(AbstractGameObject agb){
		currentLevel.addGameObject(agb);
	}

	public void mouseAction(double x, double y, boolean mouseDown) {
		player.shoot(x, y, mouseDown);
	}

}
