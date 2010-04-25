package edu.chl.tda366badbudgie.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Animation
 * 
 * An indexed sequence denoting parts of a segmented image.
 * @author jesper
 *
 */
public class Animation {

	private List<Frame> frames;
	private String animId;
	private boolean loop;
	
	public Animation(String animId, List<Integer> indices, List<Double> durationTimes, boolean loop){
		frames = new ArrayList<Frame>(indices.size());
		
		for(int i = 0; i < indices.size(); i ++){
			frames.add(new Frame(indices.get(i), durationTimes.get(i)));
		}
		this.loop = loop;
		this.animId = animId;
	}
	
	public Animation(String animId, List<Integer> indices, List<Double> durationTimes){
		this(animId, indices, durationTimes, false);
	}
	
	/**
	 * Creates a single frame animation.
	 * Useful for still images.
	 * @param animId
	 */
	public Animation(String id, int index){
		frames = new ArrayList<Frame>(1);
		frames.add(new Frame(index, 1));
		this.loop = false;
		this.animId = id;
	}
	
	public int getFrameIndex(int frame) {
		return frames.get(frame).getIndex();
	}
	
	/**
	 * Frame
	 * Used to hold frame-specific data of the animation.
	 * @author jesper
	 *
	 */
	public class Frame{
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
	
}
