package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

/**
 * MenuItem
 * 
 * An item in one of the games menus.
 * 
 * @author d.skalle
 *
 */
public class MenuItem {
	
	private Sprite sprite;
	private Vector position;
	private Vector size;
	
	public MenuItem(String id, Vector position, Vector size) {
		
		Animation idleAnim = new Animation("idle",0);
		
		Animation selectedAnim = new Animation("selected",1);
		
		List<Animation> animList = new LinkedList<Animation>();
		
		animList.add(idleAnim);
		animList.add(selectedAnim);
		sprite = new Sprite(id, 1, 4, animList);
		this.position = position;
		this.size = size;
		
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Vector getSize() {
		return size;
	}
	
	

}
