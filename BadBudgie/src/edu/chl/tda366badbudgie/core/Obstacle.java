package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Obstacle
 * 
 * Generic level object.
 * 
 * @author kvarfordt
 *
 */
public class Obstacle extends AbstractCollidable {

	// Default constructor parameters
	private static final Vector OBSTACLE_SIZE = new Vector(100, 100);
	private static final Sprite OBSTACLE_SPRITE = new Sprite("rock");
	private static final Polygon OBSTACLE_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double OBSTACLE_FRICTION = 0.5;
	private static final double OBSTACLE_ELASTICITY = 0.2;
	private static final boolean OBSTACLE_STATIONARY = false;
	
	/**
	 * Constructor
	 * 
	 * @param texId the texture id
	 * @param stationary the obstacle is immovable if true
	 */
	public Obstacle(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite, collisionData, friction, elasticity);
	}
	
	/**
	 * Constructor
	 * 
	 * @param texId the texture id
	 */
	public Obstacle(Vector position) {
		super(position, OBSTACLE_SIZE, OBSTACLE_STATIONARY, OBSTACLE_SPRITE, OBSTACLE_COLLISION_DATA, OBSTACLE_FRICTION, OBSTACLE_ELASTICITY);
	}
	
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		
	}

}
