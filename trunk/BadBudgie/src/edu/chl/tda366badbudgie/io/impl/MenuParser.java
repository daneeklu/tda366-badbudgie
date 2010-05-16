package edu.chl.tda366badbudgie.io.impl;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.core.menu.MenuItem;
import edu.chl.tda366badbudgie.core.menu.MenuManager;
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
			
			Menu menu = parseMenu(menuList.item(l));
			MenuManager.getInstance().addMenu(menu);

		}
	}
	
	private Menu parseMenu(Node menuNode) {

		// For the menu, get all attributes
		String menuId =	menuNode.getAttributes().getNamedItem(
		"id").getNodeValue();
		String texId =	menuNode.getAttributes().getNamedItem(
		"tex").getNodeValue();
		String[] boundsStrings = menuNode.getAttributes().getNamedItem(
		"bounds").getNodeValue().split(",");
		
		Rectangle menuBounds = new Rectangle(Integer.parseInt(boundsStrings[0]),
				Integer.parseInt(boundsStrings[1]),
				Integer.parseInt(boundsStrings[2]),
				Integer.parseInt(boundsStrings[3]));
		

		NodeList menuItemNodes = menuNode.getChildNodes();
		List<MenuItem> menuItemList = new LinkedList<MenuItem>();
		
		// Load all menuitems in the menu
		for (int i = 0; i < menuItemNodes.getLength(); i++) {
			if (menuItemNodes.item(i).getNodeType() == Node.ELEMENT_NODE 
					&& menuItemNodes.item(i).getNodeName().equals("menuitem"))

				menuItemList.add(parseMenuItem(menuItemNodes.item(i)));
		}
		
		
		Menu menu = new Menu(menuId, texId, menuBounds, 
					menuItemList.toArray(new MenuItem[0]));
		return menu;
	}

	private MenuItem parseMenuItem(Node menuItemNode) {
		String itemName;
		String itemTexture;
		String []boundsStrings;
		Rectangle itemBounds;
		
		//For each menuitem, get its id, textureID, and boundary rectangle
		itemName = menuItemNode.getAttributes().getNamedItem(
		"id").getNodeValue();
		itemTexture =	menuItemNode.getAttributes().getNamedItem(
		"tex").getNodeValue();
		
		boundsStrings =	menuItemNode.getAttributes().getNamedItem(
		"bounds").getNodeValue().trim().split(",");
		
		itemBounds = new Rectangle(Integer.parseInt(boundsStrings[0]),
				Integer.parseInt(boundsStrings[1]),
				Integer.parseInt(boundsStrings[2]),
				Integer.parseInt(boundsStrings[3]));
		
		return new MenuItem(itemName, itemTexture, itemBounds);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
