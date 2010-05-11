package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;

/**
 * Contains static utility methods.
 * 
 * @author kvarfordt
 *
 */
public class StaticUtilityMethods {

	/**
	 * Checks to see if the point given is inside the specified polygon.
	 * 
	 * @param point the point to check
	 * @param p the polygon to check
	 * @return true if the point is inside
	 */
	public static boolean isPointInPolygon(Vector point, Polygon p) {
		
		ArrayList<Vector> pVerts = new ArrayList<Vector>(p.getVertices());
		
		ArrayList<Vector> normals = new ArrayList<Vector>();
		
		// Add normals of a to the list of normals
		for (int i = 0; i < pVerts.size(); i++) {

			Vector p1, p2;

			p1 = pVerts.get(i);
			p2 = pVerts.get((i + 1 == pVerts.size()) ? 0 : i + 1);

			Vector normal = p2.subtract(p1).perpendicularCCW();
			normal = normal.normalize();

			normals.add(normal);
		}
		
		
		for (Vector normal : normals) {

			double min = pVerts.get(0).dotProduct(normal);
			double max = min;

			// Project each vertex of a onto the current normal and save the min
			// and max value
			for (Vector v : pVerts) {
				double dot = v.dotProduct(normal);
				if (dot < min) {
					min = dot;
				} else if (dot > max) {
					max = dot;
				}
			}
			
			double pointProj = point.dotProduct(normal);
			
			if (pointProj <= min || pointProj >= max)
				return false;
			
		}
		
		return true;
	}
	
	
	
}
