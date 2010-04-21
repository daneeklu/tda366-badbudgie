package edu.chl.tda366badbudgie.core;


public class App {
	
	// Feel free to change this implementation if you like
	private enum AppState{Intro, Menu, Newgame, Ingame, IngameMenu};
	private AppState appState;
	
	
	private GameRound gameRound;
	
	
	public void doLogic() {
		//System.out.println("app logic is being done");
		
		if (appState == AppState.Intro) {
			// TODO: Add intro logic, temporary jumps to menu
			appState = AppState.Menu;
		}
		
		if (appState == AppState.Menu) {
			if (true) {
				// TODO: Add menu logic, temporary jumps to new game
				appState = AppState.Newgame;
			}
		}
		
		if (appState == AppState.Newgame) {
			gameRound = new GameRound();
			appState = AppState.Ingame;
		}
		
		if (appState == AppState.Ingame) {
			gameRound.doLogic();
		}
		
		if (appState == AppState.IngameMenu) {
			// TODO
		}
		
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
