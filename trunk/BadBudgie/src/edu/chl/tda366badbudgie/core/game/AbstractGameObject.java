package edu.chl.tda366badbudgie.core.game;

import java.util.LinkedList;
import java.util.List;

import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * AbstractGameObject
 * 
 * Interface implemented by game objects.
 * 
 * @author jesper, kvarfordt, d.skalle
 * 
 */
public abstract class AbstractGameObject implements Cloneable {

	/**
	 * Enum containing possible "passback values" from an AbstractGameObject to the GameRound
	 */
	public enum GameRoundMessage{NO_EVENT, LEVEL_FINISHED, PLAYER_DIED, REMOVE_OBJECT};
	
	private Vector position;
	private Vector velocity;
	private Vector force;
	private Vector size;
	private Sprite sprite;
	private Level parent;
	private double mass = 1;
	private double scale = 1;
	private boolean stationary;
	private double airResistance = 1.001;
	
	private List<AbstractGameObject> children = new LinkedList<AbstractGameObject>();
	
	/**
	 * Constructor for AbstractGameObject.
	 * 
	 * @param position the position of the object
	 * @param size the size of the object
	 * @param stationary true if the object should be immovable
	 */
	public AbstractGameObject(Vector position, Vector size, boolean stationary, Sprite sprite){
		setPosition(position);
		setSize(size);
		setStationary(stationary);
		setSprite(sprite);
		setVelocity(new Vector());
		setForce(new Vector());
	}
	
	/**
	 * Returns a double which is the world coordinate in the x-axis.
	 * @return the x-coordinate of the position.
	 */
	public double getX() {
		return position.getX();
	}

	/**
	 * Returns a double which is the world coordinate in the y-axis.
	 * @return the y-coordinate of the position.
	 */
	public double getY() {
		return position.getY();
	}
	
	/**
	 * Returns a vector to the objects world coordinate.
	 * @return position vector ...
	 */
	public Vector getPosition() {
		return position;
	}

	/**
	 * Returns the velocity vector of the object
	 * @return the objects velocity vector
	 */
	public Vector getVelocity() {
		return velocity;
	}
	
	/**
	 * Returns the force acting on the object
	 * @return the objects velocity vector
	 */
	public Vector getForce() {
		return force;
	}
	
	/**
	 * Returns the mass of the object.
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * Assigns a double to the world coordinate in the x-axis.
	 * @param x the value of the new x-coordinate.
	 */
	public void setX(double x) {
		position = new Vector(x, position.getY());
	}

	/**
	 * Assigns a double to the world coordinate in the y-axis.
	 * @param y the value of the new y-coordinate.
	 */
	public void setY(double y) {
		position = new Vector(position.getX(), y);
	}
	
	/**
	 * Set the world coordinate of the object to the given vector.
	 * @param v the new position
	 */
	public void setPosition(Vector v) {
		position = v;
	}
	
	/**
	 * Translate the object by the given vector.
	 * @param v the translation vector
	 */
	public void translate(Vector v) {
		position = position.add(v);
	}
	
	/**
	 * Sets the velocity of the object to the given vector
	 * @param v the velocity vector
	 */
	public void setVelocity(Vector v) {
		velocity = v;
	}

	/**
	 * Sets the current force acting on the object
	 * @param v the velocity vector
	 */
	public void setForce(Vector v) {
		force = v;
	}

	/**
	 * Sets the current force acting on the object
	 * @param v the velocity vector
	 */
	public void applyForce(Vector v) {
		force = force.add(v);
	}
	
	/**
	 * Sets the mass of the object.
	 * @param mass
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * Method to be overridden by objects that have movement logic.
	 */
	public void updateForces() {}
	
	/**
	 * Method to be overridden by objects that have 
	 * inner logic to update each cycle.
	 * 
	 * @return a GameRoundMessage specifying if an event which must be dealt 
	 * with in GameRound has occurred.
	 */
	public GameRoundMessage update() {
		return GameRoundMessage.NO_EVENT;
	}

	/**
	 * Returns the sprite of the object.
	 * @return the sprite of the object.
	 */
	public Sprite getSprite() {
		return sprite;
	}

	/**
	 * Sets the sprite of the object.
	 * @param sprite the sprite of the object.
	 */
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Returns the width of the object.
	 * @return the width of the object.
	 */
	public double getWidth() {
		return size.getX();
	}

	/**
	 * Returns the height of the object.
	 * @return the height of the object.
	 */
	public double getHeight() {
		return size.getY();
	}
	
	/**
	 * Gets the size of the object.
	 * @return the size
	 */
	public Vector getSize(){
		return size;
	}
	
	/**
	 * Sets the size of the object.
	 * @param v the vector setting the new size.
	 */
	public void setSize(Vector v){
		this.size = v;
	}

	/**
	 * Returns the level
	 * @return
	 */
	public Level getParent() {
		try {
			return parent;
		} 
		catch (NullPointerException npe) {
			throw new IllegalStateException("The parent level of the object has " +
					"not been set, most likely because the object has not been added to a level.");
		}
	}


	public void setParent(Level parent) {
		this.parent = parent;
	}


	public void setAirResistance(double airResistance) {
		this.airResistance = airResistance;
	}


	public double getAirResistance() {
		return airResistance;
	}


	public void setStationary(boolean stationary) {
		this.stationary = stationary;
	}

	/**
	 * Returns whether the object is stationary. That is if it is immovable.
	 * 
	 * @return true if stationary
	 */
	public boolean isStationary() {
		return stationary;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getScale() {
		return scale;
	}
	
	@Override
	public AbstractGameObject clone() {
		try {
			AbstractGameObject ago = (AbstractGameObject) super.clone();
			ago.sprite = sprite.clone();
			return ago;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Clone not supported");
		}
	}

	public void addChild(AbstractGameObject child) {
		children.add(child);
	}

	public List<AbstractGameObject> getChildren() {
		return children;
	}
	
}
