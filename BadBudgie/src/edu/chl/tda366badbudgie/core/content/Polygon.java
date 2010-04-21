package edu.chl.tda366badbudgie.core.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Polygon
 * 
 * A class that represents a 2-dimensional polygon.
 * 
 * @author dkvarfordt
 *
 */
public class Polygon {
	
	protected ArrayList<Vector> vertices;
	
	/**
	 * Creates a new polygon with the given 
	 * list of Vector2D objects as vertices.
	 * 
	 * @param vertices the list of vertices
	 */
	public Polygon(List<Vector> vertices) {
		this.vertices = new ArrayList<Vector>();
		this.vertices.addAll(vertices);
		//TODO: check validity of the polygon (convex, counter-clockwise ordered vertices, simple polygon) somehow in every class extending Polygon, or in Polygon
	}
	
	protected Polygon(){}
	
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
