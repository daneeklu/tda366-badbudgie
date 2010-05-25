package edu.chl.tda366badbudgie.io;

/**
 * An interface for loading all assets
 * used by the game.
 * 
 * @author d.skalle
 *
 */

public interface IFileManager {
	
	/**
	 * A function for loading resources from the file system.
	 * @param configPath
	 */
	public void loadData(String configPath);
	
}
