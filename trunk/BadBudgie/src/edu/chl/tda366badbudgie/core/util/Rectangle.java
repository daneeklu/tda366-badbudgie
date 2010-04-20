package edu.chl.tda366badbudgie.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Rectangle
 * 
 * Represents a rectangle in the plane.
 * @author jesper
 *
 */
public class Rectangle extends Polygon {

	/**
	 * Creates a new rectangle with a given width
	 * and height.
	 * 
	 * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 */
	public Rectangle(double width, double height){
		
		this.vertices.add(new Vector(0,0));
		this.vertices.add(new Vector(0,height));
		this.vertices.add(new Vector(width,height));
		this.vertices.add(new Vector(width,0));
	}

	
	
}
