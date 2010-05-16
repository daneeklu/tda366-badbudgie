package edu.chl.tda366badbudgie.ai;

import edu.chl.tda366badbudgie.core.game.GameRound;

/**
 * 
 * Interface for AI classes.
 * 
 * @author kvarfordt
 *
 */
public interface IAI {

	/**
	 * Do AI for all the enemies in a gameround, that is:
	 * move them around and possibly make them fire at the player.
	 * @param gr
	 */
	public void doAI(GameRound gr);
}
