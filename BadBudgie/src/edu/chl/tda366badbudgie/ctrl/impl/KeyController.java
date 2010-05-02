package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
public class KeyController extends KeyAdapter implements MouseListener, MouseMotionListener{

	private IStateContext stateContext;
	private HashMap<Integer, String> menuKeyMap;
	private HashMap<Integer, String> inGameKeyMap;
	private HashMap<Integer, String> currentKeyMap;
	private boolean[] keyDown;
	
	public KeyController(IStateContext stateContext) {
		this.stateContext = stateContext;
		menuKeyMap = new HashMap<Integer, String>();
		inGameKeyMap = new HashMap<Integer, String>();
		keyDown = new boolean[65536];
		Arrays.fill(keyDown, false);
		
		// Menu key bindings
		menuKeyMap.put(KeyEvent.VK_LEFT, "left");
		menuKeyMap.put(KeyEvent.VK_RIGHT, "right");
		menuKeyMap.put(KeyEvent.VK_UP, "up");
		menuKeyMap.put(KeyEvent.VK_DOWN, "down");
		menuKeyMap.put(KeyEvent.VK_ENTER, "select");
		menuKeyMap.put(KeyEvent.VK_SPACE, "select");
		menuKeyMap.put(KeyEvent.VK_ESCAPE, "escape");
		
		// TODO: Temp in game key bindings
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



	@Override
	public void mouseClicked(MouseEvent evt) {
		stateContext.getState().mouseActionClick(true);
		
	}



	@Override
	public void mouseEntered(MouseEvent evt) {
		System.out.println("Mouse support initiated!");
		
	}



	@Override
	public void mouseExited(MouseEvent evt) {
		
	}



	@Override
	public void mousePressed(MouseEvent evt) {
		stateContext.getState().mouseActionClick(true);
		
	}



	@Override
	public void mouseReleased(MouseEvent evt) {
		stateContext.getState().mouseActionClick(false);
		
	}



	@Override
	public void mouseDragged(MouseEvent e) {
		//stateContext.getState().mouseActionMoved(e.getXOnScreen(), e.getYOnScreen());
		stateContext.getState().mouseActionMoved(e.getX(), e.getY());
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		//stateContext.getState().mouseActionMoved(e.getXOnScreen(), e.getYOnScreen());
		stateContext.getState().mouseActionMoved(e.getX(), e.getY());
	}
	
}
