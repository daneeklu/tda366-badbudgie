package edu.chl.tda366badbudgie.core.physics;

import java.util.ArrayList;

import edu.chl.tda366badbudgie.core.content.Polygon;
import edu.chl.tda366badbudgie.core.content.Vector;

public class Physics implements IPhysics{

	@Override
	public void doPhysics() {
		// TODO Auto-generated method stub
		
	}
	
	

	/**
	 * Checks to see it there is a collision between two convex polygons. 
	 * The vertices must be positioned counter-clockwise or the opposite vector will be returned.
	 * If there is a collision it returns a vector representing the minimal 
	 * translation necessary to move 'a' so that it is not overlapping 'b', else null.
	 * 
	 * The returned vector is pointing towards the first polygon 'a'.
	 * 
	 * @param a The first polygon
	 * @param b The second polygon
	 * @return The minimum translation vector directed towards the first 
	 * polygon, or null if there is no overlap.
	 */
	public Vector checkCollisionSAT(Polygon a, Polygon b) { // public for the sake of testing

		ArrayList<Vector> aVerts = new ArrayList<Vector>();
		ArrayList<Vector> bVerts = new ArrayList<Vector>();
		aVerts.addAll(a.getVertices());
		bVerts.addAll(b.getVertices());
		
		ArrayList<Vector> normals = new ArrayList<Vector>();
		
		// Add normals of a to the list of normals
		for (int i = 0; i < aVerts.size(); i++) {	
			Vector p1, p2;
			
			p1 = aVerts.get(i);
			p2 = aVerts.get((i + 1 == aVerts.size()) ? 0 : i + 1);
			
			Vector normal = p2.subtract(p1).perpendicularCCW();
			normal = normal.normalize();
			
			normals.add(normal);
			
		}
		
		// Add normals of b to the list normals
		for (int i = 0; i < bVerts.size(); i++) {	
			Vector p1, p2;
			
			p1 = bVerts.get(i);
			p2 = bVerts.get((i + 1 == bVerts.size()) ? 0 : i + 1);
			
			Vector normal = p2.subtract(p1).perpendicularCCW();
			normal = normal.normalize().scalarMultiplication(-1);
			
			normals.add(normal);
			
		}
		
		Vector minOverlapVector = null;
		double minOverlap = Double.MAX_VALUE;
		
		for (Vector normal : normals) {
			
			double aMin = aVerts.get(0).dotProduct(normal); // some value in the range, Double.MAX_DOUBLE seems to give precision issues
			double aMax = aMin;
			
			// Project each vertex of a onto the current normal and save the min and max value
			for (Vector v : aVerts) {
				double dot = v.dotProduct(normal);
				if (dot < aMin) {
					aMin = dot;
				}
				else if (dot > aMax) {
					aMax = dot;
				}
			}
			
			double bMin = bVerts.get(0).dotProduct(normal); // some value in the range
			double bMax = bMin;
			
			// Do the same for b
			for (Vector v : bVerts) {
				double dot = v.dotProduct(normal);
				if (dot < bMin) {
					bMin = dot;
				}
				if (dot > bMax) {
					bMax = dot;
				}
			}
			
			// Compare min and max values to see if there is an overlap
			if (aMin > bMax || bMin > aMax) {
				return null; //no collision
			}
			else {
				double overlap = Math.min(aMax - bMin, bMax - aMin); 
				if (overlap < minOverlap) {
					minOverlap = overlap;
					minOverlapVector = normal.scalarMultiplication(overlap);
				}
			}	
		}
		
		return minOverlapVector;
		
	}
	
	
}
