package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * 
 * @author kvarfordt
 *
 */
public class HealthPack extends AbstractCollidable {

	private int healthPoints;
	private boolean touchedByPlayer = false;
	
	public HealthPack(Vector position, Vector size, boolean stationary,
			Sprite sprite, Polygon collisionData, double friction, double elasticity, int hp) {
		super(position, size, stationary, sprite, collisionData, friction, elasticity);
		
		setHealthPoints(hp);
		
		addPhysicalCollision(TerrainSection.class);
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	@Override
	public GameRoundMessage update() {
		
		if (touchedByPlayer)
			return GameRoundMessage.REMOVE_OBJECT;
		
		return GameRoundMessage.NO_EVENT;
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
		
		if (other instanceof Player) {
			Player p = (Player) other;
			p.setHealth(p.getHealth() + getHealthPoints());
			touchedByPlayer = true;
		}
		
	}
	
}
