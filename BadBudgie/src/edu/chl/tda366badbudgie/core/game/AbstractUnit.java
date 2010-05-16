package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractUnit
 * 
 * Abstract class representing a unit in the game, such as an enemy or the
 * player.
 * 
 * @author kvarfordt
 * 
 */
public abstract class AbstractUnit extends AbstractCollidable {

	private boolean isAIControlled;
	private Weapon weapon;
	private Vector weaponOffset;
	
	private Vector groundContactVector;
	private int direction = 0;
	private int health;
	private int invincibilityTimer;
	
	
	/**
	 * Constructs a new AbstractUnit instance.
	 */
	public AbstractUnit(Vector position, Vector size, boolean stationary, 
			Sprite sprite, Polygon collisionData, double friction, 
			double elasticity) {
		super(position, size, stationary, sprite, collisionData, friction, 
				elasticity);
		setWeapon(new Weapon(getPosition(), new Sprite("gun1"), this));
		groundContactVector = new Vector();
		weaponOffset = new Vector();
	}
	
	
	@Override
	public GameRoundMessage update() {
		
		getWeapon().setPosition(getPosition().add(getSprite().getMirrored() 
				? getWeaponOffset() 
				: getWeaponOffset().mirrorY()));
		getWeapon().update();

		getSprite().animate();
		
		return GameRoundMessage.NO_EVENT;
	}
	
	
	/**
	 * Shoot a bullet with the current weapon
	 */
	public void shoot() {
		if (getWeapon() != null)
			getWeapon().shoot();
	}
	
	/**
	 * Makes the unit aim at the given x and y position.
	 * 
	 * @param x
	 * @param y
	 */
	public void setAim(double x, double y) {
		getWeapon().setAim(x, y);
	}
	

	/**
	 * Set the duration for the invincibility timer:
	 * for instance if a unit was hit, it will remain
	 * invincible for a short time.
	 * 
	 * @param invincibilityTimer the duration of the timer, in frames
	 */
	public void setInvincibilityTimer(int invincibilityTimer) {
		this.invincibilityTimer = invincibilityTimer;
	}

	/**
	 * Get the remaining duration of the invincibility timer
	 * 
	 * @return the remaining time (in frames) the unit is invincible
	 */
	public int getInvincibilityTimer() {
		return invincibilityTimer;
	}
	
	/**
	 * Sets the ground contact vector for the unit.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @param groundContactVector
	 *            the vector of the ground contact
	 */
	public void setGroundContactVector(Vector groundContactVector) {
		this.groundContactVector = groundContactVector;
	}

	/**
	 * Returns the ground contact normal of the unit. If the unit does not have
	 * ground contact, a vector of length zero will be returned.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @return the ground contact vector
	 */
	public Vector getGroundContactVector() {
		return groundContactVector;
	}

	/**
	 * Sets the health of the unit.
	 * 
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Gets the health of the unit.
	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Sets the direction of the unit. -1 means left, 1 means right.
	 * @param direction the direction
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}

	/**
	 * Returns the direction of the unit. -1 means left, 1 means right.
	 * @return the direction
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Controls whether the unit is AI-controlled or not.
	 * @param isAIControlled true if the unit should be AI-controlled.
	 */
	public void setAIControlled(boolean isAIControlled) {
		this.isAIControlled = isAIControlled;
	}

	/**
	 * Returns true if the unit is AI-controlled.
	 * 
	 * @return true if the unit is AI-controlled
	 */
	public boolean isAIControlled() {
		return isAIControlled;
	}

	/**
	 * Set the weapon for the unit
	 * 
	 * @param weapon the weapon to set.
	 */
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
		addChild(weapon);
	}
	
	/**
	 * Get the current weapon for the unit
	 * 
	 * @return the weapon
	 */
	public Weapon getWeapon(){
		return weapon;
	}

	/**
	 * Get the offset of the weapon, relative to the
	 * unit's position
	 * @return the offset vector
	 */
	protected Vector getWeaponOffset() {
		return weaponOffset.scalarMultiplication(getScale());
	}

	/**
	 * Set the offset of the weapon, relative to the
	 * unit's position
	 * 
	 * @param weaponOffset the offset vector
	 */
	protected void setWeaponOffset(Vector weaponOffset) {
		this.weaponOffset = weaponOffset;
	}
}
