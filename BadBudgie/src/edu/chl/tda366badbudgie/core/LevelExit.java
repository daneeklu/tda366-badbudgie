package edu.chl.tda366badbudgie.core;

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
	
	private boolean touchedByPlayer = false;
	
	/**
	 * Constructor
	 */
	public LevelExit(String texId) {
		stationary = true;
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}

	@Override
	public String update() {
		if (touchedByPlayer) 
			return "nextlevel";
		else
			return "";
	}
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		touchedByPlayer = true;
	}
	
}
