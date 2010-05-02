package edu.chl.tda366badbudgie.core;

public class Projectile extends AbstractItem {
	 
	public Projectile(String texId, double x, double y, Player player){
		
		setSize(new Vector(10, 10));

		Vector v = player.getProjectileForce(x, -y);
		v = v.scalarDivision(1000);
		
		setForce(v.scalarMultiplication(100));
		sprite = new Sprite(texId);
		
	}
	


	
	
	
	@Override
	public void updateState(){
		
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
