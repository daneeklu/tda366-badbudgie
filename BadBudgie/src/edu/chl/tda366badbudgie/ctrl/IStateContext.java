package edu.chl.tda366badbudgie.ctrl;
import edu.chl.tda366badbudgie.gui.GraphicsFrame;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

public interface IStateContext {

		public IState getState();
		
		public void doLogic();
		public void draw(IGraphics g);

		public void setState(IState state);
		
		public IState getGameState();
		public IState getMenuState();
		
		public void shutDown();

		public void setFrame(GraphicsFrame graphicsFrame);
		
}
