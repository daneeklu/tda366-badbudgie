package edu.chl.tda366badbudgie.ai.impl;

import edu.chl.tda366badbudgie.core.Enemy;
import edu.chl.tda366badbudgie.core.Vector;

public class SimpleAI {
	private Enemy enemy;
	private Vector controlPosition = new Vector(9999, 9999);
	public SimpleAI(Enemy enemy){
		this.enemy = enemy;
		enemy.applyForce(new Vector(5, 0));
		enemy.moveRight(true);
	}

	public void calculateMove() {
		
		Vector v = enemy.getGroundContactVector();
		
		if(new Vector(4, 0).dotProduct(v) == 0 || new Vector(-4, 0).dotProduct(v) == 0){
			
			if(enemy.getDirection().equals("left") && posControl()){
				System.out.println("Left");
				setControlPosition();
				enemy.moveLeft(false);
				enemy.moveRight(true);
			}
			else if(enemy.getDirection().equals("right") && posControl()){
				System.out.println("Right");
				setControlPosition();
				enemy.moveLeft(true);
				enemy.moveRight(false);
			}
		}
	}
	
	public void setControlPosition(){
		controlPosition = enemy.getPosition();
		System.out.println(controlPosition);
	}
	
	
	public boolean posControl(){
		double d = controlPosition.getX() - enemy.getX();
		if(d > 10 || d < -10){
			return true;
		}
		return false;
	}

}
