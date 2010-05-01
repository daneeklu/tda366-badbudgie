package edu.chl.tda366badbudgieeditor;

import edu.chl.tda366badbudgieeditor.core.ELevel;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController;
import edu.chl.tda366badbudgieeditor.gui.EditorFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ELevel level = new ELevel();
		EditorFrame frame = new EditorFrame(new EditorController(level), level);
		level.addObserver(frame);
		
	}

}
