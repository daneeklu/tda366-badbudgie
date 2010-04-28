package edu.chl.tda366badbudgie.core;

public class Weapon extends AbstractUnit{
	
	private int damage;
	private String weaponId;
	
	public Weapon(String weaponId){
		this(weaponId, 25);
	}
	
	public Weapon(String weaponId, int damage){
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		this.damage = damage;
		this.weaponId = weaponId;
		sprite = new Sprite(weaponId, 1, 1, new Animation("idle", 0));
	}
	
	public int getDamage(){
		return damage;
	}
	
	public String getId(){
		return weaponId;
	}

	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// TODO Auto-generated method stub
		
	}

	
}
