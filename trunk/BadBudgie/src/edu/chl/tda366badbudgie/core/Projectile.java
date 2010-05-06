package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Vector;

public class Projectile extends AbstractItem {

	// Default constructor parameters
	private static final Vector PROJECTILE_SIZE = new Vector(10, 10);
	private static final Sprite PROJECTILE_SPRITE = new Sprite("bullet1");
	private static final Polygon PROJECTILE_COLLISION_DATA = new Rectangle(-5,-5,10,10);
	private static final int PROJECTILE_DAMAGE = 10;
	private static final int PROJECTILE_SPEED = 30;
	
	private int damage = 10;
	private int lifeTimer = 200;
	private boolean hasCollided = false;

	
	public Projectile(Vector position, Vector direction, double speed, int damage, Vector size, Sprite sprite, Polygon collisionData) {
		super(position, size, false, sprite, collisionData, 1, 1);
		
		setVelocity(direction.normalize().scalarMultiplication(speed));
		setDamage(damage);
	}
	
	public Projectile(Vector position, Vector direction) {
		this(position, direction, PROJECTILE_SPEED, PROJECTILE_DAMAGE, PROJECTILE_SIZE, PROJECTILE_SPRITE, PROJECTILE_COLLISION_DATA);
	}
	
	
	@Override
	public GameRoundMessage update() {
		if (--lifeTimer == 0 || hasCollided)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
	}
	
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		if (!(other instanceof Player) && AbstractCollidable.isPhysicalCollision(this.getClass(), other.getClass())) {
			hasCollided = true;
		}
	}

	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
}
