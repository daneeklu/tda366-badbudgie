package edu.chl.tda366badbudgie.ai.impl;

import java.util.List;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.core.game.AbstractCollidable;
import edu.chl.tda366badbudgie.core.game.AbstractUnit;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.util.StaticUtilityMethods;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Simple AIs for the enemies in the game.
 * 
 * @author kvarfordt
 *
 */
public class EnemyAI implements IAI {
	
	private static double chkOffsX = 10;
	private static double chkOffsY = 10;
	
	public EnemyAI() {}

	@Override
	public void doAI(GameRound gr) {
		
		List<AbstractUnit> units = gr.getLevel().getUnits();
		for (AbstractUnit unit : units) {
			if (unit.isAIControlled() && unit.getGroundContactVector().getLength() != 0) {
				// The object is AI controlled and has ground contact
				
				Vector bBoxS = unit.getCollisionData(true).getBoundingBoxSize();
				Vector bBoxP = unit.getCollisionData(true).getBoundingBoxPosition();
				
				Vector leftGroundCheck = new Vector(bBoxP.getX() - chkOffsX, bBoxP.getY() - chkOffsY);
				Vector rightGroundCheck = new Vector(bBoxP.add(bBoxS).getX() + chkOffsX,  bBoxP.getY() - chkOffsY);
				Vector leftCollCheck = new Vector(bBoxP.getX() - chkOffsX, unit.getY());
				Vector rightCollCheck = new Vector(bBoxP.add(bBoxS).getX() + chkOffsX, unit.getY());
				
				boolean rightHindrance = true;
				boolean leftHindrance = true;
				
				/* This line introduces a circular dependency, so be sure to comment it out when not in use:
				 * DebugInfoRenderer.getInstance().addDebugLine(leftGroundCheck, rightGroundCheck, Color.black);
				 */
				
				// TODO: Opportunity for optimization by checking only close objects
				
				// Check for absence of ground if front of the enemy
				for (TerrainSection t : gr.getLevel().getTerrainSections()) {
					if (StaticUtilityMethods.isPointInPolygon(leftGroundCheck, t.getCollisionData(true))) {
						leftHindrance = false;
					}
					if (StaticUtilityMethods.isPointInPolygon(rightGroundCheck, t.getCollisionData(true))) {
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
						if (!units.contains(c) && AbstractCollidable.isPhysicalCollision(c, unit)) {
							// Ignore non-physical collisions and the player
							if (unit.getDirection() == -1 && StaticUtilityMethods.isPointInPolygon(leftCollCheck, c.getCollisionData(true))) {
								leftHindrance = true;
								break;
							}
							else if (unit.getDirection() == 1 && StaticUtilityMethods.isPointInPolygon(rightCollCheck, c.getCollisionData(true))) {
								rightHindrance = true;
								break;
							}
						}
					}
					for (AbstractCollidable c : gr.getLevel().getTerrainSections()) {
						if (unit.getDirection() == -1 && StaticUtilityMethods.isPointInPolygon(leftCollCheck, c.getCollisionData(true))) {
							leftHindrance = true;
							break;
						}
						else if (unit.getDirection() == 1 && StaticUtilityMethods.isPointInPolygon(rightCollCheck, c.getCollisionData(true))) {
							rightHindrance = true;
							break;
						}
					}
				}
				
				if (rightHindrance) {
					unit.setDirection(-1);
				}
				if (leftHindrance) {
					unit.setDirection(1);
				}
				
			}
		}
		
	}
	
	
	/**
	 * Transforms collision data of an object from local to global coordinates.
	 * @param ac collidable object
	 * @return globalized coordinates in a polygon.
	 *//*
	private Polygon globColData(AbstractCollidable ac){
		List<Vector> offsColData1 = new LinkedList<Vector>(); //Object 1.
		for (Vector v : ac.getCollisionData().getVertices()) {
			offsColData1.add(v.add(ac.getPosition()));
		}		
		return new Polygon(offsColData1);
	}*/
}
