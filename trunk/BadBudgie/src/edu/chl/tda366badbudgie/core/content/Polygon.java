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
	}
	
	protected Polygon(){}
	
	/**
	 * Returns a list of the vertices representing the polygon.
	 * 
	 * @return the list of vertices.
	 */
	public List<Vector> getVertices() {
		return vertices;
	}
	
	
}
