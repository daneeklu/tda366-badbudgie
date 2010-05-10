package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Level
 * 
 * Class representing a game level with terrain and objects.
 * 
 * @author kvarfordt
 *
 */
public class Level {
	
	private List<AbstractGameObject> gameObjects;
	private List<TerrainSection> terrainSections;
	
	private String backgroundTexId;
	
	/**
	 * Constructs a new Level object.
	 */
	public Level() {
		
		gameObjects = new ArrayList<AbstractGameObject>();
		terrainSections = new ArrayList<TerrainSection>();
		
		// TODO: Load from level data
		setBackgroundTexId("background");
		
	}
	
	/**
	 * Copy constructor
	 * 
	 * @param level
	 */
	public Level(Level level) {
		this();
		Player p = null;
		Weapon w = null;
		
		for (AbstractGameObject ago : level.getGameObjects()) {
			
			//TEMP WEAPON FIX
			AbstractGameObject agoClone = (AbstractGameObject) ago.clone();
			if(agoClone instanceof Player){
				p = (Player) agoClone;
			} else if(agoClone instanceof Weapon){
				w = (Weapon)agoClone;
			}
			
			addGameObject((AbstractGameObject) agoClone);
		}
		if(w != null){
			p.setWeapon(w);
		}
		for (TerrainSection ts : level.getTerrainSections()) {
			addTerrainSection((TerrainSection) ts.clone());
		}
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
		go.setParent(this);
	}

	/**
	 * Adds an AbstractGameObject to the level.
	 * @param go the AbstractGameObject to add
	 */
	public void addGameObject(Collection<AbstractGameObject> go) {
		gameObjects.addAll(go);
		for(AbstractGameObject g: go){
			g.setParent(this);
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
		for (AbstractGameObject ago : gameObjects) {
			if (ago instanceof Player)
				return (Player) ago;
		}
		throw new IllegalStateException("No player in level.");
		
	}
	
}
