package edu.chl.tda366badbudgie.core;

public class Obstacle extends AbstractCollidable {

	public Obstacle(String texId) {
		setFriction(0.5);
		setElasticity(0.5);
		setMass(1);
		sprite = new Sprite(texId, 1, 1, new Animation("idle", 0));
	}
	
	
	
	@Override
	public void executeCollisionEffect(AbstractCollidable other, Vector mtv) {
		
	}

}
