package edu.chl.tda366badbudgie.gui.graphics.impl;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

/**
 * A class for managing textures, loading them
 * from images and setting the active texture.
 *  
 * @author d.skalle
 *
 */

public class TextureManager {
	
	private Map<String,Texture> textures;
	private String activeTexture;
	
	/**
	 * Constructor
	 */
	public TextureManager() {
		textures = new HashMap<String,Texture>();
	}

	/**
	 * Return the active texture.
	 * @return the string id of the texture, or null if no texture is active.
	 */
	public String getActiveTexture() {
		return activeTexture;
		
	}

	/**
	 * Set the active texture.
	 * @param id The id of the texture.
	 */
	public void setActiveTexture(String id) {
		
		activeTexture = id;
		Texture tex = textures.get(id);
		
		if(id == null) {
			tex = null;
			return;
		}


		
		if(tex == null) {
			System.out.println("setActiveTexture: no such texture! id: " + id);
			return;
		}
		
		tex.enable();
		tex.bind();
		
	}
	
	/**
	 * Add a texture to the TextureManager
	 * @param id the id of the new texture
	 * @param data an image containing the picture
	 */
	public void addTexture(String id, BufferedImage data) {
		Texture tex = TextureIO.newTexture(data,true);
		
		if(tex == null) {
			System.out.println("addTexture: Texture creation failed!");
			return;
		}
		
		textures.put(id, tex);
	}
	
	

}
