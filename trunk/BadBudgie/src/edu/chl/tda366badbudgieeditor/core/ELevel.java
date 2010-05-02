package edu.chl.tda366badbudgieeditor.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Editor representation of a level.
 * 
 * @author kvarfordt
 *
 */
public class ELevel {
	
	private ArrayList<EGameObject> gameObjects = new ArrayList<EGameObject>();
	private ArrayList<ETerrainSection> terrainSections = new ArrayList<ETerrainSection>();
	
	
	public List<ETerrainSection> getTerrainSections() {
		return terrainSections;
	}
	
	public void addTerrainSection(ETerrainSection t) {
		terrainSections.add(t);
		notifyLevelChanged();
	}
	
	public void removeTerrainSection(ETerrainSection t) {
		terrainSections.remove(t);
		notifyLevelChanged();
	}
	
	public List<EGameObject> getGameObjects() {
		return gameObjects;
	}
	
	public void addGameObject(EGameObject t) {
		gameObjects.add(t);
		notifyLevelChanged();
	}
	
	public void removeGameObject(EGameObject t) {
		gameObjects.remove(t);
		notifyLevelChanged();
	}
	
	
	
	
	
	
	private List<ILevelObserver> observers = new LinkedList<ILevelObserver>();
	
	
	public void addObserver(ILevelObserver observer) {
		observers.add(observer);
	}
	
	public void notifyLevelChanged() {
		for (ILevelObserver o : observers) {
			o.levelChanged();
		}
	}
	
}
