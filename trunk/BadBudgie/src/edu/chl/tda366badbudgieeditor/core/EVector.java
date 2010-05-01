package edu.chl.tda366badbudgieeditor.core;

import edu.chl.tda366badbudgie.core.Vector;

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

	public boolean sameDirection(EVector v) {
		return (dotProduct(v) > 0);
	}

	public boolean oppositeDirection(EVector v) {
		return (dotProduct(v) < 0);
	}
	
}
