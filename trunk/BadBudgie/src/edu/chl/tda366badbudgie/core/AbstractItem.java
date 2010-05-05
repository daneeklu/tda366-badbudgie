package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractItem
 * 
 * Abstract class inherited by items in the game.
 * 
 * @author 
 *
 */
public abstract class AbstractItem extends AbstractCollidable {
	
	public AbstractItem(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite, collisionData, friction, elasticity);
	}
	
	
}
