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
	private Weapon weapon;
	private int score;

	public GameRound() {

		/*
		 * TODO: Real game data loading. Temporary test data:
		 */

		currentLevel = new Level();

		player = new Player("budgie");
		player.setX(0.0);
		player.setY(320.0);
		LinkedList<Vector> pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-40, -40));
		pcd.add(new Vector(40, -40));
		pcd.add(new Vector(40, 40));
		pcd.add(new Vector(-40, 40));
		player.setCollisionData(new Polygon(pcd));

		currentLevel.addGameObject(player);
		
		//Puts a dummy enemy on the screen
		
		enemy = new Enemy("enemy");
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

		
		
		List<Vector> tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(400, 0));
		tVerts.add(new Vector(400, 40));
		tVerts.add(new Vector(0, 80));
		TerrainSection t = new TerrainSection(tVerts, 0.4, 0.5, "grass1", 0.0015);
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

}
