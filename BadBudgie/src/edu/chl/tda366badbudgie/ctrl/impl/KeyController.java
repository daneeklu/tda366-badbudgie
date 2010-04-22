package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;

import edu.chl.tda366badbudgie.ctrl.IStateContext;


/**
 * Controller class for keyboard input.
 * 
 * @author tda366-badbudgie
 *
 */
public class KeyController extends KeyAdapter{

	private IStateContext stateContext;
	private HashMap<Integer, String> menuKeyMap;
	private HashMap<Integer, String> inGameKeyMap;
	private HashMap<Integer, String> currentKeyMap;
	private boolean[] keyDown;
	
	public KeyController(IStateContext stateContext) {
		this.stateContext = stateContext;
		menuKeyMap = new HashMap<Integer, String>();
		inGameKeyMap = new HashMap<Integer, String>();
		keyDown = new boolean[500]; 				// TODO: find exact value needed 
		Arrays.fill(keyDown, false);
		
		// Menu key bindings
		menuKeyMap.put(KeyEvent.VK_LEFT, "left");
		menuKeyMap.put(KeyEvent.VK_RIGHT, "right");
		menuKeyMap.put(KeyEvent.VK_UP, "up");
		menuKeyMap.put(KeyEvent.VK_DOWN, "down");
		menuKeyMap.put(KeyEvent.VK_ENTER, "enter");
		menuKeyMap.put(KeyEvent.VK_ESCAPE, "escape");
		
		// TODO: Temp in game key bindings
		inGameKeyMap.put(KeyEvent.VK_SPACE, "jump");
	}
	
	
	
	
	@Override
	public void keyPressed(KeyEvent ke) {

		if (stateContext.getState() instanceof MenuState) {
			currentKeyMap = menuKeyMap;
		}
		else if (stateContext.getState() instanceof InGameState) {
			currentKeyMap = inGameKeyMap;
		}
		
		int kc = ke.getKeyCode();
		if (currentKeyMap.containsKey(kc) && !keyDown[kc]) {
			stateContext.getState().keyboardAction(currentKeyMap.get(kc), true);
			keyDown[kc] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
		if (stateContext.getState() instanceof MenuState) {
			currentKeyMap = menuKeyMap;
		}
		else if (stateContext.getState() instanceof InGameState) {
			currentKeyMap = inGameKeyMap;
		}
		
		int kc = ke.getKeyCode();
		if (currentKeyMap.containsKey(kc) && keyDown[kc]) {
			stateContext.getState().keyboardAction(currentKeyMap.get(kc), false);
			keyDown[kc] = false;
		}
		
	}
	
}
