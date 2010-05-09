package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Rectangle;


/**
 * Menu
 * 
 * A menu of options available to the user.
 * 
 * @author d.skalle
 *
 */
public class Menu {
	
	private String id;
	private MenuItem[] menuItems;
	private int currentItem;
	private Rectangle bounds;
	private String texId;
	private boolean gamerunning;
	
	private ConfirmDialog dialog;
	
	public Menu(String id, String texId, Rectangle bounds, MenuItem[] items) {
		dialog = null;
		this.id = id;
		this.texId = texId;
		this.bounds = bounds;
		this.menuItems = items;
		
		setCurrentItem(0);
	}
	
	/**
	 * Get the id of the menu
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Select the previous enabled item in the menu
	 */
	public void selectPrevious() {
		int item = currentItem - 1;
		
		if(item < 0) item = menuItems.length - 1;
		
		//Skip disabled menuItems
		while (!menuItems[item].getEnabled()) {
			item--;
			if(item < 0) item = menuItems.length - 1;
		}
		
		menuItems[currentItem].setSelected(false);
		menuItems[item].setSelected(true);
		
		currentItem = item;
	}
	
	/**
	 * Select the next enabled item in the menu
	 */
	public void selectNext() {
		int item = currentItem + 1;
		
		if(item >= menuItems.length) item = 0;
		
		//Skip disabled menuItems
		while (!menuItems[item].getEnabled()) {
			item++;
			if(item >= menuItems.length) item = 0;
		}
		
		menuItems[currentItem].setSelected(false);
		menuItems[item].setSelected(true);
		
		currentItem = item;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public String getTexId() {
		return texId;
	}
	
	public void setGameRunning(boolean running){
		gamerunning=running;
	}
	
	/**
	 * Set the current menu item. Doesn't set
	 * if the item is disabled
	 * @param item the number of the new menu item
	 */
	
	private void setCurrentItem(int item) {
		

		if (item == -1) {
			setCurrentItem(menuItems.length - 1);
			return;
		}
		if (item == menuItems.length) {
			setCurrentItem(0);
			return;
		}
		
		if (!menuItems[item].getEnabled())
			return;
		
		menuItems[currentItem].setSelected(false);
		menuItems[item].setSelected(true);
		
		currentItem = item;
	}

	/**
	 * Key handling function, called by MenuState
	 * @param id the id of the key 
	 * @param down true if the key was pushed down
	 */
	public void keyboardAction(String id, boolean down) {
		
		if (!down) return;
		
		if (dialog == null) {
			if (id.equals("down"))
				selectNext();
			
			if (id.equals("up"))
				selectPrevious();
			
			if (id.equals("escape"))
				setCurrentItem(2);
		} else {
			if (id.equals("right"))
				dialog.change(-1);
			if (id.equals("left"))
				dialog.change(1);
		}

	}
	
	/**
	 * Get the name of the selected menu item
	 * @return the menu item's name
	 */
	public String getSelected() {

		if (dialog == null) {
			return menuItems[currentItem].getAction();
		}
		
		if (dialog.getValue()) {
			dialog = null;
			return "confirm:" + menuItems[currentItem].getAction();
		}
		else {
			dialog = null;
			return null;
		}	
	}
	
	/**
	 * Get the menu items this menu has
	 * @return the menu items
	 */
	public MenuItem[] getMenuItems() {
		return menuItems;
	}

	/**
	 * A method for doing all "game logic" of the menu
	 */ 
	public void logic() {
		for (MenuItem mi : menuItems) {
			mi.logic();
		}
		
		if (gamerunning)
		{
			if (!menuItems[3].getEnabled()) {
				
				// After a new game has been started, highlight "resume game"
				// after returning to the menu
				menuItems[3].setEnabled(true);
				setCurrentItem(3);
			}
		}
		else {
			// If there's no game state, "resume game" should be disabled
			menuItems[3].setEnabled(false);
		}
	}


	/**
	 * Show a confirmation dialog for the current menuItem
	 */
	public void showConfirmDialog() {
		dialog = new ConfirmDialog(menuItems[currentItem].getAction(), "confirm:" + menuItems[currentItem].getSprite().getId());
	}

	/**
	 * Return the currently showing confirmation dialog,
	 * if there is one
	 * @return the dialog, or null
	 */
	public ConfirmDialog getConfirmDialog() {
		return dialog;
	}
	
	
	/**
	 * An inner class representing a confirmation dialog
	 * 
	 * @author d.skalle
	 * 
	 */
	public static class ConfirmDialog {
		
		boolean value;
		String action;
		String texId;
		
		private ConfirmDialog(String action, String texId) {
			this.action = action;
			this.texId = texId;
			value = false;
		}
		
		public void change(int val) {
			value = (val == 1) ? true : false;
		}
		
		public boolean getValue() {
			return value;
		}

		public String getTexId() {
			return texId;
		}
		
	}

}
