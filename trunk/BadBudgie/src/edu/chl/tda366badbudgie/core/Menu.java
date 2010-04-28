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
	
	private String[] items = {"newgame", "options", "exit"};
	private MenuItem[] menuItems;
	
	private int currentItem;
	
	private ConfirmDialog dialog;
	
	public Menu() {
		
		dialog = null;
		
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
		
		if (dialog == null) {
			if (id.equals("down"))
				setCurrentItem(currentItem + 1);
			
			if (id.equals("up"))
				setCurrentItem(currentItem - 1);
			
			if (id.equals("escape"))
				setCurrentItem(numItems-1);
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

		if (dialog == null)
			return items[currentItem];
		
		if (dialog.getValue()) {
			dialog = null;
			return "confirm:" + items[currentItem];
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
		
	}
	
	public class ConfirmDialog {
		
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

	public void showConfirmDialog() {
		dialog = new ConfirmDialog(items[currentItem], "confirm:" + items[currentItem]);
	}

	public ConfirmDialog getConfirmDialog() {
		return dialog;
	}

}
