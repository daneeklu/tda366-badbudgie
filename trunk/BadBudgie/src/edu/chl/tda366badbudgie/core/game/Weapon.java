package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

public class Weapon extends AbstractItem {
	
	// Default constructor parameters
	private static final Vector WEAPON_SIZE = new Vector(120, 120);
	//private static final Sprite WEAPON_SPRITE = new Sprite("gun1");
	private static final Polygon WEAPON_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double WEAPON_FRICTION = 0.5;
	private static final double WEAPON_ELASTICITY = 0.2;
	private static final int WEAPON_DAMAGE = 10;
	private static final int WEAPON_COOLDOWN = 10;
	
	private int cooldown;
	private int damage;
	private String weaponId;
	private AbstractGameObject owner;
	private double aimX;
	private double aimY;
	
	private int cooldownTimer = 0;
	
	
	public Weapon(Vector position, int damage, int cooldown, Vector size, Sprite sprite, Polygon collisionData, double friction, double elasticity, AbstractGameObject owner) {
		super(position, size, false, sprite, collisionData, friction, elasticity);
		setDamage(damage);
		setCooldown(cooldown);
		setOwner(owner);
	}
	
	public Weapon(Vector position, Sprite sprite, AbstractGameObject owner) {
		this(position, WEAPON_DAMAGE, WEAPON_COOLDOWN, WEAPON_SIZE, sprite, WEAPON_COLLISION_DATA, WEAPON_FRICTION, WEAPON_ELASTICITY, owner);
	}
	
	/**
	 * Aims the weapon towards the given world coordiantes.
	 * 
	 * @param x
	 * @param y
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
			owner.getParent().scheduleForAddition(new Projectile(getPosition(), new Vector(aimX, aimY), getOwner()));
			cooldownTimer = getCooldown();
		}
	}

	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	
	public String getId(){
		return weaponId;
	}
	
	public void setOwner(AbstractGameObject owner) {
		this.owner = owner;
	}

	public AbstractGameObject getOwner() {
		return owner;
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

	public int getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}


	
	
	
	
}
