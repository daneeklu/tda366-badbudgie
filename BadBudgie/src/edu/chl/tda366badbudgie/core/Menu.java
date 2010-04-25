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
				new Vector(-0.5, 0.2),
				new Vector(1.0, 0.3));
		
		menuItems[1] = new MenuItem("options",
				new Vector(-0.5, -0.3),
				new Vector(1.0, 0.3));
		
		menuItems[2] = new MenuItem("exit",
				new Vector(-0.5, -0.8),
				new Vector(1.0, 0.3));
		
		setCurrentItem(0);
	}
	
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

	public void keyboardAction(String id, boolean down) {
		
		if (!down) return;
		
		if (id.equals("down"))
			setCurrentItem(currentItem + 1);
		
		if (id.equals("up"))
			setCurrentItem(currentItem - 1);
	}
	
	public String getSelected() {
		return items[currentItem];
	}
	
	public MenuItem[] getMenuItems() {
		return menuItems;
	}

	public void logic() {
		for (MenuItem mi : menuItems) {
			mi.logic();
		}
		
	}
	
	

}
