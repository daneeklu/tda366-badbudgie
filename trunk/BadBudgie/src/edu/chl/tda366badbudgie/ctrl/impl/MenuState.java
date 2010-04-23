package edu.chl.tda366badbudgie.ctrl.impl;


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
			StateContext.getInstance().setState("startGame");
		}
	}
	
	@Override
	public void draw(IGraphics g) {
		MenuRenderer.render(menu, g);
	}

	@Override
	public void keyboardAction(String id, boolean down) {
		System.out.println("In menu keypress: " + id + " " + down);
		
		menu.keyboardAction(id, down);
		
		if (down && id.equals("enter")) {
			String selected = menu.getSelected();
			
			if (selected.equals("New game")) {
				StateContext.getInstance().setState("startGame");
			} else if (selected.equals("Options")) {
				//TODO: add switch to options state here
				;
			} else if (selected.equals("Exit")) {
				//TODO: make program shutdown here
				;
			} 

			return;
		}
		
	}

}
