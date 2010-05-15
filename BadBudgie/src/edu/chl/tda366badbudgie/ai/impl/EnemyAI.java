package edu.chl.tda366badbudgie.ai.impl;

import java.util.List;

import edu.chl.tda366badbudgie.ai.IAI;
import edu.chl.tda366badbudgie.core.game.AbstractCollidable;
import edu.chl.tda366badbudgie.core.game.Enemy;
import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.game.Player;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.util.Polygon;
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
		
		List<Enemy> enemies = gr.getLevel().getEnemies();
		for (Enemy e : enemies) {
			if (e.getGroundContactVector().getLength() != 0 && e.getAttackTimer() == 0) {
				// The object is AI controlled, has ground contact and is currently not attacking
				
				// See if player is nearby
				Player p = gr.getPlayer();
				
				double pdx = p.getX() - e.getX();
				double pdy = p.getY() - e.getY();
				
				if (Math.signum(pdx * e.getDirection()) > 0 && (pdx * pdx + pdy * pdy) < Math.pow(e.getSightDistance(), 2)) {
					e.setAttackTimer(200);
				}
				
				Vector bBoxS = e.getCollisionData(true).getBoundingBoxSize();
				Vector bBoxP = e.getCollisionData(true).getBoundingBoxPosition();
				
				Vector leftGroundCheck = new Vector(bBoxP.getX() - chkOffsX, bBoxP.getY() - chkOffsY);
				Vector rightGroundCheck = new Vector(bBoxP.add(bBoxS).getX() + chkOffsX,  bBoxP.getY() - chkOffsY);
				Vector leftCollCheck = new Vector(bBoxP.getX() - chkOffsX, e.getY());
				Vector rightCollCheck = new Vector(bBoxP.add(bBoxS).getX() + chkOffsX, e.getY());
				
				boolean rightHindrance = true;
				boolean leftHindrance = true;
				
				/* This line introduces a circular dependency, so be sure to comment it out when not in use:
				 * DebugInfoRenderer.getInstance().addDebugLine(leftGroundCheck, rightGroundCheck, Color.black);
				 */
				
				// TODO: Opportunity for optimization by checking only close objects
				
				// Check for absence of ground if front of the enemy
				for (TerrainSection t : gr.getLevel().getTerrainSections()) {
					if (Polygon.isPointInPolygon(leftGroundCheck, t.getCollisionData(true))) {
						leftHindrance = false;
					}
					if (Polygon.isPointInPolygon(rightGroundCheck, t.getCollisionData(true))) {
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
						if (c != p && !enemies.contains(c) && AbstractCollidable.isPhysicalCollision(c, e)) {
							// Ignore non-physical collisions and the player
							if (e.getDirection() == -1 && Polygon.isPointInPolygon(leftCollCheck, c.getCollisionData(true))) {
								leftHindrance = true;
								break;
							}
							else if (e.getDirection() == 1 && Polygon.isPointInPolygon(rightCollCheck, c.getCollisionData(true))) {
								rightHindrance = true;
								break;
							}
						}
					}
					for (AbstractCollidable c : gr.getLevel().getTerrainSections()) {
						if (e.getDirection() == -1 && Polygon.isPointInPolygon(leftCollCheck, c.getCollisionData(true))) {
							leftHindrance = true;
							break;
						}
						else if (e.getDirection() == 1 && Polygon.isPointInPolygon(rightCollCheck, c.getCollisionData(true))) {
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
			else if (e.getAttackTimer() > 0) {
				e.aimAtPlayer();
				e.shoot();
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
