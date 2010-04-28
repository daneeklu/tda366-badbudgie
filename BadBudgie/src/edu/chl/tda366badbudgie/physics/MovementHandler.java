package edu.chl.tda366badbudgie.physics;

import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Vector;

public class MovementHandler {

	public void handleMovement(GameRound gameRound) {
		
		List<AbstractGameObject> gameObjects = gameRound.getLevel().getGameObjects();
		
		for(AbstractGameObject ago : gameObjects) {
			
			ago.updateForces();
			
			// Apply gravity
			ago.applyForce(new Vector(0, -Physics.g * ago.getMass()));
			
			// Set the new speed of the object
			ago.setVelocity(ago.getVelocity().add(ago.getForce().scalarDivision(ago.getMass())));
			
			// Set the new position of the object
			ago.setPosition(ago.getPosition().add(ago.getVelocity()));
			
			// Reset the force on the object
			ago.setForce(new Vector(0, 0));
			
		}

	}

}
