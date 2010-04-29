package edu.chl.tda366badbudgie.ctrl.impl;


import edu.chl.tda366badbudgie.core.GameRound;
import edu.chl.tda366badbudgie.core.Menu;
import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.MenuRenderer;

public class MenuState implements IState {

	private Menu menu;
	
	boolean startGame = false;
	
	public MenuState () {
		menu = new Menu();
	}
	
	@Override
	public void logic() {
		if (startGame) {
			StateContext.getInstance().setState(
					StateContext.getInstance().getGameState());
		} else {
			menu.logic();
		}
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
					StateContext.getInstance().setState(
							new InGameState(new GameRound()));
				} else {
					menu.showConfirmDialog();
				}
			} else if (selected.equals("resume")) {
				if (StateContext.getInstance().getGameState() != null) 
					StateContext.getInstance().setState(StateContext.getInstance().getGameState());
			} else if (selected.equals("options")) {
				//TODO: add switch to options state here
				;
			} else if (selected.equals("exit")) {
				menu.showConfirmDialog();

			} else if (selected.equals("confirm:exit")) {
				StateContext.getInstance().shutDown();
				
			} else if (selected.equals("confirm:newgame")) {
				StateContext.getInstance().setState(
					new InGameState(new GameRound()));
			}

			return;
		}
		
	}

}
