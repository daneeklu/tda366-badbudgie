package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
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
	
	private Polygon surface;			//The Quad object that stores the terrain data.
	private String texId;
	private double texRes;
	
	/**
	 * Creates a new TerrainQuad from the given polygon and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(Polygon surface, double friction, double elasticity) {
		super(surface, friction, elasticity);
		this.surface = surface;
		stationary = true;
	}
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param surface list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(Polygon surface, double friction, double elasticity, String texId, double texRes) {
		super(surface, friction, elasticity);
		stationary = true;
		this.surface = surface;
		this.setTexId(texId);
		this.setTexRes(texRes);
	}
	

	/**
	 * Returns the Polygon object forming this TerrainSection
	 * @return the Polygon object
	 */
	public Polygon getSurface() {
		return surface;
	}


	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// Collisions has no effects on terrain
	}

	/**
	 * @param texId the texId to set
	 */
	public void setTexId(String texId) {
		this.texId = texId;
	}

	/**
	 * @return the texId
	 */
	public String getTexId() {
		return texId;
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

}
