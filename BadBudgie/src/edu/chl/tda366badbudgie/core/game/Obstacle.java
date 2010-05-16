package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Obstacle
 * 
 * Generic level object.
 * 
 * @author kvarfordt
 *
 */
public class Obstacle extends AbstractCollidable {
	
	/**
	 * Constructor
	 * 
	 * @param texId the texture id
	 * @param stationary the obstacle is immovable if true
	 */
	public Obstacle(Vector position, Vector size, boolean stationary, 
			Sprite sprite, Polygon collisionData, double friction, 
			double elasticity) {
		super(position, size, stationary, 
				sprite, collisionData, friction, elasticity);

		addPhysicalCollision(TerrainSection.class);
		addPhysicalCollision(Player.class);
		addPhysicalCollision(Enemy.class);
		addPhysicalCollision(Obstacle.class);
	}

	@Override
	public Obstacle clone() {
		return (Obstacle) super.clone();
	}


	@Override
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
		/*
		 * NOTE:
		 * The effect of the collision depends on the class of other. 
		 * We know that switching on class should normally be avoided, 
		 * but we think in this case it's fine, and we have not found a better 
		 * solution.
		 * If a new class is added, you don't want it to have any collision 
		 * effects unless explicitly specified in that class' collisionEffect.
		 * Note that physical collision response (bouncing etc.) is handled by
		 * a map in AbstractCollidable and not this method.
		 * By using Class objects, it is also type safe.
		 */
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();

		if (otherClass.equals(Projectile.class)) {
			Projectile p = (Projectile) other;
			
			applyForce(p.getVelocity()
					.scalarMultiplication(p.getMass()/getMass()));
		}
	}
}
