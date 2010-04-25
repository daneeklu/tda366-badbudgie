package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;

/**
 * Rectangle
 * 
 * Represents a rectangle in the plane.
 * @author jesper, d.skalle
 *
 */
public class Rectangle extends Polygon {

	/**
	 * Creates a new rectangle with a given width
	 * and height, and position = (0,0)
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

	/**
	 * Creates a new rectangle with a given width,
	 * height and position.
	 * @param x the x position of the rectangle.
	 * @param y the y position of the rectangle.
	 * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 */
	public Rectangle(double x, double y, double width, double height){
		
		this.vertices = new ArrayList<Vector>();
		
		this.vertices.add(new Vector(x, y));
		this.vertices.add(new Vector(x, y + height));
		this.vertices.add(new Vector(x + width, y + height));
		this.vertices.add(new Vector(x + width,y ));
	}
	
	/**
	 * Creates a new rectangle with a given position
	 * and size
	 * @param pos the position vector
	 * @param size the size vector
	 */
	public Rectangle(Vector pos, Vector size){
		
		this.vertices = new ArrayList<Vector>();
		
		this.vertices.add(new Vector(pos.getX(), pos.getY()));
		this.vertices.add(new Vector(pos.getX(), pos.getY() + size.getY()));
		this.vertices.add(new Vector(pos.getX() + size.getX(), pos.getY() + size.getY()));
		this.vertices.add(new Vector(pos.getX() + size.getX(), pos.getY()));
	}
	
	/**
	 * Get the width of the rectangle
	 * @return the width
	 */
	public double getWidth() {
		return this.vertices.get(2).getX() - this.vertices.get(0).getX();
	}
	
	/**
	 * Get the height of the rectangle
	 * @return the height
	 */
	public double getHeight() {
		return this.vertices.get(2).getY() - this.vertices.get(0).getY();
	}
	
	/**
	 * Get the x position of the rectangle
	 * @return the x position
	 */
	public double getX() {
		return this.vertices.get(0).getX();
	}
	
	/**
	 * Get the y position of the rectangle
	 * @return the y position
	 */
	public double getY() {
		return this.vertices.get(0).getY();
	}
	
	
}
