package edu.chl.tda366badbudgie.physics.impl;

import java.util.ArrayList;
import java.util.List;

import edu.chl.tda366badbudgie.core.game.AbstractCollidable;
import edu.chl.tda366badbudgie.core.game.AbstractUnit;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * A collision detection and handling class.
 * 
 * @author tda366-badbudgie
 * 
 */
public class CollisionHandler {

	/**
	 * Checks all game objects for collision with each other, and all game
	 * objects for collision with terrain.
	 * 
	 * @param gr
	 */
	public void handleCollisions(GameRound gr) {

		// TODO: broader checking for possible collisions before using SAT

		ArrayList<AbstractCollidable> collidableObjects = new ArrayList<AbstractCollidable>(
				gr.getLevel().getCollidableObjects());

		ArrayList<AbstractCollidable> terrainSections = new ArrayList<AbstractCollidable>(
				gr.getLevel().getTerrainSections());

		// Reset ground contact vectors
		for (AbstractCollidable ac : collidableObjects) {
			if (ac instanceof AbstractUnit)
				((AbstractUnit) ac).setGroundContactVector(new Vector(0, 0));
		}
		
		/*
		 * The collision checks are separated into two nested loops for performance, 
		 * since we don't want to test terrain against terrain 
		 */
		
		// For every object in the list...
		for (int i = 0; i < collidableObjects.size(); i++) {
			AbstractCollidable o1 = collidableObjects.get(i);

			// ...check it against all following objects in the list...
			for (int j = i + 1; j < collidableObjects.size(); j++) {
				AbstractCollidable o2 = collidableObjects.get(j);
				
				Vector mtv = getCollisionSAT(o1.getCollisionData(true), o2.getCollisionData(true));
				if (mtv != null && !mtv.hasZeroLength()) {
					collide(o1, o2, mtv);
				}
			}

			// ...and all terrain segments.
			for (AbstractCollidable t : terrainSections) {
				Vector mtv = getCollisionSAT(o1.getCollisionData(true), t.getCollisionData(true));
				if (mtv != null && !mtv.hasZeroLength()) {
					collide(o1, t, mtv);
				}
			}
		}
		
	}
	
