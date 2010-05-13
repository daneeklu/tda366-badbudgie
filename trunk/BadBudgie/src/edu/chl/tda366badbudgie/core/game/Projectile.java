package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Sprite;
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
	private boolean isLive = true;
	private AbstractGameObject owner;
	
	
	public Projectile(Vector position, Vector direction, double speed, int damage, Vector size, Sprite sprite, Polygon collisionData, AbstractGameObject owner) {
		super(position, size, false, sprite, collisionData, 1, 1);
		
		setMass(0.1);
		setVelocity(direction.normalize().scalarMultiplication(speed));
		setDamage(damage);
		setOwner(owner);
		
		addPhysicalCollision(TerrainSection.class);
		addPhysicalCollision(Obstacle.class);
	}
	
	public Projectile(Vector position, Vector direction, AbstractGameObject owner) {
		this(position, direction, PROJECTILE_SPEED, PROJECTILE_DAMAGE, PROJECTILE_SIZE, PROJECTILE_SPRITE, PROJECTILE_COLLISION_DATA, owner);
	}
	
	
	@Override
	public GameRoundMessage update() {
		
		if (--lifeTimer == 0 || hasCollided)
			return GameRoundMessage.REMOVE_OBJECT;
		
		return GameRoundMessage.NO_EVENT;
	}
	
	


	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public Projectile clone() {
		return (Projectile) super.clone();
	}

	public void setOwner(AbstractGameObject owner) {
		this.owner = owner;
	}

	public AbstractGameObject getOwner() {
		return owner;
	}

	public boolean hasCollided() {
		return hasCollided;
	}

	public void setHasCollided(boolean hasCollided) {
		this.hasCollided = hasCollided;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	
	@Override
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
		// TODO: Explain here why class check is bad, but OK in this case.
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();
		
		if (isLive() && otherClass.equals(TerrainSection.class)) {
			hasCollided = true;
		}
		
	}
	
}
