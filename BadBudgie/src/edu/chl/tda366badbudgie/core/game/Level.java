package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.chl.tda366badbudgie.util.Vector;



/**
 * Level
 * 
 * Class representing a game level with terrain and objects.
 * 
 * @author kvarfordt
 *
 */
public class Level implements Cloneable {
	
	private List<AbstractGameObject> gameObjects = new ArrayList<AbstractGameObject>();
	private List<TerrainSection> terrainSections = new ArrayList<TerrainSection>();
	private List<AbstractGameObject> scheduledForAddition = new ArrayList<AbstractGameObject>();
	
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
		
		ArrayList<AbstractCollidable> col = new ArrayList<AbstractCollidable>();
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
	 * @param backgroundTexId the background texture id
	 */
	public void setBackgroundTexId(String backgroundTexId) {
		this.backgroundTexId = backgroundTexId;
	}

	/**
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
	
	public void setPlayer(Player p){
		player = p;
		if(!gameObjects.contains(p)){
			addGameObject(p);
		}
	}
	
	
	@Override
	public Level clone() {
		try {
			Level l;
			l = (Level) super.clone();
			l.gameObjects = new ArrayList<AbstractGameObject>();
			l.terrainSections = new ArrayList<TerrainSection>();
			
			for (AbstractGameObject ago : gameObjects) {
				l.addGameObject(ago.clone());
			}
			for(TerrainSection ts : terrainSections) {
				l.addTerrainSection(ts);
			}
			
			return l;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Clone not supported");
		}
		
	}

	public void scheduleForAddition(AbstractGameObject gameObject) {
		scheduledForAddition.add(gameObject);
	}

	public List<AbstractGameObject> getScheduledForAddition() {
		return scheduledForAddition;
	}

	public void setStartPosition(Vector startPosition) {
		this.startPosition = startPosition;
	}

	public Vector getStartPosition() {
		return startPosition;
	}

	public int getNumber() {
		return levelNumber;
	}
	
}
