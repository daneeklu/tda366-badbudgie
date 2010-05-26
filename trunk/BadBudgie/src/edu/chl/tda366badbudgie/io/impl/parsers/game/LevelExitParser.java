package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.LevelExit;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractCollidableParser;
import edu.chl.tda366badbudgie.io.impl.parsers.ParserException;
import edu.chl.tda366badbudgie.util.Vector;

public class LevelExitParser extends AbstractCollidableParser{

	public LevelExitParser(Element data) {
		super(data);
	}

	@Override
	public LevelExit parseData() throws ParserException {
		LevelExit le = new LevelExit(new Vector());
		initCollidable(le);
		return le;
	}

}
