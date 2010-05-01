package edu.chl.tda366badbudgieeditor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import edu.chl.tda366badbudgieeditor.core.ResourceManager;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController;

@SuppressWarnings("serial")
public class ToolPanel extends JPanel implements ActionListener {

	private JButton terrainToolButton;
	private JButton objectToolButton;
	private JComboBox objectTypeChooser;
	private JPanel propertiesPanel;
	private JButton saveButton;
	private JButton loadButton;
	
	public ToolPanel(EditorController controller) {
		
		setPreferredSize(new Dimension(120, 100));
		
		terrainToolButton = new JButton("Terrain Tool");
		objectToolButton = new JButton("Object Tool");
		objectTypeChooser = new JComboBox(ResourceManager.getGameObjectNames().toArray());
		propertiesPanel = new JPanel();
		saveButton = new JButton("Save");
		loadButton = new JButton("Load");
		propertiesPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
		propertiesPanel.setPreferredSize(new Dimension(100, 100));
		terrainToolButton.setActionCommand("terraintool");
		objectToolButton.setActionCommand("objecttool");
		objectTypeChooser.setActionCommand("objectypechanged");
		saveButton.setActionCommand("save");
		loadButton.setActionCommand("load");
		
		terrainToolButton.addActionListener(controller);
		objectToolButton.addActionListener(controller);
		objectTypeChooser.addActionListener(controller);
		saveButton.addActionListener(controller);
		loadButton.addActionListener(controller);
		
		terrainToolButton.addActionListener(this);
		objectToolButton.addActionListener(this);
		
		terrainToolButton.addKeyListener(controller);
		objectToolButton.addKeyListener(controller);
		objectTypeChooser.addKeyListener(controller);
		
		
		addKeyListener(controller);
		
		add(terrainToolButton);
		add(objectToolButton);
		add(objectTypeChooser);
		add(propertiesPanel);
		add(saveButton);
		add(loadButton);
		
		
		setBackground(Color.lightGray);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(e.getActionCommand().equals("terraintool")) {
//			terrainToolButton.setEnabled(false);
//			objectToolButton.setEnabled(true);
		}
		else if(e.getActionCommand().equals("objecttool")) {
//			terrainToolButton.setEnabled(true);
//			objectToolButton.setEnabled(false);
		}
	}
	
}
