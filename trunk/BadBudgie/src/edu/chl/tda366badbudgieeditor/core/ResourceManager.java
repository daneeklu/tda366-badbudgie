package edu.chl.tda366badbudgieeditor.core;

import java.util.LinkedList;
import java.util.List;

public class ResourceManager {

	public enum Tool{TerrainTool, ObjectTool};

	public static List<String> getGameObjectNames() {
		return gameObjectNames;
	}
	private static LinkedList<String> gameObjectNames = new LinkedList<String>();
	static {
		gameObjectNames.add("Player");
		gameObjectNames.add("Enemy");
		gameObjectNames.add("Obstacle");
		gameObjectNames.add("Exit");
	}

}
