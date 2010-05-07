package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

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
	private static final Vector ENEMY_SIZE = new Vector(120, 120);
	private static final Sprite ENEMY_SPRITE;
	private static final Polygon ENEMY_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double ENEMY_FRICTION = 0.5;
	private static final double ENEMY_ELASTICITY = 0.2;
	private static final int ENEMY_DAMAGE = 10;
	private static final int ENEMY_DIRECTION = 1;
	
	static {
		List<Animation> animations = new LinkedList<Animation>();
		int[] indices = {4,5,6,7};
		animations.add(new Animation("idle", 0));
		animations.add(new Animation("run", indices, 5));
		ENEMY_SPRITE = new Sprite("enemy", 4, 4, animations);
	}
	
	// Movement constants
	private static final double GROUND_MOVE_FORCE = 1.5;
	private static final double MAXIMUM_WALK_SPEED = 5.0;
	
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
		getSprite().setAnimation("run");
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

		// Walking
		if (!getGroundContactVector().hasZeroLength() 
				&& Math.abs(getVelocity().getX()) < MAXIMUM_WALK_SPEED) 
			applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication((GROUND_MOVE_FORCE) * Math.signum(direction)));

	}
	
	@Override
	public GameRoundMessage update(){
		
		getSprite().animate();
		

		
		if (getHealth() <= 0)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
	}


	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// Set the ground contact vector
		if (mtv.getY() > 0) {
			// Enemy has ground beneath his feet
			// Set ground contact vector to mean of previous and new contact vector, 
			// to allow multiple contacts in one loop
			this.setGroundContactVector(this.getGroundContactVector().add(
					mtv.normalize().scalarMultiplication(other.getFriction() + 0.000001)
					.scalarDivision(2))); // +0.000001 to avoid a zero-length vector
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
		if(direction == 1) 
			getSprite().setMirrored(true);
		else
			getSprite().setMirrored(false);
	}

	@Override
	public Object clone() {
		return new Enemy(getPosition(), getSize(), getSprite(), getCollisionData(), getFriction(), getElasticity(), getDamage(), getDirection());
	}
	
}
