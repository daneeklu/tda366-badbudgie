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
	//List of levels held by manager.
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
		return (Level)(levels.get(index%levels.size()).clone());
	}
	
}
