package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractCollidable
 * 
 * Abstract class inherited by objects handled by the 
 * collision engine.
 * 
 * @author jesper, kvarfordt
 *
 */
public abstract class AbstractCollidable extends AbstractGameObject {
	
	private Polygon collisionData;
	private double friction;
	private double elasticity;
	
	/**
	 * The set of physical collisions.
	 */
	private static Set<String> physicalCollisions = new HashSet<String>();
	
	/**
	 * Default collision data
	 */
	public final static Polygon defaultCollisionData;
	static {
		Vector v = new Vector(40, 40);
		defaultCollisionData = new Polygon(new ArrayList<Vector>(Arrays.asList(
				new Vector(-v.getX(), -v.getY()), 
				new Vector(v.getX(), -v.getY()), 
				new Vector(v.getX(), v.getY()), 
				new Vector(-v.getX(), v.getY()))));
	}
	
	/**
	 * Creates a new AbstractCollidable object with the given properties.
	 * 
	 * @param collisionData a polygon describing the object collision data
	 * @param friction the friction for the object
	 * @param elasticity the elasticity for collision
	 */
	public AbstractCollidable(Vector position, Vector size, boolean stationary,
			Sprite sprite, Polygon collisionData, double friction, 
			double elasticity) {
		super(position, size, stationary, sprite);
		
		// Make sure the collision data is valid,
		// the polygon must be convex.
		if (!Polygon.checkConvexity(collisionData)) {
			throw new IllegalArgumentException("Collisiondata polygon " +
					"is not convex.");
		} else if (!Polygon.checkCCW(collisionData)) {
			Collections.reverse(collisionData.getVertices());
		}
		
		setCollisionData(collisionData);
		setFriction(friction);
		setElasticity(elasticity);
	}
	
	/**
	 * Returns the collision data of the object.
	 * If no collision data is set an empty list will be returned.
	 * 
	 * @param transformed if true, returns global transformed coordinates.
	 * @return the collision data.
	 */
	public Polygon getCollisionData(boolean transformed) {
		List<Vector> verts = 
			new ArrayList<Vector>(collisionData.getVertices().size());
		
		if (!transformed) {
			
			verts.addAll(collisionData.getVertices());
			
		} else {
			
			for (Vector v: collisionData.getVertices()) {
				v = v.scalarMultiplication(getScale()).add(getPosition());
				verts.add(v);
			}
		}
		return new Polygon(verts);
	}
	
	/**
	 * Returns this objects collision data in object local coordinates. 
	 * If no collision data is set an empty list will be returned.
	 * 
	 * @return the collision data
	 */
	public Polygon getCollisionData(){
		return getCollisionData(false);
	}
	
	/**
	 * Returns the friction coefficient.
	 * 100 = lots of friction, 0 = no friction
	 * 
	 * @return the friction coefficient
	 */
	public double getFriction() {
		return friction;
	}
	
	/**
	 * Returns the elasticity coefficient.
	 * 1 = superball, 0 = lump of clay
	 * @return the elasticity coefficient
	 */
	public double getElasticity() {
		return elasticity;
	}
	
	/**
	 * Sets the collision data to the given polygon
	 * @param cd the collision data polygon
	 */
	public void setCollisionData(Polygon collisionData) {
		this.collisionData = collisionData;
	}
	
	/**
	 * Sets the friction of the object to the given value
	 * @param f the friction value
	 */
	public void setFriction(double friction) {
		this.friction = friction;
	}
	
	/**
	 * Sets the elasticity of the object to the given value
	 * @param e the elasticity value
	 */
	public void setElasticity(double elasticity) {
		this.elasticity = elasticity;
	}
		
	
	/**
	 * Returns true if the two given collidable game objects should 
	 * have a <i>physical</i> collision response.
	 * 
	 * @param object1 - the first object
	 * @param object2 - the second object
	 * @return true if the two objects should have a physical collision response
	 */
	public static boolean isPhysicalCollision(AbstractCollidable object1, 
			AbstractCollidable object2) {
		
		// Using the static list physicalCollisions below.
		// Implementation might change.
		String cn1 = object1.getClass().getSimpleName();
		String cn2 = object2.getClass().getSimpleName();
		String concat;
		
		// Sort the two class names lexicographically
		if (cn1.compareTo(cn2) < 0) {
			concat = cn1 + "-" + cn2;
		}
		else {
			concat = cn2 + "-" + cn1;
		}
		
		// See if the combined string of names is in physicalCollisions
		if (physicalCollisions.contains(concat))
			return true;
		
		return false;
	}
	
	/**
	 * Adds this objects class and the given class to the physical collision 
	 * set.
	 * 
	 * A game object class uses this method to specify which other 
	 * classes should have a physical collision response when colliding.
	 * Preferably this method should be called from the constructor of the 
	 * class.
	 * 
	 * Note that it's sufficient to call this method from one of the two 
	 * classes, and it still gives a mutual collision response.
	 * 
	 * @param other a string 
	 */
	protected void addPhysicalCollision(
			Class<? extends AbstractCollidable> otherClass) {
		
		String cn1 = this.getClass().getSimpleName();
		String cn2 = otherClass.getSimpleName();
		String concat;
		
		// Sort the two class names lexicographically
		if (cn1.compareTo(cn2) < 0) {
			concat = cn1 + "-" + cn2;
		}
		else {
			concat = cn2 + "-" + cn1;
		}
		
		physicalCollisions.add(concat);
	}
	
	/**
	 * Collision effect method to be overridden by subclasses that have 
	 * side effects from a collision (except the physical response).
	 * 
	 * This method is called whether the two object have a physical collision 
	 * response or not, to apply other effects such as damage or finishing the 
	 * level.
	 *  
	 * @param other the other object
	 * @param mtv minimum translation vector, 
	 * points towards this object from the other object
	 */
	public void collisionEffect(AbstractCollidable other, Vector mtv) { }
	
	@Override
	public AbstractCollidable clone() {
		return (AbstractCollidable) super.clone();
	}
	
}
