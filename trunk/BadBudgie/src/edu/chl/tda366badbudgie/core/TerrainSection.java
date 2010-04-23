package edu.chl.tda366badbudgie.core;

import java.util.List;


/**
 * Class representing a segment of the level terrain.
 * 
 * @author dkvarfordt
 *
 */
public class TerrainSection extends AbstractCollidable {
	
	private Quad quad;			//The Quad object that stores the terrain data.
	private double friction;	// Friction coefficient. ((100 = a lot, 0 = no friction) might change) 
	private double elasticity;	// Elasticity coefficient. 1 = superball, 0 = lump of clay
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainSection(List<Vector> vertices, double friction, double elasticity) {
		//TODO: check validity of vertices
		this.quad = new Quad(vertices);
		this.friction = friction;
		this.elasticity = elasticity;
		stationary = true;
	}
	
	@Override
	public Polygon getCollisionData() {
		// Terrain quads have the same collision data and vertices.
		return new Polygon(quad.getVertices());
	}

	@Override
	public double getFriction() {
		return friction;
	}

	@Override
	public double getElasticity() {
		return elasticity;
	}

	/**
	 * Returns the Quad object making this TerrainSection
	 * @return the Quad object
	 */
	public Quad getQuad() {
		return quad;
	}

}
