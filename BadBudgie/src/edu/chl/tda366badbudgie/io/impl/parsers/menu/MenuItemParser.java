package edu.chl.tda366badbudgie.io.impl.parsers.menu;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.menu.MenuItem;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractElementParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.util.Rectangle;

public class MenuItemParser extends AbstractElementParser<MenuItem> {

	public MenuItemParser(Element data) {
		super(data);
	}

	@Override
	public MenuItem parseData() throws ParserException {
		
		String id = ElementTools.getString(getData(), "id");
		String texId = ElementTools.getString(getData(), "tex");
		String[] boundsStrings = ElementTools.getString(getData(), "bounds").split(",");
		
		int x = Integer.parseInt(boundsStrings[0]);
		int y = Integer.parseInt(boundsStrings[1]);
		int w = Integer.parseInt(boundsStrings[2]);
		int h = Integer.parseInt(boundsStrings[3]);
		
		return new MenuItem(id, texId, new Rectangle(x, y, w, h));
	}

}
