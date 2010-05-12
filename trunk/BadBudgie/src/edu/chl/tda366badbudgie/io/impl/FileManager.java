package edu.chl.tda366badbudgie.io.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.chl.tda366badbudgie.io.IFileManager;


/** 
 * A filemanager, implementing the IFileManager interface.
 * @author d.skalle
 *
 */
public class FileManager implements IFileManager{
	
	@Override
	public void loadData(String configPath) {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		dbFactory.setIgnoringElementContentWhitespace(true);
		try {
			db = dbFactory.newDocumentBuilder();
		 	GameParser gameParser = 
		 		new GameParser(db.parse(configPath));
		 	gameParser.parseData();
		 	
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, BufferedImage> getImageData() {
		return ImageDataHandler.getInstance().getData();
	}
}
