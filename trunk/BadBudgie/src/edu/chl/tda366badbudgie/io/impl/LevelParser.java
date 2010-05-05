package edu.chl.tda366badbudgie.io.impl;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

import edu.chl.tda366badbudgie.core.Level;
import edu.chl.tda366badbudgie.core.LevelManager;
import edu.chl.tda366badbudgie.core.Player;
import edu.chl.tda366badbudgie.util.Polygon;

/**
 * LevelParser
 * Parses level data, creating new level instances from xml data.
 * 
 * @author jesper
 *
 */
public class LevelParser extends AbstractParser{

	public LevelParser(Document xmlDocument) {
		super(xmlDocument);
	}

	@Override
	public void parseData() {

		List<Polygon> colList = new LinkedList<Polygon>();
		
		//Create level.
		Level level = new Level();
		
		//Create and add player
		//Player player = new Player("budgie");
		//level.addGameObject(player);
		
		//Create animations.
		
		//Create sprites.
		
		//Load collision data. (or integrate).
		
		//Create gameobjects.
		
		//Create terrain.
		
		//Save level.
		LevelManager.getInstance().addLevel(level);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
