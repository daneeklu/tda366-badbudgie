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
	
	// Movement constants
	private static final double GROUND_MOVE_FORCE = 1.5;
	private static final double MAXIMUM_WALK_SPEED = 5.0;
	
	private int damage;
	
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
		setAIControlled(true);
		
		addPhysicalCollision("TerrainSection");
		addPhysicalCollision("Player");
		addPhysicalCollision("Obstacle");
		addCollisionResponse(CollisionStimulus.IMPACT, new GetHurtEffect());
		addCollisionResponse(CollisionStimulus.WALKABLE_GROUND, new StandOnGroundEffect());
		addCollisionResponse(CollisionStimulus.PLAYER, new TurnAroundEffect());
	}
	
	/**
	 * Convinience constructor with less important parameters left out.
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
			applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication((GROUND_MOVE_FORCE) * Math.signum(getDirection())));

	}
	
	@Override
	public GameRoundMessage update(){
		
		getSprite().animate();
		
		
		
		if (getHealth() <= 0)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
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
	public Object clone() {
		Enemy e = new Enemy(getPosition(), getSize(), getSprite(), getCollisionData(), getFriction(), getElasticity(), getDamage(), getDirection());
		e.setAirResistance(getAirResistance());
		return e;
	}
	
	
	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.INJURER);
		return stimuli;
	}
	
	private class GetHurtEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			setHealth(getHealth() - ((Projectile) other).getDamage());
		}
	}
	
	private class StandOnGroundEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			
			if (mtv.getY() > 0) {
				// Enemy has "ground" beneath his feet
				setGroundContactVector(getGroundContactVector().add(
						mtv.normalize().scalarMultiplication(other.getFriction() + 0.000001)
						.scalarDivision(2))); // +0.000001 to avoid a zero-length vector in case of zero friction
			}
			
		}
	}
	
	private class TurnAroundEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			setDirection(-1 * getDirection());
		}
	}
	
	
	
}
