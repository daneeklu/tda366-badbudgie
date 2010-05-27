package edu.chl.tda366badbudgie.io.impl.parsers;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.AbstractUnit;

/**
 * Abstract parser used as base for any parser that should create objects
 * of the AbstractUnit class.
 * 
 * @author jesper
 *
 */
public abstract class AbstractUnitParser extends AbstractCollidableParser {

	public AbstractUnitParser(Element data) {
		super(data);
	}

	@Override
	public abstract AbstractUnit parseData() throws ParserException;

	protected void initUnit(AbstractUnit u) throws ParserException{
		initCollidable(u);
		u.setHealth(ElementTools.getInteger(getData(), "health"));
		u.setDirection(ElementTools.getInteger(getData(), "direction"));
	}
	
}
