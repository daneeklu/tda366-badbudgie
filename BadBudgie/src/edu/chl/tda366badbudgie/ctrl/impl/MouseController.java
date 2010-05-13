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

	@Override
	public void mouseClicked(MouseEvent evt) {
		
	}

	@Override
	public void mouseEntered(MouseEvent evt) {
		
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
		stateContext.getState().mouseMoved(e.getX(), e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		stateContext.getState().mouseMoved(e.getX(), e.getY());
	}
}
