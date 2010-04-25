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
		currentItem = 0;
		
		menuItems = new MenuItem[3];
		
		menuItems[0] = new MenuItem("newgame",
				new Vector(-0.5, 0.5),
				new Vector(1.0, 0.3));
		
		menuItems[1] = new MenuItem("newgame",
				new Vector(-0.5, 0.0),
				new Vector(1.0, 0.3));
		
		menuItems[2] = new MenuItem("newgame",
				new Vector(-0.5, -0.5),
				new Vector(1.0, 0.3));
		
	}

	public void keyboardAction(String id, boolean down) {
		
		if (!down) return;
		
		if (id.equals("down"))
			currentItem++;
		
		if (id.equals("up"))
			currentItem--;
		
		if(currentItem < 0) currentItem += numItems;
		if(currentItem >= numItems) currentItem -= numItems;
	}
	
	public String getSelected() {
		return items[currentItem];
	}
	
	public MenuItem[] getMenuItems() {
		return menuItems;
	}
	
	

}
