package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.core.game.GameRound;
import edu.chl.tda366badbudgie.core.menu.Menu;
import edu.chl.tda366badbudgie.core.menu.MenuManager;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.MenuRenderer;

/**
 * A state for when the menu is showing
 * 
 * @author d.skalle
 *
 */
public class MenuState implements IState {

	private Menu menu;
	
	
	public MenuState () {
		menu = MenuManager.getInstance().getMenu("mainmenu");
	}
	
	@Override
	public void logic() {
		
		/* Signal to the menu whether the game is running or not,
		 * then let the menu handle its logic
		 */
		menu.setGameRunning(StateContext.getInstance().getGameState() != null);
		menu.logic();
	}
	
	@Override
	public void draw(IGraphics g) {
		MenuRenderer.render(menu, g);
	}

	@Override
	public void keyboardAction(String id, boolean down) {
		
		menu.keyboardAction(id, down);
		
		if (down && id.equals("select")) {
			String selected = menu.getSelected();
			
			if (selected == null) return;
			
			if (selected.equals("newgame")) {
				if (StateContext.getInstance().getGameState() == null) {
					StateContext.getInstance().setGameState();
					menu.setGameRunning(true);
				} else {
					menu.showConfirmDialog();
				}
				
			} else if (selected.equals("resume")) {
				StateContext.getInstance().setGameState();

			} else if (selected.equals("exit")) {
				menu.showConfirmDialog();

			} else if (selected.equals("confirm:exit")) {
				StateContext.getInstance().shutDown();
				
			} else if (selected.equals("confirm:newgame")) {
				StateContext.getInstance().setGameState(
					new InGameState(new GameRound()));
				
			} else if (selected.equals("mainmenu")) {
				menu = MenuManager.getInstance().getMenu("mainmenu");
				
			} else if (selected.equals("optionsmenu")) {
				menu = MenuManager.getInstance().getMenu("optionsmenu");
				
			} else if (selected.equals("helpmenu")) {
				menu = MenuManager.getInstance().getMenu("helpmenu");
				
			} else if (selected.equals("fullscreen")) {
				/* Toggle fullscreen, and toggle alternate
				 * text for "fullscreen" MenuItem
				 */
				boolean fullscreen = !StateContext.getInstance().getFullscreen();
				menu.getCurrentMenuItem().setAlternate(fullscreen);
				StateContext.getInstance().setFullscreen(fullscreen);
			}
			
		}
	}


	@Override
	public void mouseButtonAction(int button, boolean pressed) {
		// Do nothing with the mouse in-menu, at least for now
	}

	@Override
	public void mouseMoved(int x, int y) {
		// Do nothing with the mouse in-menu, at least for now
	}

}
