package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

/**
 * LevelManager
 * Singleton class that handles references 
 * to levels objects.
 * 
 * @author jesper
 *
 */
public class LevelManager {
		
	private static LevelManager lm;
	private List<Level> levels = new LinkedList<Level>();
	
	private LevelManager(){
	}
	
	/**
	 * Returns the instance of the LevelManager
	 * @return the LevelManager
	 */
	public static LevelManager getInstance(){
		if(lm==null){
			lm = new LevelManager();
		}
		return lm;
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
		return levels.get(index);
	}
	
}
