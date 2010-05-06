package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Sprite
 * 
 * Holds an arbitrary number of animations of a
 * segmented texture.
 * 
 * @author jesper, d.skalle
 *
 */
public class Sprite {
	
	private String texId;
	private int horFrames, verFrames;
	private String currentAnim;
	private int currentFrame;
	private double currentTime;
	private boolean mirrored;
	private double rotation;
	private Map<String, Animation> animations;
	
	/**
	 * Create a new sprite
	 * 
	 * @param texId the id of the texture of the sprite
	 * @param horizontalFrames number of horizontal frames in the texture
	 * @param verticalFrames number of vertical frames in the texture
	 * @param animations
	 */
	public Sprite(String texId, int horizontalFrames, int verticalFrames,
			List<Animation> animations){
		this.texId = texId;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		currentTime = 0;
		
		rotation = 0;
		mirrored = false;

		if (animations != null && !animations.isEmpty()) {
			for (Animation a : animations) {
				this.animations.put(a.getId(), a);
			}
			currentAnim = animations.get(0).getId();
			currentFrame = 0;
		} else {
			this.animations.put("idle", new Animation("idle",0));
			currentAnim = "idle";
			currentFrame = 0;
		}
		
	}
	
	/**
	 * Create a sprite with a single animation
	 * 
	 * @param texId the texture id
	 * @param horizontalFrames the number of horizontal frames in the sprite
	 * @param verticalFrames the number of vertical frames in the sprite
	 * @param animation the single animation used by the sprite
	 */
	public Sprite(String texId, int horizontalFrames, int verticalFrames,
			Animation animation){
		this.texId = texId;
		this.horFrames=horizontalFrames;
		this.verFrames=verticalFrames;
		this.animations = new HashMap<String, Animation>();
		this.animations.put(animation.getId(), animation);
		this.currentAnim = animation.getId();
		this.currentFrame = 0;
	}
	
	/**
	 * Creates a copy of the given sprite.
	 * @param s
	 */
	public Sprite(Sprite s){
		this(s.getId(), s.getHorFrames(), s.getVerFrames(), new ArrayList<Animation>(s.animations.values()));
	}
	
	/**
	 * Create a sprite with a single frame, where
	 * the single frame covers the entire texture
	 * 
	 * @param texId the texture id
	 */
	public Sprite(String texId) {
		this(texId, 1, 1, new Animation("still", 0));
	}
	
	/**
	 * Get the current frame index (image offset)
	 * of the current frame of the current animation
	 * @return the frame index.
	 */
	public int getFrameIndex() {

		int pos = animations.get(currentAnim).getFrameIndex(currentFrame);
		return pos;
	}
	
	/**
	 * Returns the id of the texture used by this sprite.
	 * @return the texture id.
	 */
	public String getId(){
		return texId;
	}
	
	//TODO: Question this method.
	public void nextAnimation(){}
	
	/**
	 * Get the horizontal frame resolution of
	 * the sprite's texture.
	 * @return the horizontal resolution.
	 */
	public int getHorFrames() {
		return horFrames;
	}

	/**
	 * Get the vertical frame resolution of
	 * the sprite's texture.
	 * @return the vertical resolution.
	 */
	public int getVerFrames() {
		return verFrames;
	}

	/**
	 * Set the current animation.
	 * @param animId the id of the animation.
	 */
	public void setAnimation(String animId) {
		if (currentAnim.equals(animId)) return;
		
		if (animations.containsKey(animId)) {
			currentAnim = animId;
			currentTime = 0;
			currentFrame = 0;
		}
	}
	
	public void setMirrored(boolean mirrored) {
		this.mirrored = mirrored;
	}
	
	public boolean getMirrored() {
		return mirrored;
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void animate() {
		
		currentTime+=1.0;
		
		if(currentTime >= animations.get(currentAnim).getFrameDuration(currentFrame)) {
			currentFrame++;
			currentTime = 0;
			if(currentFrame >= animations.get(currentAnim).getFrames().length) {
				currentFrame = 0;
			}
		}
		
	}
	
}
