package edu.chl.tda366badbudgie.core;

import edu.chl.tda366badbudgie.util.Vector;

public class Projectile extends AbstractItem {
	 
	private int lifeTimer = 200;
	
	public Projectile(String texId, double x, double y, Player player){
		
		setSize(new Vector(10, 10));

		Vector v = player.getProjectileForce(x, -y);
		v = v.scalarDivision(1000);
		
		setForce(v.scalarMultiplication(100));
		sprite = new Sprite(texId);
	}
	


	@Override
	public GameRoundMessage update() {
		if (--lifeTimer == 0)
			return GameRoundMessage.RemoveObject;
		
		return GameRoundMessage.NoEvent;
	}
	
	
	

	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		if (mtv.getY() > 0) {
			// Projectile has ground beneath his feet
			// Set ground contact vector to mean of previous and new contact vector, 
			// to allow multiple contacts in one loop
			this.setGroundContactVector(this.getGroundContactVector().add(
					mtv.normalize().scalarDivision(2)));
			this.setGroundContactObject(other);
		}
	}
	
}
