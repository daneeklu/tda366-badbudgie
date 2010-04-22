package edu.chl.tda366badbudgie.core;
/**
 * Quad
 * Convex four-corner polygon. 
 * @author jesper
 *
 */
import java.util.List;

public class Quad extends Polygon {

	/**
	 * Creates a new quad given four vectors.
	 * 
	 * @param v1 first vector 1.
	 * @param v2 second vector 2.
	 * @param v3 third vector 3.
	 * @param v4 fourth vector 4.
	 */
	public Quad(Vector v1, Vector v2, Vector v3, Vector v4){
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
	}
	
	public Quad(List<Vector> vertices) {
		super(vertices);
		//TODO: somehow the number of vertices should be checked in all the constructors
	}
	
	protected Quad() {}
	
}
