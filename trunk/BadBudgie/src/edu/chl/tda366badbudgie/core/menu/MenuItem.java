package edu.chl.tda366badbudgie.core.menu;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Animation;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Sprite;

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
	
	/* A MenuItem can display two different texts:
	 * the normal text, and an alternate text. If
	 * the alternate value == true, then the alternate
	 * text will be drawn. A different part of the
	 * texture will be used, described in altSprite
	 */
	private boolean alternate;
	private Sprite altSprite;
	
	/**
	 * Create a MenuItem.
	 * @param action the action string for the MenuItem.
	 * @param texId the texture id for the MenuItem.
	 * @param position the position of the MenuItem.
	 * @param size the size of the MenuItem.
	 */
	public MenuItem(String action, String texId, Rectangle bounds, 
			boolean enabled) {
		
		// Initiate sprite for the normal text
		Animation idleAnim = new Animation("idle",0);
		Animation selectedAnim = new Animation("selected",1);
		List<Animation> animList = new LinkedList<Animation>();
		animList.add(idleAnim);
		animList.add(selectedAnim);
		
		// Initiate altSprite for the alternate text
		Animation altIdleAnim = new Animation("idle",2);
		Animation altSelectedAnim = new Animation("selected",3);
		List<Animation> altAnimList = new LinkedList<Animation>();
		altAnimList.add(altIdleAnim);
		altAnimList.add(altSelectedAnim);

		sprite = new Sprite(texId, 1, 4, animList);
		altSprite = new Sprite(texId, 1, 4, altAnimList);
		
		this.bounds = bounds;
		this.selected = false;
		this.action = action;
		this.enabled = enabled;
		
		// The normal text is displayed by default
		this.alternate = false;
		
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
		if (selected) {
			sprite.setAnimation("selected");
			altSprite.setAnimation("selected");
		} else {
			sprite.setAnimation("idle");
			altSprite.setAnimation("idle");
		}
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
		if (alternate)
			return altSprite;
		else
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

	/**
	 * Set whether the alternate text should be displayed or not.
	 * @param alternate true if the alternate text should be displayed.
	 */
	public void setAlternate(boolean alternate) {
		this.alternate = alternate;
	}
	
	/**
	 * Get whether the alternate text is the currently set one.
	 * @return false for normal text, true for alternate text.
	 */
	public boolean getAlternate() {
		return alternate;
	}

}
