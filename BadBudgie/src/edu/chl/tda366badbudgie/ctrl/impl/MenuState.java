package edu.chl.tda366badbudgie.ctrl.impl;

import java.util.LinkedList;
import java.util.Queue;

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
		
		if (down && id.equals("enter")) {
			startGame = true;
		}
		
	}

}
