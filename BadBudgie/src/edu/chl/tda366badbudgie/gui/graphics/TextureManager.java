package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class TextureManager {
	
	private Map<String,Texture> textures;
	private String activeTexture;
	Texture a;
	
	public TextureManager() {
		textures = new HashMap<String,Texture>();
	}

	public String getActiveTexture() {
		return activeTexture;
		
	}

	public void setActiveTexture(String id) {
		
		activeTexture = id;
		Texture tex = textures.get(id);
		
		if(id == null) {
			tex = null;
			return;
		}


		
		if(tex == null) {
			System.out.println("setActiveTexture: no such texture!");
			return;
		}
		
		tex.enable();
		tex.bind();
		
	}
	
	public void addTexture(String id, BufferedImage data) {
		Texture tex = TextureIO.newTexture(data,true);
		
		if(tex == null) {
			System.out.println("addTexture: Texture creation failed!");
			return;
		}
		
		textures.put(id, tex);
	}
	
	

}
