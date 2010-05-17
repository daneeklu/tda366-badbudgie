package edu.chl.tda366badbudgie.core.game;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.core.game.GameRound.GameRoundMessage;
import edu.chl.tda366badbudgie.util.Animation;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Screen;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Player
 * 
 * Represents the player unit in the game.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class Player extends AbstractUnit {
	
	// Default constructor parameters
	private static final Vector PLAYER_SIZE = new Vector(120, 120);
	private static final Sprite PLAYER_SPRITE;
	static {
		List<Animation> animations = new LinkedList<Animation>();
		int[] indices = {4,5,6,7,6};
		animations.add(new Animation("idle",0));
		animations.add(new Animation("run",indices,5));
		PLAYER_SPRITE = new Sprite("budgie", 4, 4, animations);
	}
	private static final Polygon PLAYER_COLLISION_DATA = 
		AbstractCollidable.defaultCollisionData;
	private static final double PLAYER_FRICTION = 0.9;
	private static final double PLAYER_ELASTICITY = 0.2;
	
	// Movement constants
	private static final double GROUND_MOVE_FORCE = 5.0;
	private static final double AIR_MOVE_FORCE = 0.2;
	private static final double JUMP_FORCE = 15.0;
	private static final double MAXIMUM_WALK_SPEED = 10.0;
	private static final double MAXIMUM_AIR_SPEED = 5.0;
	
	
	private boolean isMovingLeft;
	private boolean isMovingRight;
	private boolean isJumping;
	private boolean isShooting;
	private double flyingEnergy;
	private double maxFlyingEnergy;
	private boolean flying = false;
	


	/**
	 * Constructor
	 * 
	 * @param position the players position
	 * @param size
	 * @param sprite
	 * @param collisionData
	 * @param friction
	 * @param elasticity
	 */
	public Player(Vector position, Vector size, Sprite sprite, 
			Polygon collisionData, double friction, double elasticity) {
		super(position, size, false, sprite, 
				collisionData, friction, elasticity);
		
		setHealth(100);
		setFlyingEnergy(100);
		setMaxFlyingEnergy(100);
		setAIControlled(false);
		getWeapon().setNozzleOffset(new Vector(65, 8));
		getWeapon().setNozzleSpeed(25);
		
		addPhysicalCollision(TerrainSection.class);
		addPhysicalCollision(Enemy.class);
		addPhysicalCollision(Obstacle.class);
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param position the players position
	 */
	public Player(Vector position) {
		this(position, PLAYER_SIZE, PLAYER_SPRITE, PLAYER_COLLISION_DATA, 
				PLAYER_FRICTION, PLAYER_ELASTICITY);
	}
	
	
	
	/*
	 * 
	 * PLAYER ACTIONS AND LOGIC
	 * 
	 */
	

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
	 * Called by GameRound when the player wants to jump or flap his wings.
	 * 
	 * @param down true if the key was pressed, false if released
	 */
	public void jumpOrFlap(boolean down) {
		
		if (down) {
			if (!getGroundContactVector().hasZeroLength()) {
				isJumping = true;
			}
			else {
				flying = true;
			}
		}
		else {
			flying = false;
		}
		
	}
	
	
	
	@Override
	public void updateForces() {
		
		// The booleans are needed to make the controls work nicely.
		if (isMovingLeft && !isMovingRight) {
			setDirection(-1);
		}
		else if (isMovingRight && !isMovingLeft) {
			setDirection(1);
		}
		else {
			setDirection(0);
		}
		
		// Player wants to move left
		if (getDirection() == -1) {
			if (!getGroundContactVector().hasZeroLength() 
					&& -getVelocity().getX() < MAXIMUM_WALK_SPEED) {
				Vector force = 
					getGroundContactVector().perpendicularCCW()
					.scalarMultiplication(GROUND_MOVE_FORCE)
					.add(new Vector(-AIR_MOVE_FORCE, 0));
				applyForce(force);
			}
			else if (-getVelocity().getX() < MAXIMUM_AIR_SPEED) {
				applyForce(new Vector(-AIR_MOVE_FORCE, 0));
			}
		}
		
		// Player wants to move right
		if (getDirection() == 1) {
			if (!getGroundContactVector().hasZeroLength() 
					&& getVelocity().getX() < MAXIMUM_WALK_SPEED) {
				Vector force = 
					getGroundContactVector().perpendicularCW()
					.scalarMultiplication(GROUND_MOVE_FORCE)
					.add(new Vector(AIR_MOVE_FORCE, 0));
				applyForce(force);
			}
			else if (getVelocity().getX() < MAXIMUM_AIR_SPEED) {
				applyForce(new Vector(AIR_MOVE_FORCE, 0));
			}
		}
		
		// Player should jump
		if (isJumping) {
			applyForce(getGroundContactVector().normalize()
					.project(new Vector(0, 1)).scalarMultiplication(JUMP_FORCE));
			isJumping = false;
		}
		
		// Player is flying
		if (flying && flyingEnergy > 0) {
			double yVel = getVelocity().getY();
			applyForce(new Vector(0, yVel < 0 ? 1.5 : (6 - yVel) / 2));
			
			flyingEnergy -= 2;
		}
		
	}
	
	@Override
	public GameRoundMessage update(){
		
		// Run update on superclass and return its value if it has an event.
		GameRoundMessage superMessage = super.update();
		if (superMessage != GameRoundMessage.NO_EVENT)
			return superMessage;
		
		GameRoundMessage grMessage = GameRoundMessage.NO_EVENT;
		
		if(!getGroundContactVector().hasZeroLength() 
				&& getFlyingEnergy() < maxFlyingEnergy){
			setFlyingEnergy(getFlyingEnergy() + 4);
			if(getFlyingEnergy() > 100){
				setFlyingEnergy(100.0);
			}
		}
		
		if(this.getWeapon() != null){
			getWeapon().setPosition(this.getPosition());
		}

		if (isShooting)
			getWeapon().shoot();
		
		if (flyingEnergy <= 0) {
			flying = false;
		}
		
		if(getInvincibilityTimer() > 0) {
			setInvincibilityTimer(getInvincibilityTimer() - 1);
		}
		
		if (getDirection() != 0) {
			getSprite().setAnimation("run");
		} else {
			getSprite().setAnimation("idle");
		}

		if(getHealth() <= 0) {
			grMessage = GameRoundMessage.PLAYER_DIED;
		}
		
		return grMessage;
	}

	/**
	 * Transform the screen coordinates to world coordinates,
	 * then aim at them
	 * @param mx the screen x coordinate
	 * @param my the screen y coordinate
	 */
	public void setAimScreenCoords(double mx, double my) {
		
		int screenWidth = Screen.getScreenWidth();
		int screenHeight = Screen.getScreenHeight();
		
		setAim(getX() - screenWidth/2 + mx, getY() - screenHeight/2 + screenHeight - my);
		
		double dx = mx - screenWidth/2;
		double dy = screenHeight/2 - my;
		
		double rotation = Math.toDegrees(Math.atan2(dx, dy)) + 90;
		
		if (rotation > 90 && rotation < 270)
			getSprite().setMirrored(true);
		else
			getSprite().setMirrored(false);
		
	}
	
	/**
	 * Set whether the player is shooting
	 * @param shoot true if the player should be shooting
	 */
	public void shootToggle(boolean shoot) {
		isShooting = shoot;
	}
	
	
	/*
	 * 
	 * GETTERS AND SETTERS
	 * 
	 */
	
	
	/**
	 * @param flyingEnergy the flyingEnergy to set
	 */
	public void setFlyingEnergy(double flyingEnergy) {
		this.flyingEnergy = flyingEnergy;
	}

	/**
	 * @return the flyingEnergy
	 */
	public double getFlyingEnergy() {
		return flyingEnergy;
	}
	
	/**
	 * @return the maxFlyingEnergy
	 */
	public double getMaxFlyingEnergy() {
		return maxFlyingEnergy;
	}
	
	/**
	 * @param maxFlyingEnergy the maxFlyingEnergy to set
	 */
	public void setMaxFlyingEnergy(int maxFlyingEnergy) {
		this.maxFlyingEnergy = maxFlyingEnergy;
	}
	

	@Override
	public Player clone() {
		return (Player) super.clone();
	}

	@Override
	public void collisionEffect(AbstractCollidable other, Vector mtv) {
		
		/*
		 * NOTE:
		 * The effect of the collision depends on the class of other. 
		 * We know that switching on class should normally be avoided, 
		 * but we think in this case it's fine, and we have not found a better 
		 * solution.
		 * If a new class is added, you don't want it to have any collision 
		 * effects unless explicitly specified in that class' collisionEffect.
		 * Note that physical collision response (bouncing etc.) is handled by 
		 * a map in AbstractCollidable and not this method.
		 * By using Class objects, it is also type safe.
		 */
		
		Class<? extends AbstractCollidable> otherClass = other.getClass();
		
		if (		otherClass.equals(TerrainSection.class)
				||	otherClass.equals(Obstacle.class) ) {
			if (mtv.getY() > 0) {
				// Player has "ground" beneath his feet
				setGroundContactVector(getGroundContactVector().add(
						mtv.normalize().scalarMultiplication(other.getFriction() + 0.000001)
						.scalarDivision(2))); // +0.000001 to avoid a zero-length vector in case of zero friction
				flying = false;
			}
		}
		
		if (otherClass.equals(Enemy.class)) {
			if (getInvincibilityTimer() == 0) {
				setHealth(getHealth() - ((Enemy)other).getMeleeDamage()) ;
				setInvincibilityTimer(20);
				
				// Bump player away from enemy
				int dir = (int) Math.signum(getX() - other.getX());
				applyForce(new Vector(dir * 6, 6));
			}
		}
		
		if (otherClass.equals(Projectile.class)) {
			Projectile p = (Projectile) other;
			if (p.getOwner() != this) {
				if (getInvincibilityTimer() == 0) {
					setHealth(getHealth() - p.getDamage());
					setInvincibilityTimer(20);
				}
				
				// Total transfer of momentum
				applyForce(p.getVelocity().scalarMultiplication(p.getMass()/getMass()));
			}
		}
	}
	
}
