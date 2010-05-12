package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon
 * 
 * Represents an immutable convex 2-dimensional polygon.
 * 
 * @author kvarfordt, jesper
 * 
 */
public class Polygon {

	private ArrayList<Vector> vertices;
	private Vector boundingBoxPosition;
	private Vector boundingBoxSize;
	
	/**
	 * Creates a new polygon with the given list of Vector2D objects as
	 * vertices.
	 * The vertices must form a simple, convex polygon.
	 * A list of invalid vertices will throw an IllegalArgumentException.
	 * @param vertices
	 *            	the list of vertices
	 * @throws IllegalArgumentException if the list of vertices 
	 * 				does not form a simple, convex polygon.
	 */
	public Polygon(List<Vector> vertices) {

		this.vertices = new ArrayList<Vector>(vertices.size());
		this.vertices.addAll(vertices);
		
		calculateBoundingBox();
		
	}


	/**
	 * Checks whether the supplied polygon is convex.
	 * 
	 * @return true if the polygon is convex, otherwise false.
	 */
	public static boolean checkConvexity(Polygon p) {
		
		List<Vector> vertices = p.getVertices();
		
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
	public static boolean checkCCW(Polygon p) {

		List<Vector> vertices = p.getVertices();
		
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
	
	
	/**
	 * Calculates and stores the position, width and height of the 
	 * minimal box containing this TerrainSection.
	 */
	private void calculateBoundingBox() {
		
		double minX = vertices.get(0).getX();
		double maxX = minX;
		double minY = vertices.get(0).getY();;
		double maxY = minY;
		
		for (Vector v : vertices) {
			if (v.getX() < minX)
				minX = v.getX();
			else if (v.getX() > maxX)
				maxX = v.getX();
			
			if (v.getY() < minY)
				minY = v.getY();
			else if (v.getY() > maxY)
				maxY = v.getY();
			
		}
		
		boundingBoxPosition = new Vector(minX, minY);
		boundingBoxSize = new Vector(maxX - minX, maxY - minY);
	}

	/**
	 * Returns the position of the minimum rectangle containing this polygon.
	 * @return the position
	 */
	public Vector getBoundingBoxSize() {
		return boundingBoxSize;
	}

	/**
	 * Returns the size of the minimum rectangle containing this polygon.
	 * @return the size
	 */
	public Vector getBoundingBoxPosition() {
		return boundingBoxPosition;
	}

}
