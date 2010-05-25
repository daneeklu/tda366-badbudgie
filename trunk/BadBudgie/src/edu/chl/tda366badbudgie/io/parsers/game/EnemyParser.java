package edu.chl.tda366badbudgie.io.parsers.game;

import org.w3c.dom.Element;
import edu.chl.tda366badbudgie.core.game.Enemy;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;

public class EnemyParser extends AbstractUnitParser {

	public EnemyParser(Element data) {
		super(data);
	}

	@Override
	public Enemy parseData() throws ParserException {
		Enemy e = new Enemy(null, null, tempSprite(), tempCol(), 0, 0, 0, 0);
		initUnit(e);
		return e;
	}
}