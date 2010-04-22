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

	private IStateContext app;
	private HashMap<Integer, String> keyMap;
	private boolean[] keyDown;
	
	public KeyController(IStateContext app) {
		this.app = app;
		keyMap = new HashMap<Integer, String>();
		keyDown = new boolean[500]; 				// TODO: find exact value needed 
		Arrays.fill(keyDown, false);
		
		// TODO: temp key bindings, will be loaded from xml later
		keyMap.put(KeyEvent.VK_LEFT, "left");
		keyMap.put(KeyEvent.VK_RIGHT, "right");
		keyMap.put(KeyEvent.VK_UP, "up");
		keyMap.put(KeyEvent.VK_DOWN, "down");
	}
	
	@Override
	public void keyPressed(KeyEvent ke) {
		
		int kc = ke.getKeyCode();
		if (keyMap.containsKey(kc) && !keyDown[kc]) {
			app.buttonStateChanged(keyMap.get(kc), true);
			keyDown[kc] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		
		int kc = ke.getKeyCode();
		if (keyMap.containsKey(kc) && keyDown[kc]) {
			app.buttonStateChanged(keyMap.get(kc), false);
			keyDown[kc] = false;
		}
		
	}
	
}