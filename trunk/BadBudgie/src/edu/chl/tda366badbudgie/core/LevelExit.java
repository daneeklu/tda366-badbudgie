package edu.chl.tda366badbudgie.core;

/**
 * LevelExit
 * 
 * The exit in a level. When the player collides with an object of this class, the next level is loaded.
 * 
 * @author kvarfordt
 *
 */
public class LevelExit extends AbstractCollidable {
	
	private GameRound gameRound;
	
	/**
	 * Constructor
	 */
	public LevelExit(GameRound gameRound, String texId) {
		this.gameRound = gameRound;
		stationary = true;
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}

	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		gameRound.levelFinished();
	}
	
}
