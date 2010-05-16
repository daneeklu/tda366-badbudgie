package edu.chl.tda366badbudgie.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Sprite
 * 
 * Holds an arbitrary number of animations of a
 * segmented texture.
 * 
 * @author jesper, d.skalle
 *
 */
public class Sprite implements Cloneable {
	
	// The texture and orientation of the sprite
	private String texId;
	private boolean mirrored;
	private double rotation;
	
	// The animations and resolution of the sprite
	private Map<String, Animation> animations;
	private int horFrames, verFrames;

	// The current animation state of the frame
	private String currentAnim;
	private int currentFrame;
	private double currentTime;

	
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
		this(s.getId(), s.getHorFrames(), s.getVerFrames(), 
				new ArrayList<Animation>(s.animations.values()));
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
	
	/**
	 * Skip to the next animation, looping the
	 * current animation if it's set to loop.
	 */
	public void nextAnimation() {
		currentFrame = 0;
		if (animations.get(currentAnim).isLooping())
			setAnimation("idle");
	}
	
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
	
	/**
	 * Set the mirrored value for the sprite
	 * @param mirrored true if mirrored
	 */
	public void setMirrored(boolean mirrored) {
		this.mirrored = mirrored;
	}
	
	/**
	 * Get the mirrored value for the sprite
	 * @return mirrored true if the sprite is mirrored
	 */
	public boolean getMirrored() {
		return mirrored;
	}
	
	/**
	 * Set the rotation values for the sprite
	 * @param rotation the rotation, in degrees
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Get the rotation values for the sprite
	 * @return the rotation, in degrees
	 */
	public double getRotation() {
		return rotation;
	}
	
	/**
	 * Step the sprite one step forward in the animation,
	 * changing the animations as frames as fit.
	 */
	public void animate() {
		
		currentTime+=1.0;
		
		if(currentTime 
				>= animations.get(currentAnim).getFrameDuration(currentFrame)) {
			currentFrame++;
			currentTime = 0;
			if(currentFrame >= animations.get(currentAnim).getFrames().length) {
				currentFrame = 0;
			}
		}
		
	}
	
	
	/**
	 * Step the sprite one step forward in the animation,
	 * changing the animations as frames as fit.
	 * @param t the number of time steps
	 */
	public void animate(double t) {
		
		currentTime+=1.0;
		
		if(currentTime 
				>= animations.get(currentAnim).getFrameDuration(currentFrame)) {
			currentFrame++;
			currentTime = 0;
			if(currentFrame >= animations.get(currentAnim).getFrames().length) {
				nextAnimation();
			}
		}
		
	}
	
	@Override
	public Sprite clone() {
		try {
			Sprite s = (Sprite) super.clone();
			s.animations = new HashMap<String, Animation>();
			for (Entry<String, Animation> e : animations.entrySet()) {
				s.animations.put(e.getKey(), e.getValue().clone());
			}
			return s;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Clone not supported");
		}
		
	}
	
}
