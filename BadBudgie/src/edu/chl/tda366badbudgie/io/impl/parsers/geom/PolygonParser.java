package edu.chl.tda366badbudgie.io.impl.parsers.geom;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.io.impl.parsers.AbstractPolygonParser;
import edu.chl.tda366badbudgie.io.impl.parsers.ParserException;
import edu.chl.tda366badbudgie.io.impl.parsers.VectorParser;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

public class PolygonParser extends AbstractPolygonParser {

	public PolygonParser(Element data) {
		super(data);
	}

	@Override
	public Polygon parseData() throws ParserException {

		Element pgonData = getData();
		NodeList vectors = pgonData.getElementsByTagName("vector");
		List<Vector> vecList = new LinkedList<Vector>();
		
		
		for(int i = 0; i < vectors.getLength(); i++){
			VectorParser vp = new VectorParser((Element)vectors.item(i));
			vecList.add(vp.parseData());
		}
		
		return new Polygon(vecList);
	}

}
