package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.ai.impl.SimpleAI;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Enemy
 * 
 * Class representing enemy units in the game.
 * 
 * @author kvarfordt
 *
 */
public class Enemy extends AbstractUnit {
	private static final double MOVE_FORCE = 0.9;
	private static final double JUMP_FORCE = 6.0;
	
	private int health;
	private int damage;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private SimpleAI ai;
	
	
	/**
	 * Constructor
	 * 
	 * @param sprite the texture id of the enemy.
	 */
	public Enemy(Sprite sprite) {
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		health = 100;
		setDamage(10);
		this.sprite = sprite;
		ai = new SimpleAI(this);
		
	}


	/**
	 * @param down true if the key was pressed, false if released
	 */
	public void moveLeft(boolean down) {
		isMovingLeft = down;
		
	}

	/**
	 * @param down true if the key was pressed, false if released
	 */
	public void moveRight(boolean down) {
		isMovingRight = down;
	}
	
	/**
	 * @param down true if the key was pressed, false if released
	 */
	public void jumpOrFlap(boolean down) {
		
	}
	
	public void glide(boolean down) {

	}
	
	public String getDirection(){
		if(isMovingLeft){
			return "left";
		}
		else if(isMovingRight){
			return "right";
		}
		else{
			return null;
		}
	}

	
	@Override
	public void updateForces() {
		
		// Player wants to move left
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCCW().scalarMultiplication(MOVE_FORCE));
			}
		}
		
		// Player wants to move right
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(MOVE_FORCE));
			}
		}
	}
	
	@Override
	public void updateState(){
		ai.initAI();
	}


	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// Set the ground contact vector
		if (mtv.getY() > 0) {
			// Player has ground beneath his feet
			// Set ground contact vector to mean of previous and new contact vector, 
			// to allow multiple contacts in one loop
			this.setGroundContactVector(this.getGroundContactVector().add(
					mtv.normalize().scalarDivision(2)));
			this.setGroundContactObject(other);
		}
		
		
	}


	/**
	 * Sets the amount of damage this enemy does to the player.
	 * 
	 * @param damage the damage
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}


	/**
	 * Gets the amount of damage this enemy does to the player.
	 * 
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}
}
