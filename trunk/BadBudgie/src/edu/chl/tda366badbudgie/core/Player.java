package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Player
 * 
 * Represents the player unit in the game.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class Player extends AbstractUnit {
	
	// Default constructor parameters
	private static final Vector PLAYER_SIZE = new Vector(120, 120);
	private static final Sprite PLAYER_SPRITE;
	static {
		List<Animation> animations = new LinkedList<Animation>();
		int[] indices = {4,5,6,7,6};
		animations.add(new Animation("idle",0));
		animations.add(new Animation("run",indices,5));
		PLAYER_SPRITE = new Sprite("budgie", 4, 4, animations);
	}
	private static final Polygon PLAYER_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double PLAYER_FRICTION = 0.9;
	private static final double PLAYER_ELASTICITY = 0.2;
	
	// Movement constants
	private static final double GROUND_MOVE_FORCE = 5.0;
	private static final double AIR_MOVE_FORCE = 0.2;
	private static final double JUMP_FORCE = 15.0;
	private static final double MAXIMUM_WALK_SPEED = 10.0;
	private static final double MAXIMUM_AIR_SPEED = 5.0;
	
	private int invincibilityTimer;
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private boolean jump;
	private int flyingEnergy;
	private int maxFlyingEnergy;
	private boolean flying = false;
	
	private Weapon weapon;
	private Projectile bullet;


	/**
	 * Constructor
	 * 
	 * @param position the players position
	 * @param size
	 * @param sprite
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 */
	public Player(Vector position, Vector size, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, false, sprite, collisionData, friction, elasticity);
		
		setHealth(100);
		setFlyingEnergy(100);
		setMaxFlyingEnergy(150);
		
		addCollisionResponse(CollisionStimulus.INJURER, new GetHurtEffect());
		addCollisionResponse(CollisionStimulus.WALKABLE_GROUND, new StandOnGroundEffect());
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param position the players position
	 */
	public Player(Vector position) {
		this(position, PLAYER_SIZE, PLAYER_SPRITE, PLAYER_COLLISION_DATA, PLAYER_FRICTION, PLAYER_ELASTICITY);
	}
	
	
	
	/*
	 * 
	 * PLAYER ACTIONS AND LOGIC
	 * 
	 */
	

	/**
	 * Called by GameRound when the player wants to move left
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void moveLeft(boolean down) {
		isMovingLeft = down;
	}

	/**
	 * Called by GameRound when the player wants to move right
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void moveRight(boolean down) {
		isMovingRight = down;
	}
	
	/**
	 * Called by GameRound when the player wants to jump or flap his wings.
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void jumpOrFlap(boolean down) {
		
		if (down) {
			if (!getGroundContactVector().hasZeroLength()) {
				jump = true;
			}
			else {
				flying = true;
			}
		}
		else {
			flying = false;
		}
		
	}
	
	
	/**
	 * 
	 * @param x - the x coordinate for the aim, or mouse position if you will.
	 * @param y - the y coordinate for the aim, or mouse position if you will.
	 * @param mouseDown - a boolean that says if the mouse is pressed or not.
	 */
	
	
	public void shootAt(double x, double y, boolean mouseDown) {
		if(mouseDown){
			//TODO: Get rid of hard coded screen width and height
			bullet = new Projectile(new Vector(getX(), getY()), new Vector(x - 800/2, - y + 600/2), this);
			getParent().addGameObject(bullet);
		}
		
	}
	
	
	public void setAim(double x, double y) {
		
		// TODO: Translate the screen coordinates to world coordinates correctly
		Vector v = new Vector(x,y);
		
		Vector dist = new Vector(400,300);
		v = v.subtract(dist);
		
		//v = v.subtract(getPosition());
		
		double rot = Math.atan2(v.getY(),v.getX());
		rot = Math.toDegrees(rot) + 180;

		if(rot > 90 && rot < 270) {
			getSprite().setMirrored(true);
			rot-=180;
			weapon.getSprite().setMirrored(true);

		}
		else {
			getSprite().setMirrored(false);
			weapon.getSprite().setMirrored(false);			
		}

		
		
		weapon.getSprite().setRotation(rot);
	}
	
	@Override
	public void updateForces() {
		
		// Player wants to move left
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength() && -getVelocity().getX() < MAXIMUM_WALK_SPEED) {
				Vector force = 
					getGroundContactVector().perpendicularCCW()
					.scalarMultiplication(GROUND_MOVE_FORCE).add(new Vector(-AIR_MOVE_FORCE, 0));
				applyForce(force);
			}
			else if (-getVelocity().getX() < MAXIMUM_AIR_SPEED) {
				applyForce(new Vector(-AIR_MOVE_FORCE, 0));
			}
		}
		
		// Player wants to move right
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength() && getVelocity().getX() < MAXIMUM_WALK_SPEED) {
				Vector force = 
					getGroundContactVector().perpendicularCW()
					.scalarMultiplication(GROUND_MOVE_FORCE).add(new Vector(AIR_MOVE_FORCE, 0));
				applyForce(force);
			}
			else if (getVelocity().getX() < MAXIMUM_AIR_SPEED) {
				applyForce(new Vector(AIR_MOVE_FORCE, 0));
			}
		}
		
		// Player should jump
		if (jump) {
			applyForce(getGroundContactVector().normalize().project(new Vector(0, 1)).scalarMultiplication(JUMP_FORCE));
			jump = false;
		}
		
		
		
		// Player is flying
		if (flying && flyingEnergy > 0) {
			double yVel = getVelocity().getY();
			applyForce(new Vector(0, yVel < 0 ? 1.5 : (6 - yVel) / 2));
			
			flyingEnergy -= 4;
		}
		
		
		
	}
	
	@Override
	public GameRoundMessage update(){
		
		GameRoundMessage grMessage = GameRoundMessage.NoEvent;
		
		if(getFlyingEnergy() < maxFlyingEnergy){
			setFlyingEnergy(getFlyingEnergy() + 1);
		}
		
		if(this.weapon != null){
			weapon.setPosition(this.getPosition());
		}
		
		if (flyingEnergy <= 0) {
			flying = false;
		}
		
		if(invincibilityTimer > 0) {
			invincibilityTimer--;
		}
		
		getSprite().animate();
		if (isMovingLeft || isMovingRight) {
			//if(isMovingRight)
			//	getSprite().setMirrored(true);
			//else
			//	getSprite().setMirrored(false);
			
			
			getSprite().setAnimation("run");
		} else {
			getSprite().setAnimation("idle");
		}

		if(getHealth() <= 0) {
			grMessage = GameRoundMessage.PlayerDied;
		}
		
		return grMessage;
		
	}



	
	
	/*
	 * 
	 * GETTERS AND SETTERS
	 * 
	 */
	
	
	
	/**
	 * @param flyingEnergy the flyingEnergy to set
	 */
	public void setFlyingEnergy(int flyingEnergy) {
		this.flyingEnergy = flyingEnergy;
	}

	/**
	 * @return the flyingEnergy
	 */
	public int getFlyingEnergy() {
		return flyingEnergy;
	}
	
	/**
	 * @return the maxFlyingEnergy
	 */
	public int getMaxFlyingEnergy() {
		return maxFlyingEnergy;
	}
	
	/**
	 * @param maxFlyingEnergy the maxFlyingEnergy to set
	 */
	public void setMaxFlyingEnergy(int maxFlyingEnergy) {
		this.maxFlyingEnergy = maxFlyingEnergy;
	}
	

	/**
	 * @param invincibilityTimer the invincibilityTimer to set
	 */
	public void setInvincibilityTimer(int invincibilityTimer) {
		this.invincibilityTimer = invincibilityTimer;
	}

	/**
	 * @return the invincibilityTimer
	 */
	public int getInvincibilityTimer() {
		return invincibilityTimer;
	}
	
	public void setWeapon(Weapon wep){
		this.weapon = wep;
	}
	
	public Weapon getActiveWeapon(){
		return weapon;
	}

	@Override
	public Object clone() {
		Player p = new Player(getPosition(), getSize(), getSprite(), getCollisionData(), getFriction(), getElasticity());
		p.setAirResistance(this.getAirResistance());
		return p;
	}


	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.PLAYER);
		return stimuli;
	}
	
	private class GetHurtEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			
			if (invincibilityTimer == 0) {
				setHealth(getHealth() - ((Enemy)other).getDamage()) ;
				invincibilityTimer = 20;
				
				// Bump player away from enemy
				int dir = (int) Math.signum(getX() - other.getX());
				applyForce(new Vector(dir * 6, 6));
			}
		}
	}
	
	private class StandOnGroundEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			
			if (mtv.getY() > 0) {
				// Player has "ground" beneath his feet
				setGroundContactVector(getGroundContactVector().add(
						mtv.normalize().scalarMultiplication(other.getFriction() + 0.000001)
						.scalarDivision(2))); // +0.000001 to avoid a zero-length vector in case of zero friction
				flying = false;
			}
			
		}
	}
	
	
}
