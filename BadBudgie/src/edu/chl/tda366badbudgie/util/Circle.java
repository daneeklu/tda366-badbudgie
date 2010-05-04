package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Polygon {
	
	public Circle(double radius, int resolution){
		super(circleVertices(new Vector(), radius, resolution));
	}
	
	public Circle(Vector position, double radius, int resolution){
		super(circleVertices(position, radius, resolution));
	}
	
	private static List<Vector> circleVertices(Vector position, double radius, int resolution){
		
		List<Vector> list = new ArrayList<Vector>(resolution);
		
		for(int i = 0; i < resolution; i++){
			double x = position.getX() + radius * Math.cos(i*((Math.PI*2)/resolution));
			double y = position.getY() + radius * Math.sin(i*((Math.PI*2)/resolution));
			Vector v = new Vector(x, y);
			list.add(v);
		}
		return list;
	}
	
}
