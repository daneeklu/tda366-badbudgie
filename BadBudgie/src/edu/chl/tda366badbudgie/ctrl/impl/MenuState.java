package edu.chl.tda366badbudgie.ctrl.impl;

import edu.chl.tda366badbudgie.ctrl.IState;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;
import edu.chl.tda366badbudgie.gui.render.MenuRenderer;

public class MenuState implements IState {

	private Menu menu;
	
	public MenuState () {
		menu = new Menu();
	}
	
	@Override
	public void logic() {

	}
	
	@Override
	public void draw(IGraphics g) {
		MenuRenderer.render(menu, g);
	}

}
