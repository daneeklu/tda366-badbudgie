package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A rounded rectangle
 * 
 * @author jesper
 *
 */

public class RoundedRectangle extends Polygon {

	private static int RESOLUTION = 4;
	
	public RoundedRectangle(Vector position, double width, double height, double cornerRadius, int resolution){
		super(roundRectVertices(position, width, height, cornerRadius, resolution));
	}
	
	public RoundedRectangle(Vector position, double width, double height, double cornerRadius){
		this(position, width, height, cornerRadius, 16);
	}
	
	public RoundedRectangle(double width, double height, double cornerRadius, int resolution){
		this(new Vector(), width, height, cornerRadius, resolution);
	}

	public RoundedRectangle(double width, double height, double cornerRadius){
		this(new Vector(), width, height, cornerRadius, RESOLUTION);
	}
	
	private static List<Vector> roundRectVertices(Vector position, double width, double height, double cornerRadius, int resolution){
		
		List<Vector> list = new LinkedList<Vector>();
				
		//1
		list.add(new Vector(width/2, -(height/2-cornerRadius)).add(position));
		list.add(new Vector(width/2, height/2-cornerRadius).add(position));
		
		list.addAll(circleSlice(position, new Vector(width/2 - cornerRadius, height/2-cornerRadius), cornerRadius, resolution, 0));
		
		//2
		list.add(new Vector(width/2 - cornerRadius, height/2).add(position));
		list.add(new Vector(-(width/2 - cornerRadius), height/2).add(position));
		
		list.addAll(circleSlice(position, new Vector(-(width/2 - cornerRadius), height/2-cornerRadius), cornerRadius, resolution, 1));
		
		//3
		list.add(new Vector(-width/2, height/2 - cornerRadius).add(position));
		list.add(new Vector(-width/2, -(height/2 - cornerRadius)).add(position));
		
		list.addAll(circleSlice(position, new Vector(-(width/2 - cornerRadius), -(height/2 - cornerRadius)), cornerRadius, resolution, 2));
		
		
		//4
		list.add(new Vector(-(width/2 - cornerRadius), -height/2).add(position));
		list.add(new Vector(width/2 - cornerRadius, -height/2).add(position));
		
		list.addAll(circleSlice(position, new Vector(width/2 - cornerRadius, -(height/2 - cornerRadius)), cornerRadius, resolution, 3));
		
		return list;
	}
	
	private static List<Vector> circleSlice(Vector position, Vector center, double radius, int resolution, int quarter){
		List<Vector> list = new ArrayList<Vector>(resolution);
		for(int i = 0; i < resolution; i++){
			double x = center.getX() + radius * Math.cos((i+1)*((Math.PI/2)/(resolution+2)) + (Math.PI/2) * quarter);
			double y = center.getY() + radius * Math.sin((i+1)*((Math.PI/2)/(resolution+2)) + (Math.PI/2) * quarter);
			list.add(new Vector(x, y).add(position));
		}
		
		return list;
	}
	
}
