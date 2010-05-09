package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

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
		
		addCollisionResponse(CollisionStimulus.PLAYER, new LevelExitEffect());
	}
	
	public LevelExit(Vector position) {
		this(position, EXIT_SIZE, EXIT_SPRITE, EXIT_COLLISION_DATA);
	}
	

	@Override
	public GameRoundMessage update() {
		if (touchedByPlayer){
			touchedByPlayer = false;
			return GameRoundMessage.LevelFinished;
		}
		else
			return GameRoundMessage.NoEvent;
	}
	
//	@Override
//	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
//		if (other instanceof Player){
//			touchedByPlayer = true;
//			other.translate(mtv.scalarMultiplication(-2));
//		}
//	}

	@Override
	public Object clone() {
		return new LevelExit(getPosition(), getSize(), getSprite(), getCollisionData());
	}
	
	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.LEVEL_EXIT);
		return stimuli;
	}

	
	private class LevelExitEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {
			touchedByPlayer = true;
		}
	}
	
	
}
