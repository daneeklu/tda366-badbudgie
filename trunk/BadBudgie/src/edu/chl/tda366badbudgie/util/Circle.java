package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Polygon {
	
	public Circle(double radius, int resolution){
		super(circleVertices(radius, resolution));
	}
	
	private static List<Vector> circleVertices(double radius, int resolution){
		
		List<Vector> list = new ArrayList<Vector>(resolution);
		
		//Currently centered in origo
		for(int i = 0; i < resolution; i++){
			double x = radius * Math.cos(i*((Math.PI*2)/resolution)); // + radius;
			double y = radius * Math.sin(i*((Math.PI*2)/resolution)); // + radius;
			Vector v = new Vector(x, y);
			list.add(v);
		}
		return list;
	}
}
