package edu.chl.tda366badbudgie.util;

/**
 * Vector
 * 
 * Immutable class holding fields and methods for two-dimensional vectors with double precision.
 * 
 * @author kvarfordt, jesper
 * 
 */
public class Vector {
	private double x;
	private double y;

	/**
	 * Creates a new 2DVector with given x- and y-coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Creates a new vector with length zero.
	 */
	public Vector() {
		this(0, 0);
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
	 * Returns the value of the cross product of this vector with a second
	 * vector v. The cross product of two vectors in the plane is of limited
	 * use, and can be seen as analogous to the resulting z-component of the
	 * test in N3-space.
	 * 
	 * @param v
	 *            the vector by which the cross product will be calculated.
	 * @return the resulting value of the cross product.
	 */
	public double crossProduct(Vector v) {
		return this.x * v.y - this.y * v.x;
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

	/**
	 * Returns true if the vector has a length of zero.
	 * 
	 * @return true if the vector is of zero length
	 */
	public boolean hasZeroLength() {
		if (x == 0 && y == 0)
			return true;
		return false;
	}

	//Private direction check used by public tests.
	private double directionCheck(Vector v) {

		double coeff = 0;
		
		if (v.hasZeroLength() || this.hasZeroLength()) {
			coeff = 0;
		} else if(x == 0 && v.x == 0) {
			coeff = v.y / y;
		} else if(x == 0 || v.x == 0) {
			coeff = 0;
		} else if(y == 0 && v.y == 0) {
			coeff = v.x / x;
		} else if(y == 0 || v.y == 0) {
			coeff = 0;
		} else if(v.x /x == v.y /y){
			coeff = v.x /x;
		}
		
		return coeff;
	}
	
	/**
	 * Checks if the direction of the vector is the same
	 * as another.
	 * 
	 * @param v vector to compare direction with.
	 * @return true if the vector have the same direction.
	 */
	public boolean sameDirection(Vector v) {
		return directionCheck(v) > 0;
	}
	
	/**
	 * Checks if the direction of the vector is the
	 * opposite of another.
	 * 
	 * @param v vector to compare direction with.
	 * @return true if the vector have the opposite direction.
	 */
	public boolean oppositeDirection(Vector v) {
		return directionCheck(v) < 0;
	}
	
	@Override
	public String toString() {
		return "[" + x + " , " + y + "]";
	}
	
	@Override
	public Object clone() {
		return new Vector(x, y);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Vector) {

			Vector v = (Vector) o;
			return (x == v.x && y == v.y);
		} else {
			return false;
		}

	}
}
