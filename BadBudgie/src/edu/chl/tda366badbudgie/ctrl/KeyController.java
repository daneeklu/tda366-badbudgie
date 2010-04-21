package edu.chl.tda366badbudgie.ctrl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;

import edu.chl.tda366badbudgie.core.App;

/**
 * Controller class for keyboard input.
 * 
 * @author tda366-badbudgie
 *
 */
public class KeyController extends KeyAdapter{

	private App app;
	private HashMap<Integer, String> keyMap;
	private boolean[] keyDown;
	
	public KeyController(App app) {
		this.app = app;
		keyMap = new HashMap<Integer, String>();
		keyDown = new boolean[500]; 				// TODO: find exact value needed 
		Arrays.fill(keyDown, false);
		
		// temp key bindings, will be loaded from xml later
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
