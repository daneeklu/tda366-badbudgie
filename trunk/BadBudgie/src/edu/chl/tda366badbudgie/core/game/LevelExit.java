package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
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
		
		// TODO: Explain here why class check is bad, but OK in this case.
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();
		
		if (otherClass.equals(Player.class)) {
			touchedByPlayer = true;
		}
	}
	
}
