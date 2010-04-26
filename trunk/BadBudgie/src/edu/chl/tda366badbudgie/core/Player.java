package edu.chl.tda366badbudgie.core;

import java.awt.Color;

import edu.chl.tda366badbudgie.gui.render.DebugInfoRenderer;

/**
 * Player
 * 
 * Represents the player unit in the game.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class Player extends AbstractUnit {
	
	private static final double MOVE_FORCE = 2.0;
	private static final double AIR_MOVE_FORCE = 0.2;
	private static final double JUMP_FORCE = 16.0;
	private static final double GLIDE_FORCE_RATIO = 0.15;
	private static final double[] FLYING_FORCE = {0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4, 3, 2, 0, -2, -2, -1, -0.5};
	
	private int health;
	private int flyingEnergy;
	
	private int wingTimer;
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private boolean isFlying;
	
	/**
	 * Constructor
	 * 
	 * @param texId the texture id of the player.
	 */
	public Player(String texId) {
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		health = 100;
		flyingEnergy = 100;
		wingTimer = 0;
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}


	/**
	 * Called by GameRound when the player wants to move left
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void moveLeft(boolean down) {
		isMovingLeft = down;
	}

	/**
	 * Called by GameRound when the player wants to move right
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void moveRight(boolean down) {
		isMovingRight = down;
	}
	
	/**
	 * Called by GameRound when the player wants to jump or fly
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void jump(boolean down) {
		if (down) {
			
			Vector groundContactVector = getGroundContactVector();
			
			if (!groundContactVector.hasZeroLength()) {
				// Instant jump
				applyForce(groundContactVector.normalize().scalarMultiplication(JUMP_FORCE));
			}
			else {
				// Start wing flap
				isFlying = true;
				if (flyingEnergy >= 5 && wingTimer == 0) {
					flyingEnergy -= 20;
					wingTimer = 20;
				}
			}
		}
		else {
			isFlying = false;
		}
		
	}
	
	@Override
	public void updateForces() {
		
		// Player wants to move left
		if (isMovingLeft) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCCW().scalarMultiplication(MOVE_FORCE));
			}
			else {
				applyForce(new Vector(-1, 0).scalarMultiplication(AIR_MOVE_FORCE));
			}
		}
		
		// Player wants to move right
		if (isMovingRight) {
			if (!getGroundContactVector().hasZeroLength()) {
				applyForce(getGroundContactVector().perpendicularCW().scalarMultiplication(MOVE_FORCE));
			}
			else {
				applyForce(new Vector(1, 0).scalarMultiplication(AIR_MOVE_FORCE));
			}
		}
		
		
		// Wings flapping
		if (wingTimer > 0) {
			// Flying straight up
			if (!isMovingLeft && !isMovingRight) {
				applyForce(new Vector(0, FLYING_FORCE[wingTimer-1]));
			}
			// Flying to the left
			if (isMovingLeft) {
				applyForce(new Vector(FLYING_FORCE[wingTimer-1] * -0.2, FLYING_FORCE[wingTimer-1] * 0.7));
			}
			// Flying to the right
			if (isMovingRight) {
				applyForce(new Vector(FLYING_FORCE[wingTimer-1] * 0.2, FLYING_FORCE[wingTimer-1] * 0.7));
			}
		}
		
		// Gliding
		if (isFlying) {
			if (getVelocity().getY() < 0) {
				applyForce(new Vector(0, GLIDE_FORCE_RATIO * -getVelocity().getY()));
			}
			// Gliding force due to horizontal movement
			applyForce(new Vector(0, GLIDE_FORCE_RATIO / 15 * Math.abs(getVelocity().getX())));
		}
		
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
			DebugInfoRenderer.getInstance().addDebugLine(getPosition(), getPosition().add(getForce().scalarMultiplication(100)), Color.red);
			DebugInfoRenderer.getInstance().addDebugLine(getPosition(), getPosition().add(getVelocity().scalarMultiplication(70)), Color.blue);
			DebugInfoRenderer.getInstance().addDebugText("Player");
			DebugInfoRenderer.getInstance().addDebugText("x:" + getX() + " y:" + getY());
			DebugInfoRenderer.getInstance().addDebugText("vx:" + getVelocity().getX() + " vy:" + getVelocity().getY());
			DebugInfoRenderer.getInstance().addDebugText("fx:" + getForce().getX() + " fy:" + getForce().getY());
			DebugInfoRenderer.getInstance().addDebugText("FlyingEnergy:" + flyingEnergy);
			DebugInfoRenderer.getInstance().addDebugText("WingTimer:" + wingTimer);
		}
		
	}
	
	@Override
	public void updateState(){
		if(flyingEnergy < 100){
			flyingEnergy += 1;
		}
		
		if (wingTimer > 0) {
			wingTimer--;
		}
	}
	
}
