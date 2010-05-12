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
	private Rectangle bounds;
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
	public MenuItem(String action, String texId, Rectangle bounds, boolean enabled) {
		
		Animation idleAnim = new Animation("idle",0);
		
		Animation selectedAnim = new Animation("selected",1);
		
		List<Animation> animList = new LinkedList<Animation>();
		
		animList.add(idleAnim);
		animList.add(selectedAnim);
		sprite = new Sprite(texId, 1, 4, animList);
		this.bounds = bounds;
		this.selected = false;
		this.action = action;
		this.enabled = enabled;
		
	}
	
	/**
	 * Create a MenuItem, usin
	 * @param action
	 * @param texId
	 * @param bounds
	 */
	public MenuItem(String action, String texId, Rectangle bounds) {
		this(action, texId, bounds, true);
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
	
	/**
	 * Mark the menu as enabled
	 * or disabled
	 * @param enabled true, if enabled
	 */
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
	 * Get the id of the action associated with the
	 * MenuItem
	 * @return the action's string id
	 */
	public String getAction() {
		return action;
	}

}
