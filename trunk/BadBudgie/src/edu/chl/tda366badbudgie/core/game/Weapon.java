package edu.chl.tda366badbudgie.core.game;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

public class Weapon extends AbstractItem {
	
	// Default constructor parameters
	private static final Vector WEAPON_SIZE = new Vector(120, 120);
	//private static final Sprite WEAPON_SPRITE = new Sprite("gun1");
	private static final Polygon WEAPON_COLLISION_DATA = AbstractCollidable.defaultCollisionData;
	private static final double WEAPON_FRICTION = 0.5;
	private static final double WEAPON_ELASTICITY = 0.2;
	private static final int WEAPON_DAMAGE = 10;
	
	private int damage;
	private String weaponId;
	
	
	public Weapon(Vector position, int damage, Vector size, Sprite sprite, Polygon collisionData, double friction, double elasticity) {
		super(position, size, false, sprite, collisionData, friction, elasticity);
		setDamage(damage);
	}
	
	public Weapon(Vector position, Sprite sprite) {
		this(position, WEAPON_DAMAGE, WEAPON_SIZE, sprite, WEAPON_COLLISION_DATA, WEAPON_FRICTION, WEAPON_ELASTICITY);
	}
	
	
	public void setDamage(int damage){
		this.damage = damage;
	}
	
	public int getDamage(){
		return damage;
	}
	
	
	public String getId(){
		return weaponId;
	}
	
	

	@Override
	public Weapon clone() {
		return (Weapon) super.clone(); 
	}


	/*
	 * COLLISION EFFECT MEMBERS
	 */
	
	@Override
	public List<CollisionStimulus> getCollisionStimulus() {
		LinkedList<CollisionStimulus> stimuli = new LinkedList<CollisionStimulus>();
		stimuli.add(CollisionStimulus.WEAPON);
		return stimuli;
	}
	
	
	
	
}
