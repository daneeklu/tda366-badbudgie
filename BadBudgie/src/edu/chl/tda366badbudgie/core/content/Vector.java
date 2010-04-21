package edu.chl.tda366badbudgie.core.content;

/**
 * Vector
 * 
 * Immutable class holding fields and methods for two-dimensional vectors with double precision.
 * 
 * @author dkvarfordt, jesper
 * 
 */
public class Vector {
	private double x;
	private double y;

	/**
	 * Creates a new 2DVector with give x- and y-coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the length of the vectors x-component.
	 * @return the length of the vectors x-component.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * Returns the length of the vectors y-component.
	 * @return the length of the vectors y-component.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Returns the length of the vector.
	 * @return the length of the vector.
	 */
	public double getLength() {
		if (x == 0) {
			return y;
		}
		else if (y == 0) {
			return x;
		}
		
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Multiples the values of the vector with a given scalar
	 * and returns the result.
	 * @param s value of the scalar.
	 * @return the multiplied vector
	 */
	public Vector scalarMultiplication(double s) {
		return new Vector(x * s, y * s);
	}

	/**
	 * Divides the vector length by a scalar number
	 * @param s the scalar number to divide by.
	 * @return the resulting vector.
	 */
	public Vector scalarDivision(double s) {
		return new Vector(x / s, y / s);
	}
	
	/**
	 * Returns the dot-product of this vector with a second vector v.
	 * @param v the vector which will be multiplied to this.
	 * @return the resulting vector.
	 */
	public double dotProduct(Vector v) {
		return x * v.x + y * v.y;
	}

	/**
	 * Returns the projection of this vector on a second vector c.
	 * @param v the vector which this vector will be projected on.
	 * @return the resulting vector.
	 */
	public Vector project(Vector v) {
		double vLengthSquared = v.x * v.x + v.y * v.y;
		double scalarProd = dotProduct(v);
		return v.scalarMultiplication(scalarProd / vLengthSquared);
	}

	/**
	 * Returns the sum of a given vector and this vector.
	 * @param v the vector to be added to this vector.
	 * @return the resulting vector.
	 */
	public Vector add(Vector v) {
		return new Vector(x + v.x, y + v.y);
	}
	
	/**
	 * Returns the subtraction of a given vector from this vector.
	 * @param v the vector to be subtracted from this vector.
	 * @return the resulting vector.
	 */
	public Vector subtract(Vector v) {
		return new Vector(x - v.x, y - v.y);
	}
	
	/**
	 * Normalizes the vector and returns the result.
	 * The new vector will maintain the direction but have a length of 1.
	 * @return the normalized vector.
	 */
	public Vector normalize() {
		double mag = Math.sqrt(x * x + y * y);
		return new Vector(x / mag, y / mag);
	}

	/**
	 * Returns a vector of the same length that is 
	 * perpendicular to the original and rotated clockwise. 
	 * @return the clockwise perpendicular vector.
	 */
	public Vector perpendicularCW() {
		return new Vector(y, -x);
	}

	/**
	 * Returns a vector of the same length that is 
	 * perpendicular to the original and rotated conter-clockwise. 
	 * @return the counter-clockwise perpendicular vector.
	 */
	public Vector perpendicularCCW() {
		return new Vector(-y, x);
	}
	
	@Override
	public String toString() {
		return "[" + x + " , " + y + "]";
	}

}
