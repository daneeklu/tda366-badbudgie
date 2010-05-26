package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.chl.tda366badbudgie.util.Vector;

/**
 * Level
 * 
 * Class representing a game level, containing 
 * terrain data and game objects.
 * 
 * @author kvarfordt
 *
 */
public class Level implements Cloneable {
	
	private List<AbstractGameObject> gameObjects = 
		new ArrayList<AbstractGameObject>();
	private List<TerrainSection> terrainSections = 
		new ArrayList<TerrainSection>();
	private List<AbstractGameObject> scheduledForAddition = 
		new ArrayList<AbstractGameObject>();
	
	private String backgroundTexId;
	private Vector startPosition;
	private Player player;
	
	private int levelNumber;
	
	/**
	 * Constructs a new Level object.
	 */
	public Level(int levelNumber, Vector startPosition) {
		this.levelNumber = levelNumber;
		setBackgroundTexId("background");
		this.setStartPosition(startPosition);
	}
	
	/**
	 * Returns a list of all the game objects in the level.
	 * @return a list of AbstractGameObject
	 */
	public List<AbstractGameObject> getGameObjects() {
		return gameObjects;
	}
	
	/**
	 * Returns a list of all the terrain sections in the level.
	 * @return a list of all TerrainSection
	 */
	public List<TerrainSection> getTerrainSections() {
		return terrainSections;
	}
	
	/**
	 * Returns a list of all the game objects inheriting AbstractCollidable 
	 * @return list of collidable objects
	 */
	public List<AbstractCollidable> getCollidableObjects() {
		
		ArrayList<AbstractCollidable> col = 
			new ArrayList<AbstractCollidable>();
		
		for (AbstractGameObject ago : gameObjects) {
			if (ago instanceof AbstractCollidable)
				col.add((AbstractCollidable) ago);
		}
		return col;
	}
	
	/**
	 * Returns a list of all the game objects which are AbstractUnits
	 * @return list of units
	 */
	public List<Enemy> getEnemies() {
		
		ArrayList<Enemy> units = new ArrayList<Enemy>();
		for (AbstractGameObject ago : gameObjects) {
			if (ago instanceof Enemy)
				units.add((Enemy) ago);
		}
		return units;
	}
	
	/**
	 * Adds a TerrainSection to the level.
	 * @param t the TerrainSection to add
	 */
	public void addTerrainSection(TerrainSection t) {
		terrainSections.add(t);
	}
	
	/**
	 * Adds an AbstractGameObject to the level.
	 * @param go the AbstractGameObject to add
	 */
	public void addGameObject(AbstractGameObject go) {
		gameObjects.add(go);
		go.setLevel(this);
	}

	/**
	 * Adds an AbstractGameObject to the level.
	 * @param go the AbstractGameObject to add
	 */
	public void addGameObject(Collection<AbstractGameObject> go) {
		gameObjects.addAll(go);
		for(AbstractGameObject g: go){
			g.setLevel(this);
		}
	}
	
	/**
	 * Get the id for the texture used as the background
	 * image for the level.
	 * 
	 * @param backgroundTexId the background texture id
	 */
	public void setBackgroundTexId(String backgroundTexId) {
		this.backgroundTexId = backgroundTexId;
	}

	/**
	 * Set the id for the texture used as the background
	 * image for the level.
	 * 
	 * @return the background texture id
	 */
	public String getBackgroundTexId() {
		return backgroundTexId;
	}

	
	/**
	 * Return the player in this level.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Set the player object in this level
	 * 
	 * @param p
	 */
	public void setPlayer(Player player){
		this.player = player;
		if(!gameObjects.contains(player)){
			addGameObject(player);
		}
	}
	
	
	@Override
	public Level clone() {
		try {
			Level level;
			level = (Level) super.clone();
			level.gameObjects = new ArrayList<AbstractGameObject>();
			level.terrainSections = new ArrayList<TerrainSection>();
			
			for (AbstractGameObject ago : gameObjects) {
				level.addGameObject(ago.clone());
			}
			for(TerrainSection ts : terrainSections) {
				level.addTerrainSection(ts);
			}
			
			return level;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Clone not supported");
		}
		
	}

	/**
	 * Schedule an object for addition to the level, at the next
	 * logic call. This method is used to add objects to a running 
	 * level, to avoid concurrent modification of the gameObjects list.
	 * @param gameObject the object to add
	 */
	public void scheduleForAddition(AbstractGameObject gameObject) {
		scheduledForAddition.add(gameObject);
	}

	/**
	 * Get a list of all game objects scheduled for addition to this level
	 * @return the list of game objects
	 */
	public List<AbstractGameObject> getScheduledForAddition() {
		return scheduledForAddition;
	}

	/**
	 * Set the start position for the player on this level
	 * 
	 * @param startPosition the position vector
	 */
	public void setStartPosition(Vector startPosition) {
		this.startPosition = startPosition;
	}

	/**
	 * Get the start position for the player on this level
	 * 
	 * @return the position vector
	 */
	public Vector getStartPosition() {
		return startPosition;
	}

	/**
	 * Get the level number for this level,
	 * starting with number = 1 for the first level.
	 * 
	 * @return the level number
	 */
	public int getNumber() {
		return levelNumber;
	}
	
	/**
	 * Set the level number for this level
	 * @param levelNumber
	 */
	public void setNumber(int levelNumber){
		this.levelNumber = levelNumber;
	}
	
}
