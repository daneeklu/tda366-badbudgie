package edu.chl.tda366badbudgie.core;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


/**
 * Stores image data which can be accessed throughout the program.
 * 
 * @author jesper
 */
public class ImageDataHandler {
		
	private Map<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private static ImageDataHandler handleInstance;
	
	public void addImage(String id, BufferedImage image){
		images.put(id, image);
	}
	
	private ImageDataHandler(){}
	
	public static ImageDataHandler getInstance(){
		if(handleInstance==null){
			handleInstance = new ImageDataHandler();
		}
		return handleInstance;
	}
	
	public Map<String, BufferedImage> getData(){
		Map<String, BufferedImage> map = new HashMap<String, BufferedImage>();
		map.putAll(images);
		return map;
	}
	
}
