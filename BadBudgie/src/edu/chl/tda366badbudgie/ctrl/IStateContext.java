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
		
		/**
		 * Tells the GraphicsFrame to shut down the program.
		 * @throws an IllegalStateException if graphicsframe has not been set.
		 */
		public void shutDown();
		
	    /**
	     * Set the frame to fullscreen or windowed. 
	     * 
	     * @param fullscreen true if fullscreen
	     */
		public void setFullscreen(boolean fullscreen);

		public void setFrame(GraphicsFrame graphicsFrame);
		
}
