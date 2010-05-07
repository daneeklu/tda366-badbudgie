package edu.chl.tda366badbudgie.io.impl;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


/**
 * ImageDataHandler
 * Stores image data which can be accessed throughout the program.
 * 
 * @author jesper
 */
public class ImageDataHandler {
		
	private Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private static ImageDataHandler handleInstance;
	
	/**
	 * Adds a new image to the data handler.
	 * 
	 * @param id the id to which the image is associated.
	 * @param image the image.
	 */
	public void addImage(String id, BufferedImage image){
		images.put(id, image);
	}
	
	private ImageDataHandler(){}
	
	/**
	 * Returns the instance of ImageDataHandler.
	 * @return
	 */
	public static ImageDataHandler getInstance(){
		if(handleInstance==null){
			handleInstance = new ImageDataHandler();
		}
		return handleInstance;
	}
	
	/**
	 * Returns a shallow copy of the image map.
	 * 
	 * @return a map containing references to the images currently held by the handler.
	 */
	public Map<String, BufferedImage> getData(){
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.putAll(images);
		return map;
	}
	
}
