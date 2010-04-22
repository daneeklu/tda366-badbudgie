package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.List;

import edu.chl.tda366badbudgie.physics.ICollidable;

public class Level {

	private ArrayList<ICollidable> collidables;
	private ArrayList<TerrainSection> terrainQuads;
	
	public List<ICollidable> getCollidableObjects() {
		return collidables;
	}
	
	public List<TerrainSection> getTerrainSections() {
		return terrainQuads;
	}
	
}
