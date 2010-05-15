package edu.chl.tda366badbudgie.util;

/**
 * Holds screen data
 * 
 * @author kvarfordt
 *
 */
public class Screen {


	private static int sWidth;
	private static int sHeight;

	
	public static void setScreenWidth(int screenWidth) {
		sWidth = screenWidth;
	}


	public static int getScreenWidth() {
		return sWidth;
	}


	public static void setScreenHeight(int screenHeight) {
		sHeight = screenHeight;
	}


	public static int getScreenHeight() {
		return sHeight;
	}
	
}
