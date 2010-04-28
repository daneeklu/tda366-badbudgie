package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

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
	private double friction; 		// Friction coefficient. (100 = a lot, 0 = no friction)  
	private double elasticity; 		// Elasticity coefficient. 1 = superball, 0 = lump of clay
	private Vector groundContactVector;
	private AbstractCollidable groundContactObject;
	
	
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
		setGroundContactVector(new Vector());
		setGroundContactObject(new AbstractCollidable() {
			// Dummy default collision object
			@Override public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {}});
	}
	
	protected AbstractCollidable() {
		setGroundContactVector(new Vector());
	}
	
	/**
	 * Returns the collision data of the object.
	 * If no collision data is set an empty list will be returned.
	 * 
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
	 * Sets the ground contact vector for the unit.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @param groundContactVector
	 *            the vector of the ground contact
	 */
	public void setGroundContactVector(Vector groundContactVector) {
		this.groundContactVector = groundContactVector;
	}

	/**
	 * Returns the ground contact normal of the unit. If the unit does not have
	 * ground contact, a vector of length zero will be returned.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @return the ground contact vector
	 */
	public Vector getGroundContactVector() {
		return groundContactVector;
	}
	

	
	/**
	 * Notifies the object that there is a collision and lets it act accordingly.
	 * 
	 * @param other the colliding object
	 * @param mtv minimum translation vector for the collision
	 */
	public abstract void executeCollisionEffect(AbstractCollidable other, Vector mtv);

	/**
	 * 
	 * 
	 * @param groundContactObject
	 */
	public void setGroundContactObject(AbstractCollidable groundContactObject) {
		this.groundContactObject = groundContactObject;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public AbstractCollidable getGroundContactObject() {
		return groundContactObject;
	}
	
}
