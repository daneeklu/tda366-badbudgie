package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Rectangle
 * 
 * Represents a rectangle in the plane.
 * @author jesper, d.skalle
 *
 */
public class Rectangle extends Polygon {

	private double x, y, width, height;
	
	
	/**
	 * Creates a new rectangle with a given width
	 * and height, and position = (0,0)
	 * 
	 * @param width the width of the rectangle.
	 * @param height the height of the rectangle.
	 */
	public Rectangle(double width, double height){
		this(-width/2,-height/2, width, height);
		//this(0,0, width, height);
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
		super(new ArrayList<Vector>(Arrays.asList(
				new Vector(x, y), 
				new Vector(x, y + height), 
				new Vector(x + width, y + height), 
				new Vector(x + width, y))));

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Creates a new rectangle with a given position
	 * and size
	 * @param pos the position vector
	 * @param size the size vector
	 */
	public Rectangle(Vector pos, Vector size){
		this(pos.getX(), pos.getY(), size.getX(), size.getY());
	}
	
	/**
	 * Get the width of the rectangle
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the rectangle
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * Get the x position of the rectangle
	 * @return the x position
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Get the y position of the rectangle
	 * @return the y position
	 */
	public double getY() {
		return y;
	}
	
	
}
