package edu.chl.tda366badbudgieeditor.core;

/**
 * Editor representation of a game object.
 * 
 * @author kvarfordt
 *
 */
public class EGameObject {

	/**
	 * This enum lists all the available object types.
	 */
	public enum ObjectType{Player, Enemy, Obstacle, Exit};
	
	private ObjectType type;
	private EVector position;
	
	public EGameObject(int x, int y, ObjectType type) {
		this.setType(type);
		this.position = new EVector(x, y);
	}

	public void setPosition(EVector position) {
		this.position = position;
	}

	public EVector getPosition() {
		return position;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public ObjectType getType() {
		return type;
	}
	
	public int getX() {
		return position.getX();
	}
	
	public int getY() {
		return position.getY();
	}
	
	public void setX(int x) {
		position.setX(x);
	}
	
	public void setY(int y) {
		position.setY(y);
	}
	
}
