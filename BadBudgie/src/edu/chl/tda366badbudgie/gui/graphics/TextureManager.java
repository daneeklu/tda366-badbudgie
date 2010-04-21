package edu.chl.tda366badbudgie.gui.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;

public class TextureManager {
	
	private Map<String,Texture> textures;
	private String activeTexture;
	GL gl;
	Texture a;
	
	public TextureManager(GL gl) {
		this.gl = gl;
		textures = new HashMap<String,Texture>();
	}
	

	public String getActiveTexture() {
		return activeTexture;
		
	}

	public void setActiveTexture(String id) {
		activeTexture = id;

		Texture tex = textures.get(id);
		
		tex.enable();
		tex.bind();
		
	}
	
	public void addTexture(String id, BufferedImage data) {
		Texture tex = TextureIO.newTexture(data,true);
		
		textures.put(id, tex);
	}
	
	

}
