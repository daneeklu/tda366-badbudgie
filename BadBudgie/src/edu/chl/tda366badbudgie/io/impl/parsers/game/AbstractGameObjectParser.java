package edu.chl.tda366badbudgie.io.impl.parsers.game;

import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.AbstractGameObject;
import edu.chl.tda366badbudgie.io.impl.parsers.AbstractElementParser;
import edu.chl.tda366badbudgie.io.impl.parsers.geom.VectorParser;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.impl.parsers.util.ParserException;
import edu.chl.tda366badbudgie.io.impl.parsers.util.SpriteParser;
import edu.chl.tda366badbudgie.util.Sprite;
import edu.chl.tda366badbudgie.util.Vector;


/**
 * AbstractGameObjectParser
 * 
 * ElementParser limited to AbstractGameObjects.
 * Every parser associated with a class extending 
 * AbstractGameObject must extends this class.
 * 
 * @author jesper
 *
 */
public abstract class AbstractGameObjectParser extends AbstractElementParser<AbstractGameObject> {

	public AbstractGameObjectParser(Element data) {
		super(data);
	}

	protected Vector getNamedVector(String vectorName){
		return (new VectorParser((Element)getData()
				.getElementsByTagName(vectorName).item(0))).parseData();
	}
	
	protected Vector getPosition() throws ParserException{
		return getNamedVector("position");
	}
	
	protected Vector getSize() throws ParserException{
		return getNamedVector("size");
	}
	
	protected Vector getVelocity() throws ParserException{
		return getNamedVector("velocity");
	}
	
	protected Vector getForce() throws ParserException{
		return getNamedVector("force");
	}
	
	protected Sprite getSprite() throws ParserException{
		return (new SpriteParser((Element)getData().getElementsByTagName("sprite").item(0))).parseData();
	}
	
	protected void initGameObject(AbstractGameObject ago) throws ParserException{
		ago.setMass(ElementTools.getDouble(getData(), "mass"));
		ago.setScale(ElementTools.getDouble(getData(), "scale"));
		ago.setStationary(ElementTools.getBoolean(getData(), "stationary"));
		ago.setSize(getSize());
		ago.setForce(getForce());
		ago.setSprite(getSprite());
		ago.setVelocity(getVelocity());
		ago.setPosition(getPosition());
	}
	
	protected Sprite tempSprite(){
		return new Sprite("");
	}
}
