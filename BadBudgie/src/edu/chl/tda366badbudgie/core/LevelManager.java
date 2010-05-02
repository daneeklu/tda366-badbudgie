package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

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
		levels.add(makeTestLevel());
		levels.add(makeTestLevel2());
		levels.add(makeTestLevel());
		levels.add(makeTestLevel2());
		levels.add(makeTestLevel());
		levels.add(makeTestLevel2());
		levels.add(makeTestLevel());
		levels.add(makeTestLevel2());
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
	private Level makeTestLevel() {
		Level level = new Level();

		Player player = new Player("budgie");
		player.setX(0.0);
		player.setY(320.0);
		LinkedList<Vector> pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		player.setCollisionData(new Polygon(pcd));

		level.addGameObject(player);
		
		//Animation test
		
		List<Double> durations = new LinkedList<Double>();
		List<Integer> indices = new LinkedList<Integer>();
		for(int i = 0; i <10; i++){
			durations.add(0.1);
			indices.add(i+1);
		}
		List<Animation> anims = new LinkedList<Animation>();
		anims.add(new Animation("",indices, durations));
		
		Sprite enemySprite = new Sprite("enemy");
		Sprite weaponSprite = new Sprite("gun1");
		
		//Puts a dummy enemy on the screen
		
		Enemy enemy = new Enemy(enemySprite);
		enemy.setX(100.0);
		enemy.setY(300.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		enemy.setCollisionData(new Polygon(pcd));
		
		level.addGameObject(enemy);
		
		
		//Adds the weapon to the player
		
		Weapon wep = new Weapon("gun1");
		wep.setX(player.getX());
		wep.setY(player.getY());
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		wep.setCollisionData(new Polygon(pcd));
		wep.setSize(new Vector(60, 30));
		level.addGameObject(wep);
		player.setWeapon(wep);
		
		
		//Adds a little rock, then another.

		Obstacle rock = new Obstacle("rock");
		rock.setX(600.0);
		rock.setY(320.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -15));
		
		pcd.add(new Vector(-25, -35));
		pcd.add(new Vector(25, -35));
		
		pcd.add(new Vector(40, -15));
		pcd.add(new Vector(20, 20));
		pcd.add(new Vector(-20, 20));
		rock.setCollisionData(new Polygon(pcd));
		
		level.addGameObject(rock);
		
		rock = new Obstacle("rock");
		rock.setX(690.0);
		rock.setY(320.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -15));
		
		pcd.add(new Vector(-25, -35));
		pcd.add(new Vector(25, -35));
		
		pcd.add(new Vector(40, -15));
		pcd.add(new Vector(20, 20));
		pcd.add(new Vector(-20, 20));
		rock.setCollisionData(new Polygon(pcd)); 

		level.addGameObject(rock);
		
		List<Vector> tVerts = new LinkedList<Vector>();
		TerrainSection t;
		
		
		/*
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(400, 40));
		tVerts.add(new Vector(0, 80));
		TerrainSection t = new TerrainSection(tVerts, 0.4, 0.5, "grass1", 0.0005);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(800, 0));
		tVerts.add(new Vector(800, 40));
		tVerts.add(new Vector(400, 40));
		t = new TerrainSection(tVerts, 0.4, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-400, -3380));
		tVerts.add(new Vector(0, -400));
		tVerts.add(new Vector(0, -320));
		tVerts.add(new Vector(-400, -360));
		t = new TerrainSection(tVerts, 0.1, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-400, 80));
		tVerts.add(new Vector(-80, 400));
		tVerts.add(new Vector(-200, 400));
		tVerts.add(new Vector(-400, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-120, -320));
		tVerts.add(new Vector(-160, -320));
		tVerts.add(new Vector(-80, -160));
		tVerts.add(new Vector(-40, -160));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-120, -320));
		tVerts.add(new Vector(-160, -320));
		tVerts.add(new Vector(-240, -160));
		tVerts.add(new Vector(-200, -160));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);
		*/
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(1000, 10));
		tVerts.add(new Vector(-1000, 10));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 810));
		tVerts.add(new Vector(-1000, 810));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(-990, 0));
		tVerts.add(new Vector(-990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(990, 0));
		tVerts.add(new Vector(990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(100, 0));
		tVerts.add(new Vector(100, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-300);
		level.addTerrainSection(t);
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(120, 0));
		tVerts.add(new Vector(120, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-310);
		level.addTerrainSection(t);		
		
		LevelExit lf = new LevelExit("reagan");
		lf.setX(800);
		lf.setY(500);
		level.addGameObject(lf);
		
		return level;
	}
	
	

	/*
	 * Test level
	 */
	private Level makeTestLevel2() {
		Level level = new Level();

		Player player = new Player("budgie");
		player.setX(0.0);
		player.setY(320.0);
		LinkedList<Vector> pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		player.setCollisionData(new Polygon(pcd));

		level.addGameObject(player);
		
		//Animation test
		
		List<Double> durations = new LinkedList<Double>();
		List<Integer> indices = new LinkedList<Integer>();
		for(int i = 0; i <10; i++){
			durations.add(0.1);
			indices.add(i+1);
		}
		List<Animation> anims = new LinkedList<Animation>();
		anims.add(new Animation("",indices, durations));
		
		Sprite enemySprite = new Sprite("enemy");
		Sprite weaponSprite = new Sprite("gun1");
		
		//Puts a dummy enemy on the screen
		
		Enemy enemy = new Enemy(enemySprite);
		enemy.setX(100.0);
		enemy.setY(300.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		enemy.setCollisionData(new Polygon(pcd));
		
		level.addGameObject(enemy);
		
		
		//Adds the weapon to the player
		
		Weapon wep = new Weapon("gun1");
		wep.setX(player.getX());
		wep.setY(player.getY());
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		wep.setCollisionData(new Polygon(pcd));
		wep.setSize(new Vector(60, 30));
		level.addGameObject(wep);
		player.setWeapon(wep);
		
		
		//Adds a little rock, then another.

		Obstacle rock = new Obstacle("rock");
		rock.setX(-600.0);
		rock.setY(320.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -15));
		
		pcd.add(new Vector(-25, -35));
		pcd.add(new Vector(25, -35));
		
		pcd.add(new Vector(40, -15));
		pcd.add(new Vector(20, 20));
		pcd.add(new Vector(-20, 20));
		rock.setCollisionData(new Polygon(pcd));
		
		level.addGameObject(rock);
		
		rock = new Obstacle("rock");
		rock.setX(-690.0);
		rock.setY(320.0);
		pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -15));
		
		pcd.add(new Vector(-25, -35));
		pcd.add(new Vector(25, -35));
		
		pcd.add(new Vector(40, -15));
		pcd.add(new Vector(20, 20));
		pcd.add(new Vector(-20, 20));
		rock.setCollisionData(new Polygon(pcd)); 

		level.addGameObject(rock);
		
		List<Vector> tVerts = new LinkedList<Vector>();
		TerrainSection t;
		
		
		/*
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(400, 40));
		tVerts.add(new Vector(0, 80));
		TerrainSection t = new TerrainSection(tVerts, 0.4, 0.5, "grass1", 0.0005);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(800, 0));
		tVerts.add(new Vector(800, 40));
		tVerts.add(new Vector(400, 40));
		t = new TerrainSection(tVerts, 0.4, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-400, -3380));
		tVerts.add(new Vector(0, -400));
		tVerts.add(new Vector(0, -320));
		tVerts.add(new Vector(-400, -360));
		t = new TerrainSection(tVerts, 0.1, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-400, 80));
		tVerts.add(new Vector(-80, 400));
		tVerts.add(new Vector(-200, 400));
		tVerts.add(new Vector(-400, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-120, -320));
		tVerts.add(new Vector(-160, -320));
		tVerts.add(new Vector(-80, -160));
		tVerts.add(new Vector(-40, -160));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-120, -320));
		tVerts.add(new Vector(-160, -320));
		tVerts.add(new Vector(-240, -160));
		tVerts.add(new Vector(-200, -160));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.0015);
		currentLevel.addTerrainSection(t);
		*/
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(1000, 10));
		tVerts.add(new Vector(-1000, 10));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 810));
		tVerts.add(new Vector(-1000, 810));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(-990, 0));
		tVerts.add(new Vector(-990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(990, 0));
		tVerts.add(new Vector(990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		level.addTerrainSection(t);		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(100, 0));
		tVerts.add(new Vector(100, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-300);
		level.addTerrainSection(t);
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(120, 0));
		tVerts.add(new Vector(120, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-310);
		level.addTerrainSection(t);		
		
		LevelExit lf = new LevelExit("reagan");
		lf.setX(-300);
		lf.setY(500);
		level.addGameObject(lf);
		
		return level;
	}

	/**
	 * Sets the level list. Used by the level editor to test a level.
	 * 
	 * @param levels
	 */
	public void setLevels(List<Level> levels) {
		this.levels = levels;
	}
	
}
