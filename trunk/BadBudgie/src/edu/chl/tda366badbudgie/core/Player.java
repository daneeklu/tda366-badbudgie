package edu.chl.tda366badbudgie.core;

public class Player extends AbstractUnit {
	
	private static final double moveForce = 0.005;
	private static final double airMoveForce = 0.001;
	private static final double jumpStrength = 0.04;
	private static double energy = 100;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	
	public Player() {
		setFriction(0.5);
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
			System.out.println(energy);
			Vector groundContactVector = getGroundContactVector();
			if (!groundContactVector.hasZeroLength()) {
				if(energy > 30){
					energy -= 30;
					applyForce(groundContactVector.normalize().scalarMultiplication(jumpStrength));
				}
			}
			
		}
	}
	
	public double getEnergy(){
		return energy;
	}
	
	public void setEnergy(double i){
		energy = i;
	}
	
	@Override
	public void updateForces() {
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCCW().scalarMultiplication(moveForce));
			}
			else {
				applyForce(new Vector(-1, 0).scalarMultiplication(airMoveForce));
			}
		}
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(moveForce));
			}
			else {
				applyForce(new Vector(1, 0).scalarMultiplication(airMoveForce));
			}
		}
		
		setGroundContactVector(new Vector(0, 0));
	}


	
}
