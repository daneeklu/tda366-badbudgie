package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

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
	private AbstractGameObject owner;

	
	public Projectile(Vector position, Vector direction, double speed, int damage, Vector size, Sprite sprite, Polygon collisionData, AbstractGameObject owner) {
		super(position, size, false, sprite, collisionData, 1, 1);
		
		setVelocity(direction.normalize().scalarMultiplication(speed));
		setDamage(damage);
		setOwner(owner);
		
		addCollisionResponse(CollisionStimulus.WALKABLE_GROUND, new DisappearEffect());
		addCollisionResponse(CollisionStimulus.IMPENETRABLE, new DisappearEffect());
		addCollisionResponse(CollisionStimulus.INJURER, new DisappearEffect());
	}
	
	public Projectile(Vector position, Vector direction, AbstractGameObject owner) {
		this(position, direction, PROJECTILE_SPEED, PROJECTILE_DAMAGE, PROJECTILE_SIZE, PROJECTILE_SPRITE, PROJECTILE_COLLISION_DATA, owner);
	}
	
	
	@Override
	public GameRoundMessage update() {
		if (--lifeTimer == 0 || hasCollided)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
	}
	
	


	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	@Override
	public Object clone() {
		return new Projectile(getPosition(), getVelocity(), getVelocity().getLength(), getDamage(), getSize(), getSprite(), getCollisionData(), getOwner());
	}

	public void setOwner(AbstractGameObject owner) {
		this.owner = owner;
	}

	public AbstractGameObject getOwner() {
		return owner;
	}
	
	
	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.IMPACT);
		return stimuli;
	}
	private class DisappearEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			hasCollided = true;
		}
	}
	
}
