package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.LevelExit;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;

public class LevelExitParser extends AbstractCollidableParser{

	public LevelExitParser(Element data) {
		super(data);
	}

	@Override
	public LevelExit parseData() throws ParserException {
		LevelExit le = new LevelExit(null);
		initCollidable(le);
		return le;
	}

}
