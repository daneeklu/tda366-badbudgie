package edu.chl.tda366badbudgie.io.impl;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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
