package edu.chl.tda366badbudgie.io.impl.parsers.menu;

import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractDocumentParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;

/**
 * Creates lists of Menu instances from xml-documents.
 * 
 * @author d.skalle, jesper
 *
 */
public class MenuListParser extends AbstractDocumentParser<List<Menu>>{

	public MenuListParser(Document menuData) {
		super(menuData);
	}

	//@Override
	public List<Menu> parseData(){
		Document xml = getData();
		
		List<Menu> menuL = new LinkedList<Menu>();
		
		// Load every menu in the file
		NodeList menuList = xml.getElementsByTagName("menu");

		for (int l = 0; l < menuList.getLength(); l++) {
			Menu menu;
			try {
				menu = (new MenuParser((Element)menuList.item(l))).parseData();
				menuL.add(menu);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (ParserException e) {
				e.printStackTrace();
			} 
		}
		return menuL;
	}

	// TODO Auto-generated catch block
}
