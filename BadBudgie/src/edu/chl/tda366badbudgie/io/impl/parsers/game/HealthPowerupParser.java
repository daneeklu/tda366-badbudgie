package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.HealthPowerup;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Vector;

public class HealthPowerupParser extends AbstractCollidableParser {

	public HealthPowerupParser(Element data) {
		super(data);
	}

	@Override
	public HealthPowerup parseData() throws ParserException {
		HealthPowerup hp = new HealthPowerup(new Vector(), new Vector(), false, tempSprite(), tempCol(), 0, 0, ElementTools.getInteger(getData(), "healthpoints"));
		initCollidable(hp);
		return hp;
	}

}
