package edu.chl.tda366badbudgie.core;

/**
 * Animation
 * 
 * An indexed sequence denoting parts of a segmented image.
 * @author jesper
 *
 */
public class Animation {

	private Frame[] frames;
	private String animId;
	private boolean loop;
	
	public Animation(String animId, int[] indices, double[] durationTimes, boolean loop){
		frames = new Frame[indices.length];
		
		for(int i = 0; i < indices.length; i ++){
			frames[i] = new Frame(indices[i], durationTimes[i]);
		}
		this.setLooping(loop);
		this.animId = animId;
	}
	
	public Animation(String animId, int[] indices, double durationTime, boolean loop){
		frames = new Frame[indices.length];
		
		for(int i = 0; i < indices.length; i ++){
			frames[i] = new Frame(indices[i], durationTime);
		}
		this.setLooping(loop);
		this.animId = animId;
	}
	
	public Animation(String animId, int[] indices,  double[] durationTimes){
		this(animId, indices, durationTimes, false);
	}
	
	public Animation(String animId, int[] indices, double durationTime){
		this(animId, indices, durationTime, false);
	}
	
	/**
	 * Creates a single frame animation.
	 * Useful for still images.
	 * @param animId
	 */
	public Animation(String id, int index){
		frames = new Frame[1];
		frames[0] = new Frame(index, 1);
		this.setLooping(false);
		this.animId = id;
	}
	
	public int getFrameIndex(int frame) {
		return frames[frame].getIndex();
	}
	
	public double getFrameDuration(int frame) {
		return frames[frame].getDuration();
	}
	
	public Frame[] getFrames() {
		return frames;
	}
	
	/**
	 * Frame
	 * Used to hold frame-specific data of the animation.
	 * @author jesper
	 *
	 */
	public static class Frame{
		private int posIndex;
		private double duration;
		
		/**
		 * Creates a new frame with a given index and duration.
		 * 
		 * @param index the position index of the frame.
		 * @param duration the duration time of the frame.
		 */
		private Frame(int index, double duration){
			this.posIndex = index;
			this.duration = duration;
		}
		
		/**
		 * Returns the index of the frame, in the segmented image it belongs to.
		 * @return
		 */
		public int getIndex(){
			return posIndex;
		}
		
		/**
		 * Returns the time the frame will shown in the animation.
		 * @return the duration time of the frame.
		 */
		public double getDuration(){
			return duration;
		}
	}

	public String getId() {
		return animId;
	}

	public void setLooping(boolean loop) {
		this.loop = loop;
	}

	public boolean isLooping() {
		return loop;
	}
	
}