	/**
	 * This method decides what effects the collision should have.
	 * 
	 * @param o1 the first object
	 * @param o2
	 * @param mtv
	 */
	private void collide(AbstractCollidable o1,
			AbstractCollidable o2, Vector mtv) {
		
		// Let the classes exercise any side effects of the collision
		o1.executeCollisionEffect(o2, mtv);
		o2.executeCollisionEffect(o1, mtv.scalarMultiplication(-1));
		
		if (AbstractCollidable.isPhysicalCollision(o1, o2)) {
			// Resolve the collision by translating one or both of the objects
			resolveCollision(o1, o2, mtv);
	
			// Change velocity of the objects
			handleImpact(o1, o2, mtv);
		}
		
	}
	

	
	/**
	 * Checks to see it there is a collision between two convex polygons. The
	 * vertices must be positioned counter-clockwise or the opposite vector will
	 * be returned. If there is a collision it returns a vector representing the
	 * minimal translation necessary to move 'a' so that it is not overlapping
	 * 'b', else a vector with x and y equal to 0.
	 * 
	 * The returned vector is pointing towards the first polygon 'a'.
	 * 
	 * Uses the separating axis theorem for convex polygons.
	 * 
	 * @param a
	 *            The first polygon
	 * @param b
	 *            The second polygon
	 * @return The minimum translation vector directed towards the first
	 *         polygon, or a Vector with (x,y)==(0,0) if there is no overlap.
	 */
	Vector getCollisionSAT(Polygon a, Polygon b) {

		List<Vector> aVerts = new ArrayList<Vector>(a.getVertices());
		List<Vector> bVerts = new ArrayList<Vector>(b.getVertices());
		
		List<Vector> normals = new ArrayList<Vector>();

		Vector aMidPoint = new Vector();
		Vector bMidPoint = new Vector();

		// Add normals of a to the list of normals
		for (int i = 0; i < aVerts.size(); i++) {
			aMidPoint = aMidPoint.add(aVerts.get(i));

			Vector p1, p2;

			p1 = aVerts.get(i);
			p2 = aVerts.get((i + 1 == aVerts.size()) ? 0 : i + 1);

			Vector normal = p2.subtract(p1).perpendicularCCW();
			normal = normal.normalize();

			normals.add(normal);

		}

		// Add normals of b to the list normals
		for (int i = 0; i < bVerts.size(); i++) {
			bMidPoint = bMidPoint.add(bVerts.get(i));

			Vector p1, p2;

			p1 = bVerts.get(i);
			p2 = bVerts.get((i + 1 == bVerts.size()) ? 0 : i + 1);

			Vector normal = p2.subtract(p1).perpendicularCW();
			normal = normal.normalize();

			normals.add(normal);

		}

		aMidPoint = aMidPoint.scalarDivision(aVerts.size());
		bMidPoint = bMidPoint.scalarDivision(bVerts.size());

		// The vector from b's midpoint to a's midpoint
		Vector ba = aMidPoint.subtract(bMidPoint);

		Vector minOverlapVector = null;
		double minOverlap = Double.MAX_VALUE;

		for (Vector normal : normals) {

			// If we have a normal not pointing in the general direction from b
			// to a, reverse it
			if (ba.dotProduct(normal) < 0)
				normal = normal.scalarMultiplication(-1);

			double aMin = aVerts.get(0).dotProduct(normal); // some value in the
															// projected range
			double aMax = aMin;

			// Project each vertex of a onto the current normal and save the min
			// and max value
			for (Vector v : aVerts) {
				double dot = v.dotProduct(normal);
				if (dot < aMin) {
					aMin = dot;
				} else if (dot > aMax) {
					aMax = dot;
				}
			}

			double bMin = bVerts.get(0).dotProduct(normal); // some value in the
															// projected range
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
				return new Vector(0, 0); // no overlap - no collision
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
	 * Resolves the collision between two AbstractCollidable objects 
	 * by moving them out of each other.
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param mtv
	 *            the minimal translation vector for the collision, given by
	 *            getCollisionSAT().
	 */
	private void resolveCollision(AbstractCollidable a, AbstractCollidable b,
			Vector mtv) {

		if (!a.isStationary() && !b.isStationary()) {
			// Both objects are movable

			double aMassRatio = a.getMass() / (b.getMass() + a.getMass());
			double bMassRatio = 1 - aMassRatio;

			// Move objects out of each other
			a.translate(mtv.scalarMultiplication(bMassRatio));
			b.translate(mtv.scalarMultiplication(aMassRatio * -1));

		} else if (a.isStationary()) {
			// Move b out of a
			b.translate(mtv);

		} else if (b.isStationary()) {
			// Move a out of b
			a.translate(mtv);
			
		} else {
			// Both objects stationary, just move out of collision by equal
			// distance
			a.translate(mtv.scalarMultiplication(0.5));
			b.translate(mtv.scalarMultiplication(-0.5));
		}
	}
	
	
	/**
	 * Changes the velocities of the colliding objects, taking into account 
	 * friction and elasticity.
	 * 
	 * @param a
	 *            the first object
	 * @param b
	 *            the second object
	 * @param mtv
	 *            the minimal translation vector for the collision, given by
	 *            getCollisionSAT().
	 */
	private void handleImpact(AbstractCollidable a, AbstractCollidable b,
			Vector mtv) {
		
		Vector vRel = b.getVelocity().subtract(a.getVelocity());
		if (mtv.dotProduct(vRel) < 0) {
			// Objects are moving away from each other
			return;
		}

		if (!a.isStationary() && !b.isStationary()) {
			// Both objects are movable

			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() * b.getFriction() + 1;
			double ma = a.getMass();
			double mb = b.getMass();

			
			// Effect normal to collision
			double vaNormal = a.getVelocity().dotProduct(collisionNormal);
			double vbNormal = b.getVelocity().dotProduct(collisionNormal);

			double vCentreOfMassNormal = (ma * vaNormal + mb * vbNormal)
					/ (ma + mb);

			double vaNormal2 = (2 * vCentreOfMassNormal - vaNormal)
					* totalRestitution;
			double vbNormal2 = (2 * vCentreOfMassNormal - vbNormal)
					* totalRestitution;

			
			// Effect tangent to collision
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);
			double vbTangent = b.getVelocity().dotProduct(collisionTangent);

			double vaTangent2 = vaTangent / totalFriction;
			double vbTangent2 = vbTangent / totalFriction;


			// Calculate required forces
			double faN = (vaNormal2 - vaNormal) * ma + b.getForce().dotProduct(collisionNormal);
			double fbN = (vbNormal2 - vbNormal) * mb + a.getForce().dotProduct(collisionNormal);
			double faT = (vaTangent2 - vaTangent) * ma;
			double fbT = (vbTangent2 - vbTangent) * mb;
			
			
			// Apply force
			a.applyForce(collisionNormal.scalarMultiplication(faN).add(
					collisionTangent.scalarMultiplication(faT)));
			b.applyForce(collisionNormal.scalarMultiplication(fbN).add(
					collisionTangent.scalarMultiplication(fbT)));
			
			

		} else if (a.isStationary()) {
			
			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() * b.getFriction() + 1;

			// Normal and tangential velocity
			double vbNormal = b.getVelocity().dotProduct(collisionNormal);
			double vbTangent = b.getVelocity().dotProduct(collisionTangent);
			
			// Calculate required forces
			double fN = (vbNormal + vbNormal * totalRestitution) *  -1 * b.getMass();
			double fT = (vbTangent / totalFriction - vbTangent) * b.getMass(); 
			
			// Apply force???
			b.applyForce(collisionNormal.scalarMultiplication(fN).add(
					collisionTangent.scalarMultiplication(fT)));

		} else if (b.isStationary()) {

			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() * b.getFriction() + 1;

			// Normal and tangential velocity
			double vaNormal = a.getVelocity().dotProduct(collisionNormal);
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);
			
			// Calculate required forces
			double fN = (vaNormal + vaNormal * totalRestitution) *  -1 * a.getMass();
			double fT = (vaTangent / totalFriction - vaTangent) * a.getMass(); 
			
			// Apply force???
			a.applyForce(collisionNormal.scalarMultiplication(fN).add(
					collisionTangent.scalarMultiplication(fT)));


		}
		
		
	}

}
