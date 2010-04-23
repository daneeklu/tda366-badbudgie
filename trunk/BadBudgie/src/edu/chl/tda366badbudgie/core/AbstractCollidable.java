package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;


/**
 * AbstractCollidable
 * 
 * Abstract class inherited by objects handled by the 
 * collision engine.
 * 
 * @author jesper
 *
 */
public abstract class AbstractCollidable extends AbstractGameObject {
	
	private Polygon collisionData;
	private double friction; // Friction coefficient. (100 = a lot, 0 = no friction)  
	private double elasticity; // Elasticity coefficient. 1 = superball, 0 = lump of clay
	
	
	
	/**
	 * Creates a new AbstractCollidable object with the given properties.
	 * 
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 */
	public AbstractCollidable(Polygon collisionData, double friction, double elasticity) {
		this.collisionData = collisionData;
		this.friction = friction;
		this.elasticity = elasticity;
	}
	
	protected AbstractCollidable() {
		
	}
	
	/**
	 * Returns the collision data of the object.
	 * @return the collision data.
	 */
	public Polygon getCollisionData() {
		// Move the collision data to the objects current position.
		List<Vector> offsetCollisionData = new LinkedList<Vector>();
		for (Vector v : collisionData.getVertices()) {
			offsetCollisionData.add(v.add(getPosition()));
		}
		return new Polygon(offsetCollisionData);
	}
	
	/**
	 * Returns the friction coefficient.
	 * 1 = instant stop, 0 = no friction 
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
	
}
