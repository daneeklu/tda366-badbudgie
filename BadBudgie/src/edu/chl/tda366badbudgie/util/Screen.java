package edu.chl.tda366badbudgie.util;

/**
 * Holds and services screen dimension and coordinates.
 * 
 * @author kvarfordt
 * 
 */
public class Screen {

	private static int sWidth;
	private static int sHeight;

	/**
	 * Sets the screen width.
	 * 
	 * @param screenWidth
	 */
	public static void setScreenWidth(int screenWidth) {
		sWidth = screenWidth;
	}

	/**
	 * Returns the screen width.
	 * 
	 * @return the screen width
	 */
	public static int getScreenWidth() {
		return sWidth;
	}

	/**
	 * Sets the screen height.
	 * 
	 * @param screenHeight
	 */
	public static void setScreenHeight(int screenHeight) {
		sHeight = screenHeight;
	}

	/**
	 * Returns the screen height.
	 * 
	 * @return the screen height
	 */
	public static int getScreenHeight() {
		return sHeight;
	}

	/**
	 * Translates the given screen coordinates into world coordinates.
	 * 
	 * @param screenCoords
	 * @param cameraWorldPosition
	 * @return
	 */
	public static Vector screenToWorldCoordinates(Vector screenCoords,
										   Vector cameraWorldPosition) {
		return new Vector(cameraWorldPosition.getX() - getScreenWidth() / 2
				+ screenCoords.getX(), cameraWorldPosition.getY()
				- (double) getScreenHeight() / 2 + (double) getScreenHeight()
				- screenCoords.getY());
	}

}
