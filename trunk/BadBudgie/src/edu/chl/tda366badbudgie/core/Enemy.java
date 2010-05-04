package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Vector;

/**
 * Enemy
 * 
 * Class representing enemy units in the game.
 * 
 * @author 
 *
 */
public class Enemy extends AbstractUnit {
	private static final double MOVE_FORCE = 0.9;
	
	private int health;
	private int damage;
	
	
	private int direction = 0;
	
	
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
		direction = -1;
	}
	
	
	@Override
	public void updateForces() {
		
		
		if (!getGroundContactVector().hasZeroLength()) 
			applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(MOVE_FORCE * direction));


	}
	
	@Override
	public GameRoundMessage update(){
		//ai.initAI();
		return GameRoundMessage.NoEvent;
	}


	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// Set the ground contact vector
		if (mtv.getY() > 0 && other instanceof TerrainSection) {
			// Player has ground beneath his feet
			// Set ground contact vector to mean of previous and new contact vector, 
			// to allow multiple contacts in one loop
			this.setGroundContactVector(this.getGroundContactVector().add(
					mtv.normalize().scalarDivision(2)));
			this.setGroundContactObject(other);
		}
		else if (other instanceof Player) {
			setDirection(-1 * getDirection());
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

	/**
	 * Gets the enemy's direction.
	 * @return 1 if heading right, -1 if heading left
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Sets the enemy's direction.
	 * @param direction 1 if heading right, -1 if heading left
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
}
