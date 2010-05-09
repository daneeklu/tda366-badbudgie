package edu.chl.tda366badbudgie.ai.impl;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.core.AbstractCollidable;
import edu.chl.tda366badbudgie.core.AbstractUnit;
import edu.chl.tda366badbudgie.core.Enemy;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Projectile;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.util.Polygon;
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
	private static double checkOffsetY = 5;
	
	public EnemyAI() {}

	@Override
	public void doAI(GameRound gr) {
		
		for (AbstractCollidable ago : gr.getLevel().getCollidableObjects()) {
			// For each ago that is an enemy
			if ((ago instanceof Enemy) && ((AbstractUnit) ago).getGroundContactVector().getLength() != 0) {
				// The object is an enemy and has ground contact
				
				Enemy e = (Enemy) ago;
				
				Vector leftGroundCheck = new Vector(e.getX() - e.getWidth()/2 - checkOffsetX, e.getY() - e.getHeight()/2 - checkOffsetY);
				Vector rightGroundCheck = new Vector(e.getX() + e.getWidth()/2 + checkOffsetX, e.getY() - e.getHeight()/2 - checkOffsetY);
				Vector leftCollCheck = new Vector(e.getX() - e.getWidth()/2 - checkOffsetX, e.getY());
				Vector rightCollCheck = new Vector(e.getX() + e.getWidth()/2 + checkOffsetX, e.getY());
				
				boolean rightHindrance = true;
				boolean leftHindrance = true;
				
				/* This line introduces a circular dependency, so be sure to comment it out when not in use:
				 * DebugInfoRenderer.getInstance().addDebugLine(leftGroundCheck, rightGroundCheck, Color.black);
				 */
				
				// TODO: Opportunity for optimization by checking only close objects
				
				// Check for absence of ground if front of the enemy
				for (TerrainSection t : gr.getLevel().getTerrainSections()) {
					if (StaticUtilityMethods.checkPointCollision(leftGroundCheck, globColData(t))) {
						leftHindrance = false;
					}
					if (StaticUtilityMethods.checkPointCollision(rightGroundCheck, globColData(t))) {
						rightHindrance = false;
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
							if (e.getDirection() == -1 && StaticUtilityMethods.checkPointCollision(leftCollCheck, globColData(c))) {
								leftHindrance = true;
								break;
							}
							else if (e.getDirection() == 1 && StaticUtilityMethods.checkPointCollision(rightCollCheck, globColData(c))) {
								rightHindrance = true;
								break;
							}
						}
					}
					for (AbstractCollidable c : gr.getLevel().getTerrainSections()) {
						if (e.getDirection() == -1 && StaticUtilityMethods.checkPointCollision(leftCollCheck, globColData(c))) {
							leftHindrance = true;
							break;
						}
						else if (e.getDirection() == 1 && StaticUtilityMethods.checkPointCollision(rightCollCheck, globColData(c))) {
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
	
	
	/**
	 * Transforms collision data of an object from local to global coordinates.
	 * @param ac collidable object
	 * @return globalized coordinates in a polygon.
	 */
	private Polygon globColData(AbstractCollidable ac){
		List<Vector> offsColData1 = new LinkedList<Vector>(); //Object 1.
		for (Vector v : ac.getCollisionData().getVertices()) {
			offsColData1.add(v.add(ac.getPosition()));
		}		
		return new Polygon(offsColData1);
	}
}
