package edu.chl.tda366badbudgieeditor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.chl.tda366badbudgieeditor.core.EGameObject;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController.Tool;

/**
 * GUI control panel.
 * 
 * @author Daniel
 *
 */
@SuppressWarnings("serial")
public class ToolPanel extends JPanel implements ActionListener {

	private JComboBox objectTypeChooser;
	private JPanel propertiesPanel;
	private JButton saveButton;
	private JButton loadButton;
	private JButton playButton;
	
	public ToolPanel(EditorController controller) {
		
		setPreferredSize(new Dimension(120, 100));
		setBackground(Color.lightGray);
		
		// Add a button, listener etc, for each Tool
		for (Tool ot : EditorController.Tool.values()) {
			JButton tb = new JButton(ot.toString());
			tb.setActionCommand(ot.toString().toLowerCase().replaceAll(" ", ""));
			tb.addActionListener(controller);
			tb.addActionListener(this);
			tb.addKeyListener(controller);
			add(tb);
		}
		
		// More buttons
		objectTypeChooser = new JComboBox(EGameObject.ObjectType.values());
		propertiesPanel = new JPanel();
		saveButton = new JButton("Save");
		loadButton = new JButton("Load");
		playButton = new JButton("Play");
		propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
		propertiesPanel.setPreferredSize(new Dimension(100, 100));
		objectTypeChooser.setActionCommand("objectypechanged");
		saveButton.setActionCommand("save");
		loadButton.setActionCommand("load");
		playButton.setActionCommand("play");
		
		// Listeners
		objectTypeChooser.addActionListener(controller);
		saveButton.addActionListener(controller);
		loadButton.addActionListener(controller);
		playButton.addActionListener(controller);
		objectTypeChooser.addKeyListener(controller);
		addKeyListener(controller);
		
		add(objectTypeChooser);
		add(propertiesPanel);
		add(saveButton);
		add(loadButton);
		add(playButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("terraintool")) {
			
		}
		else if(e.getActionCommand().equals("objecttool")) {
			
		}
	}
	
}
