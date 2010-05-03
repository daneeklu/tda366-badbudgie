package edu.chl.tda366badbudgieeditor.core;

import edu.chl.tda366badbudgie.util.Vector;

/**
 * Vector class with int accuracy (editor accuracy).
 * Has a selection of the methods from Vector.
 * 
 * @author kvarfordt
 *
 */
public class EVector {
	private int x;
	private int y;

	/**
	 * Creates a new 2DVector with given x- and y-coordinates.
	 * @param x the x-coordinate.
	 * @param y the y-coordinate.
	 */
	public EVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getDistanceTo(EVector other) {
		return Math.sqrt((other.getX() - getX())*(other.getX() - getX()) + (other.getY() - getY())*(other.getY() - getY()));
	}
	
	@Override
	public String toString() {
		return "[" + x + " , " + y + "]";
	}

	public EVector add(EVector v) {
		return new EVector(x - v.x, y - v.y);
	}
	
	public EVector subtract(EVector v) {
		return new EVector(x - v.x, y - v.y);
	}

	public double dotProduct(EVector v) {
		return x * v.x + y * v.y;
	}
	
	public double crossProduct(EVector v) {
		return this.x * v.y - this.y * v.x;
	}
	
	public boolean hasZeroLength(){
		return (x==0 && y==0);
	}
	
	private double directionCheck(EVector v) {

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
	public boolean sameDirection(EVector v) {
		return directionCheck(v) > 0;
	}
	
	/**
	 * Checks if the direction of the vector is the
	 * opposite of another.
	 * 
	 * @param v vector to compare direction with.
	 * @return true if the vector have the opposite direction.
	 */
	public boolean oppositeDirection(EVector v) {
		return directionCheck(v) < 0;
	}
}
