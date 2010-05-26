package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.Obstacle;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Vector;

public class ObstacleParser extends AbstractCollidableParser {

	public ObstacleParser(Element data) {
		super(data);
	}

	@Override
	public Obstacle parseData() throws ParserException{
		Obstacle o = new Obstacle(new Vector(), new Vector(), false, tempSprite(), tempCol(), 0, 0);
		initCollidable(o);
		return o;
	}

}
