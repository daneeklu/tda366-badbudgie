package edu.chl.tda366badbudgie.core;

public class Projectile extends AbstractItem {
	 
	private GameRound gameRound;
	private Vector startPos;
	private Projectile projectile;
	
	
	public Projectile(String texId, double x, double y, Projectile projectile){
		//System.out.println("Success!");
		this.projectile = projectile;
		startPos = new Vector(x, y);
		
		setSize(new Vector(20, 20));
		
		Vector v = getForce(x, y);
		setForce(v.scalarMultiplication(1));
		setVelocity(v);
		
		sprite = new Sprite(texId);
		
	}
	
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector getForce(double x, double y){
		if(x < 400){
			x = -1*getModTen(x);
		}
		else
			x = getModTen(x);
		
		if(y < 300){
			y = -1*getModTen(y);
		}
		else
			y = getModTen(y);
		
		return new Vector(x, y);
	}
	
	
	/**
	 * Calculates the d parameter to be less than 10
	 * @param d
	 * @return
	 */
	public double getModTen(double d){
		while(d>100){
			System.out.println(d);
			d = d%10;
			System.out.println(d);
		}
		return d;
	}

	
	@Override
	public void updateState(){
		
		//TODO: Garbage control, maybe not here?
//		if(startPos.getX() > startPos.getX()+1000 ||
//		startPos.getX() < startPos.getX()-1000 ||
//		startPos.getY() > startPos.getY()+1000 ||
//		startPos.getY() < startPos.getY()-1000){
//			System.out.println("Garbage control fixed: " + projectile);
//			this.projectile = null;
//			
//		}
	}
	
	

	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		// TODO Auto-generated method stub
		
	}
	
}
