package edu.chl.tda366badbudgie.physics.impl;

import java.util.List;

import edu.chl.tda366badbudgie.core.game.AbstractGameObject;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Handles game objects movement.
 * 
 * @author kvarfordt
 *
 */
public class MovementHandler {

	/**
	 * Handles game objects movement and updates their position.
	 * @param gameRound
	 */
	public void handleMovement(GameRound gameRound) {
		
		List<AbstractGameObject> gameObjects = 
			gameRound.getLevel().getGameObjects();
		
		for(AbstractGameObject ago : gameObjects) {
			
			if (!ago.isStationary()) {
				// Update additional external forces acting on the object
				ago.updateForces();
				
				// Air friction
				ago.setVelocity(ago.getVelocity()
						.scalarDivision(ago.getAirResistance()));
				
				//Limit velocity
				if (ago.getVelocity().getLength() > ago.getMaxVelocity())
					ago.setVelocity(ago.getVelocity().normalize()
							.scalarMultiplication(ago.getMaxVelocity()));
				
				// Apply gravity
				ago.applyForce(new Vector(0, -Physics.g * ago.getMass()));
				
				// Set the new speed of the object
				ago.setVelocity(ago.getVelocity().add(ago.getForce()
						.scalarDivision(ago.getMass())));
				
				// Set the new position of the object
				ago.setPosition(ago.getPosition().add(ago.getVelocity()));
				
				// Reset the force on the object
				ago.setForce(new Vector());
			}
			
		}

	}

}
