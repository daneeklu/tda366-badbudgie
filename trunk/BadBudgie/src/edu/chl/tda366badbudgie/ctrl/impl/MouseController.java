package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import edu.chl.tda366badbudgie.ctrl.IStateContext;

public class MouseController implements MouseListener, MouseMotionListener{
	
	private IStateContext stateContext;
	
	public MouseController(IStateContext stateContext){
		this.stateContext = stateContext;
	}

	/*
	 * For these methods, forward the input to the active state
	 */

	@Override
	public void mousePressed(MouseEvent evt) {
		stateContext.getState().mouseActionClick(evt.getButton(), true);
	}

	@Override
	public void mouseReleased(MouseEvent evt) {
		stateContext.getState().mouseActionClick(evt.getButton(), false);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		stateContext.getState().mouseMoved(e.getX(), e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		stateContext.getState().mouseMoved(e.getX(), e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		// Do nothing, method needed for implemented interface
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		// Do nothing, method needed for implemented interface
	}

	@Override
	public void mouseExited(MouseEvent evt) {
		// Do nothing, method needed for implemented interface
	}
	
	
}
