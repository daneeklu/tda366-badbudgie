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
	private boolean selected;
	
	/**
	 * Create a MenuItem.
	 * @param id the texture id for the MenuItem.
	 * @param position the position of the MenuItem.
	 * @param size the size of the MenuItem.
	 */
	public MenuItem(String id, Vector position, Vector size) {
		
		Animation idleAnim = new Animation("idle",0);
		
		Animation selectedAnim = new Animation("selected",1);
		
		List<Animation> animList = new LinkedList<Animation>();
		
		animList.add(idleAnim);
		animList.add(selectedAnim);
		sprite = new Sprite(id, 1, 4, animList);
		this.position = position;
		this.size = size;
		this.selected = false;
		
	}
	
	/**
	 * Mark this MenuItem as selected or
	 * not selected.
	 * @param value true, if selected.
	 */
	public void setSelected(boolean value) {
		selected = value;
		if (selected) 
			sprite.setAnimation("selected");
		else
			sprite.setAnimation("idle");
	}
	
	/**
	 * Get whether this MenuItem is
	 * selected or not.
	 * @return true, if selected.
	 */
	public boolean getSelected() {
		return selected;
	}
	
	/**
	 * Get the sprite object of the MenuItem.
	 * @return the associated sprite.
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Get the position of the MenuItem.
	 * @return the position vector.
	 */
	public Vector getPosition() {
		return position;
	}
	
	/**
	 * Get the size of the MenuItem
	 * @return the size vector.
	 */
	public Vector getSize() {
		return size;
	}

	/**
	 * Do the logic for the MenuItem.
	 */
	public void logic() {
		// TODO Should menuitem have logic?
	}
	
	

}
