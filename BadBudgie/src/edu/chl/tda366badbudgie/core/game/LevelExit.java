package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * LevelExit
 * 
 * The exit in a level. When the player collides with an object of this class, 
 * the next level is loaded.
 * 
 * @author kvarfordt
 *
 */
public class LevelExit extends AbstractCollidable {
	
	// Default constructor parameters
	private static final Vector EXIT_SIZE = new Vector(40, 40);
	private static final Sprite EXIT_SPRITE = new Sprite("reagan");
	private static final Polygon EXIT_COLLISION_DATA = 
		AbstractCollidable.defaultCollisionData;
	
	private boolean touchedByPlayer = false;
	
	/**
	 * Constructor for LevelExit.
	 * 
	 * @param position
	 * @param size
	 * @param sprite
	 * @param collisionData
	 */
	public LevelExit(Vector position, Vector size, Sprite sprite, 
			Polygon collisionData) {
		super(position, size, true, sprite, collisionData, 1, 1);
		
	}
	
	/**
	 * Constructor for LevelExit.
	 * 
	 * @param position
	 */
	public LevelExit(Vector position) {
		this(position, EXIT_SIZE, EXIT_SPRITE, EXIT_COLLISION_DATA);
	}
	
	@Override
	public GameRoundMessage update() {
		if (touchedByPlayer){
			touchedByPlayer = false;
			return GameRoundMessage.LEVEL_FINISHED;
		}
		else
			return GameRoundMessage.NO_EVENT;
	}
	
	@Override
	public LevelExit clone() {
		return (LevelExit) super.clone();
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
		
		if (otherClass.equals(Player.class)) {
			touchedByPlayer = true;
		}
	}
	
}
