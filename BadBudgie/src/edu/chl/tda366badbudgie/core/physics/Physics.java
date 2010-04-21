package edu.chl.tda366badbudgie.core.physics;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.physics.collision.CollisionHandler;

public class Physics implements IPhysics{

	private CollisionHandler collisionHandler;
	private GameRound gameRound;
	
	public Physics(GameRound gameRound, CollisionHandler collisionHandler) {
		this.collisionHandler = collisionHandler;
		this.gameRound = gameRound;
	}
	
	@Override
	public void doPhysics() {
		
		collisionHandler.handleCollisions(gameRound);
		
	}
	
	
}
