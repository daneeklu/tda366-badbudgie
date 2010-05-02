package edu.chl.tda366badbudgie.core;

import java.util.List;


/**
 * TerrainSection
 * 
 * Class representing a segment of a levels terrain.
 * 
 * @author kvarfordt
 *
 */
public class TerrainSection extends AbstractCollidable {
	
	private Quad quad;			//The Quad object that stores the terrain data.
	private String texId;
	private double texRes;
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(List<Vector> vertices, double friction, double elasticity) {
		super(new Polygon(vertices), friction, elasticity);
		this.quad = new Quad(vertices.get(0),vertices.get(1),vertices.get(2),vertices.get(3));
		stationary = true;
	}
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(List<Vector> vertices, double friction, double elasticity, String texId, double texRes) {
		super(new Polygon(vertices), friction, elasticity);
		this.quad = new Quad(vertices.get(0),vertices.get(1),vertices.get(2),vertices.get(3));
		stationary = true;
		this.setTexId(texId);
		this.setTexRes(texRes);
	}
	

	/**
	 * Returns the Quad object making this TerrainSection
	 * @return the Quad object
	 */
	public Quad getQuad() {
		return quad;
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
