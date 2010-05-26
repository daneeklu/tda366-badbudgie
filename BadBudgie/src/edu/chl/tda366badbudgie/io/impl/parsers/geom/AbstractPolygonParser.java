package edu.chl.tda366badbudgie.io.impl.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.impl.parsers.AbstractElementParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Polygon;

public abstract class AbstractPolygonParser extends AbstractElementParser<Polygon> {

	public AbstractPolygonParser(Element data) {
		super(data);
	}

	@Override
	public abstract Polygon parseData() throws ParserException;

}
