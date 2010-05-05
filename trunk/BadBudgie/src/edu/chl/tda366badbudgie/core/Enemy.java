package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
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
	
	// Default constructor parameters
	private static final Vector ENEMY_SIZE = new Vector(80, 80);
	private static final Sprite ENEMY_SPRITE = new Sprite("enemy");
	private static final Polygon ENEMY_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double ENEMY_FRICTION = 0.5;
	private static final double ENEMY_ELASTICITY = 0.2;
	private static final int ENEMY_DAMAGE = 10;
	private static final int ENEMY_DIRECTION = 1;
	
	// Movement constants
	private static final double MOVE_FORCE = 0.9;
	
	
	private int damage;
	private int direction = 0;
	
	/**
	 * Constructor
	 * 
	 * @param position
	 * @param size
	 * @param sprite
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 * @param damage
	 * @param direction
	 */
	public Enemy(Vector position, Vector size, Sprite sprite, Polygon collisionData, double friction, double elasticity, int damage, int direction) {
		super(position, size, false, sprite, collisionData, friction, elasticity);

		setHealth(100);
		setDamage(damage);
		setDirection(direction);
	}
	
	/**
	 * Constructor
	 * 
	 * @param position the enemy's position
	 */
	public Enemy(Vector position) {
		this(position, ENEMY_SIZE, ENEMY_SPRITE, ENEMY_COLLISION_DATA, ENEMY_FRICTION, ENEMY_ELASTICITY, ENEMY_DAMAGE, ENEMY_DIRECTION);
	}
	
	
	@Override
	public void updateForces() {
		
		if (!getGroundContactVector().hasZeroLength()) 
			applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(MOVE_FORCE * Math.signum(direction)));

	}
	
	@Override
	public GameRoundMessage update(){
		if (getHealth() <= 0)
			return GameRoundMessage.RemoveObject;
		
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
			//this.setGroundContactObject(other);
		}
		else if (other instanceof Player) {
			setDirection(-1 * getDirection());
		}
		else if (other instanceof Projectile) {
			setHealth(getHealth() - ((Projectile) other).getDamage());
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
