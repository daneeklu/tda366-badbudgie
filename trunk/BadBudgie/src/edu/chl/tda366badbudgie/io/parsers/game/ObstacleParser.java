package edu.chl.tda366badbudgie.io.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.Obstacle;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;

public class ObstacleParser extends AbstractCollidableParser {

	public ObstacleParser(Element data) {
		super(data);
	}

	@Override
	public Obstacle parseData() throws ParserException{
		Obstacle o = new Obstacle(null, null, false, tempSprite(), tempCol(), 0, 0);
		initCollidable(o);
		return o;
	}

}
