package edu.chl.tda366badbudgie.core.game;

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
	public AbstractUnit(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite, collisionData, friction, elasticity);
		setWeapon(new Weapon(getPosition(), new Sprite("gun1"), this));
		groundContactVector = new Vector();
		weaponOffset = new Vector();
	}
	
	
	@Override
	public GameRoundMessage update() {
		
		getWeapon().setPosition(getPosition().add(getSprite().getMirrored() ? weaponOffset : weaponOffset.mirrorY()));
		getWeapon().update();

		getSprite().animate();
		
		return GameRoundMessage.NO_EVENT;
	}
	
	
	
	/**
	 * 
	 * 
	 * @param x - the x coordinate for the aim, or mouse position if you will.
	 * @param y - the y coordinate for the aim, or mouse position if you will.
	 */
	
	public void shoot() {
		//TODO: Get rid of hard coded screen width and height
//		Projectile bullet = new Projectile(new Vector(getX(), getY()), new Vector(x - 800/2, - y + 600/2), this);
//		getParent().addGameObject(bullet);
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
	 * @param isAIControlled
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

	public void setWeapon(Weapon wep){
		this.weapon = wep;
		addChild(wep);
	}
	
	public Weapon getWeapon(){
		return weapon;
	}


	protected Vector getWeaponOffset() {
		return weaponOffset;
	}


	protected void setWeaponOffset(Vector weaponOffset) {
		this.weaponOffset = weaponOffset;
	}
	
	@Override
	public void setScale(double scale) {
		super.setScale(scale);
	}
	
}
