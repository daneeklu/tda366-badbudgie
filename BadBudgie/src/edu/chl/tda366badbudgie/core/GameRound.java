package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.physics.CollisionHandler;
import edu.chl.tda366badbudgie.physics.ICollidable;
import edu.chl.tda366badbudgie.physics.Physics;

public class GameRound {
	
	private Level currentLevel;
	private Player player;
	private int score;
	
	public GameRound() {
		
		/*
		 * TODO: Real game data loading. Temporary test data:
		 */
		
		currentLevel = new Level();
		player = new Player();
		
	}
	
	public Level getLevel() {
		return currentLevel;
	}
	
	
}
