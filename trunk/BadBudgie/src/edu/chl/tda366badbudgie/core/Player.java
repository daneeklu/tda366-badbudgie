package edu.chl.tda366badbudgie.core;

import java.awt.Color;

import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;

/**
 * Player
 * 
 * Represents the player unit in the game.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class Player extends AbstractUnit {
	
	private static final double moveForce = 0.005;
	private static final double airMoveForce = 0.0005;
	private static final double jumpStrength = 0.04;
	private static final double glideStrength = 0.01;
	private static final double[] flyingStrength = {0, 0, 0, 0, 0, 0, 0, 0.005, 0.005, 0.005, 0.01, 0.01, 0.01, 0.005, 0.005, 0, -0.005, -0.005, -0.002, -0.001};
	
	private int health;
	private double flyingEnergy;
	
	private int wingTimer;
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private boolean isFlying;
	
	public Player(String texId) {
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		health = 100;
		flyingEnergy = 100;
		wingTimer = 0;
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}

	public void moveLeft(boolean down) {
		isMovingLeft = down;
	}

	public void moveRight(boolean down) {
		isMovingRight = down;
	}
	
	public void jump(boolean down) {
		if (down) {
			
			Vector groundContactVector = getGroundContactVector();
			
			if (!groundContactVector.hasZeroLength()) {
				// Instant jump
				applyForce(groundContactVector.normalize().scalarMultiplication(jumpStrength));
			}
			else {
				// Start flying
				isFlying = true;
			}
		}
		else {
			isFlying = false;
		}
		
	}
	
	@Override
	public void updateForces() {
		
		// Player wants to move left
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCCW().scalarMultiplication(moveForce));
			}
			else {
				applyForce(new Vector(-1, 0).scalarMultiplication(airMoveForce));
			}
		}
		
		// Player wants to move right
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(moveForce));
			}
			else {
				applyForce(new Vector(1, 0).scalarMultiplication(airMoveForce));
			}
		}
		
		// Start wing flap
		if (isFlying && flyingEnergy >= 5 && wingTimer == 0) {
			flyingEnergy -= 20;
			wingTimer = 20;
			System.out.println("Fly!");
		}
		
		// Wings flapping
		if (wingTimer > 0) {
			// Flying straight up
			if (!isMovingLeft && !isMovingRight) {
				applyForce(new Vector(0, flyingStrength[wingTimer-1]));
			}
			// Flying to the left
			if (isMovingLeft) {
				applyForce(new Vector(flyingStrength[wingTimer-1] * -0.2, flyingStrength[wingTimer-1] * 0.7));
			}
			// Flying to the right
			if (isMovingRight) {
				applyForce(new Vector(flyingStrength[wingTimer-1] * 0.2, flyingStrength[wingTimer-1] * 0.7));
			}
		}
		
		// Gliding
		if (isFlying) {
			if (getVelocity().getY() < 0) {
				applyForce(new Vector(0, glideStrength * -getVelocity().getY() * 15));
			}
			// Gliding force due to horizontal movement
			applyForce(new Vector(0, glideStrength * Math.abs(getVelocity().getX())));
		}
		
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
			DebugInfoRenderer.getInstance().addDebugLine(getPosition(), getPosition().add(getForce().scalarMultiplication(100)), Color.red);
			DebugInfoRenderer.getInstance().addDebugLine(getPosition(), getPosition().add(getVelocity().scalarMultiplication(70)), Color.blue);
			DebugInfoRenderer.getInstance().addDebugText("Player");
			DebugInfoRenderer.getInstance().addDebugText("x:" + getX() + " y:" + getY());
			DebugInfoRenderer.getInstance().addDebugText("vx:" + getVelocity().getX() + " vy:" + getVelocity().getY());
			DebugInfoRenderer.getInstance().addDebugText("fx:" + getForce().getX() + " fy:" + getForce().getY());
			DebugInfoRenderer.getInstance().addDebugText("FlyingEnergy:" + flyingEnergy);
			DebugInfoRenderer.getInstance().addDebugText("WingTimer:" + wingTimer);
		}
		
	}
	
	
	public void updateState(){
		if(flyingEnergy < 100){
			flyingEnergy += 0.1;
		}
		
		if (wingTimer > 0) {
			wingTimer--;
		}
	}
	
}
