package edu.chl.tda366badbudgie.core;

import java.util.List;

import edu.chl.tda366badbudgie.core.content.Level;
import edu.chl.tda366badbudgie.core.content.TerrainSection;
import edu.chl.tda366badbudgie.core.physics.ICollidable;
import edu.chl.tda366badbudgie.core.physics.Physics;

public class GameRound {
	
	private Level currentLevel;
	private Physics physics;

	
	public List<ICollidable> getCollidableObjects() {
		return currentLevel.getCollidableObjects();
	}
	
	public List<TerrainSection> getTerrainSections() {
		return currentLevel.getTerrainSections();
	}
	
	public void doLogic() {
		if(physics == null) return;
		physics.doPhysics();
	}
	
	
}
