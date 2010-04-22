package edu.chl.tda366badbudgie.physics;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.Polygon;

/**
 * ICollidable
 * 
 * Interface implemented by objects handled by the 
 * collision engine.
 * 
 * @author jesper
 *
 */
public abstract class ICollidable extends AbstractGameObject {
	
	/**
	 * Returns the collision data of the object.
	 * 
	 * @return the collision data.
	 */
	public abstract Polygon getCollisionData();
	
	/**
	 * Returns the friction coefficient.
	 * 1 = instant stop, 0 = no friction
	 * 
	 * @return the friction coefficient
	 */
	public abstract double getFriction();
	
	/**
	 * Returns the elasticity coefficient.
	 * 1 = superball, 0 = lump of clay
	 * 
	 * @return the elasticity coefficient
	 */
	public abstract double getElasticity();
	
}