package edu.chl.tda366badbudgie.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import edu.chl.tda366badbudgie.gui.graphics.IGraphics;


public class FileManager implements IFileManager{
	
	private IGraphics g;
	
	public FileManager(IGraphics g) {
		this.g = g;
	}

	@Override
	public void loadData() {
		
		//load a test image with texture id "test"
		//later all resources will be defined in an xml file

		try {
			loadImage("test", "res/test.png");
			loadImage("newgame", "res/newgame.png");

			//image = ImageIO.read(new File("res/test.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		//g.addTexture("test", image);
		
		
		
	}
	
	private void loadImage(String id, String path) throws IOException {
		BufferedImage image;

		image = ImageIO.read(new File(path));
		g.addTexture(id, image);
	}

}
