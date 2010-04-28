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
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(List<Vector> vertices, double friction, double elasticity) {
		super(new Polygon(vertices), friction, elasticity);
		//TODO: check validity of vertices?
		this.quad = new Quad(vertices);
		stationary = true;
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

}
