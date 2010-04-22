package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;

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
		
		this.vertices = new ArrayList<Vector>();
		
		this.vertices.add(new Vector(0, 0));
		this.vertices.add(new Vector(0, height));
		this.vertices.add(new Vector(width, height));
		this.vertices.add(new Vector(width, 0));
	}

	public Rectangle(double x, double y, double width, double height){
		
		this.vertices = new ArrayList<Vector>();
		
		this.vertices.add(new Vector(x, y));
		this.vertices.add(new Vector(x, y + height));
		this.vertices.add(new Vector(x + width, y + height));
		this.vertices.add(new Vector(x + width,y ));
	}
	
	public double getWidth() {
		return this.vertices.get(2).getX() - this.vertices.get(0).getX();
	}
	
	public double getHeight() {
		return this.vertices.get(2).getY() - this.vertices.get(0).getY();
	}
	
	public double getX() {
		return this.vertices.get(0).getX();
	}
	
	public double getY() {
		return this.vertices.get(0).getY();
	}
	
	
}
