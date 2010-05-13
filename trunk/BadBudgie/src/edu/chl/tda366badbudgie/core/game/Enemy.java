package edu.chl.tda366badbudgie.core.game;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Animation;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
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
	
	// Constants
	private static final double GROUND_MOVE_FORCE = 1.5;
	private static final double MAXIMUM_WALK_SPEED = 5.0;
	
	
	private int meleeDamage;
	private double sightDistance = 400;
	
	
	private int attackTimer = 0;
	
	/**
	 * Constructor
	 * 
	 * @param position
	 * @param size
	 * @param sprite
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 * @param meleeDamage
	 * @param direction
	 */
	public Enemy(Vector position, Vector size, Sprite sprite, Polygon collisionData, double friction, double elasticity, int meleeDamage, int direction) {
		super(position, size, false, sprite, collisionData, friction, elasticity);

		setHealth(100);
		setMeleeDamage(meleeDamage);
		setDirection(direction);
		getSprite().setAnimation("run");
		setAIControlled(true);
		getWeapon().setSprite(new Sprite("gun"));
		getWeapon().setCooldown(10);
		
		addPhysicalCollision(TerrainSection.class);
		addPhysicalCollision(Player.class);
		addPhysicalCollision(Obstacle.class);
	}
	
	/**
	 * Convenience constructor with less important parameters left out.
	 * 
	 * @param position the enemy's position
	 */
	public Enemy(Vector position) {
		this(position, ENEMY_SIZE, ENEMY_SPRITE, ENEMY_COLLISION_DATA, ENEMY_FRICTION, ENEMY_ELASTICITY, ENEMY_DAMAGE, ENEMY_DIRECTION);
	}

	public void aimAtPlayer() {
		Player p = getParent().getPlayer();
		double pdx = p.getX() - getX();
		setAim(p.getX(), p.getY() + Math.abs(pdx/6));
		setDirection( (p.getX() - getX() > 0) ? 1 : -1);
	}
	
	@Override
	public void updateForces() {

		// Walking
		if (getAttackTimer() == 0 && !getGroundContactVector().hasZeroLength() 
				&& Math.abs(getVelocity().getX()) < MAXIMUM_WALK_SPEED) 
			applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication((GROUND_MOVE_FORCE) * Math.signum(getDirection())));

	}
	
	@Override
	public GameRoundMessage update(){
		
		// Run update on superclass and return its value if it has an event.
		GameRoundMessage superMessage = super.update();
		if (superMessage != GameRoundMessage.NO_EVENT)
			return superMessage;
		
		if (getAttackTimer() > 0) {
			setAttackTimer(getAttackTimer() - 1);
			getSprite().setAnimation("idle");
		}
		else {
			getWeapon().getSprite().setRotation(0);
			getWeapon().getSprite().setMirrored(getDirection() > 0);
			getSprite().setAnimation("run");
		}
		
		if (getHealth() <= 0)
			return GameRoundMessage.REMOVE_OBJECT;
		
		return GameRoundMessage.NO_EVENT;
	}


	/**
	 * Sets the amount of damage this enemy does to the player.
	 * 
	 * @param damage the damage
	 */
	public void setMeleeDamage(int damage) {
		this.meleeDamage = damage;
	}

	/**
	 * Gets the amount of damage this enemy does to the player.
	 * 
	 * @return the damage
	 */
	public int getMeleeDamage() {
		return meleeDamage;
	}
	
	public void setSightDistance(double sightDistance) {
		this.sightDistance = sightDistance;
	}

	public double getSightDistance() {
		return sightDistance;
	}
	
	public void setAttackTimer(int attackTimer) {
		this.attackTimer = attackTimer;
	}

	public int getAttackTimer() {
		return attackTimer;
	}
	
	/**
	 * Sets the enemy's direction.
	 * @param direction 1 if heading right, -1 if heading left
	 */
	@Override
	public void setDirection(int direction) {
		super.setDirection(direction);
		if(direction == 1) 
			getSprite().setMirrored(true);
		else
			getSprite().setMirrored(false);
	}
	
	@Override
	public Enemy clone() {
		return (Enemy) super.clone();
	}
	
	
	@Override
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
		// TODO: Explain here why class check is bad, but OK in this case.
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();
		
		if (otherClass.equals(TerrainSection.class)) {
			if (mtv.getY() > 0) {
				// Player has "ground" beneath his feet
				setGroundContactVector(getGroundContactVector().add(
						mtv.normalize().scalarMultiplication(other.getFriction() + 0.000001)
						.scalarDivision(2))); // +0.000001 to avoid a zero-length vector in case of zero friction
			}
		}
		
		if (otherClass.equals(Projectile.class)) {
			Projectile p = (Projectile) other;
			if (!p.getOwner().getClass().equals(getClass()) && p.isLive()) {
				setHealth(getHealth() - p.getDamage()) ;
				
				applyForce(p.getVelocity().scalarMultiplication(p.getMass()/getMass()));
				p.setHasCollided(true);
			}
		}
		
	}



	
	
	
}
