package edu.chl.tda366badbudgie.io.impl;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.chl.tda366badbudgie.core.ImageDataHandler;

/**
 * GameParser Validates and parses the game xml. Loads texture data to core and
 * level data to levelmanager.
 * 
 * @author jesper
 * 
 */
public class GameParser extends AbstractParser {

	public GameParser(Document xmlDocument) {
		super(xmlDocument);
	}

	@Override
	public void parseData() {
		Document xml = this.getData();
		// Extracts game data
		// Loads and delegates level data
		NodeList levelList = xml.getElementsByTagName("level");

		for (int l = 0; l < levelList.getLength(); l++) {

			String xmlPath = levelList.item(l).getAttributes().getNamedItem(
					"path").getNodeValue();

			// TODO: Break up this sheit properly. Add validation check.
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder db;
			try {
				db = dbFactory.newDocumentBuilder();
				LevelParser levelParser = new LevelParser(db
						.parse(xmlPath));
				levelParser.parseData();

			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			}

		}
		
		// Load image data to data handler in core.
		NodeList textureList = xml.getElementsByTagName("texture");

		for (int i = 0; i < textureList.getLength(); i++) {

			String id = textureList.item(i).getAttributes().getNamedItem("id")
					.getNodeValue();
			String path = textureList.item(i).getAttributes().getNamedItem(
					"path").getNodeValue();

			try {
				ImageDataHandler.getInstance().addImage(id,
						ImageIO.read(new File(path)));
			} catch (IOException e) {
				System.out.println("Failed to load texture: \"" + id
						+ "\" from " + path);
			}
		}

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
