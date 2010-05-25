package edu.chl.tda366badbudgie.io.parsers.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.chl.tda366badbudgie.core.game.AbstractGameObject;
import edu.chl.tda366badbudgie.core.game.Level;
import edu.chl.tda366badbudgie.core.game.TerrainSection;
import edu.chl.tda366badbudgie.io.parsers.AbstractDocumentParser;
import edu.chl.tda366badbudgie.io.parsers.util.ElementTools;
import edu.chl.tda366badbudgie.io.parsers.util.ParserException;
import edu.chl.tda366badbudgie.io.parsers.util.ParserTools;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * LevelParser
 * Parses level data, creating new level instances from xml data.
 * 
 * @author jesper
 *
 */
public class LevelParser extends AbstractDocumentParser<Level>{
	
	public LevelParser(Document levelData) {
		super(levelData);
	}
	
	@Override
	/**
	 * Parses the data and returns a level
	 * 
	 * @throws ParserException if an error occurs.
	 */
	public Level parseData() throws ParserException {

		preParse("instobjects", "defobjects");
		preParse("instterrain", "defterrain");
		Document data = getData();
		
		//Get start position
		Element playerPos = (Element)data.getElementsByTagName("playerposition").item(0);
		Vector startPos = new Vector(ElementTools.getDouble(playerPos, "x"), ElementTools.getDouble(playerPos, "y"));
		
		//Init level
		Level level = new Level(1, startPos);
		
		//Loop through terrain elements, call parsers and add.
		List<Element> terrainSections = ElementTools.
			getImmediateChildren((Element)data.
					getElementsByTagName("instterrain").item(0));
		for(Element terrain : terrainSections){
			
			level.addTerrainSection((TerrainSection)(ParserTools.getElementParser(terrain)).parseData());			
		}
		
		//Loop through game objects, call parsers and add.
		List<Element> gameObjects = ElementTools.
		getImmediateChildren((Element)data.
				getElementsByTagName("instobjects").item(0));
		for(Element gameob : gameObjects){
			level.addGameObject((AbstractGameObject)(ParserTools.getElementParser(gameob)).parseData());			
		}
		
		return level;
	}
	
	/**
	 * Creates full, valid Elements from instances of defined Elements.
	 * Instanced elements can either copy all attributes and children from 
	 * the defined target, or overwrite an arbitrary number of child nodes and
	 * elements.
	 * 
	 * @throws ParserException if a defined object doesn't contain a valid identifier.
	 */
	private void preParse(String instTagName, String defTagName) throws ParserException{
		Document data = getData();
		Map<String, Element> defMap = new HashMap<String, Element>();
		Element defined = (Element)data.getElementsByTagName(defTagName).item(0);
		Element instances = (Element)data.getElementsByTagName(instTagName).item(0);
		
		//Store all defined objects
		List<Element> defList = ElementTools.getImmediateChildren(defined);
		for(Element def : defList){
			defMap.put(ElementTools.getString(def, "id"), def);
		}
		
		//Loop through all instantiated game objects
		List<Element> instList = ElementTools.getImmediateChildren(instances);
		
		for(Element inst : instList){
			if(inst.hasAttribute("target")){
				//Copy any existing attributes and elements in the instance to the defined and return the result.
				Element fullCopy = ElementTools.copyAll(inst, defMap.get(ElementTools.getString(inst, "target")), true);
				
				//Add the complete element
				instances.insertBefore(fullCopy, inst);
				
				//Remove the old instance
				instances.removeChild(inst);
			}
		}
	}
}