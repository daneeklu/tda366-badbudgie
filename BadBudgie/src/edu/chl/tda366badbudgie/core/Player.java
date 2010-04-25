package edu.chl.tda366badbudgie.core;

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
	private static final double airMoveForce = 0.001;
	private static final double jumpStrength = 0.04;
	private static final double flyingStrength = 0.03;
	
	private int health;
	private double flyingEnergy;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	
	public Player(String texId) {
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		health = 100;
		flyingEnergy = 100;
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
			System.out.println("Current Energy: " + flyingEnergy);
			Vector groundContactVector = getGroundContactVector();
			Vector vector = getPosition();
			if (!groundContactVector.hasZeroLength()) {
				applyForce(groundContactVector.normalize().scalarMultiplication(jumpStrength));
			}
			else{
				if(flyingEnergy > 30){
					flyingEnergy -= 30;
					vector = new Vector(0, vector.getY());
					applyForce(vector.scalarMultiplication(flyingStrength));
				}
			}	
		}
	}
	
	public double getEnergy(){
		return flyingEnergy;
	}
	
	public void setEnergy(double i){
		flyingEnergy = i;
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
	
	
	public void updateState(){
		if(flyingEnergy < 100){
			flyingEnergy += 0.1;
		}
	}
	
}
