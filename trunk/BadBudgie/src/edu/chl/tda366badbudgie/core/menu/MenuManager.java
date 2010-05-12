package edu.chl.tda366badbudgie.core.menu;

import java.util.HashMap;
import java.util.Map;

/**
 * A manager for menus, keeping a map of
 * menus and their id strings.
 * 
 * @author d.skalle
 *
 */
public class MenuManager {

	//Singleton instance
	private static MenuManager instance;
	
	private Map<String,Menu> menus = new HashMap<String,Menu>();
	
	private MenuManager() {

	}
	
	/**
	 * Returns the instance of the MenuManager
	 * @return the MenuManager
	 */
	public static MenuManager getInstance() {
		if(instance==null){
			instance = new MenuManager();
		}
		return instance;
	}
	
	/**
	 * Adds a Menu object to the menu manager.
	 * @param menu the menu that will be added.
	 */
	public void addMenu(Menu menu) {
		menus.put(menu.getId(), menu);
	}
	
	/**
	 * Get a menu from the manager.
	 * @param id the id of the menu.
	 */
	public Menu getMenu(String id) { 
		return menus.get(id);
	}
	
}
