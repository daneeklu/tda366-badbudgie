package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Quad
 * 
 * Convex four-corner polygon. 
 * @author jesper
 *
 */
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
		super(new ArrayList<Vector>(Arrays.asList(v1, v2, v3, v4)));
	}
	
}