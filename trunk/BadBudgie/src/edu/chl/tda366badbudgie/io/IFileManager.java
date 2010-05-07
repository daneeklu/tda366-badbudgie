package edu.chl.tda366badbudgie.io;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface IFileManager {
	
	/**
	 * A function for loading resources from the file system.
	 */
	public void loadData();
	
	/**
	 * Returns loaded image data.
	 */
	public Map<String, BufferedImage> getImageData();
}
