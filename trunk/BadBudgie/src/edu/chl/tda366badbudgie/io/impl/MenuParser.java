package edu.chl.tda366badbudgie.io.impl;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.core.MenuItem;
import edu.chl.tda366badbudgie.core.MenuManager;
import edu.chl.tda366badbudgie.util.Rectangle;

/**
 * A parser for menu xml
 * 
 * @author d.skalle
 *
 */
public class MenuParser extends AbstractParser{

	public MenuParser(Document xmlDocument) {
		super(xmlDocument);
	}

	@Override
	public void parseData() {
		Document xml = this.getData();
		
		// Load every menu in the file
		NodeList menuList = xml.getElementsByTagName("menu");

		for (int l = 0; l < menuList.getLength(); l++) {
			
			// Load all menuitems in the menu
			NodeList menuItemList = xml.getElementsByTagName("menuitem");
			int numItems = menuItemList.getLength();
			
			MenuItem[] menuItems = new MenuItem[numItems];

			for (int i = 0; i < numItems; i++) {
				String itemName;
				String itemTexture;
				String []boundsStrings;
				Rectangle itemBounds;
									
				
				//For each menuitem, get its id, textureID, and boundary rectangle
				itemName = menuItemList.item(i).getAttributes().getNamedItem(
				"id").getNodeValue();
				itemTexture =	menuItemList.item(i).getAttributes().getNamedItem(
				"tex").getNodeValue();
				
				boundsStrings =	menuItemList.item(i).getAttributes().getNamedItem(
				"bounds").getNodeValue().trim().split(",");
				
				itemBounds = new Rectangle(Integer.parseInt(boundsStrings[0]),
						Integer.parseInt(boundsStrings[1]),
						Integer.parseInt(boundsStrings[2]),
						Integer.parseInt(boundsStrings[3]));
				
				menuItems[i] = new MenuItem(itemName, itemTexture, itemBounds);
			}
			
			
			// For the menu, get all 
			String menuId =	menuList.item(l).getAttributes().getNamedItem(
			"id").getNodeValue();
			String texId =	menuList.item(l).getAttributes().getNamedItem(
			"tex").getNodeValue();
			String[] boundsStrings =	menuList.item(l).getAttributes().getNamedItem(
			"bounds").getNodeValue().split(",");
			
			Rectangle menuBounds = new Rectangle(Integer.parseInt(boundsStrings[0]),
					Integer.parseInt(boundsStrings[1]),
					Integer.parseInt(boundsStrings[2]),
					Integer.parseInt(boundsStrings[3]));
			
			// Create the menu using all its submenus
			Menu menu = new Menu(menuId, texId, menuBounds, menuItems);
			MenuManager.getInstance().addMenu(menu);
		}
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
