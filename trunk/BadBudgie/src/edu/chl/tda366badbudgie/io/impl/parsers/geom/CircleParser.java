package edu.chl.tda366badbudgie.io.impl.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.impl.parsers.AbstractPolygonParser;
import edu.chl.tda366badbudgie.io.impl.parsers.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.ParserException;
import edu.chl.tda366badbudgie.util.Circle;

/**
 * Creates a Circle from a DOM element.
 * 
 * @author jesper
 *
 */
public class CircleParser extends AbstractPolygonParser {

	public CircleParser(Element data) {
		super(data);
	}

	@Override
	public Circle parseData() throws ParserException {
		int resolution = ElementTools.getInteger(getData(), "resolution");
		int radius = ElementTools.getInteger(getData(), "radius");
		return new Circle(radius, resolution);
	}

}
