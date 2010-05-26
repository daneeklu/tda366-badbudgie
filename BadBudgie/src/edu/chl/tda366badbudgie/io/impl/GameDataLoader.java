package edu.chl.tda366badbudgie.io.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.chl.tda366badbudgie.core.game.Level;
import edu.chl.tda366badbudgie.core.game.LevelManager;
import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.core.menu.MenuManager;
import edu.chl.tda366badbudgie.io.impl.parsers.game.LevelParser;
import edu.chl.tda366badbudgie.io.impl.parsers.menu.MenuListParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserTools;


/**
 * GameDataLoader
 * Starting point for loading of game data.
 * 
 * @author jesper
 * 
 */
public class GameDataLoader {

	private Document xml;
	
	public GameDataLoader(Document xmlDocument) {
		xml = xmlDocument;
	}
	
	public void loadData() throws ParserException {
		
		//Load and configure parser bindings
		Element parserbindings = (Element)xml.getElementsByTagName("parserbindings").item(0);
		List<Element> groups = ElementTools.getImmediateChildren(parserbindings);
		for(Element g : groups){
			String path = ElementTools.getString(g, "path");
			List<Element> bindings = ElementTools.getImmediateChildren(g);
			for(Element b : bindings){
				ParserTools.bindParser(ElementTools.getString(b, "element"), 
						ElementTools.getString(b, "class"), path);
			}
		}
		
		//Load and delegate menu data
		NodeList menuList = xml.getElementsByTagName("menu");

		for (int l = 0; l < menuList.getLength(); l++) {

			String xmlPath = menuList.item(l).getAttributes().getNamedItem(
					"path").getNodeValue();

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			
			dbFactory.setIgnoringComments(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder db;
			
			try {
				db = dbFactory.newDocumentBuilder();
				MenuListParser menuListParser = new MenuListParser(db
						.parse(xmlPath));
				List<Menu> menus = menuListParser.parseData();
				for(Menu m : menus){
					MenuManager.getInstance().addMenu(m);
				}

			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

		}
		
		
		// Loads and delegates level data
		List<Element> levelList = ElementTools.getImmediateChildren((Element)xml.getElementsByTagName("levels").item(0));

		for (Element level : levelList) {

			String xmlPath = ElementTools.getString(level, "path");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			dbFactory.setIgnoringComments(true);
			dbFactory.setIgnoringElementContentWhitespace(true);
			
			DocumentBuilder db;
			try {
				db = dbFactory.newDocumentBuilder();
				LevelParser levelParser = new LevelParser(db
						.parse(xmlPath));
				Level l = levelParser.parseData();
				l.setNumber(ElementTools.getInteger(level, "nr"));
				LevelManager.getInstance().addLevel(l);

			} catch (Exception e){
				e.printStackTrace();
			}

		}
				
		// Load image data to data handler in core.
		NodeList texList = xml.getElementsByTagName("texture");
		
		for (int i = 0; i < texList.getLength(); i++) {
				Element texture = (Element) texList.item(i);
				String path = ElementTools.getString(texture, "path");
				String id = ElementTools.getString(texture, "id");
				
				//Step up through hierarchy to get file path.
				Element parent = (Element)texture.getParentNode();
				do{
					path = ElementTools.getString(parent, "path") + path;
					parent = (Element)parent.getParentNode();
				} while(parent.hasAttribute("path"));
				
				//Load image data
				try {
					ImageDataHandler.getInstance().addImage(id,
							ImageIO.read(new File(path)));
				} catch (IOException e) {
					System.out.println("Failed to load texture: \"" + id
							+ "\" from " + path);
				}
		}
	}
	
}
