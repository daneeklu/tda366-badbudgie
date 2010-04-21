package edu.chl.tda366badbudgie.core.physics;

import java.util.ArrayList;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.content.Polygon;
import edu.chl.tda366badbudgie.core.content.TerrainQuad;
import edu.chl.tda366badbudgie.core.content.Vector;

public class Physics implements IPhysics{

	@Override
	public void doPhysics(GameRound gr) {
		
		ArrayList<ICollidable> collidableObjects = new ArrayList<ICollidable>();
		collidableObjects.addAll(gr.getCollidableObjects());
		
		ArrayList<TerrainQuad> terrainQuads = new ArrayList<TerrainQuad>();
		terrainQuads.addAll(gr.getTerrainQuads());
		
		// for every object in the list...
		for(int i = 0; i < collidableObjects.size(); i++) {
			ICollidable o1 = collidableObjects.get(i);
			
			// ...check it against all following objects...
			for(int j = i + 1; j < collidableObjects.size(); j++) {
				ICollidable o2 = collidableObjects.get(j);
				
				Vector mtv = checkCollisionSAT(o1.getCollisionData(), o2.getCollisionData());
				if (mtv.getLength() != 0) {
					handleCollision(o1, o2, mtv);
				}
			}
			
			// ...and all terrain segments.
			for(TerrainQuad t : terrainQuads) {
				Vector mtv = checkCollisionSAT(o1.getCollisionData(), t.getCollisionData());
				if (mtv.getLength() != 0) {
					handleCollision(o1, t, mtv);
				}
			}
		}
		
	}
	
	/**
	 * Checks to see it there is a collision between two convex polygons. 
	 * The vertices must be positioned counter-clockwise or the opposite 
	 * vector will be returned.
	 * If there is a collision it returns a vector representing the minimal 
	 * translation necessary to move 'a' so that it is not overlapping 'b', 
	 * else a vector with x and y equal to 0.
	 * 
	 * The returned vector is pointing towards the first polygon 'a'.
	 * 
	 * @param a The first polygon
	 * @param b The second polygon
	 * @return The minimum translation vector directed towards the first 
	 * polygon, or a Vector with (x,y)==(0,0) if there is no overlap.
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
				return new Vector(0,0); //no collision
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
	
	/**
	 * Handles the collision between two ICollidable objects which are not terrain.
	 * 
	 * @param a the first object
	 * @param b the second object
	 * @param mtv the minimal translation vector for the collision, given by checkCollision()
	 */
	private void handleCollision(ICollidable a, ICollidable b, Vector mtv) {
		
		if (a instanceof IPhysical && b instanceof IPhysical) {
			// both objects are physical and therefore have mass
			
			IPhysical pa = (IPhysical) a;
			IPhysical pb = (IPhysical) b;
			
			double aMassRatio = pa.getMass() / (pb.getMass() + pa.getMass());
			double bMassRatio = 1 - aMassRatio;
			
			pa.translate(mtv.scalarMultiplication(bMassRatio));
			pb.translate(mtv.scalarMultiplication(aMassRatio * -1));
			
			//TODO: handle speed and direction
		}
		else if (a instanceof IPhysical) {
			// only a has mass eg. b has infinite mass
			IPhysical pa = (IPhysical) a;
			
			//TODO: handle speed and direction
		}
		else if (b instanceof IPhysical) {
			// b has mass eg. a has infinite mass
			IPhysical pb = (IPhysical) b;
			
			//TODO: handle speed and direction
		}
		else {
			a.translate(mtv.scalarMultiplication(0.5));
			b.translate(mtv.scalarMultiplication(-0.5));
		}
	}
	
	/**
	 * Handles the collision of an ICollidable object with a terrain segment
	 * 
	 * @param a the colliding object
	 * @param t the terrain segment
	 * @param mtv the minimal translation vector for the collision, given by checkCollision()
	 */
	private void handleCollision(ICollidable a, TerrainQuad t, Vector mtv) {
		//TODO: Implement collision handling
	}
	
	
}
