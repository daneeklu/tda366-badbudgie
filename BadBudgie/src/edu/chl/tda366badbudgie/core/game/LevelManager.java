package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
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
	//List of levels held by manager.
	private List<Level> levels = new ArrayList<Level>();
	
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
	 * Get a level from the manager.
	 * @return the number of the level to get, starting from 1.
	 */
	public Level getLevel(int levelNumber) {
		return (Level)(levels.get((levelNumber-1)%levels.size()).clone());
	}

	/**
	 * Returns the number of levels held by the LevelManager
	 * @return
	 */
	public int numberOfLevels(){
		return levels.size();
	}
	
}
