package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.List;

import edu.chl.tda366badbudgie.core.content.TerrainQuad;
import edu.chl.tda366badbudgie.core.physics.ICollidable;

public class GameRound {
	
	private ArrayList<ICollidable> collidables;
	private ArrayList<TerrainQuad> terrainQuads;
	
	
	public List<ICollidable> getCollidableObjects() {
		return collidables;
	}
	
	public List<TerrainQuad> getTerrainQuads() {
		return terrainQuads;
	}
	
	
}
