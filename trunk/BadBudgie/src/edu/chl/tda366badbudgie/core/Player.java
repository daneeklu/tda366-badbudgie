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
	
	private static final double MOVE_FORCE = 2.0;
	private static final double AIR_MOVE_FORCE = 0.2;
	private static final double JUMP_FORCE = 36.0;
	private static final double FLYING_FORCE = 0.75;
	
	private int health;
	private int invincibilityTimer;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;

	private int flyingEnergy;
	private int maxFlyingEnergy;
	private boolean flying = false;
	
	private Weapon wep;
	private Projectile bullet;


	/**
	 * Constructor
	 * 
	 * @param texId the texture id of the player.
	 */
	public Player(String texId) {
		setFriction(0.5);
		setElasticity(0.2);
		setMass(1);
		health = 100;
		setFlyingEnergy(100);
		setMaxFlyingEnergy(100);
		
		List<Animation> animations = new LinkedList<Animation>();
		
		animations.add(new Animation("idle",0));
		
		int[] indices = {4,5,6,7,6};
		
		animations.add(new Animation("idle",0));
		animations.add(new Animation("run",indices,5));
		
		sprite = new Sprite(texId, 4, 4, animations);
		//sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
		
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
			
			Vector groundContactVector = getGroundContactVector();
			
			if (!groundContactVector.hasZeroLength()) {
				// Instant jump
				applyForce(groundContactVector.project(new Vector(0, 1)).scalarMultiplication(JUMP_FORCE));
				// If we should follow Newtons 2nd law:
				//getGroundContactObject().applyForce(groundContactVector.normalize().scalarMultiplication(-JUMP_FORCE));
			}
			else {
				// fly
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
	
	
	public void shoot(double x, double y, boolean mouseDown) {
		//shooting = mouseDown;
		if(mouseDown){
			bullet = new Projectile("bullet1", x, y, this);
			bullet.setX(this.getX());
			bullet.setY(this.getY());
			LinkedList<Vector> pcd = new LinkedList<Vector>();
			pcd.add(new Vector(-20, -20));
			pcd.add(new Vector(20, -20));
			pcd.add(new Vector(20, 20));
			pcd.add(new Vector(-20, 20));
			bullet.setCollisionData(new Polygon(pcd));
			getParent().addGameObject(bullet);	
		}
		
	}
	
	public Vector getProjectileForce(double x, double y){
		int resWidth = 800;
		int resHeight = -600;
		
		if(x < resWidth/2){
			x = x-resWidth/2;
		}
		else if(x > resWidth/2){
			x = x-resWidth/2;
		}
		
		if(y > resHeight/2){
			y = y-resHeight/2;
		}
		else if(y < resHeight/2){
			y = y-resHeight/2;
		}		
		return new Vector(x, y);
	}

	
	@Override
	public void updateForces() {
		
		// Player wants to move left
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength()) {
				Vector force = getGroundContactVector().perpendicularCCW().scalarMultiplication(MOVE_FORCE);
				applyForce(force);
			}
			else {
				applyForce(new Vector(-1, 0).scalarMultiplication(AIR_MOVE_FORCE));
			}
		}
		
		// Player wants to move right
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength()) {
				Vector force = getGroundContactVector().perpendicularCW().scalarMultiplication(MOVE_FORCE);
				applyForce(force);
			}
			else {
				applyForce(new Vector(1, 0).scalarMultiplication(AIR_MOVE_FORCE));
			}
		}
		
		// Player is flying
		if (flying && flyingEnergy > 0) {
			double yVel = getVelocity().getY();
			applyForce(new Vector(0, yVel < 0 ? 1.5 : (6 - yVel) / 2));
			
			flyingEnergy -= 2;
		}
		
		
		
	}
	
	@Override
	public String update(){
		
		String gameEvent = "";
		
		if(getFlyingEnergy() < maxFlyingEnergy){
			setFlyingEnergy(getFlyingEnergy() + 1);
		}
		
		if(this.wep != null){
			wep.setPosition(this.getPosition());
		}
		
		if(health <= 0) {
			gameEvent = "playerdied";
		}
		
		if (flyingEnergy <= 0) {
			flying = false;
		}
		
		if(invincibilityTimer > 0) {
			invincibilityTimer--;
		}
		
		sprite.animate();
		if (isMovingLeft || isMovingRight) {
			sprite.setAnimation("run");
		} else {
			sprite.setAnimation("idle");
		}
		
		return gameEvent;
		
	}


	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		
		if (other instanceof TerrainSection) {
			// Set the ground contact vector
			if (mtv.getY() > 0) {
				// Player has ground beneath his feet
				// Set ground contact vector to mean of previous and new contact vector, 
				// to allow multiple contacts in one loop
				this.setGroundContactVector(this.getGroundContactVector().add(
						mtv.normalize().scalarDivision(2)));
				this.setGroundContactObject(other);
			}
			flying = false;
		}
		
		// Enemy hurts player
		if (invincibilityTimer == 0 && other instanceof Enemy) {
			health -= ((Enemy)other).getDamage();
			invincibilityTimer = 20;
			
			// Bump player away from enemy
			int dir = (int) Math.signum(getX() - other.getX());
			applyForce(new Vector(dir * 6, 6));
		}
		
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
	 * Get the players health
	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Set the players health
	 * 
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
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
		this.wep = wep;
	}
	
	public Weapon getActiveWeapon(){
		return wep;
	}


	
}
