package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * A class describing a weapon
 * 
 * @author lumbo
 *
 */

public class Weapon extends AbstractItem {
	
	// Default constructor parameters
	private static final Vector WEAPON_SIZE = new Vector(120, 120);
	private static final Polygon WEAPON_COLLISION_DATA = 
		AbstractCollidable.defaultCollisionData;
	private static final double WEAPON_FRICTION = 0.5;
	private static final double WEAPON_ELASTICITY = 0.2;
	private static final int WEAPON_DAMAGE = 10;
	private static final int WEAPON_COOLDOWN = 10;
	
	private int cooldown;
	private int damage;
	private double nozzleSpeed;
	private AbstractGameObject owner;
	private Vector nozzleOffset;
	
	private double aimX;
	private double aimY;
	private int cooldownTimer = 0;
	
	/**
	 * Contructor 
	 * 
	 * @param position
	 * @param damage
	 * @param cooldown
	 * @param size
	 * @param sprite
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 * @param owner
	 */
	public Weapon(Vector position, int damage, int cooldown, Vector size, 
			Sprite sprite, Polygon collisionData, double friction, 
			double elasticity, AbstractGameObject owner) {
		super(position, size, false, sprite, collisionData, friction, 
				elasticity);
		setDamage(damage);
		setCooldown(cooldown);
		setOwner(owner);
		setNozzleOffset(new Vector());
		setNozzleSpeed(15);
	}
	
	/**
	 * Constructor
	 * 
	 * @param position
	 * @param sprite
	 * @param owner
	 */
	public Weapon(Vector position, Sprite sprite, AbstractGameObject owner) {
		this(position, WEAPON_DAMAGE, WEAPON_COOLDOWN, WEAPON_SIZE, sprite, 
				WEAPON_COLLISION_DATA, WEAPON_FRICTION, WEAPON_ELASTICITY, 
				owner);
	}
	
	/**
	 * Aims the weapon towards the given world coordinates.
	 * 
	 * @param x - The x Coordinate for the aiming
	 * @param y - The y Coordinate for the aiming
	 */
	public void setAim(double x, double y) {
		double dx = x - getX();
		double dy = y - getY();
		
		double rotation = Math.toDegrees(Math.atan2(dx, dy)) + 90;
		
		if (rotation > 90 && rotation < 270) {
			getSprite().setMirrored(true);
			rotation -= 180;
		}
		else
			getSprite().setMirrored(false);
		
		getSprite().setRotation(rotation);
		
		aimX = dx;
		aimY = dy;
	}
	
	/**
	 * Makes the weapon shoot.
	 */
	public void shoot() {
		if (cooldownTimer == 0) {
			owner.getLevel().scheduleForAddition(
					new Projectile(
							getPosition().add(getSprite().getMirrored() 
									? getNozzleOffset()
										.rotate(getSprite().getRotation())
									: getNozzleOffset()
										.mirrorY()
										.rotate(getSprite().getRotation())), 
							new Vector(aimX, aimY), 
							getNozzleSpeed(), 
							getOwner()));
			cooldownTimer = getCooldown();
		}
	}

	/**
	 * Sets the damage the weapon delivers.
	 * @param damage the damage
	 */
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	/**
	 * Gets the damage the weapon delivers.
	 * @return the damage
	 */
	public int getDamage(){
		return damage;
	}

	/**
	 * Set the owner of the weapon.
	 * @param owner the owner
	 */
	public void setOwner(AbstractGameObject owner) {
		this.owner = owner;
	}

	/**
	 * Returns the owner of the weapon.
	 * @return the owner
	 */
	public AbstractGameObject getOwner() {
		return owner;
	}
	
	/**
	 * Returns the cooldown time (in gameframes) of the weapon.
	 * @return the cooldown time
	 */
	public int getCooldown() {
		return cooldown;
	}

	/**
	 * Sets the cooldown time (gameframes) of the weapon. 
	 * @param cooldown teh cooldown
	 */
	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	/**
	 * Sets the nozzle offset of the weapon. This is the vector from the 
	 * weapon's position to its nozzle when facing left.
	 * @param nozzleOffset
	 */
	public void setNozzleOffset(Vector nozzleOffset) {
		this.nozzleOffset = nozzleOffset;
	}

	/**
	 * Returns the nozzle offset of the weapon. This is the vector from the 
	 * weapon's position to its nozzle when facing left.
	 * @return the offset
	 */
	public Vector getNozzleOffset() {
		return nozzleOffset.scalarMultiplication(getScale());
	}

	/**
	 * Sets the exit speed of the projectile this weapon shoots.
	 * @param nozzleSpeed
	 */
	public void setNozzleSpeed(double nozzleSpeed) {
		this.nozzleSpeed = nozzleSpeed;
	}
	
	/**
	 * Returns the exit speed of the projectile this weapon shoots.
	 * @return the speed
	 */
	public double getNozzleSpeed() {
		return nozzleSpeed;
	}
	
	@Override
	public GameRoundMessage update() {
		
		if (cooldownTimer > 0)
			cooldownTimer--;
		
		return GameRoundMessage.NO_EVENT;
	}
	
	@Override
	public Weapon clone() {
		return (Weapon) super.clone(); 
	}
	
}
