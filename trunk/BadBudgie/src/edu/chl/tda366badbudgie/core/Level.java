package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.physics.ICollidable;

public class Level {
	
	private ArrayList<AbstractGameObject> gameObjects;
	private ArrayList<TerrainSection> terrainQuads;
	
	public Level() {
		
		gameObjects = new ArrayList<AbstractGameObject>();
		terrainQuads = new ArrayList<TerrainSection>();
		
		/*
		 * TODO: real data loading. temporary data:
		 */
		List<Vector> tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(0, 1));
		tVerts.add(new Vector(1, 1));
		tVerts.add(new Vector(1, 0));
		TerrainSection t = new TerrainSection(tVerts, 0.5, 0.5);
		terrainQuads.add(t);
		
	}
	
	public List<ICollidable> getCollidableObjects() {
		ArrayList<ICollidable> col = new ArrayList<ICollidable>();
		for (AbstractGameObject ago : gameObjects) {
			if (ago instanceof ICollidable)
				col.add((ICollidable) ago);
		}
		return col;
	}
	
	public List<AbstractGameObject> getGameObjects() {
		return gameObjects;
	}
	
	public List<TerrainSection> getTerrainSections() {
		return terrainQuads;
	}
	
	
}
