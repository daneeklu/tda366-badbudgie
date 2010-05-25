package edu.chl.tda366badbudgie.io.parsers.geom;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.io.parsers.AbstractElementParser;
import edu.chl.tda366badbudgie.io.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Vector;

public class VectorParser extends AbstractElementParser<Vector> {

	public VectorParser(Element data) {
		super(data);
	}

	@Override
	public Vector parseData() {
		Element source = getData();
		double x=0, y=0;
		try{
			
			x=ElementTools.getDouble(source, "x");
			y=ElementTools.getDouble(source, "y");
			
		} catch(NumberFormatException ne){
			ne.printStackTrace();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return new Vector(x, y);
	}
	
}