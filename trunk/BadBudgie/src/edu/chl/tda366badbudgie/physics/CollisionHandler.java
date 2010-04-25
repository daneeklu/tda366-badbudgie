package edu.chl.tda366badbudgie.physics;

import java.awt.Color;
import java.util.ArrayList;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.AbstractCollidable;
import edu.chl.tda366badbudgie.core.Polygon;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;

/**
 * A collision detection and handling class.
 * 
 * @author tda366-badbudgie
 * 
 */
public class CollisionHandler {

	/**
	 * Checks all game objects for collision with each other, and all game
	 * objects for collision with terrain. Then it resolves found collisions.
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
			ac.setGroundContactVector(new Vector(0, 0));
		}
		
		// For every object in the list...
		for (int i = 0; i < collidableObjects.size(); i++) {
			AbstractCollidable o1 = collidableObjects.get(i);

			// ...check it against all following objects in the list...
			for (int j = i + 1; j < collidableObjects.size(); j++) {
				AbstractCollidable o2 = collidableObjects.get(j);

				Vector mtv = getCollisionSAT(o1.getCollisionData(), o2
						.getCollisionData());
				if (mtv.hasZeroLength()) {
					checkCollisionEffect(o1, o2, mtv);
				}
			}

			// ...and all terrain segments.
			for (AbstractCollidable t : terrainSections) {
				Vector mtv = getCollisionSAT(o1.getCollisionData(), t
						.getCollisionData());

				if (mtv.getLength() != 0) {
					checkCollisionEffect(o1, t, mtv);
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
	private void checkCollisionEffect(AbstractCollidable o1,
			AbstractCollidable o2, Vector mtv) {
		
		// Set the ground contact vector
		double friction = o1.getFriction() * o2.getFriction() + 1;
		if (mtv.getY() > 0) {
			// o1 on top of o2
			o1.setGroundContactVector(o1.getGroundContactVector().add(
					mtv.normalize().scalarMultiplication(friction))
					.scalarDivision(2));
			// o1 and o2 can have contact with more than one object, hence we must set
			// the vector to the mean of the current and the new, both in length
			// (friction), and direction
		}
		else {
			// o2 on top of o1
			o2.setGroundContactVector(o2.getGroundContactVector().add(
					mtv.normalize().scalarMultiplication(friction))
					.scalarDivision(2));
		}
		
		DebugInfoRenderer.getInstance().addDebugLine(o1.getPosition(), o1.getPosition().add(mtv.scalarMultiplication(100)), Color.green);
		
		// Resolve the collision by translating one or both of the objects
		resolveCollision(o1, o2, mtv);

		// Change velocity of the objects
		handleImpact(o1, o2, mtv);
		
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

		ArrayList<Vector> aVerts = new ArrayList<Vector>(a.getVertices());
		ArrayList<Vector> bVerts = new ArrayList<Vector>(b.getVertices());

		ArrayList<Vector> normals = new ArrayList<Vector>();

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

			// Move objects out of each other
			b.translate(mtv);


		} else if (b.isStationary()) {

			// Move a out of b
			// TODO: Think this over:
			/*
			if (a.getVelocity().dotProduct(mtv.perpendicularCCW()) / a.getVelocity().dotProduct(mtv) > 0.5) {
				Vector vn = a.getVelocity().normalize();
				a.translate(vn.scalarMultiplication(Math.pow(mtv.getLength(), 2) / (vn.dotProduct(mtv))));
			}
			else {
				a.translate(mtv);
			}
			*/
			
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
		
		
		Vector vRel = a.getVelocity().subtract(b.getVelocity());
		if (mtv.dotProduct(vRel) > 0) {
			return;
		}

		if (!a.isStationary() && !b.isStationary()) {
			// Both objects are movable

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

			double vCentreOfMassNormal = (ma * vaNormal + mb * vbNormal)
					/ (ma + mb);

			double vaNormal2 = (2 * vCentreOfMassNormal - vaNormal)
					* totalRestitution;
			double vbNormal2 = (2 * vCentreOfMassNormal - vbNormal)
					* totalRestitution;

			// Effect tangent to collision
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);
			double vbTangent = b.getVelocity().dotProduct(collisionTangent);

			double vCentreOfMassTangent = (ma * vaTangent + mb * vbTangent)
					/ (ma + mb);

			double vaTangent2 = (2 * vCentreOfMassTangent - vaTangent)
					/ totalFriction;
			double vbTangent2 = (2 * vCentreOfMassTangent - vbTangent)
					/ totalFriction;

			// Apply result
			a.setVelocity(collisionNormal.scalarMultiplication(vaNormal2).add(
					collisionTangent.scalarMultiplication(vaTangent2)));

			b.setVelocity(collisionNormal.scalarMultiplication(vbNormal2).add(
					collisionTangent.scalarMultiplication(vbTangent2)));

		} else if (a.isStationary()) {
			
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
			b.setVelocity(collisionNormal.scalarMultiplication(vbNormal).add(
					collisionTangent.scalarMultiplication(vbTangent)));

		} else if (b.isStationary()) {

			// Needed vectors and constants
			Vector collisionNormal = mtv.normalize();
			Vector collisionTangent = mtv.normalize().perpendicularCCW();
			double totalRestitution = a.getElasticity() * b.getElasticity();
			double totalFriction = a.getFriction() * b.getFriction() + 1;

			// Normal and tangential velocity
			double vaNormal = a.getVelocity().dotProduct(collisionNormal);
			double vaTangent = a.getVelocity().dotProduct(collisionTangent);

			// Affect velocities
			vaNormal = -1.0 * vaNormal * totalRestitution;
			vaTangent = vaTangent / totalFriction;

			// Apply result
			a.setVelocity(collisionNormal.scalarMultiplication(vaNormal).add(
					collisionTangent.scalarMultiplication(vaTangent)));

		}
		
		
	}

}
