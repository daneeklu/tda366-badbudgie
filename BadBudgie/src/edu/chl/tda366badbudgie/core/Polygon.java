package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Polygon
 * 
 * A convex 2-dimensional polygon.
 * 
 * @author dkvarfordt, jesper
 * 
 */
public class Polygon {

	protected ArrayList<Vector> vertices;

	/**
	 * Creates a new polygon with the given list of Vector2D objects as
	 * vertices.
	 * The vertices must form a simple, convex polygon.
	 * A list of invalid vertices will throw an IllegalArgumentException.
	 * @param vertices
	 *            the list of vertices
	 */
	public Polygon(List<Vector> vertices) {

		if (!checkConvexity(vertices)) {
			throw new IllegalArgumentException();
		} else if (!checkCCW(vertices)) {
			Collections.reverse(vertices);
		}
		this.vertices = new ArrayList<Vector>(vertices.size());
		this.vertices.addAll(vertices);
	}

	protected Polygon() {
	}

	/**
	 * Checks whether the supplied vertices form a convex polygon.
	 * 
	 * @return true if the polygon is convex, otherwise false.
	 */
	protected static boolean checkConvexity(List<Vector> vertices) {
		
		if (vertices.size() < 3) {
			return false;
		}
		
		//Stores the sign between checks.
		double oldSign = 0;
		boolean changedDirection = false;

		for (int i = 0; i < vertices.size(); i++) {			

			//Get the vectors forming two adjacent edges
			Vector aVec = vertices.get(i);
			Vector bVec = vertices.get((i + 1) % vertices.size());
			Vector cVec = vertices.get((i + 2) % vertices.size());

			if (aVec.equals(bVec) || aVec.equals(cVec)) {
				return false;
			}

			//Calculate edge vectors.
			Vector nV1 = bVec.subtract(aVec);
			Vector nV2 = cVec.subtract(bVec);

			//Ensure the vertices don't form a straight line.
			if (!changedDirection && !nV1.sameDirection(nV2)) {
				changedDirection = true;
			}

			if (nV1.oppositeDirection(nV2)) {
				return false;
			}
			
			//calculate the sign of the edges
			double newSign = Math.signum(nV1.crossProduct(nV2));

			//Check whether sign has reversed.
			if(newSign == -oldSign){
				return false;
			}
			
			oldSign = newSign;
		}
		
		return changedDirection;
	}

	/**
	 * Checks whether the polygon is CCW-ordered.
	 * 
	 * @return true, if the order is counter clockwise, otherwise false.
	 */
	protected static boolean checkCCW(List<Vector> vertices) {

		if (vertices.size() < 3) {
			return false;
		}

		Vector aVec = vertices.get(0);
		Vector bVec = vertices.get(1);
		Vector cVec = vertices.get(2);

		Vector nV1 = bVec.subtract(aVec);
		Vector nV2 = cVec.subtract(bVec);

		return nV1.crossProduct(nV2) > 0;
	}

	/**
	 * Returns a list of the vertices representing the polygon.
	 * 
	 * @return the list of vertices.
	 */
	public List<Vector> getVertices() {
		ArrayList<Vector> r = new ArrayList<Vector>();
		r.addAll(vertices);
		return r;
	}

}
