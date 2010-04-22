package edu.chl.tda366badbudgie.physics;

import java.util.ArrayList;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Polygon;
import edu.chl.tda366badbudgie.core.Vector;

/**
 * A collision detection and handling class.
 * 
 * @author tda366-badbudgie
 *
 */
public class CollisionHandler {
	
	/**
	 * Checks all game objects for collision with each other,
	 * and all game objects for collision with terrain.
	 * Then it resolves found collisions.
	 * 
	 * @param gr
	 */
	public void handleCollisions(GameRound gr) {
		
		// TODO: broader checking for possible collisions before using SAT
		// TODO: perhaps handle collisions between all AbstractGameObjects (powerups etc)
		
		ArrayList<ICollidable> collidableObjects = 
			new ArrayList<ICollidable>(gr.getLevel().getCollidableObjects());
		
		ArrayList<ICollidable> terrainSections = 
			new ArrayList<ICollidable>(gr.getLevel().getTerrainSections());
		
		// for every object in the list...
		for(int i = 0; i < collidableObjects.size(); i++) {
			ICollidable o1 = collidableObjects.get(i);
			
			// ...check it against all following objects in the list...
			for(int j = i + 1; j < collidableObjects.size(); j++) {
				ICollidable o2 = collidableObjects.get(j);
				
				Vector mtv = getCollisionSAT(o1.getCollisionData(), o2.getCollisionData());
				if (mtv.getLength() != 0) {
					checkCollisionEffect(o1, o2, mtv);
				}
			}
			
			// ...and all terrain segments.
			for(ICollidable t : terrainSections) {
				Vector mtv = getCollisionSAT(o1.getCollisionData(), t.getCollisionData());
				if (mtv.getLength() != 0) {
					resolveCollision(o1, t, mtv);
				}
			}
		}
	}
	
	
	private void checkCollisionEffect(ICollidable o1, ICollidable o2, Vector mtv) {
		// TODO: find out what effect the collision has and act accordingly
		resolveCollision(o1, o2, mtv);
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
	 * Uses the separating axis theorem for convex polygons.
	 * 
	 * @param a The first polygon
	 * @param b The second polygon
	 * @return The minimum translation vector directed towards the first 
	 * polygon, or a Vector with (x,y)==(0,0) if there is no overlap.
	 */
	Vector getCollisionSAT(Polygon a, Polygon b) {

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
	 * Handles the collision between two ICollidable objects.
	 * 
	 * @param a the first object
	 * @param b the second object
	 * @param mtv the minimal translation vector for the collision, 
	 * given by getCollisionSAT().
	 */
	private void resolveCollision(ICollidable a, ICollidable b, Vector mtv) {
		
		if (a.getVelocity().dotProduct(mtv) > 0) {
			// a is moving away from b, ignore collision
			return;
		}
		
		if (!a.isStationary() && !b.isStationary()) {
			// Both objects are movable
			
			double aMassRatio = a.getMass() / (b.getMass() + a.getMass());
			double bMassRatio = 1 - aMassRatio;
			
			// Move objects out of each other
			a.translate(mtv.scalarMultiplication(bMassRatio));
			b.translate(mtv.scalarMultiplication(aMassRatio * -1));
			
			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() + b.getFriction();
			double ma = a.getMass();
			double mb = b.getMass();
			
			// Effect normal to collision
			double vaNormal = a.getVelocity().dotProduct(collisionNormal);
			double vbNormal = b.getVelocity().dotProduct(collisionNormal);
			
			double vCentreOfMassNormal = (ma*vaNormal + mb*vbNormal) / (ma + mb); 
			
			double vaNormal2 = (2 * vCentreOfMassNormal - vaNormal) * totalRestitution;
			double vbNormal2 = (2 * vCentreOfMassNormal - vbNormal) * totalRestitution;
			
			// Effect tangent to collision
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);
			double vbTangent = b.getVelocity().dotProduct(collisionTangent);
			
			double vCentreOfMassTangent = (ma*vaTangent + mb*vbTangent) / (ma + mb); 
			
			double vaTangent2 = (2 * vCentreOfMassTangent - vaTangent) / totalFriction;
			double vbTangent2 = (2 * vCentreOfMassTangent - vbTangent) / totalFriction;
			
			// Apply result
			a.setVelocity(collisionNormal.scalarMultiplication(vaNormal2)
					.add(collisionTangent.scalarMultiplication(vaTangent2)));
			
			b.setVelocity(collisionNormal.scalarMultiplication(vbNormal2)
					.add(collisionTangent.scalarMultiplication(vbTangent2)));
			
		}
		else if (a.isStationary()) {
			
			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() + b.getFriction();
			
			// Normal and tangential velocity
			double vbNormal = b.getVelocity().dotProduct(collisionNormal);
			double vbTangent = b.getVelocity().dotProduct(collisionTangent);
			
			// Affect velocities
			vbNormal = -1.0 * vbNormal / totalRestitution;
			vbTangent = vbTangent / totalFriction;
			
			// Apply result
			b.setVelocity(collisionNormal.scalarMultiplication(vbNormal)
					.add(collisionTangent.scalarMultiplication(vbTangent)));
			
		}
		else if (b.isStationary()) {
			
			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() + b.getFriction();
			
			// Normal and tangential velocity
			double vaNormal = a.getVelocity().dotProduct(collisionNormal);
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);

			// Affect velocities
			vaNormal = -1.0 * vaNormal / totalRestitution;
			vaTangent = vaTangent / totalFriction;
			
			// Apply result
			a.setVelocity(collisionNormal.scalarMultiplication(vaNormal)
					.add(collisionTangent.scalarMultiplication(vaTangent)));
			
		}
		else {
			// Both objects stationary, just move out of collision by equal distance
			a.translate(mtv.scalarMultiplication(0.5));
			b.translate(mtv.scalarMultiplication(-0.5));
		}
	}
	
	
}
