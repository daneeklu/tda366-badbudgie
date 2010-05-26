package edu.chl.tda366badbudgie.io.impl.parsers;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.util.Vector;

public class VectorParser extends AbstractElementParser<Vector> {

	public VectorParser(Element data) {
		super(data);
	}

	@Override
	public Vector parseData() throws ParserException {
		try{

			double x=0, y=0;
			x=ElementTools.getDouble(getData(), "x");
			y=ElementTools.getDouble(getData(), "y");
			
			return new Vector(x, y);	
		} catch(Exception e){
			throw new ParserException("Invalid vector.");
		}
	}
	
}