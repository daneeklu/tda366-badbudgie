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
		
		//TODO: Should load images using the texture xml file

		loadImage("newgame", "res/newgame.png");
		loadImage("test", "res/test.png");
		loadImage("smurf", "res/SmurfSpin.png");
		loadImage("budgie", "res/test_alpha.png");		

		loadImage("options", "res/options.png");
		loadImage("exit", "res/exit.png");
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
