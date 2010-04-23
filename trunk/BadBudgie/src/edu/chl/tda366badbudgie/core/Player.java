package edu.chl.tda366badbudgie.core;

public class Player extends AbstractCollidable {
	
	private static final double moveForce = 0.001;
	private static final double jumpStrength = 0.04;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	
	public Player() {
		setFriction(10);
		setElasticity(0.5);
		setMass(1);
	}

	public void moveLeft(boolean down) {
		isMovingLeft = down;
	}

	public void moveRight(boolean down) {
		isMovingRight = down;
	}
	
	public void jump(boolean down) {
		if (down) {
			applyForce(new Vector(0, jumpStrength));
		}
	}
	
	
	@Override
	public void updateForces() {
		if (isMovingLeft) {
			applyForce(new Vector(-moveForce, 0));
		}
		if (isMovingRight) {
			applyForce(new Vector(moveForce, 0));
		}
	}


	
}
