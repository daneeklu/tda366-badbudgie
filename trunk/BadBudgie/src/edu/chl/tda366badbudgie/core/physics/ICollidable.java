package edu.chl.tda366badbudgie.core.physics;

import edu.chl.tda366badbudgie.core.content.IPositioned;
import edu.chl.tda366badbudgie.core.content.Polygon;

/**
 * ICollidable
 * 
 * Interface implemented by objects handled by the 
 * collision engine.
 * 
 * @author jesper
 *
 */
public interface ICollidable extends IPositioned {
	
	/**
	 * Returns the collision data of the object.
	 * 
	 * @return the collision data.
	 */
	public Polygon getCollisionData();
	
	/**
	 * Sets the collision data of the object.
	 * 
	 * @param collisionData collision data.
	 */
	public void setCollisionData(Polygon collisionData);
	
}
