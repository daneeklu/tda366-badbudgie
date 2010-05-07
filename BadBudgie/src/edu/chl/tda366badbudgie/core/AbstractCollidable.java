package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractCollidable
 * 
 * Abstract class inherited by objects handled by the 
 * collision engine.
 * 
 * @author jesper, kvarfordt
 *
 */
public abstract class AbstractCollidable extends AbstractGameObject {
	
	private Polygon collisionData;
	private double friction;
	private double elasticity;
	
	/**
	 * Creates a new AbstractCollidable object with the given properties.
	 * 
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 */
	public AbstractCollidable(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite);
		
		// Make sure the collision data is valid
		if (!Polygon.checkConvexity(collisionData)) {
			throw new IllegalArgumentException("Collisiondata polygon is not convex.");
		} else if (!Polygon.checkCCW(collisionData)) {
			Collections.reverse(collisionData.getVertices());
		}
		
		setCollisionData(collisionData);
		setFriction(friction);
		setElasticity(elasticity);

	}
	
	/**
	 * Returns the collision data of the object.
	 * If no collision data is set an empty list will be returned.
	 * 
	 * @return the collision data.
	 */
	public Polygon getCollisionData() {
		return new Polygon(collisionData.getVertices());
	}
	
	/**
	 * Returns the friction coefficient.
	 * 100 = lots of friction, 0 = no friction
	 * 
	 * @return the friction coefficient
	 */
	public double getFriction() {
		return friction;
	}
	
	/**
	 * Returns the elasticity coefficient.
	 * 1 = superball, 0 = lump of clay
	 * @return the elasticity coefficient
	 */
	public double getElasticity() {
		return elasticity;
	}
	
	/**
	 * Sets the collision data to the given polygon
	 * @param cd the collision data polygon
	 */
	public void setCollisionData(Polygon cd) {
		collisionData = cd;
	}
	
	/**
	 * Sets the friction of the object to the given value
	 * @param f the friction value
	 */
	public void setFriction(double f) {
		friction = f;
	}
	
	/**
	 * Sets the elasticity of the object to the given value
	 * @param e the elasticity value
	 */
	public void setElasticity(double e) {
		elasticity = e;
	}
	
	/**
	 * Notifies the object that there is a collision and lets it act accordingly.
	 * 
	 * @param other the colliding object
	 * @param mtv minimum translation vector for the collision
	 * @return true if the objects should collide physically
	 */
	public abstract void executeCollisionEffect(AbstractCollidable other, Vector mtv);

	
	/**
	 * Returns true if the objects of the two given classes should collide physically.
	 * 
	 * @param class1
	 * @param class2
	 * @return
	 */
	public static boolean isPhysicalCollision(Class<? extends AbstractCollidable> class1, Class<? extends AbstractCollidable> class2) {
		
		/*
		 * Using the static list physicalCollisions below.
		 * Implementation might change.
		 */
		String cn1 = class1.getSimpleName();
		String cn2 = class2.getSimpleName();
		String concat;
		
		// Sort the two class names lexicographically
		if (cn1.compareTo(cn2) < 0) {
			concat = cn1 + "-" + cn2;
		}
		else {
			concat = cn2 + "-" + cn1;
		}
		
		// See if the combined string of names is in physicalCollisions
		if (physicalCollisions.contains(concat))
			return true;
		
		return false;
	}
	
	private static List<String> physicalCollisions = new ArrayList<String>();
	static {
		physicalCollisions.add("Enemy-TerrainSection");
		physicalCollisions.add("Enemy-Obstacle");
		physicalCollisions.add("Enemy-Player");
		physicalCollisions.add("Enemy-Projectile");
		physicalCollisions.add("Obstacle-Obstacle");
		physicalCollisions.add("Obstacle-Player");
		physicalCollisions.add("Obstacle-Projectile");
		physicalCollisions.add("Obstacle-TerrainSection");
		physicalCollisions.add("Player-TerrainSection");
		physicalCollisions.add("Projectile-TerrainSection");
		
	}
	
	
	public final static Polygon defaultCollisionData;
	static {
		Vector v = new Vector(40, 40);
		defaultCollisionData = new Polygon(new ArrayList<Vector>(Arrays.asList(
				new Vector(-v.getX(), -v.getY()), 
				new Vector(v.getX(), -v.getY()), 
				new Vector(v.getX(), v.getY()), 
				new Vector(-v.getX(), v.getY()))));
	}
	
	
}
