package edu.chl.tda366badbudgie.ai.impl;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.core.AbstractCollidable;
import edu.chl.tda366badbudgie.core.AbstractUnit;
import edu.chl.tda366badbudgie.core.Enemy;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Projectile;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.util.StaticUtilityMethods;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Simple AIs for the enemies in the game.
 * 
 * @author kvarfordt
 *
 */
public class EnemyAI implements IAI {
	
	private static double checkOffsetX = 5;
	private static double checkOffsetY = -15;
	
	public EnemyAI() {}

	@Override
	public void doAI(GameRound gr) {
		
		for (AbstractCollidable ago : gr.getLevel().getCollidableObjects()) {
			// For each ago that is an enemy
			if (ago instanceof Enemy && ago.getGroundContactVector().getLength() != 0) {
				// The object is an enemy and has ground contact
				
				Enemy e = (Enemy) ago;
				
				Vector leftGroundCheck = new Vector(e.getX() - e.getWidth()/2 - checkOffsetX, e.getY() - e.getHeight()/2 - checkOffsetY);
				Vector rightGroundCheck = new Vector(e.getX() + e.getWidth()/2 + checkOffsetX, e.getY() - e.getHeight()/2 - checkOffsetY);
				Vector leftCollCheck = new Vector(e.getX() - e.getWidth()/2 - checkOffsetX, e.getY());
				Vector rightCollCheck = new Vector(e.getX() + e.getWidth()/2 + checkOffsetX, e.getY());
				
				boolean rightHindrance = false;
				boolean leftHindrance = false;
				
				// TODO: Opportunity for optimization by checking only close objects
				
				// Check for absence of ground if front of the enemy
				for (TerrainSection t : gr.getLevel().getTerrainSections()) {
					if (!StaticUtilityMethods.checkPointCollision(leftGroundCheck, t.getCollisionData())) {
						leftHindrance = true;
					}
					if (!StaticUtilityMethods.checkPointCollision(rightGroundCheck, t.getCollisionData())) {
						rightHindrance = true;
					}
				}
				
				// Fix for thin ground
				if (leftHindrance && rightHindrance) {
					leftHindrance = rightHindrance = false;
				}
				
				// Check for obstructing objects in the enemy's path
				if (!rightHindrance && !leftHindrance) {
					for (AbstractCollidable c : gr.getLevel().getCollidableObjects()) {
						if (!(c instanceof AbstractUnit) && !(c instanceof Projectile) && AbstractCollidable.isPhysicalCollision(c.getClass(), e.getClass())) {
							// Ignore non-physical collisions and the player
							if (e.getDirection() == -1 && StaticUtilityMethods.checkPointCollision(leftCollCheck, c.getCollisionData())) {
								leftHindrance = true;
								break;
							}
							else if (e.getDirection() == 1 && StaticUtilityMethods.checkPointCollision(rightCollCheck, c.getCollisionData())) {
								rightHindrance = true;
								break;
							}
						}
					}
					for (AbstractCollidable c : gr.getLevel().getTerrainSections()) {
						if (e.getDirection() == -1 && StaticUtilityMethods.checkPointCollision(leftCollCheck, c.getCollisionData())) {
							leftHindrance = true;
							break;
						}
						else if (e.getDirection() == 1 && StaticUtilityMethods.checkPointCollision(rightCollCheck, c.getCollisionData())) {
							rightHindrance = true;
							break;
						}
					}
				}

				if (rightHindrance) {
					e.setDirection(-1);
				}
				if (leftHindrance) {
					e.setDirection(1);
				}
				
			}
			
		}
		
	}
}
