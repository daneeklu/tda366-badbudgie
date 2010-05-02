package edu.chl.tda366badbudgie.core;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Rectangle;

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
	Rectangle bounds;
	private String action;
	private boolean selected;
	private boolean enabled;
	
	/**
	 * Create a MenuItem.
	 * @param action the action string for the MenuItem.
	 * @param texId the texture id for the MenuItem.
	 * @param position the position of the MenuItem.
	 * @param size the size of the MenuItem.
	 */
	public MenuItem(String action, String texId, Rectangle bounds) {
		
		Animation idleAnim = new Animation("idle",0);
		
		Animation selectedAnim = new Animation("selected",1);
		
		List<Animation> animList = new LinkedList<Animation>();
		
		animList.add(idleAnim);
		animList.add(selectedAnim);
		sprite = new Sprite(texId, 1, 4, animList);
		this.bounds = bounds;
		this.selected = false;
		enabled = true;
		this.action = action;
		
	}
	
	/**
	 * Create a MenuItem.
	 * @param id the id for both the action string and the texture.
	 * @param position the position of the MenuItem.
	 * @param size the size of the MenuItem.
	 */
	public MenuItem(String id, Rectangle bounds) {
		
		this(id, id, bounds);
		
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
	 * Check if the menu is enabled
	 * @return true if enabled
	 */
	public boolean getEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Get the sprite object of the MenuItem.
	 * @return the associated sprite.
	 */
	public Sprite getSprite() {
		return sprite;
	}
	
	/**
	 * Get the boundary rectangle for the menuItem
	 * @return the rectangle
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Do the logic for the MenuItem.
	 */
	public void logic() {
		// TODO Should menuitem have logic?
	}
	
	public String getAction() {
		return action;
	}
	
	

}
