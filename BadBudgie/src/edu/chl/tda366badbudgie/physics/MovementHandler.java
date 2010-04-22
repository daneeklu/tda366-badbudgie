package edu.chl.tda366badbudgie.physics;

import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Level;
import edu.chl.tda366badbudgie.core.Vector;

public class MovementHandler {

	public void handleMovement(GameRound gameRound) {
		
		Level level = gameRound.getLevel();
		List<AbstractGameObject> gameObjects = level.getGameObjects();
		
		for(AbstractGameObject ago : gameObjects) {
			
			// Apply gravity
			ago.setForce(new Vector(0, -Physics.g * ago.getMass()));
			
			// Set the new speed of the object
			ago.setVelocity(ago.getVelocity().add(ago.getForce().scalarDivision(ago.getMass())));
			
			// Set the new position of the object
			ago.setPosition(ago.getPosition().add(ago.getVelocity()));
			
			// Reset the force on the object
			ago.setForce(new Vector(0, 0));
			
		}
		
	}

}
