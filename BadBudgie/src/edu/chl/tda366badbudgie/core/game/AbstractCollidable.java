package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
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
	public Polygon getCollisionData(boolean transformed) {
		List<Vector> verts = new ArrayList<Vector>(collisionData.getVertices().size());
		if(!transformed){
		verts.addAll(collisionData.getVertices());}
		else{
			for(Vector v: collisionData.getVertices()){
				v = v.scalarMultiplication(getScale()).add(getPosition());
				verts.add(v);
			}
		}
		return new Polygon(verts);
	}
	
	public Polygon getCollisionData(){
		return getCollisionData(false);
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
	 * Returns true if the two given game objects should collide physically.
	 * 
	 * @param class1
	 * @param class2
	 * @return
	 */
	public static boolean isPhysicalCollision(AbstractCollidable class1, AbstractCollidable class2) {
		/*
		 * Using the static list physicalCollisions below.
		 * Implementation might change.
		 */
		String cn1 = class1.getClass().getSimpleName().toLowerCase();
		String cn2 = class2.getClass().getSimpleName().toLowerCase();
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
	
	/**
	 * Collision effect method to be overridden by subclasses that have 
	 * additional effects from a collision.
	 *  
	 * @param other the other object
	 * @param mtv minimum translation vector, points towards this object from other
	 */
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
	}
	
	/**
	 * Adds the given class name to the physical collision set for this object.
	 * 
	 * @param other a string 
	 */
	protected void addPhysicalCollision(Class<? extends AbstractCollidable> otherClass) {
		
		String cn1 = this.getClass().getSimpleName().toLowerCase().trim();
		String cn2 = otherClass.getSimpleName().toLowerCase().trim();
		String concat;
		
		// Sort the two class names lexicographically
		if (cn1.compareTo(cn2) < 0) {
			concat = cn1 + "-" + cn2;
		}
		else {
			concat = cn2 + "-" + cn1;
		}
		
		physicalCollisions.add(concat);
	}
	
	private static Set<String> physicalCollisions = new HashSet<String>();
	
	/**
	 * Default collision data
	 */
	public final static Polygon defaultCollisionData;
	static {
		Vector v = new Vector(40, 40);
		defaultCollisionData = new Polygon(new ArrayList<Vector>(Arrays.asList(
				new Vector(-v.getX(), -v.getY()), 
				new Vector(v.getX(), -v.getY()), 
				new Vector(v.getX(), v.getY()), 
				new Vector(-v.getX(), v.getY()))));
	}
	
	@Override
	public AbstractCollidable clone() {
		return (AbstractCollidable) super.clone();
	}
	
}
