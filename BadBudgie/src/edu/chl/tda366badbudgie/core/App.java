package edu.chl.tda366badbudgie.core;

public class App {
	
	public void doLogic() {
		//TODO: add logic here
	}

	public void buttonStateChanged(String id, boolean down) {
		if (down) {
			System.out.println("button pressed:" + id);
		}
		else {
			System.out.println("button released:" + id);
		}
		
	}
}
