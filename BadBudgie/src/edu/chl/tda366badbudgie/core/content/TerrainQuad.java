package edu.chl.tda366badbudgie.core.content;

import java.util.List;

import edu.chl.tda366badbudgie.core.physics.ICollidable;

/**
 * Class representing a segment of the level terrain.
 * 
 * @author dkvarfordt
 *
 */
public class TerrainQuad extends Quad implements ICollidable {
	
	
	// Friction coefficient. 1 = instant stop, 0 = no friction
	private double friction;
	
	// Elasticity coefficient. 1 = superball, 0 = lump of clay
	private double elasticity;
	
	
	/**
	 * Creates a new TerrainQuad from the given vertices and properties.
	 * 
	 * @param vertices list of vectors representing the vertices of the quad.
	 * @param friction 
	 * @param elasticity
	 */
	public TerrainQuad(List<Vector> vertices, double friction, double elasticity) {
		super(vertices);
		this.friction = friction;
		this.elasticity = elasticity; 
	}
	
	@Override
	public Polygon getCollisionData() {
		return new Polygon(vertices);
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getFriction() {
		return friction;
	}

	@Override
	public double getElasticity() {
		return elasticity;
	}

	@Override
	public void translate(Vector v) {
		// TODO Auto-generated method stub
	}

}
