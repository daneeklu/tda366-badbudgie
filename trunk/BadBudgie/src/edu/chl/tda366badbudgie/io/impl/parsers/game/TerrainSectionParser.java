package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;

public class TerrainSectionParser extends AbstractCollidableParser{

	public TerrainSectionParser(Element data) {
		super(data);
	}
	
	public TerrainSection parseData() throws ParserException{
		
		TerrainSection t = new TerrainSection(tempCol());
		initCollidable(t);
		t.setTexRes(ElementTools.getDouble(getData(), "texres"));
		
		return t;
	}

}