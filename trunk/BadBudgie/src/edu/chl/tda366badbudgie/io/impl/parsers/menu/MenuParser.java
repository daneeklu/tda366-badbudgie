package edu.chl.tda366badbudgie.io.impl.parsers.menu;

import java.util.List;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.core.menu.MenuItem;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractElementParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Rectangle;

public class MenuParser extends AbstractElementParser<Menu>{

	public MenuParser(Element data) {
		super(data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Menu parseData() throws ParserException{
		
		String id = ElementTools.getString(getData(), "id");
		String texId = ElementTools.getString(getData(), "tex");
		String[] boundsStrings = ElementTools.getString(getData(), "bounds").split(",");
		
		int x = Integer.parseInt(boundsStrings[0]);
		int y = Integer.parseInt(boundsStrings[1]);
		int w = Integer.parseInt(boundsStrings[2]);
		int h = Integer.parseInt(boundsStrings[3]);
		
		List<Element> menItemElems = ElementTools.getImmediateChildren(getData());
		MenuItem[] menuItems = new MenuItem[menItemElems.size()];
		
		for(int i = 0; i < menItemElems.size(); i++){
			menuItems[i] = (new MenuItemParser(menItemElems.get(i))).parseData();
		}
		
		return new Menu(id, texId, new Rectangle(x, y, w, h), menuItems);
	}

}
