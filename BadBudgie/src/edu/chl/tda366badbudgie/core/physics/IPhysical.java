package edu.chl.tda366badbudgie.core.physics;

import edu.chl.tda366badbudgie.core.content.IPositioned;
import edu.chl.tda366badbudgie.core.util.Vector;


/**
 * IPhysical
 * 
 * Interface implemented by objects holding physical properties.
 * @author jesper
 *
 */
public interface IPhysical extends IPositioned {
	
	/**
	 * Returns the mass of the object.
	 * @return the mass.
	 */
	public double getMass();
	
	/**
	 * Sets the mass of the object.
	 * @param mass the mass.
	 */
	public void setMass(double mass);
	
	/**
	 * Returns the friction of the object.
	 * @return
	 */
	public double getFriction();
	
	/**
	 * Sets the friction of the object.
	 * @param friction the new friction constant
	 */
	public void setFriction(double friction);
	
	/**
	 * Returns the speed of the object, a vector in the plane.
	 * @return the vector representing the speed of the object.
	 */
	public Vector getSpeed();
	
	/**
	 * Sets the speed of the object.
	 * @param speed the new speed.
	 */
	public void setSpeed(Vector speed);
	
}
