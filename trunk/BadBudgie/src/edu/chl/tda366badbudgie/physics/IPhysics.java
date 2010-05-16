package edu.chl.tda366badbudgie.physics;

import edu.chl.tda366badbudgie.core.game.GameRound;

/**
 * Interface for the physics sub-system.
 * 
 * @author kvarfordt
 *
 */
public interface IPhysics {
	
	/**
	 * Handles the physics of the objects in the level of the given gameround.
	 * @param gr
	 */
	public void doPhysics(GameRound gr);
	
}
