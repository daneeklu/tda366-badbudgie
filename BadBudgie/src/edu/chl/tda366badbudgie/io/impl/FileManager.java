package edu.chl.tda366badbudgie.io.impl;

import java.io.IOException;

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
	
	/**
	 * Create a new filemanager, using a
	 * specified graphics object to load
	 * the textures.
	 * @param g the graphics object.
	 */
	public FileManager(){
	}

	@Override
	public void loadData() {
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		try {
			db = dbFactory.newDocumentBuilder();
		 	GameParser gameParser = 
		 		new GameParser(db.parse("config/config.xml"));
		 	gameParser.parseData();
		 	
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
}
