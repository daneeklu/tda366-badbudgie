package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

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
	private static final double OBSTACLE_FRICTION = 0.9;
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

		addPhysicalCollision("TerrainSection");
		addPhysicalCollision("Player");
		addPhysicalCollision("Enemy");
		addPhysicalCollision("Obstacle");
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
	public Object clone() {
		Obstacle o = new Obstacle(getPosition(), getSize(), isStationary(), getSprite(), getCollisionData(), getFriction(), getElasticity());
		//o.setAirResistance(getAirResistance());
		return o;
	}
	
	
	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.IMPENETRABLE);
		stimuli.add(CollisionStimulus.WALKABLE_GROUND);
		return stimuli;
	}

}
