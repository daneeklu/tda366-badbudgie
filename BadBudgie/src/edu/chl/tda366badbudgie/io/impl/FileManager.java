package edu.chl.tda366badbudgie.io.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.io.IFileManager;


/**
 * A filemanager, implementing the IFileManager interface.
 * @author daniel
 *
 */
public class FileManager implements IFileManager{
	
	private IGraphics g;
	
	/**
	 * Create a new filemanager, using a
	 * specified graphics object to load
	 * the textures.
	 * @param g the graphics object.
	 */
	public FileManager(IGraphics g) {
		this.g = g;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		//Temp loading
		loadImage("test", "res/test.png");
		loadImage("exit", "res/exit.png");
		loadImage("smurf", "res/SmurfSpin.png");
		loadImage("newgame", "res/newgame.png");	
		loadImage("options", "res/options.png");
		loadImage("budgie", "res/test_alpha.png");
	}
	
	private void loadImage(String id, String path) {
		BufferedImage image;

		try {
			image = ImageIO.read(new File(path));
			g.addTexture(id, image);
		} catch (IOException e) {
			System.out.println("COULDN'T LOAD TEXTURE: " + id + " at \"" + path + "\"");
		}
	}
}
