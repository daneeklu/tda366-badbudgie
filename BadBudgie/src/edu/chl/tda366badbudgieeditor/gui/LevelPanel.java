package edu.chl.tda366badbudgieeditor.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

import edu.chl.tda366badbudgieeditor.core.EGameObject;
import edu.chl.tda366badbudgieeditor.core.ELevel;
import edu.chl.tda366badbudgieeditor.core.ETerrainSection;
import edu.chl.tda366badbudgieeditor.core.EVector;
import edu.chl.tda366badbudgieeditor.ctrl.EditorController;

/**
 * Graphics area displaying the level and allowing the user to edit it.
 * 
 * @author kvarfordt
 *
 */
@SuppressWarnings("serial")
public class LevelPanel extends JPanel {
	
	private ELevel level;
	private EditorController controller;
	
	public LevelPanel(ELevel level, EditorController controller) {
		this.level = level;
		this.controller = controller;
		
		setBackground(Color.white);
		
		addMouseListener(controller);
		addMouseMotionListener(controller);
		addKeyListener(controller);
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Color invalidColor = new Color(200,50,50);
		Color drawingColor = new Color(50,50,200);
		Color selectedColor = new Color(100,250,50);
		
		int camX = controller.getCamX();
		int camY = controller.getCamY();
		
		/*
		 * Draw axis
		 */
		g.setColor(Color.black);
		g.drawLine(-camX, 0, -camX, getHeight());
		g.drawLine(0, -camY, getWidth(), -camY);
		
		/*
		 * Draw each terrain section
		 */
		for (ETerrainSection t : level.getTerrainSections()) {
			
			List<EVector> verts = t.getVerts();
			
			// Draw unfinished quads green, finished black etc.
			if (verts.size() < 4)
				g.setColor(drawingColor);
			else if (t == controller.getSelectedTerrainSection())
				g.setColor(selectedColor);
			else if (!t.isValidQuad())
				g.setColor(invalidColor);
			else
				g.setColor(Color.black);
			
			for (int i = 0; i < verts.size(); i++) {
				
				EVector v1 = verts.get(i);
				EVector v2 = verts.get((i + 1) % verts.size());
				
				g.drawLine((int) v1.getX() - camX, (int) v1.getY() - camY, (int) v2.getX() - camX, (int) v2.getY() - camY);
				g.fillOval((int) v1.getX() - camX - 2, (int) v1.getY() - camY - 2, 5, 5);
			}
		}
		
		/*
		 *  Draw game objects
		 */
		for (EGameObject go : level.getGameObjects()) {
			if(go == controller.getSelectedGameObject()) {
				g.setColor(selectedColor);
			}
			else {
				g.setColor(Color.black);
			}
			
			g.drawOval(go.getX() - 2 - camX, go.getY() - 2 - camY, 5, 5);
			g.drawString(go.getType().toString(), go.getX() - camX + 5, go.getY() - camY);
		}
		
		
	}
	
}
