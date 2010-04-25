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
	private int score;

	public GameRound() {

		/*
		 * TODO: Real game data loading. Temporary test data:
		 */

		currentLevel = new Level();

		player = new Player("budgie");
		player.setX(0.0);
		player.setY(0.8);
		LinkedList<Vector> pcd = new LinkedList<Vector>();
		pcd.add(new Vector(-0.1, -0.1));
		pcd.add(new Vector(0.1, -0.1));
		pcd.add(new Vector(0.1, 0.1));
		pcd.add(new Vector(-0.1, 0.1));
		player.setCollisionData(new Polygon(pcd));

		currentLevel.addGameObject(player);

		List<Vector> tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(0, 0));
		tVerts.add(new Vector(1, 0));
		tVerts.add(new Vector(1, 0.2));
		tVerts.add(new Vector(0, 0.1));
		TerrainSection t = new TerrainSection(tVerts, 0.3, 0.5);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1, -0.95));
		tVerts.add(new Vector(0, -1));
		tVerts.add(new Vector(0, -0.8));
		tVerts.add(new Vector(-1, -0.9));
		t = new TerrainSection(tVerts, 0.01, 4);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-1, 0.2));
		tVerts.add(new Vector(-0.2, 1));
		tVerts.add(new Vector(-0.5, 1));
		tVerts.add(new Vector(-1, 0.5));
		t = new TerrainSection(tVerts, 0.5, 0.5);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-0.3, -0.8));
		tVerts.add(new Vector(-0.4, -0.8));
		tVerts.add(new Vector(-0.2, -0.4));
		tVerts.add(new Vector(-0.1, -0.4));
		t = new TerrainSection(tVerts, 0.5, 0.5);
		currentLevel.addTerrainSection(t);

		tVerts = new LinkedList<Vector>();
		tVerts.add(new Vector(-0.3, -0.8));
		tVerts.add(new Vector(-0.4, -0.8));
		tVerts.add(new Vector(-0.6, -0.4));
		tVerts.add(new Vector(-0.5, -0.4));
		t = new TerrainSection(tVerts, 0.5, 0.5);
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
			player.jump(down);
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
