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
	
	//Offset for checking for the ground.
	private static double checkOffsetX = 10;
	private static double checkOffsetY = 25;
	
	public EnemyAI() {}

	@Override
	public void doAI(GameRound gr) {
		
		List<Enemy> enemies = gr.getLevel().getEnemies();
		for (Enemy e : enemies) {
			if (e.getGroundContactVector().getLength() != 0 
					&& e.getAttackTimer() == 0) {
				// The object is AI controlled, has ground contact and is currently not attacking
				
				// See if player is nearby
				Player p = gr.getPlayer();
				double pdx = p.getX() - e.getX();
				double pdy = p.getY() - e.getY();
				
				if (Math.signum(pdx * e.getDirection()) > 0 
						&& (pdx * pdx + pdy * pdy) 
							< Math.pow(e.getSightDistance(), 2)) {
					// Player is in sight, attack
					e.setAttackTimer(200);
				}
				
				boolean rightHindrance = true;
				boolean leftHindrance = true;
				
				Vector bBoxS = 
					e.getCollisionData(true).getBoundingBoxSize();
				Vector bBoxP = 
					e.getCollisionData(true).getBoundingBoxPosition();

				// Generate points for ground check
				Vector leftGroundCheck = 
					new Vector(bBoxP.getX() - checkOffsetX, 
							   bBoxP.getY() - checkOffsetY);
				Vector rightGroundCheck = 
					new Vector(bBoxP.add(bBoxS).getX() + checkOffsetX,  
							   bBoxP.getY() - checkOffsetY);
				
				// TODO: Opportunity for optimization by checking only close obj
				
				// Check for ground in front of e
				for (TerrainSection t : gr.getLevel().getTerrainSections()) {
					if (Polygon.isPointInPolygon(
							leftGroundCheck, t.getCollisionData(true))) {
						leftHindrance = false;
					}
					if (Polygon.isPointInPolygon(
							rightGroundCheck, t.getCollisionData(true))) {
						rightHindrance = false;
					}
				}
				
				// Fix for thin ground
				if (leftHindrance && rightHindrance) {
					leftHindrance = rightHindrance = false;
				}
				
				// Generate points for collision check
				Vector leftCollCheck = 
					new Vector(bBoxP.getX() - checkOffsetX, 
							   e.getY());
				Vector rightCollCheck = 
					new Vector(bBoxP.add(bBoxS).getX() + checkOffsetX, 
							   e.getY());
				
				// Check for obstructing objects in the enemy's path
				if (!rightHindrance && !leftHindrance) {
					
					// Check for obstructing game objects
					for (AbstractCollidable c 
							: gr.getLevel().getCollidableObjects()) {
						if (c != p 
								&& !enemies.contains(c) 
								&& AbstractCollidable.isPhysicalCollision(c, e)
								) {
							
							if (e.getDirection() == -1 
									&& Polygon.isPointInPolygon(
											leftCollCheck, 
											c.getCollisionData(true))) {
								// e is facing left and has obstacle in front
								leftHindrance = true;
								break;
							}
							else if (e.getDirection() == 1 
									&& Polygon.isPointInPolygon(
											rightCollCheck, 
											c.getCollisionData(true))) {
								// e is facing right and has obstacle in front
								rightHindrance = true;
								break;
							}
							
						}
					}
					
					// Check for obstructing terrain
					for (AbstractCollidable c 
							: gr.getLevel().getTerrainSections()) {
						
						if (e.getDirection() == -1 
								&& Polygon.isPointInPolygon(
										leftCollCheck, 
										c.getCollisionData(true))) {
							// e is facing left and has obstacle in front
							leftHindrance = true;
							break;
						}
						else if (e.getDirection() == 1 
								&& Polygon.isPointInPolygon(
										rightCollCheck, 
										c.getCollisionData(true))) {
							// e is facing right and has obstacle in front
							rightHindrance = true;
							break;
						}
						
					}
					
				}
				
				if (rightHindrance) {
					e.setDirection(-1);
				}
				else if (leftHindrance) {
					e.setDirection(1);
				}
				else if (e.getTimeInCurrDir() > 100 
						&& (e.getVelocity().getLength()) < 0.1) {
					// stops e from getting stuck
					e.setDirection(e.getDirection()*-1); 
				}
				
			}
			else if (e.getAttackTimer() > 0) {
				e.aimAtPlayer();
				e.shoot();
			}
		}
		
	}
}
