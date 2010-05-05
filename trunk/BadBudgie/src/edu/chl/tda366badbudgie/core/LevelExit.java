package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.physics.impl.CollisionHandler;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * LevelExit
 * 
 * The exit in a level. When the player collides with an object of this class, the next level is loaded.
 * 
 * @author kvarfordt
 *
 */
public class LevelExit extends AbstractCollidable {
	
	// Default constructor parameters
	private static final Vector EXIT_SIZE = new Vector(40, 40);
	private static final Sprite EXIT_SPRITE = new Sprite("reagan");
	private static final Polygon EXIT_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	
	private boolean touchedByPlayer = false;
	
	/**
	 * Constructor
	 */
	public LevelExit(Vector position, Vector size, Sprite sprite, Polygon collisionData) {
		super(position, size, true, sprite, collisionData, 1, 1);
	}
	
	public LevelExit(Vector position) {
		this(position, EXIT_SIZE, EXIT_SPRITE, EXIT_COLLISION_DATA);
	}
	

	@Override
	public GameRoundMessage update() {
		if (touchedByPlayer) 
			return GameRoundMessage.LevelFinished;
		else
			return GameRoundMessage.NoEvent;
	}
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		if (other instanceof Player)
			touchedByPlayer = true;
	}
	
}
