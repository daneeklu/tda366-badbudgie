package edu.chl.tda366badbudgie.io;

import java.awt.image.BufferedImage;
import java.util.Map;

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
	
	/**
	 * Returns loaded image data.
	 */
	public Map<String, BufferedImage> getImageData();
}
