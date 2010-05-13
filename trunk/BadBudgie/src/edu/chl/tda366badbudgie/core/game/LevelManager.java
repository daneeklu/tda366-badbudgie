package edu.chl.tda366badbudgie.core.game;

import java.util.LinkedList;
import java.util.List;

/**
 * LevelManager
 * Singleton class that handles references 
 * to levels objects.
 * 
 * @author jesper, kvarfordt
 *
 */
public class LevelManager {
		
	//Singleton instance
	private static LevelManager instance;
	
	private List<Level> levels = new LinkedList<Level>();
	
	private LevelManager(){
	}
	
	/**
	 * Returns the instance of the LevelManager
	 * @return the LevelManager
	 */
	public static LevelManager getInstance(){
		if(instance==null){
			instance = new LevelManager();
		}
		return instance;
	}
	
	/**
	 * Adds a Level object to the level manager.
	 * @param level the level that will be added.
	 */
	public void addLevel(Level level){
		levels.add(level);
	}
	
	
	/**
	 * Returns the Level of the index.
	 * @param index
	 * @return the Level corresponding to the given index.
	 */
	public Level getLevel(int index){
		return levels.get(index%levels.size()).clone();
	}
	
	/**
	 * Sets the level list. Used by the level editor to test a level.
	 * 
	 * @param levels
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
}
