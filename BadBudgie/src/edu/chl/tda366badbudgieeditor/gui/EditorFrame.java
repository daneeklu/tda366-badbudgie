package edu.chl.tda366badbudgieeditor.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import edu.chl.tda366badbudgieeditor.core.ELevel;
import edu.chl.tda366badbudgieeditor.core.ILevelObserver;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController;

/**
 * The frame of the editor gui.
 * 
 * @author kvarfordt
 *
 */
@SuppressWarnings("serial")
public class EditorFrame extends JFrame implements ILevelObserver {
	
	private EditorController controller;
	private ELevel level;
	
	private LevelPanel levelPanel;
	private ToolPanel toolPanel;
	
	public EditorFrame(EditorController controller, ELevel level) {
		this.controller = controller;
		this.level = level;
		
		levelPanel = new LevelPanel(level, controller);
		toolPanel = new ToolPanel(controller);
		
		addKeyListener(controller);
		
		add(levelPanel, BorderLayout.CENTER);
		add(toolPanel, BorderLayout.EAST);
		
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}

	@Override
	public void levelChanged() {
		levelPanel.repaint();
	}
	
}
