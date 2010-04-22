package edu.chl.tda366badbudgie.gui.render;

import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Rectangle;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public class GameRenderer {

	public static void render(GameRound gameRound, IGraphics g) {
		if(!g.startRendering()) 
			return;
		g.setActiveTexture("test");
		g.drawRect(new Rectangle(1.0, 1.0, -2.0, -2.0));
		g.drawText("AppState: ingame");
		g.stopRendering();
	}

}
