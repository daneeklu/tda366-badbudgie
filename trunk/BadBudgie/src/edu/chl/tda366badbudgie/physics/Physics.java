package edu.chl.tda366badbudgie.physics;

import edu.chl.tda366badbudgie.core.GameRound;

public class Physics implements IPhysics{

	public static final double g = 0.002;
	
	private CollisionHandler collisionHandler;
	private MovementHandler movementHandler;
	private GameRound gameRound;
	
	
	public Physics(GameRound gameRound, CollisionHandler collisionHandler, MovementHandler movementHandler) {
		this.collisionHandler = collisionHandler;
		this.movementHandler = movementHandler;
		this.gameRound = gameRound;
	}
	
	@Override
	public void doPhysics(GameRound gr) {
		
		movementHandler.handleMovement(gameRound);
		collisionHandler.handleCollisions(gameRound);
		
	}
	
	
}
