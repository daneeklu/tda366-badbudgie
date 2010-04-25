package edu.chl.tda366badbudgie.gui.render;

import java.util.List;

import edu.chl.tda366badbudgie.core.AbstractGameObject;
import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.core.TerrainSection;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class GameRenderer {

	public static void render(GameRound gameRound, IGraphics g) {
		if(!g.startRendering()) 
			return;
		
		drawTerrain(gameRound.getLevel().getTerrainSections(), g);
		drawGameObjects(gameRound.getLevel().getGameObjects(), g);
		
		if (DebugInfoRenderer.getInstance().isDebugInfoEnabled()) {
			DebugInfoRenderer.getInstance().drawDebugInfo(g);
		}
		
		g.stopRendering();
		
	}
	
	
	private static void drawTerrain(List<TerrainSection> ts, IGraphics g) {
		for (TerrainSection t : ts) {
			g.drawQuad(t.getQuad());
		}
	}
	
	private static void drawGameObjects(List<AbstractGameObject> gos, IGraphics g) {
		for (AbstractGameObject go : gos) {
			g.drawRect(new Rectangle(go.getX()-0.1, go.getY()-0.1,0.2,0.2 ));
		}
	}

}
