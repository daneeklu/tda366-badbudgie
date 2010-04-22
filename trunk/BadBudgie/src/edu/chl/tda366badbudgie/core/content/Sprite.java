package edu.chl.tda366badbudgie.core.content;

import java.util.HashMap;
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
	
	private String id;
	private int horFrames, verFrames;
	private Map<String, Animation> animations;
	
	public Sprite(String id, int horizontalFrames, int verticalFrames,
			Map<String, Animation> animations){
		this.id = id;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		this.animations.putAll(animations);
	}
	
	/**
	 * Returns the id of the texture used by this sprite.
	 * @return
	 */
	public String getId(){
		return id;
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
