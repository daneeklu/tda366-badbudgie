package edu.chl.tda366badbudgie.core.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
	 * Creates a new AbstractCollidable object with the given properties.
	 * 
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 */
	public AbstractCollidable(Vector position, Vector size, boolean stationary, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, stationary, sprite);
		
		// Make sure the collision data is valid
		if (!Polygon.checkConvexity(collisionData)) {
			throw new IllegalArgumentException("Collisiondata polygon is not convex.");
		} else if (!Polygon.checkCCW(collisionData)) {
			Collections.reverse(collisionData.getVertices());
		}
		
		setCollisionData(collisionData);
		setFriction(friction);
		setElasticity(elasticity);

		collisionResponses = new HashMap<CollisionStimulus, CollisionEffect>();
	}
	
	/**
	 * Returns the collision data of the object.
	 * If no collision data is set an empty list will be returned.
	 * 
	 * @return the collision data.
	 */
	public Polygon getCollisionData(boolean transformed) {
		List<Vector> verts = new ArrayList<Vector>(collisionData.getVertices().size());
		if(!transformed){
		verts.addAll(collisionData.getVertices());}
		else{
			for(Vector v: collisionData.getVertices()){
				v = v.scalarMultiplication(getScale()).add(getPosition());
				verts.add(v);
			}
		}
		return new Polygon(verts);
	}
	
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
	public void setCollisionData(Polygon cd) {
		collisionData = cd;
	}
	
	/**
	 * Sets the friction of the object to the given value
	 * @param f the friction value
	 */
	public void setFriction(double f) {
		friction = f;
	}
	
	/**
	 * Sets the elasticity of the object to the given value
	 * @param e the elasticity value
	 */
	public void setElasticity(double e) {
		elasticity = e;
	}
		
	
	/*
	 * COLLISION EFFECT MEMBERS
	 */

	/**
	 * Enum whose values define different stimuli in a collision.
	 * 
	 * @author kvarfordt
	 *
	 */
	public enum CollisionStimulus{NO_EFFECT, PLAYER, WALKABLE_GROUND, INJURER, IMPACT, LEVEL_EXIT, IMPENETRABLE, WEAPON};
	
	/**
	 * Interface for collision effects. Collision effects have the method run which executes the effect on the object.
	 * 
	 * @author kvarfordt
	 *
	 */
	public static interface CollisionEffect {
		public void run(AbstractCollidable other, Vector mtv);
	}
	
	/**
	 * Map between stimuli and effect.
	 */
	private HashMap<CollisionStimulus, CollisionEffect> collisionResponses;
	
	/**
	 * Adds an entry with an effect for a given stimulus.
	 * 
	 * @param stimulus
	 * @param effect
	 */
	protected void addCollisionResponse(CollisionStimulus stimulus, CollisionEffect effect) {
		collisionResponses.put(stimulus, effect);
	}
	
	/**
	 * Returns the effect for the given stimulus for this object.
	 * 
	 * @param stimulus the stimulus
	 * @return the collision effect
	 */
	private CollisionEffect getCollisionEffect(CollisionStimulus stimulus) {
		if (collisionResponses.containsKey(stimulus)) {
			return collisionResponses.get(stimulus);
		}
		else {
			return new NoEffect();
		}
	}
	
	/**
	 * Returns a list of the stimulus this object exercises.
	 * 
	 * @return
	 */
	protected abstract List<CollisionStimulus> getCollisionStimulus();
	
	/**
	 * Executes the correct effect on this object given the collision with object 'other'.
	 * 
	 * @param other the colliding object
	 * @param mtv minimum translation vector for the collision
	 */
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		for (CollisionStimulus cs : other.getCollisionStimulus()) 
			respondTo(cs, other, mtv);
	}

	/**
	 * Gets and runs the right effect from the given stimulus.
	 * 
	 * @param stimulus the stimulus
	 * @param other the other object
	 * @param mtv the minimum translation vector for the collision
	 */
	private void respondTo(CollisionStimulus stimulus, AbstractCollidable other, Vector mtv) {
		CollisionEffect effect = getCollisionEffect(stimulus);
		effect.run(other, mtv);
	}
	
	/**
	 * Effect class with no effect. Returned if the stimulus in getCollisionEffect has no effect.
	 * 
	 * @author kvarfordt
	 *
	 */
	private static class NoEffect implements CollisionEffect {
		@Override
		public void run(AbstractCollidable other, Vector mtv) {}
	}
	
	
	/**
	 * Returns true if the two given game objects should collide physically.
	 * 
	 * @param class1
	 * @param class2
	 * @return
	 */
	public static boolean isPhysicalCollision(AbstractCollidable class1, AbstractCollidable class2) {
		/*
		 * Using the static list physicalCollisions below.
		 * Implementation might change.
		 */
		String cn1 = class1.getClass().getSimpleName().toLowerCase();
		String cn2 = class2.getClass().getSimpleName().toLowerCase();
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
	 * Adds the given class name to the physical collision set for this object.
	 * 
	 * @param other a string 
	 */
	protected void addPhysicalCollision(Class<? extends AbstractCollidable> otherClass) {
		
		String cn1 = this.getClass().getSimpleName().toLowerCase().trim();
		String cn2 = otherClass.getSimpleName().toLowerCase().trim();
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
	
	@Override
	public AbstractCollidable clone() {
		return (AbstractCollidable) super.clone();
	}
	
}
