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
	
	private static final double MOVE_FORCE = 2.0;
	private static final double AIR_MOVE_FORCE = 0.2;
	private static final double JUMP_FORCE = 16.0;
	private static final double GLIDE_FORCE_RATIO = 0.15;
	private static final double[] FLYING_FORCE = {0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 2, 1, 0.5, 0.2, 0.1, 0.1};
	
	private int health;
	private int invincibilityTimer;
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private boolean isGliding;
	private int wingTimer;
	private double flyingEnergy;
	private int maxFlyingEnergy;
	
	private GameRound gameRound;
	private Weapon wep;


	/**
	 * Constructor
	 * @param gameRound 
	 * 
	 * @param texId the texture id of the player.
	 */
	public Player(GameRound gameRound, String texId) {
		this.gameRound = gameRound;
		setFriction(0.5);
		setElasticity(0.2);
		setMass(1);
		health = 100;
		setFlyingEnergy(100);
		setMaxFlyingEnergy(300);
		setWingTimer(0);
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
		
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
				applyForce(groundContactVector.normalize().scalarMultiplication(JUMP_FORCE));
				getGroundContactObject().applyForce(groundContactVector.normalize().scalarMultiplication(-JUMP_FORCE));
			}
			else {
				// Start wing flap
				if (getFlyingEnergy() >= 5 && getWingTimer() == 0) {
					setWingTimer(20);
				}
			}
		}
		else {
			if (getWingTimer() > 11)
				setWingTimer(9);
		}
		
	}
	
	public void glide(boolean down) {
		if (down) {
			isGliding = true;
		}
		else {
			isGliding = false;
		}
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
		
		
		// Wings flapping
		if (getWingTimer() > 0 && getFlyingEnergy() > 0) {
			// Flying straight up
			if (!isMovingLeft && !isMovingRight) {
				applyForce(new Vector(0, FLYING_FORCE[getWingTimer()-1]));
			}
			// Flying to the left
			if (isMovingLeft) {
				applyForce(new Vector(FLYING_FORCE[getWingTimer()-1] * -0.2, FLYING_FORCE[getWingTimer()-1] * 0.7));
			}
			// Flying to the right
			if (isMovingRight) {
				applyForce(new Vector(FLYING_FORCE[getWingTimer()-1] * 0.2, FLYING_FORCE[getWingTimer()-1] * 0.7));
			}

			setFlyingEnergy(getFlyingEnergy() - (FLYING_FORCE[getWingTimer()-1] * 6));
		}
		
		// Gliding
		if (isGliding) {
			if (getVelocity().getY() < 0) {
				applyForce(new Vector(0, GLIDE_FORCE_RATIO * -getVelocity().getY()));
			}
			// Gliding force due to horizontal movement
			applyForce(new Vector(0, GLIDE_FORCE_RATIO / 15 * Math.abs(getVelocity().getX())));
		}
		

		
	}
	
	@Override
	public void updateState(){
		if(getFlyingEnergy() < maxFlyingEnergy){
			setFlyingEnergy(getFlyingEnergy() + 1);
		}
		
		if (getWingTimer() > 0) {
			setWingTimer(getWingTimer() - 1);
		}
		
		if(this.wep != null){
			wep.setPosition(this.getPosition());
			//System.out.println("Vapen: " + wep.getPosition() + " Spelare: " + this.getPosition());
		}
		
		if(health <= 0) {
			gameRound.playerDied();
		}
		
		if(invincibilityTimer > 0) {
			invincibilityTimer--;
		}
		
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
	public void setFlyingEnergy(double flyingEnergy) {
		this.flyingEnergy = flyingEnergy;
	}

	/**
	 * @return the flyingEnergy
	 */
	public double getFlyingEnergy() {
		return flyingEnergy;
	}

	/**
	 * @param wingTimer the wingTimer to set
	 */
	public void setWingTimer(int wingTimer) {
		this.wingTimer = wingTimer;
	}

	/**
	 * @return the wingTimer
	 */
	public int getWingTimer() {
		return wingTimer;
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
	
}
