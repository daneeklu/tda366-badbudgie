package edu.chl.tda366badbudgie.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sprite
 * 
 * Holds an arbitrary number of animations of a
 * segmented texture.
 * 
 * @author jesper
 *
 */
public class Sprite {
	
	private String texId;
	private int horFrames, verFrames;
	private Map<String, Animation> animations;
	
	public Sprite(String texId, int horizontalFrames, int verticalFrames,
			Map<String, Animation> animations){
		this.texId = texId;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		this.animations.putAll(animations);
	}
	
	public Sprite(String texId, int horizontalFrames, int verticalFrames,
			List<Animation> animations){
		this.texId = texId;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		
		for (Animation a : animations) {
			this.animations.put(a.getId(), a);
		}
	}
	
	/**
	 * Create a sprite with a single animation
	 * 
	 * @param texId the texture id
	 * @param horizontalFrames the number of horizontal frames in the sprite
	 * @param verticalFrames the number of vertical frames in the sprite
	 * @param animations
	 */
	
	public Sprite(String texId, int horizontalFrames, int verticalFrames,
			Animation animation){
		this.texId = texId;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		this.animations.put(animation.getId(), animation);
	}
	
	/**
	 * Returns the id of the texture used by this sprite.
	 * @return
	 */
	public String getId(){
		return texId;
	}
	
	//TODO: Question this method.
	public void nextAnimation(){}
	
	public int getHorFrames() {
		return horFrames;
	}

	public int getVerFrames() {
		return verFrames;
	}
	
}