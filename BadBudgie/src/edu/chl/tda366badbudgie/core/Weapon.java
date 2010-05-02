package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Vector;

public class Weapon extends AbstractItem{
	
	private int damage;
	private String weaponId;

	public Weapon(String texId){
		this(texId, 25);
	}
	
	public Weapon(String texId, int damage){
		setFriction(0.5);
		setElasticity(0.2);
		setMass(1);
		this.stationary = true;
		this.damage = damage;
		sprite = new Sprite(texId);
		
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
	public void updateState(){
		
	}
	
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {

	}

	
}
