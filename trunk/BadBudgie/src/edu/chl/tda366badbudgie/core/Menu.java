package edu.chl.tda366badbudgie.core;



public class Menu {

	private final int numItems = 3;
	
	private String[] items = {"New game", "Options", "Exit"};
	
	private int currentItem;
	
	public Menu() {
		currentItem = 0;
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
	
	

}
