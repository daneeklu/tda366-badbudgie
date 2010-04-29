package edu.chl.tda366badbudgie.io.impl;

import org.w3c.dom.Document;

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
		//Load textures.
		
		//Load collision data. (or integrate).
		
		//Create animations.
		
		//Create sprites.
		
		//Create gameobjects.
		
		//Create terrain.
		
		//Create and setup level.
		
		//Save level.
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
