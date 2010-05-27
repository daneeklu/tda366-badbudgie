package edu.chl.tda366badbudgie.io.impl.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.impl.parsers.AbstractPolygonParser;
import edu.chl.tda366badbudgie.io.impl.parsers.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.ParserException;
import edu.chl.tda366badbudgie.util.Rectangle;

/**
 * Creates a Rectangle instance from a DOM element.
 * @author jesper
 *
 */
public class RectangleParser extends AbstractPolygonParser {

	public RectangleParser(Element data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Rectangle parseData() throws ParserException {
		double width = ElementTools.getDouble(getData(), "width");
		double height = ElementTools.getDouble(getData(), "height");
		return new Rectangle(width, height);
	}

}
