package edu.chl.tda366badbudgie.core;

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

	/**
	 * Constructor
	 * 
	 * @param texId the texture id
	 * @param stationary the obstacle is immovable if true
	 */
	public Obstacle(String texId, boolean stationary) {
		this.stationary = stationary;
		setFriction(1);
		setElasticity(0.1);
		setMass(1);
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}
	
	/**
	 * Constructor
	 * 
	 * @param texId the texture id
	 */
	public Obstacle(String texId) {
		this(texId, false);
	}
	
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		
	}

}
