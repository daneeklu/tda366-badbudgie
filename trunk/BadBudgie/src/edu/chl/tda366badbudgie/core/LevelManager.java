package edu.chl.tda366badbudgie.core;

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
		return levels.get(index % levels.size());
	}
	
	/*
	 * Test level
	 */
	//TODO: Remove this when levels are stored/loaded from xml.
	/*private Level makeTestLevel() {
		Level level = new Level();

		Player player = new Player(new Vector(0, 320));

		level.addGameObject(player);
		
		//Animation test
		
		/*
		double[] durations = new double[10];
		int[] indices = new int[10];
		for(int i = 0; i <indices.length; i++){
			durations[i] = 0.1;
			indices[i] = i;
		}
		List<Animation> anims = new LinkedList<Animation>();
		anims.add(new Animation("",indices, durations));
		
		Sprite weaponSprite = new Sprite("gun1");
		
		
		//Puts a dummy enemy on the screen
		
		Enemy enemy = new Enemy(new Vector(100, 300));
		
		level.addGameObject(enemy);
		
		
		//Adds the weapon to the player
		
		Weapon wep = new Weapon(new Vector(player.getX(), player.getY()), new Sprite("gun1"));
		level.addGameObject(wep);
		player.setWeapon(wep);
		
		
		//Adds a little rock, then another.

		Obstacle rock = new Obstacle(new Vector(600, 320));
		level.addGameObject(rock);
		
		rock = new Obstacle(new Vector(690, 320));
		level.addGameObject(rock);
		
		
		List<Vector> tVerts = new LinkedList<Vector>();
		TerrainSection t;
		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(1000, 10));
		tVerts.add(new Vector(-1000, 10));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 810));
		tVerts.add(new Vector(-1000, 810));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(-990, 0));
		tVerts.add(new Vector(-990, 800));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(990, 0));
		tVerts.add(new Vector(990, 800));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(100, 0));
		tVerts.add(new Vector(100, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(120, 0));
		tVerts.add(new Vector(120, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(new Polygon(tVerts));
		t.setX(-310);
		level.addTerrainSection(t);		
		
		LevelExit lf = new LevelExit(new Vector(800, 500));
		level.addGameObject(lf);
		
		return level;
	}
	
	

	
	private Level makeTestLevel2() {
		Level level = new Level();

		Player player = new Player(new Vector(0, 320));

		level.addGameObject(player);
		
		//Animation test
		
		/*
		double[] durations = new double[10];
		int[] indices = new int[10];
		for(int i = 0; i <indices.length; i++){
			durations[i] = 0.1;
			indices[i] = i;
		}
		List<Animation> anims = new LinkedList<Animation>();
		anims.add(new Animation("",indices, durations));
		
		Sprite weaponSprite = new Sprite("gun1");
		
		
		//Puts a dummy enemy on the screen
		
		Enemy enemy = new Enemy(new Vector(100, 300));
		
		level.addGameObject(enemy);
		
		
		//Adds the weapon to the player
		
		Weapon wep = new Weapon(new Vector(player.getX(), player.getY()), new Sprite("gun1"));
		level.addGameObject(wep);
		player.setWeapon(wep);
		
		
		//Adds a little rock, then another.

		Obstacle rock = new Obstacle(new Vector(600, 320));
		level.addGameObject(rock);
		
		rock = new Obstacle(new Vector(690, 320));
		level.addGameObject(rock);
		
		
		List<Vector> tVerts = new LinkedList<Vector>();
		TerrainSection t;
		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(1000, 10));
		tVerts.add(new Vector(-1000, 10));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 810));
		tVerts.add(new Vector(-1000, 810));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(-990, 0));
		tVerts.add(new Vector(-990, 800));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(990, 0));
		tVerts.add(new Vector(990, 800));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(100, 0));
		tVerts.add(new Vector(100, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(new Polygon(tVerts));
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(120, 0));
		tVerts.add(new Vector(120, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(new Polygon(tVerts));
		t.setX(-310);
		level.addTerrainSection(t);		
		
		LevelExit lf = new LevelExit(new Vector(0, 500));
		level.addGameObject(lf);
		
		return level;
	}*/

	/**
	 * Sets the level list. Used by the level editor to test a level.
	 * 
	 * @param levels
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
}
