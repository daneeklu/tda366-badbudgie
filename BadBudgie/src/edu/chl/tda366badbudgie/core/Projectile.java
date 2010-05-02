package edu.chl.tda366badbudgie.core;

public class Projectile extends AbstractItem {
	 
	private GameRound gameRound;
	private Vector vector;
	
	public Projectile(String texId, double x, double y, GameRound gameRound){
		
		this.gameRound = gameRound;
		vector = gameRound.getPlayer().getPosition();
		setSize(new Vector(20, 20));

//		System.out.println("Mouse Pos: " + new Vector(x, y));
//		System.out.println("Player Pos: " + vector);
//		System.out.println("Resulting force: " + getForce(x, y).scalarMultiplication(20));

		Vector v = getForce(x, -y);
		v = v.scalarDivision(1000);
		
		System.out.println("Resulting force: " + v);
		
		setForce(v.scalarMultiplication(100));
		//setVelocity(v);
		
		sprite = new Sprite(texId);
		
	}
	
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector getForce(double x, double y){
		int resWidth = 800;
		int resHeight = -600;
		System.out.println(y);
		
		//If x is smaller then width/2 then calculate how far 
		if(x < resWidth/2){
			x = x-resWidth/2;
		}
		if(x > resWidth/2){
			x = x-resWidth/2;
		}
		
		
		if(y > resHeight/2){
			y = y-resHeight/2;
			System.out.println("> " + y);
		}
		
		if(y < resHeight/2){
			y = y-resHeight/2;
			System.out.println("< " + y);
		}		
		
		System.out.println(x + " " + y);
		
		
		return new Vector(x, y);
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
