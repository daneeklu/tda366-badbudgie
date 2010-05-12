package edu.chl.tda366badbudgie.physics.impl;

import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.physics.IPhysics;

/**
 * Physics
 * 
 * Contains methods to handle the physics.
 * 
 * @author kvarfordt
 *
 */
public class Physics implements IPhysics{

	public static final double g = 0.6;
	
	private CollisionHandler collisionHandler;
	private MovementHandler movementHandler;
	
	/**
	 * Constructor 
	 * 
	 * @param collisionHandler
	 * @param movementHandler
	 */
	public Physics() {
		this.collisionHandler = new CollisionHandler();
		this.movementHandler = new MovementHandler();
	}
	
	@Override
	public void doPhysics(GameRound gr) {
		
		movementHandler.handleMovement(gr);
		collisionHandler.handleCollisions(gr);
		
	}
	
	
}
