package edu.chl.tda366badbudgie.core.physics;

/**
 * Vector2D
 * 
 * Immutable class holding fields and methods for two-dimensional vectors.
 * 
 * @author kvarfordt, jesper
 * 
 */
public class Vector2D {
	protected double x;
	protected double y;

	/**
	 * Creates a new 2DVector with give x- and y-coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Multiples the values of the vector with a give scalar
	 * and returns the result.
	 * @param s value of the scalar.
	 * @return the multiplied vector
	 */
	public Vector2D scalarMultiplication(double s) {
		return new Vector2D(x * s, y * s);
	}

	//Is this strictly speaking necessary? -Jesper
	public Vector2D scalarDivision(double s) {
		return new Vector2D(x / s, y / s);
	}
	
	/**
	 * Returns the dot-product of this vector with a second vector v.
	 * @param v the vector which will be multiplied to this.
	 * @return the resulting vector.
	 */
	public double dot(Vector2D v) {
		return x * v.x + y * v.y;
	}
	/**
	 * Returns the cross product of this vector with a second vector v.
	 * @param v the vector which will be multiplied to this.
	 * @return the resulting vector.
	 */
	public Vector2D cross(Vector2D v) {
		return new Vector2D(x * v.y - y * v.x, y * v.x - x * v.y);
	}

	/**
	 * Returns the projection of this vector on a second vector c.
	 * @param v the vector which this vector will be projected on.
	 * @return the resulting vector.
	 */
	public Vector2D project(Vector2D v) {
		double vLengthSquared = v.x * v.x + v.y * v.y;
		double scalarProd = dot(v);
		return v.scalarMultiplication(scalarProd / vLengthSquared);
	}
	/**
	 * Returns the subtraction of a given vector from this vector.
	 * @param v the vector to be subtracted from this vector.
	 * @return the resulting vector.
	 */
	public Vector2D minus(Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

	/**
	 * Returns the length of the vector.
	 * @return the length of the vector.
	 */
	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Normalizes the vector and returns the result.
	 * The new vector will maintain the direction but have a length of 1.
	 * @return the normalized vector.
	 */
	public Vector2D normalize() {
		double mag = Math.sqrt(x * x + y * y);
		return new Vector2D(x / mag, y / mag);
	}

	public Vector2D perp() {
		return new Vector2D(-y, x);
	}

}
