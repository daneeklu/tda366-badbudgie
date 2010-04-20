package edu.chl.tda366badbudgie.core.util;

import java.util.ArrayList;
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
	
}
