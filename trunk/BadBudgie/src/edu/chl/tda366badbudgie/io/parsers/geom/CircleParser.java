package edu.chl.tda366badbudgie.io.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Circle;

public class CircleParser extends AbstractPolygonParser {

	public CircleParser(Element data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Circle parseData() throws ParserException {
		int resolution = ElementTools.getInteger(getData(), "resolution");
		int radius = ElementTools.getInteger(getData(), "radius");
		return new Circle(radius, resolution);
	}

}
