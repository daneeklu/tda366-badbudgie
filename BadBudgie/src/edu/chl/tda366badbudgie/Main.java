package edu.chl.tda366badbudgie;

import edu.chl.tda366badbudgie.ctrl.impl.FlowController;

/**
 * Bad Budgie Main class
 * 
 * @author tda366-badbudgie
 *
 */
public class Main {
	public static void main(String[] args) throws InterruptedException {
		
		// Instantiate a new flow controller, which will show a splash screen,
		// then show the main graphics frame, init all classes at startup,
		// and load all assets used by the program.
		new FlowController();
	}
}