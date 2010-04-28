package edu.chl.tda366badbudgie.core;


/**
 * Menu
 * 
 * A menu of options available to the user.
 * 
 * @author d.skalle
 *
 */
public class Menu {

	private final int numItems = 3;
	
	private String[] items = {"New game", "Options", "Exit"};
	private MenuItem[] menuItems;
	
	private int currentItem;
	
	public Menu() {
		
		menuItems = new MenuItem[3];
		
		menuItems[0] = new MenuItem("newgame",
				new Vector(-200, 0),
				new Vector(400, 100));
		
		menuItems[1] = new MenuItem("options",
				new Vector(-200, -120),
				new Vector(400, 100));
		
		menuItems[2] = new MenuItem("exit",
				new Vector(-200, -240),
				new Vector(400, 100));
		
		setCurrentItem(0);
	}
	
	/**
	 * Set the current menu item
	 * @param item the number of the new menu item
	 */
	public void setCurrentItem(int item) {
		
		if (item == -1) {
			setCurrentItem(numItems - 1);
			return;
		}
		if (item == numItems) {
			setCurrentItem(0);
			return;
		}
		
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
		
		if (id.equals("down"))
			setCurrentItem(currentItem + 1);
		
		if (id.equals("up"))
			setCurrentItem(currentItem - 1);
	}
	
	/**
	 * Get the name of the selected menu item
	 * @return the menu item's name
	 */
	public String getSelected() {
		return items[currentItem];
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
		
	}
	
	

}
