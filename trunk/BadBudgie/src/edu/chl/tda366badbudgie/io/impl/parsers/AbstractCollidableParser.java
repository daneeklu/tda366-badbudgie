package edu.chl.tda366badbudgie.io.impl.parsers;

import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.chl.tda366badbudgie.core.game.AbstractCollidable;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Rectangle;

/**
 * AbstractCollidable parser
 * 
 * Contains methods for quickly extracting data used by AbstractCollision instances.
 * 
 * @author jesper
 *
 */
public abstract class AbstractCollidableParser extends AbstractGameObjectParser {

	public AbstractCollidableParser(Element data) {
		super(data);
	}

	@Override
	public abstract AbstractCollidable parseData() throws ParserException;

	/**
	 * Retrieves collision data through dynamic parsing.
	 * 
	 * Is called and handled automatically through initCollidable()
	 * 
	 * @return a Polygon instance
	 * @throws IllegalArgumentException if polygon data is not valid, i.e non-convex.
	 * @throws SecurityException if security settings denies retrieving classes dynamically.
	 * @throws ClassNotFoundException if polygon parser is not found.
	 * @throws InstantiationException if polygon parser can not be instantiated.
	 * @throws IllegalAccessException if polygon parser can not be accessed.
	 * @throws InvocationTargetException if invocation of the polygon parser fails.
	 * @throws ParserException
	 */
	protected Polygon getCollisionData() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, ParserException{
		Element e = getData();
		Element colData = (Element)e.getElementsByTagName("coldata").item(0);
		if(colData==null){
			throw new ParserException("No defined collisiondata in element: "+e.getTagName());
		}
		Element polygonData = null;
		for(int i=0; i < colData.getChildNodes().getLength(); i++){
			if(colData.getChildNodes().item(i).getNodeType()==Node.ELEMENT_NODE){
				polygonData = (Element) colData.getChildNodes().item(i);
				break;
			}
		}
		
		if(polygonData==null){
			throw new ParserException("No defined collisiondata in element: "+e.getTagName());
		}
		
		try{
			AbstractPolygonParser parser = (AbstractPolygonParser) ParserTools.getElementParser(polygonData);
			return (Polygon)parser.parseData();
		} catch(ClassCastException cce){
			ParserException p = new ParserException("Polygon parsers must inherit AbstractPolygonParser.");
			p.setStackTrace(cce.getStackTrace());
			throw p;
		}
		
	}
	
	protected void initCollidable(AbstractCollidable collidable) throws ParserException{
		initGameObject(collidable);
		collidable.setMass(ElementTools.getDouble(getData(), "mass"));
		collidable.setFriction(ElementTools.getDouble(getData(), "friction"));
		collidable.setElasticity(ElementTools.getDouble(getData(), "elasticity"));
		try {
			collidable.setCollisionData(getCollisionData());
		} catch (IllegalArgumentException e) {
			ParserException p = new ParserException("Non-convex collision data!");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (SecurityException e) {
			ParserException p = new ParserException("Could not access class-loader. Check security settings!");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (ClassNotFoundException e) {
			ParserException p = new ParserException("Could not find polygon-parsing class!");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (InstantiationException e) {
			ParserException p = new ParserException("Could not instantiate polygon-parsing class.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (IllegalAccessException e) {
			ParserException p = new ParserException("Could not access polygon-parsing class.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		} catch (InvocationTargetException e) {
			ParserException p = new ParserException("Invalid constructor parameters.");
			p.setStackTrace(e.getStackTrace());
			throw p;
		}
	}
	
	protected Polygon tempCol(){
		return new Rectangle(1, 1);
	}
	
}