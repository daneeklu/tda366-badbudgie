package edu.chl.tda366badbudgie.core;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * SplashScreen
 * 
 * TEMPORARY, WILL PROBABLY BE ALTERED.
 * 
 * @author jesper
 *
 */
public class SplashScreen extends JFrame {

	
	private static final long serialVersionUID = -1888144280413261482L;
	transient Image image;
	
	public SplashScreen(){

		setUndecorated(true);
		setBounds(60, 60, 350, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		try {
			
			BufferedImage img = ImageIO.read(new File("res/splashscreen.png"));
			image = img;
			this.repaint();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g){		
		if(image!=null){
			g.drawImage(image, 0, 0, this);
		}
	}
	
}
