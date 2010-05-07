package edu.chl.tda366badbudgie.io.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.Animation;
import edu.chl.tda366badbudgie.core.Enemy;
import edu.chl.tda366badbudgie.core.Level;
import edu.chl.tda366badbudgie.core.LevelExit;
import edu.chl.tda366badbudgie.core.LevelManager;
import edu.chl.tda366badbudgie.core.Obstacle;
import edu.chl.tda366badbudgie.core.Player;
import edu.chl.tda366badbudgie.core.Sprite;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.core.Weapon;
import edu.chl.tda366badbudgie.util.Circle;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Rectangle;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * LevelParser
 * Parses level data, creating new level instances from xml data.
 * 
 * @author jesper
 *
 */
public class LevelParser extends AbstractParser{

	//Maps used by private methods.
	private Map<String, Sprite> spriteMap = new HashMap<String, Sprite>();
	private Map<String, Polygon> collisionMap = new HashMap<String, Polygon>();
	
	public LevelParser(Document xmlDocument) {
		
		super(xmlDocument);
	}

	@Override
	public void parseData() {

		Document xmlData = getData();

		//Create level.
		Level level = new Level();
		
		//Create and add player //TODO:Remove this section when it's handled by the GameRound class properly.
		double pX, pY;
		Element playPosL = (Element)xmlData.getElementsByTagName("playerposition").item(0);
		pX = Double.parseDouble(playPosL.getAttribute("x"));
		pY = Double.parseDouble(playPosL.getAttribute("y"));
		
		Player player = new Player(new Vector(pX, pY));
		player.setCollisionData(new Rectangle(-20,-40,40,80));
		Weapon wep = new Weapon(new Vector(player.getX(), player.getY()), new Sprite("gun1"));
		player.setWeapon(wep);
		level.addGameObject(player);
		level.addGameObject(wep);
		
		//TODO: Possibly remove this section
		//Create animations.
		/*
		NodeList animList = ((Element)xmlData.getElementsByTagName("animations").item(0)).getElementsByTagName("animation");
		for(int i = 0; i < animList.getLength(); i++){
			Element anim = (Element)animList.item(i);
			animMap.put(anim.getAttribute("id"), createAnimation(anim));
		}*/
				
		//Create sprites.
		NodeList spriteList = ((Element)xmlData.getElementsByTagName("sprites").item(0)).getElementsByTagName("sprite");
		for(int i = 0; i < spriteList.getLength(); i++){
			Element sprite = (Element)spriteList.item(i);
			spriteMap.put(sprite.getAttribute("id"), createSprite(sprite));
		}
		
		//Load collision data.
		NodeList collList = ((Element)xmlData.getElementsByTagName("collisiondata").item(0)).getElementsByTagName("col");
		for(int i = 0; i < collList.getLength(); i++){
			Element sprite = (Element)collList.item(i);
			collisionMap.put(sprite.getAttribute("id"), createPolygon(sprite));
		}
		
		//Create gameobjects.
		Element defined = (Element)xmlData.getElementsByTagName("definedobjects").item(0);
		Element instanced = (Element)xmlData.getElementsByTagName("placedobjects").item(0);
		List<AbstractGameObject> gameObjList = createGameObjects(defined, instanced);
		
		for(AbstractGameObject ob : gameObjList){
			level.addGameObject(ob);
		}
			
		//Create terrain sections.
		NodeList terrainList = xmlData.getElementsByTagName("terrain");
		for(int i = 0; i < terrainList.getLength(); i++){
			Element ts = (Element) terrainList.item(i);
			level.addTerrainSection(createTS(ts));
		}
		
		//Add level to manager.
		LevelManager.getInstance().addLevel(level);
	}

	private TerrainSection createTS(Element e){
		TerrainSection t;
		Polygon surface;
		
		//Extract terrain attributes
		String spriteID = e.getAttribute("sprite");
		double x = Double.parseDouble(e.getAttribute("x"));
		double y = Double.parseDouble(e.getAttribute("y"));
		double fric = Double.parseDouble(e.getAttribute("friction"));
		double elas = Double.parseDouble(e.getAttribute("elasticity"));
		double res = Double.parseDouble(e.getAttribute("resolution"));
		
		//Extract surface vectors
		surface = createPolygon((Element)e.getElementsByTagName("surface").item(0));
		
		t = new TerrainSection(surface, spriteMap.get(spriteID), fric, elas);
		t.setTexRes(res);
		t.setPosition(new Vector(x, y));
		
		return t;
	}
	
	private Polygon createPolygon(Element e){
		Polygon p;
		//Alternatives based on attributes
		String type = e.getAttribute("type");
		
		if(type.equals("rectangle")){
			
			double width = Double.parseDouble(e.getAttribute("width"));
			double height = Double.parseDouble(e.getAttribute("height"));
			p = new Rectangle(width, height);
			
		} else if(type.equals("circle")){
			
			double radius = Double.parseDouble(e.getAttribute("radius"));
			int res =Integer.parseInt(e.getAttribute("segments"));
			p = new Circle(radius, res);
			
		} else if(type.equals("polygon")){
			List<Vector> vectors = new LinkedList<Vector>();
			
			NodeList vectorData = e.getElementsByTagName("vector");
			for(int i = 0; i < vectorData.getLength(); i++){
				vectors.add(createVector((Element)vectorData.item(i)));
			}
			p = new Polygon(vectors);
			
		} else{
			throw new IllegalArgumentException("NO TYPE DEFINED FOR POLYGON");
		}
		return p;
	}
	
