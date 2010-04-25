package edu.chl.tda366badbudgie.physics;

import java.awt.Color;
import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Vector;
import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;

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
			
			if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
				DebugInfoRenderer.getInstance().addDebugLine(ago.getPosition(), ago.getPosition().add(ago.getForce().scalarMultiplication(100)), Color.red);
				DebugInfoRenderer.getInstance().addDebugLine(ago.getPosition(), ago.getPosition().add(ago.getVelocity().scalarMultiplication(100)), Color.blue);
				DebugInfoRenderer.getInstance().addDebugText("pos: " + "x:" + ago.getX() + " y:" + ago.getY());
			}
			
			// Reset the force on the object
			ago.setForce(new Vector(0, 0));
			
		}
		
	}

}
