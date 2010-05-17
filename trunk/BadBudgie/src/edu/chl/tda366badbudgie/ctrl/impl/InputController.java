package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.HashMap;

import edu.chl.tda366badbudgie.ctrl.IStateContext;


/**
 * InputController
 * 
 * Controller class for input.
 * 
 * @author kvarfordt, lumbo
 *
 */
public class InputController implements KeyListener, MouseListener, 
		MouseMotionListener {

	private IStateContext stateContext;
	private HashMap<Integer, String> menuKeyMap;
	private HashMap<Integer, String> inGameKeyMap;
	private HashMap<Integer, String> currentKeyMap;
	
	// An array storing the current states of all keys
	private boolean[] isKeyDown;
	
	/**
	 * Constructor for InputController
	 * @param stateContext the state context that receives delegation of input 
	 * commands.
	 */
	public InputController(IStateContext stateContext) {
		this.stateContext = stateContext;
		menuKeyMap = new HashMap<Integer, String>();
		inGameKeyMap = new HashMap<Integer, String>();
		isKeyDown = new boolean[65536];
		Arrays.fill(isKeyDown, false);
		
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
		if (currentKeyMap.containsKey(kc) && !isKeyDown[kc]) {
			stateContext.getState().keyboardAction(currentKeyMap.get(kc), true);
			isKeyDown[kc] = true;
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
		if (currentKeyMap.containsKey(kc) && isKeyDown[kc]) {
			stateContext.getState().keyboardAction(currentKeyMap.get(kc), false);
			isKeyDown[kc] = false;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent evt) {
		if(evt.getButton() == 1)
			stateContext.getState().mouseButtonAction(evt.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		stateContext.getState().mouseButtonAction(evt.getButton(), false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		stateContext.getState().mouseMoved(e.getX(), e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		stateContext.getState().mouseMoved(e.getX(), e.getY());
	}

	/*
	 * Unused interface methods.
	 */
	@Override
	public void mouseClicked(MouseEvent evt) {}

	@Override
	public void mouseEntered(MouseEvent evt) {}

	@Override
	public void mouseExited(MouseEvent evt) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
