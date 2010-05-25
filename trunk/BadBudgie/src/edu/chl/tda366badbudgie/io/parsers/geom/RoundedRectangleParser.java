package edu.chl.tda366badbudgie.io.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.RoundedRectangle;

public class RoundedRectangleParser extends AbstractPolygonParser {

	public RoundedRectangleParser(Element data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoundedRectangle parseData() throws ParserException {
		double radius = ElementTools.getDouble(getData(), "radius");
		double width = ElementTools.getDouble(getData(), "width");
		double height = ElementTools.getDouble(getData(), "height");
		int resolution = ElementTools.getInteger(getData(), "resolution");
		
		return new RoundedRectangle(width, height, radius, resolution);
	}

}
