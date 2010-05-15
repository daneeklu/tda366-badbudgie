package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
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
	
	private int damage = 10;
	private int lifeTimer = 200;
	private boolean hasCollided = false;
	private AbstractGameObject owner;
	
	
	public Projectile(Vector position, Vector direction, double speed, int damage, Vector size, Sprite sprite, Polygon collisionData, AbstractGameObject owner) {
		super(position, size, false, sprite, collisionData, 1, 1);
		
		setMass(0.1);
		setVelocity(direction.normalize().scalarMultiplication(speed));
		setDamage(damage);
		setOwner(owner);
	}
	
	public Projectile(Vector position, Vector direction, double speed, AbstractGameObject owner) {
		this(position, direction, speed, PROJECTILE_DAMAGE, PROJECTILE_SIZE, PROJECTILE_SPRITE, PROJECTILE_COLLISION_DATA, owner);
	}
	
	@Override
	public void updateForces() {
		applyForce(new Vector(0, 0.05));
	}
	
	@Override
	public GameRoundMessage update() {
		
		
		getSprite().setRotation(getVelocity().getRotation());
		
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

	
	
	@Override
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
		/*
		 * NOTE:
		 * The effect of the collision depends on the class of other. 
		 * We know that switching on class should normally be avoided, 
		 * but we think in this case it's fine. 
		 * If a new class is added, you don't want it to have any collision 
		 * effects unless explicitly specified in that class' collisionEffect.
		 * Note that physical collision response (bouncing etc.) is handled by 
		 * a map in AbstractCollidable and not this method.
		 * By using Class objects, it is also type safe.
		 */
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();

		if (		otherClass.equals(TerrainSection.class)
				||  otherClass.equals(Obstacle.class)
				|| (otherClass.equals(Player.class) && owner != other)
				|| (otherClass.equals(Enemy.class) && owner != other)) {
			hasCollided = true;
		}
		
	}
	
}
