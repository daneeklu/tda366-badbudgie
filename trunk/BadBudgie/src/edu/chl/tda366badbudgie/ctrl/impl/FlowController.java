package edu.chl.tda366badbudgie.ctrl.impl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.chl.tda366badbudgie.ctrl.IStateContext;
import edu.chl.tda366badbudgie.gui.graphics.IGraphics;

/**
 * FlowController
 * 
 * The game update controller. Calls on the state context 
 * to update the game at a regular interval.
 * 
 * @author d.skalle, kvarfordt
 *
 */
public class FlowController implements ActionListener {

	public static FlowController ctrl;
	
	public IStateContext stateContext;
	public IGraphics graphics;
	
	public FlowController(IStateContext app, IGraphics graphics) {
		super();
		this.stateContext = app;
		this.graphics = graphics;
	}
	
	public void loop() {
		stateContext.doLogic();
		stateContext.draw(graphics);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		loop();
	}

}
