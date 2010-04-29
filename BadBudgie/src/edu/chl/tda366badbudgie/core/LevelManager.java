package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

public class LevelManager {
		
	private static LevelManager lm;
	private List<Level> levels = new LinkedList<Level>();
	
	private LevelManager(){
	}
	
	public static LevelManager getInstance(){
		if(lm==null){
			lm = new LevelManager();
		}
		return lm;
	}
	
	public void addLevel(Level level){
		levels.add(level);
	}
	
	
	/**
	 * Returns the level of the index.
	 * @param index
	 * @return
	 */
	public Level getLevel(int index){
		return levels.get(index);
	}
	
}
