package edu.chl.tda366badbudgie.core;

/**
 * IGameObject
 * 
 * Interface implemented by game objects.
 * 
 * @author jesper
 * 
 */
public abstract class AbstractGameObject {

	private Vector position;
	private Vector velocity;
	private Vector force;
	private double mass;
	protected boolean stationary;
	
	
	public AbstractGameObject() {
		position = new Vector();
		velocity = new Vector();
		force = new Vector();
		mass = 1;
		stationary = false;
	}
	
	
	/**
	 * Returns a double which is the world coordinate in the x-axis.
	 * @return the x-coordinate of the position.
	 */
	public double getX() {
		return position.getX();
	}

	/**
	 * Returns a double which is the world coordinate in the y-axis.
	 * @return the y-coordinate of the position.
	 */
	public double getY() {
		return position.getY();
	}
	
	/**
	 * Returns a vector to the objects world coordinate.
	 * @return position vector
	 */
	public Vector getPosition() {
		return (Vector) position.clone();
	}

	/**
	 * Returns the velocity vector of the object
	 * @return the objects velocity vector
	 */
	public Vector getVelocity() {
		return (Vector) velocity.clone();
	}
	
	/**
	 * Returns the force acting on the object
	 * @return the objects velocity vector
	 */
	public Vector getForce() {
		return (Vector) force.clone();
	}
	
	/**
	 * Returns the mass of the object.
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Assigns a double to the world coordinate in the x-axis.
	 * @param x the value of the new x-coordinate.
	 */
	public void setX(double x) {
		position = new Vector(x, position.getY());
	}

	/**
	 * Assigns a double to the world coordinate in the y-axis.
	 * @param y the value of the new y-coordinate.
	 */
	public void setY(double y) {
		position = new Vector(position.getX(), y);
	}
	
	/**
	 * Set the world coordinate of the object to the given vector.
	 * @param v the new position
	 */
	public void setPosition(Vector v) {
		position = (Vector) v.clone();
	}
	
	/**
	 * Translate the object by the given vector.
	 * @param v the translation vector
	 */
	public void translate(Vector v) {
		position = position.add(v);
	}
	
	/**
	 * Sets the velocity of the object to the given vector
	 * @param v the velocity vector
	 */
	public void setVelocity(Vector v) {
		velocity = (Vector) v.clone();
	}

	/**
	 * Sets the current force acting on the object
	 * @param v the velocity vector
	 */
	public void setForce(Vector v) {
		force = (Vector) v.clone();
	}

	/**
	 * Sets the current force acting on the object
	 * @param v the velocity vector
	 */
	public void applyForce(Vector v) {
		force = force.add(v);
	}
	
	/**
	 * Sets the mass of the object.
	 * @param mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	/**
	 * Returns whether the object is stationary. That is if it is immovable.
	 * @return
	 */
	public boolean isStationary() {
		return stationary;
	}

	/**
	 * Method to be overridden by objects that have movement logic.
	 */
	public void updateForces() {}
	
	/**
	 * Method to be overridden by objects that have inner state logic.
	 */
	public void updateState() {}
	
	
	
	
}
