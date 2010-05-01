package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

/**
 * GameRound
 * 
 * A session of the game. 
 * Keeps track of the current level and the players score.
 * 
 * @author kvarfordt
 * 
 */
public class GameRound {

	private Level currentLevel;
	private Player player;
	private Enemy enemy;
	//private Weapon weapon;
	private int score;

	public GameRound() {

		/*
		 * TODO: Real game data loading. Temporary test data:
		 */

		currentLevel = new Level();

		player = new Player(this, "budgie");
		player.setX(0.0);
		player.setY(320.0);
		LinkedList<Vector> pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		player.setCollisionData(new Polygon(pcd));

		currentLevel.addGameObject(player);
		
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
		
		//Puts a dummy enemy on the screen
		
		enemy = new Enemy(enemySprite);
		enemy.setX(100.0);
		enemy.setY(300.0);
		LinkedList<Vector> ecd = new LinkedList<Vector>();
		ecd.add(new Vector(-40, -40));
		ecd.add(new Vector(40, -40));
		ecd.add(new Vector(40, 40));
		ecd.add(new Vector(-40, 40));
		enemy.setCollisionData(new Polygon(ecd));
		
		currentLevel.addGameObject(enemy);
		

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
		
		currentLevel.addGameObject(rock);
		
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

		currentLevel.addGameObject(rock);
		
		List<Vector> tVerts = new LinkedList<Vector>();
		TerrainSection t;

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(1000, 10));
		tVerts.add(new Vector(-1000, 10));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 810));
		tVerts.add(new Vector(-1000, 810));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1000, 800));
		tVerts.add(new Vector(-1000, 0));
		tVerts.add(new Vector(-990, 0));
		tVerts.add(new Vector(-990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(1000, 800));
		tVerts.add(new Vector(1000, 0));
		tVerts.add(new Vector(990, 0));
		tVerts.add(new Vector(990, 800));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		currentLevel.addTerrainSection(t);		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(100, 0));
		tVerts.add(new Vector(100, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-300);
		currentLevel.addTerrainSection(t);
		
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(120, 0));
		tVerts.add(new Vector(120, 200));
		tVerts.add(new Vector(0, 200));
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-310);
		currentLevel.addTerrainSection(t);		
		t = new TerrainSection(tVerts, 0.5, 0.5, "grass1", 0.005);
		t.setX(-510);
		t.setY(100);
		currentLevel.addTerrainSection(t);
		
		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(400, 20));
		tVerts.add(new Vector(0, 20));
		t = new TerrainSection(tVerts, 0.0, 0.0, "smurf", 0.005);
		t.setX(-1000);
		t.setY(5);
		currentLevel.addTerrainSection(t);
		
		
		LevelExit lf = new LevelExit(this, "reagan");
		lf.setX(800);
		lf.setY(500);
		currentLevel.addGameObject(lf);
		
		
	}

	public Level getLevel() {
		return currentLevel;
	}

	public Player getPlayer() {
		return player;
	}

	/**
	 * Delegates actions to the right objects.
	 * 
	 * @param id
	 *            a string identifying the action
	 * @param down
	 *            a boolean that's true if the key was pressed, and false if it
	 *            was released.
	 */
	public void keyboardAction(String id, boolean down) {

		if (id.equals("moveleft")) {
			player.moveLeft(down);
		}
		if (id.equals("moveright")) {
			player.moveRight(down);
		}
		if (id.equals("jump")) {
			player.jumpOrFlap(down);
		}
		if (id.equals("moveup")) {
			player.glide(down);
		}

	}

	/**
	 * This method calls the updateState on all objects inheriting
	 * AbstractGameObject.
	 */
	public void updateGameObjects() {
		for (AbstractGameObject ago : currentLevel.getGameObjects()) {
			ago.updateState();
		}
	}

	public void levelFinished() {
		// TODO Next level
		System.out.println("Next level");
	}

	public void playerDied() {
		// TODO Kill player
		System.out.println("Player died");
	}

}
