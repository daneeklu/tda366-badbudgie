package edu.chl.tda366badbudgie.core.content;

/**
 * IGameObject
 * 
 * Interface implemented by game objects.
 * 
 * @author jesper
 * 
 */
public abstract class IGameObject {

	private Vector position;
	private Vector velocity;
	private double mass;
	protected boolean stationary = false;
	
	/**
	 * Returns a double which is the world coordinate in the x-axis.
	 * 
	 * @return the x-coordinate of the position.
	 */
	public double getX() {
		return position.getX();
	}

	/**
	 * Returns a double which is the world coordinate in the y-axis.
	 * 
	 * @return the y-coordinate of the position.
	 */
	public double getY() {
		return position.getY();
	}
	
	/**
	 * Returns a vector to the objects world coordinate.
	 * 
	 * @return position vector
	 */
	public Vector getPosition() {
		return (Vector) position.clone();
	}

	/**
	 * Returns the velocity vector of the object
	 * 
	 * @return the objects velocity vector
	 */
	public Vector getVelocity() {
		return (Vector) velocity.clone();
	}
	
	/**
	 * Returns the mass of the object.
	 * 
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Assigns a double to the world coordinate in the x-axis.
	 * 
	 * @param x the value of the new x-coordinate.
	 */
	public void setX(double x) {
		position = new Vector(x, position.getY());
	}

	/**
	 * Assigns a double to the world coordinate in the y-axis.
	 * 
	 * @param y the value of the new y-coordinate.
	 */
	public void setY(double y) {
		position = new Vector(position.getX(), y);
	}
	
	/**
	 * Set the world coordinate of the object to the given vector.
	 * 
	 * @param v the new position
	 */
	public void setPosition(Vector v) {
		position = (Vector) v.clone();
	}
	
	/**
	 * Translate the object by the given vector.
	 * 
	 * @param v the translation vector
	 */
	public void translate(Vector v) {
		position = position.add(v);
	}
	
	/**
	 * Sets the velocity of the object to the given vector
	 * 
	 * @param v the velocity vector
	 */
	public void setVelocity(Vector v) {
		velocity = (Vector) v.clone();
	}

	/**
	 * Returns whether the object is stationary. That is if it is immovable.
	 * @return
	 */
	public boolean isStationary() {
		return stationary;
	}
	
}