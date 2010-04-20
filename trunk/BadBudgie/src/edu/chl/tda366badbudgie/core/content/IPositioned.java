package edu.chl.tda366badbudgie.core.content;

/**
 * IPositioned
 * 
 * Interface implemented by objects of classes having a position in the plane.
 * 
 * @author jesper
 * 
 */
public interface IPositioned {

	/**
	 * Returns a double which is the world coordinate in the x-axis.
	 * 
	 * @return the x-coordinate of the position.
	 */
	public double getX();

	/**
	 * Returns a double which is the world coordinate in the y-axis.
	 * 
	 * @return the y-coordinate of the position.
	 */
	public double getY();

	/**
	 * Assigns a double to the world coordinate in the x-axis.
	 * 
	 * @param x the value of the new x-coordinate.
	 */
	public void setX(double x);

	/**
	 * Assigns a double to the world coordinate in the y-axis.
	 * 
	 * @param y the value of the new y-coordinate.
	 */
	public void setY(double y);
}