	private Vector createVector(Element e){
		double x = Double.parseDouble(e.getAttribute("x"));
		double y = Double.parseDouble(e.getAttribute("y"));
		return new Vector(x, y);
	}
	
	private List<AbstractGameObject> createGameObjects(Element def, Element inst){
		
		List<AbstractGameObject> l =  new LinkedList<AbstractGameObject>();
		Map<String, Element> defMap = new HashMap<String, Element>();
		
		//Place definitions in map
		NodeList defList = def.getElementsByTagName("object");
		for(int i = 0; i < defList.getLength(); i++){
			Element defElem = (Element) defList.item(i); 
			defMap.put(defElem.getAttribute("obid"), defElem);
		}
		
		//Loop through all instances
		NodeList instList = inst.getElementsByTagName("gameobject");
		for(int i = 0; i < instList.getLength(); i++){
			
			Element instOb = (Element) instList.item(i);
			Element defOb = defMap.get(instOb.getAttribute("id"));
			AbstractGameObject gamOb = instantiateGameObject(defOb);
			
			double px, py, sx, sy;
			
			Element size = (Element)instOb.getElementsByTagName("size").item(0);
			Element pos = (Element)instOb.getElementsByTagName("position").item(0);
			
			px = Double.parseDouble(pos.getAttribute("x"));
			py = Double.parseDouble(pos.getAttribute("y"));
			sx = Double.parseDouble(size.getAttribute("x"));
			sy = Double.parseDouble(size.getAttribute("y"));
			
			gamOb.setSize(new Vector(sx, sy));
			gamOb.setPosition(new Vector(px, py));
			
			l.add(gamOb);
		}
		
		return l;
	}
	
	private AbstractGameObject instantiateGameObject(Element e){
		
		//Currently if statements, admittedly a very poor solution.
		String type = e.getAttribute("type");
		AbstractGameObject obj;
		Vector z = new Vector();
		

		String sID = e.getAttribute("spriteid");
		String cID = e.getAttribute("colid");
		double fric = 1;
		double elas = 0;
		try{
			fric = Double.parseDouble(e.getAttribute("friction"));
			elas = Double.parseDouble(e.getAttribute("elasticity"));
		} catch(NumberFormatException ex){
		}
			//OBSTACLE
			if(type.equals("obstacle")){
				
				boolean stat = e.getAttribute("stationary").equals("true");
				
				obj = new Obstacle(z, z, stat, new Sprite(spriteMap.get(sID)), collisionMap.get(cID), fric, elas);
				
			} 
			//ENEMY
			else if(type.equals("enemy")){
				
				int dam = Integer.parseInt(e.getAttribute("damage"));
				int dir = Integer.parseInt(e.getAttribute("direction"));
				
				obj = new Enemy(z, z, new Sprite(spriteMap.get(sID)), collisionMap.get(cID), fric, elas, dam, dir);	
			} 
			//LEVEL EXIT
			else if(type.equals("exit")){
				obj = new LevelExit(z,z, spriteMap.get(sID), collisionMap.get(cID));
			}
			//ILLEGAL TYPE
			else{
				throw new IllegalArgumentException("Undefined object type");
			}
		
		return obj;
	}
	
	private Animation createAnimation(Element e){
		
		double duration = Double.parseDouble(e.getAttribute("duration"));
		String id = e.getAttribute("id");
		String[] stringInd = e.getAttribute("indices").split(",");
		int[] indices = new int[stringInd.length];
		
		for(int i = 0; i < stringInd.length; i++){
			indices[i] = Integer.parseInt(stringInd[i]);
		}
		
		return new Animation(id, indices, duration);
	}
	
	private Sprite createSprite(Element e){
		
		Sprite s;
		
		String texId = e.getAttribute("textureid");
		
		if(e.getAttribute("animated").equals("true")){
			Element segment = (Element) e.getElementsByTagName("segments").item(0);
			
			int sX = Integer.parseInt(segment.getAttribute("x"));
			int sY = Integer.parseInt(segment.getAttribute("y"));
			
			List<Animation> animList = new LinkedList<Animation>();
			NodeList animElements = e.getElementsByTagName("animation");
			
			for(int i = 0; i < animElements.getLength(); i++){
				animList.add(createAnimation((Element) animElements.item(i)));
			}
			
			s = new Sprite(texId, sX, sY, animList);
			
		} else{
			s = new Sprite(texId);
		}
		return s;
	}
	
	@Override
	public boolean validate() {
		// TODO Setup xsd-based validation of data.
		return false;
	}

}
