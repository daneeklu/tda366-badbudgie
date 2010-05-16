package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;

import edu.chl.tda366badbudgie.ctrl.IStateContext;


/**
 * KeyController
 * 
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
	
	// An array storing the current states of all keys
	private boolean[] keyDown;
	
	public KeyController(IStateContext stateContext) {
		this.stateContext = stateContext;
		menuKeyMap = new HashMap<Integer, String>();
		inGameKeyMap = new HashMap<Integer, String>();
		keyDown = new boolean[65536];
		Arrays.fill(keyDown, false);
		
		// Key bindings for the menu
		menuKeyMap.put(KeyEvent.VK_UP, "up");
		menuKeyMap.put(KeyEvent.VK_DOWN, "down");
		menuKeyMap.put(KeyEvent.VK_LEFT, "left");
		menuKeyMap.put(KeyEvent.VK_RIGHT, "right");

		menuKeyMap.put(KeyEvent.VK_W, "up");
		menuKeyMap.put(KeyEvent.VK_S, "down");
		menuKeyMap.put(KeyEvent.VK_A, "left");
		menuKeyMap.put(KeyEvent.VK_D, "right");
		
		menuKeyMap.put(KeyEvent.VK_ENTER, "select");
		menuKeyMap.put(KeyEvent.VK_SPACE, "select");
		menuKeyMap.put(KeyEvent.VK_ESCAPE, "escape");
		
		// Key bindings for the ingame state
		inGameKeyMap.put(KeyEvent.VK_LEFT, "moveleft");
		inGameKeyMap.put(KeyEvent.VK_RIGHT, "moveright");
		inGameKeyMap.put(KeyEvent.VK_UP, "moveup");
		inGameKeyMap.put(KeyEvent.VK_DOWN, "movedown");
		inGameKeyMap.put(KeyEvent.VK_ENTER, "enter");
		inGameKeyMap.put(KeyEvent.VK_ESCAPE, "escape");
		inGameKeyMap.put(KeyEvent.VK_SPACE, "jump");
		inGameKeyMap.put(KeyEvent.VK_CONTROL, "jump");
		inGameKeyMap.put(KeyEvent.VK_W, "moveup");
		inGameKeyMap.put(KeyEvent.VK_D, "moveright");
		inGameKeyMap.put(KeyEvent.VK_A, "moveleft");
		inGameKeyMap.put(KeyEvent.VK_S, "movedown");
		inGameKeyMap.put(KeyEvent.VK_F, "debug");
		
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
