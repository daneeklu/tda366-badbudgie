package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
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
	
	private ArrayList<AbstractGameObject> gameObjects;
	private ArrayList<TerrainSection> terrainQuads;
	
	private String backgroundTexId;
	
	/**
	 * Constructs a new Level object.
	 */
	public Level() {
		
		gameObjects = new ArrayList<AbstractGameObject>();
		terrainQuads = new ArrayList<TerrainSection>();
		
		// TODO: Load from level data
		setBackgroundTexId("background");
		
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
		return terrainQuads;
	}
	
	/**
	 * Adds a TerrainSection to the level.
	 * @param t the TerrainSection to add
	 */
	public void addTerrainSection(TerrainSection t) {
		terrainQuads.add(t);
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
