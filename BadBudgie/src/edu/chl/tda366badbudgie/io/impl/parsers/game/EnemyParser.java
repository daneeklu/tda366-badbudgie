package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;
import edu.chl.tda366badbudgie.core.game.Enemy;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractUnitParser;
import edu.chl.tda366badbudgie.io.impl.parsers.ParserException;
import edu.chl.tda366badbudgie.util.Vector;

public class EnemyParser extends AbstractUnitParser {

	public EnemyParser(Element data) {
		super(data);
	}

	@Override
	public Enemy parseData() throws ParserException {
		Enemy e = new Enemy(new Vector(), new Vector(), tempSprite(), tempCol(), 0, 0, 0, 0);
		initUnit(e);
		return e;
	}
}