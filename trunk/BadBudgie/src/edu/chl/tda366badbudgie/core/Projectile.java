package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Vector;

public class Projectile extends AbstractItem {
	 
	private int lifeTimer = 200;
	private boolean hasCollided = false;
	private int damage = 10;
	
	public Projectile(String texId, double x, double y, Player player){
		
		setSize(new Vector(10, 10));

		Vector v = player.getProjectileForce(x, -y);
		v = v.scalarDivision(1000);
		
		setForce(v.scalarMultiplication(100));
		sprite = new Sprite(texId);
	}
	


	@Override
	public GameRoundMessage update() {
		if (--lifeTimer == 0 || hasCollided)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
	}
	
	
	

	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		if (!(other instanceof Player) && AbstractCollidable.isPhysicalCollision(this.getClass(), other.getClass())) {
			hasCollided = true;
		}
	}


	public int getDamage() {
		return damage;
	}
	
}
