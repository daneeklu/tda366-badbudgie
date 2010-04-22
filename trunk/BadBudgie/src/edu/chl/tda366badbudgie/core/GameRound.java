package edu.chl.tda366badbudgie.core;

import java.util.List;

import edu.chl.tda366badbudgie.physics.ICollidable;
import edu.chl.tda366badbudgie.physics.Physics;

public class GameRound {
	
	private Level currentLevel;
	private Physics physics;
	private Player player;
	private int score;
	
	public List<ICollidable> getCollidableObjects() {
		return currentLevel.getCollidableObjects();
	}
	
	public List<TerrainSection> getTerrainSections() {
		return currentLevel.getTerrainSections();
	}
	
}
