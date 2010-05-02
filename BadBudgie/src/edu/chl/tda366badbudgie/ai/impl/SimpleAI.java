package edu.chl.tda366badbudgie.ai.impl;

import edu.chl.tda366badbudgie.core.Enemy;
import edu.chl.tda366badbudgie.util.Vector;

public class SimpleAI {
	private Enemy enemy;
	private Vector groundContact;
	private Vector controlPosition = new Vector(9999, 9999);
	
	public SimpleAI(Enemy enemy){
		this.enemy = enemy;
		//enemy.applyForce(new Vector(5, 0));
		enemy.moveRight(true);
	}

	public void initAI() {
		
		groundContact = enemy.getGroundContactVector();
		
		if(new Vector(1, 0).dotProduct(groundContact) == 0 || 
				new Vector(-1, 0).dotProduct(groundContact) == 0){
			if(!isTooClose()){
				changeDirection();
			}
		}
	}
	
	/**
	 * Changes the direction of the enemy
	 */
	public void changeDirection(){
		setControlPosition();
		if(enemy.getDirection().equals("left")){
			//System.out.println("Changes direction to Left");
			enemy.moveLeft(false);
			enemy.moveRight(true);
		}
		else if(enemy.getDirection().equals("right")){
			//System.out.println("Changes direction to Right");
			enemy.moveLeft(true);
			enemy.moveRight(false);
		}
		
		/*
		 * Gives the enemy a little boost if he gets stuck in the graphics,
		 * often caused when starting in a steep hill. 
		 */
		enemy.applyForce(new Vector(0,1));
	}
	
	
	/**
	 * Sets the position from which the non-turning radius will be calculated
	 */
	public void setControlPosition(){
		controlPosition = enemy.getPosition();
	}
	
	/**
	 * Checks if the enemy is too close to the turning area.
	 * @return true if the turning point is too close for a turn.
	 */
	public boolean isTooClose(){
		//Gets the distance between the enemy and the non-turning radius
		double d = controlPosition.getX() - enemy.getX();
		
		//Declares a radius from the turning point, where the enemy may not turn again.
		if(d > 25 || d < -25){
			return false;
		}
		return true;
	}
	
	/**
	 * If there is a collision with something, this method will override the non-turning radius
	 * and change the direction of the enemy.
	 * @param b that should be true if a collision has happened.
	 */
	public void isCollision(boolean b){
		if(b){
			changeDirection();
		}
	}

}
