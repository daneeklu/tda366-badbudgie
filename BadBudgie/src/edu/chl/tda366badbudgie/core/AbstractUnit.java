package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractUnit
 * 
 * Abstract class representing a unit in the game, such as an enemy or the
 * player.
 * 
 * @author kvarfordt
 * 
 */
public abstract class AbstractUnit extends AbstractCollidable {

	private Vector groundContactVector;
	
	private int health;
	
	/**
	 * Constructs a new AbstractUnit instance.
	 */
	public AbstractUnit(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite, collisionData, friction, elasticity);
		groundContactVector = new Vector();
	}
	
	
	/**
	 * Sets the ground contact vector for the unit.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @param groundContactVector
	 *            the vector of the ground contact
	 */
	public void setGroundContactVector(Vector groundContactVector) {
		this.groundContactVector = groundContactVector;
	}

	/**
	 * Returns the ground contact normal of the unit. If the unit does not have
	 * ground contact, a vector of length zero will be returned.
	 * The magnitude signifies the friction of the contact.
	 * 
	 * @return the ground contact vector
	 */
	public Vector getGroundContactVector() {
		return groundContactVector;
	}

	/**
	 * Sets the health of the unit.
	 * 
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Gets the health of the unit.
	 * 
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	
	
}
