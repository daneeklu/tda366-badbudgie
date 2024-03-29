package edu.chl.tda366badbudgieeditor.ctrl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.Level;
import edu.chl.tda366badbudgie.core.game.LevelExit;
import edu.chl.tda366badbudgie.core.game.LevelManager;
import edu.chl.tda366badbudgie.core.game.Player;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.ctrl.impl.InGameState;
import edu.chl.tda366badbudgie.ctrl.impl.StateContext;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
import edu.chl.tda366badbudgie.io.IFileManager;
import edu.chl.tda366badbudgie.io.impl.FileManager;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;
import edu.chl.tda366badbudgieeditor.core.EGameObject;
import edu.chl.tda366badbudgieeditor.core.ELevel;
import edu.chl.tda366badbudgieeditor.core.ETerrainSection;
import edu.chl.tda366badbudgieeditor.core.EVector;

public class LevelTestPlayer {

	public LevelTestPlayer(ELevel elevel) {

		ArrayList<Level> levelList = new ArrayList<Level>();
		levelList.add(convertToGameLevel(elevel));
		LevelManager.getInstance().addLevel(convertToGameLevel(elevel));
		
		
		//Load assets
		IFileManager fileManager = new FileManager();
		fileManager.loadData("config/config.xml");
		

		
		final GraphicsFrame gameFrame = new GraphicsFrame();
		gameFrame.setVisible(true);
		gameFrame.removeWindowListener(gameFrame.getWindowListeners()[0]);
		gameFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				StateContext.getInstance().setFrame(null);
				StateContext.getInstance().setGameState(new InGameState(new GameRound()));
				gameFrame.dispose();
			}
		});
	}
	
	
	
	/*
	 * Converts an editor level into a normal game level.
	 */
	private Level convertToGameLevel(ELevel elevel) {

		Level result = new Level(0,new Vector());
		
		// Convert terrain sections
		for (ETerrainSection et : elevel.getTerrainSections()) {
			ArrayList<Vector> verts = new ArrayList<Vector>();
			for (EVector ev : et.getVerts()) {
				verts.add(new Vector(ev.getX(), -ev.getY()));
			}
			result.addTerrainSection(new TerrainSection(new Polygon(verts)));
		}
		
		for (EGameObject ego : elevel.getGameObjects()) {
			switch (ego.getType()) {
				case Player:
					Player p = new Player(new Vector(ego.getX(), -ego.getY()));
//					LinkedList<Vector> pcd = new LinkedList<Vector>();
//					pcd.add(new Vector(-40, -40));
//					pcd.add(new Vector(40, -40));
//					pcd.add(new Vector(40, 40));
//					pcd.add(new Vector(-40, 40));
//					p.setCollisionData(new Polygon(pcd));
					
					result.addGameObject(p);
					break;
				case Enemy:
					//Enemy enemy = new Enemy(new Vector(ego.getX(), -ego.getY()));
//					pcd = new LinkedList<Vector>();
//					pcd.add(new Vector(-40, -40));
//					pcd.add(new Vector(40, -40));
//					pcd.add(new Vector(40, 40));
//					pcd.add(new Vector(-40, 40));
//					enemy.setCollisionData(new Polygon(pcd));
					
					//result.addGameObject(enemy);
					break;
				case Obstacle:
					//Obstacle rock = new Obstacle(new Vector(ego.getX(), -ego.getY()));
//					pcd = new LinkedList<Vector>();
//					pcd.add(new Vector(-40, -15));
//					
//					pcd.add(new Vector(-25, -35));
//					pcd.add(new Vector(25, -35));
//					
//					pcd.add(new Vector(40, -15));
//					pcd.add(new Vector(20, 20));
//					pcd.add(new Vector(-20, 20));
//					rock.setCollisionData(new Polygon(pcd));
					
					//result.addGameObject(rock);
					break;
				case Exit:
					LevelExit lf = new LevelExit(new Vector(ego.getX(), -ego.getY()));
					
					result.addGameObject(lf);
					break;
					
			}
		}
		
		
		return result;
		
	}
	
}
