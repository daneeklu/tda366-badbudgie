package edu.chl.tda366badbudgie.core.game;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;


/**
 * TerrainSection
 * 
 * Class representing a segment of a levels terrain.
 * 
 * @author kvarfordt
 *
 */
public class TerrainSection extends AbstractCollidable {
	
	// Default constructor parameters
	private static final Sprite TERRAIN_SPRITE = new Sprite("grass1");
	private static final double TERRAIN_FRICTION = 0.4;
	private static final double TERRAIN_ELASTICITY = 0.5;
	
	private double texRes = 0.0005;

	/**
	 * Creates a new Terrain instance from the given polygon and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the polygon.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(Polygon surface, Sprite sprite, double friction, 
			double elasticity) {
		super(new Vector(), new Vector(), true, sprite, surface, friction, 
				elasticity);
	}
	
	public TerrainSection(Polygon surface) {
		this(surface, TERRAIN_SPRITE, TERRAIN_FRICTION, TERRAIN_ELASTICITY);
	}
	
	
	/**
	 * Returns the Polygon object forming this TerrainSection, 
	 * in this case the same as the collision data.
	 * @return the Polygon object
	 */
	public Polygon getSurface() {
		return getCollisionData();
	}
	
	/**
	 * @param texRes the texRes to set
	 */
	public void setTexRes(double texRes) {
		this.texRes = texRes;
	}

	/**
	 * @return the texRes
	 */
	public double getTexRes() {
		return texRes;
	}

	@Override
	public TerrainSection clone() {
		return (TerrainSection) super.clone();
	}
	
}
