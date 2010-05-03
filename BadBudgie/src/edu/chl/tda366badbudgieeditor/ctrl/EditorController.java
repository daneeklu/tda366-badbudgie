package edu.chl.tda366badbudgieeditor.ctrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;

import edu.chl.tda366badbudgieeditor.LevelTestPlayer;
import edu.chl.tda366badbudgieeditor.core.EGameObject;
import edu.chl.tda366badbudgieeditor.core.ELevel;
import edu.chl.tda366badbudgieeditor.core.ETerrainSection;
import edu.chl.tda366badbudgieeditor.core.EVector;
import static edu.chl.tda366badbudgieeditor.core.EGameObject.ObjectType;


/**
 * Controller class for the editor.
 * 
 * @author Daniel
 *
 */
public class EditorController implements MouseListener, MouseMotionListener, KeyListener, ActionListener {

	private static final int snapDistance = 15;
	
	public enum Tool{TerrainTool, ObjectTool};
	
	private int camX;
	private int camY;
	private int oldMouseX;
	private int oldMouseY;
	
	private ELevel level;
	private Tool selectedTool;
	
	private int mouseButtonDown = 0;
	
	/*
	 * Terrain section drawing vars
	 */
	private boolean drawingTerrain;
	private ETerrainSection currentSelectedTerrainSection;
	private ETerrainSection currentDrawTerrainSection;
	private EVector currentDrawVertex;
	private EVector currentMoveVertex;
	
	/*
	 * Game object drawing vars 
	 */
	private EGameObject currentGameObject;
	private ObjectType selectedObjectType;
	
	
	/**
	 * Constructor
	 * 
	 * @param level the ELevel used.
	 */
	public EditorController(ELevel level) {
		this.level = level;
		camX = -100;
		camY = -100;
		selectedTool = Tool.TerrainTool;
		selectedObjectType = ObjectType.Player;
		drawingTerrain = false;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// A button or control in the GUI has been used.
		
		if (e.getActionCommand().equals("objectypechanged")) {
			selectedObjectType = ObjectType.valueOf(((JComboBox)e.getSource()).getSelectedItem().toString());
		}
		if (e.getActionCommand().equals("terraintool")) {
			selectedTool = Tool.TerrainTool;
			currentGameObject = null;
		}
		if (e.getActionCommand().equals("objecttool")) {
			selectedTool = Tool.ObjectTool;
			currentSelectedTerrainSection = null;
			currentDrawTerrainSection = null;
			currentDrawVertex = null;
			currentMoveVertex = null;
		}
		if (e.getActionCommand().equals("save")) {
			System.out.println("Save Button Clicked");
		}
		if (e.getActionCommand().equals("load")) {
			System.out.println("Load Button Clicked");
		}
		if (e.getActionCommand().equals("play")) {
			new LevelTestPlayer(level);
		}
		level.notifyLevelChanged();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {

		mouseButtonDown = e.getButton();
		
		int mx = e.getX() + camX;
		int my = e.getY() + camY;
		
		/*
		 * TERRAIN CREATION
		 */
		if (selectedTool == Tool.TerrainTool) {
			
			// Left mouse button was clicked
			if (mouseButtonDown == 1) {
				if (!drawingTerrain) {
					// Start drawing a new Terrain Section
					drawingTerrain = true;
					
					// Create section and add to level
					ETerrainSection newTerr = new ETerrainSection(level);
					EVector newVert = new EVector(mx, my);
					newTerr.addVert(newVert);
					level.addTerrainSection(newTerr);
					currentDrawTerrainSection = newTerr;
					currentDrawVertex = newVert;
					currentSelectedTerrainSection = newTerr;
					
				}
				else {
					// Continue drawing existing terrain section
					if (currentDrawTerrainSection.getVerts().size() < 4) {
						EVector newVert = new EVector(mx, my);
						currentDrawTerrainSection.addVert(newVert);
						currentDrawVertex = newVert;
						
						if (currentDrawTerrainSection.getVerts().size() == 4) {
							// Quad finished, stop drawing the current quad
							drawingTerrain = false;
							if (currentDrawTerrainSection.isConvex() && !currentDrawTerrainSection.isCCW()) {
								currentDrawTerrainSection.reverseVertexOrder();
							}
						}
					}
				}
			}
			else if (mouseButtonDown == 3) {
				// Right mouse button down, select closest vertex if close enough
				EVector closestVert = getClosestVert(mx, my);
				if (closestVert != null && closestVert.getDistanceTo(new EVector(mx, my)) < snapDistance) {
					currentMoveVertex = closestVert;
					currentSelectedTerrainSection = getParentOfVertex(currentMoveVertex);
				}
				else {
					// if no close terrain vertex found, search for game object instead
					EGameObject closestGO = getClosestGameObject(mx, my);
					if (closestGO != null && closestGO.getPosition().getDistanceTo(new EVector(mx, my)) < snapDistance) {
						currentGameObject = closestGO;
						selectedTool = Tool.ObjectTool;
					}
					
					// if no vertex or object, deselect all
					currentMoveVertex = null;
					currentSelectedTerrainSection = null;
				}
				
			}
		}
		
		
		/*
		 * OBJECT CREATION
		 */
		if (selectedTool == Tool.ObjectTool) {

			if (mouseButtonDown == 1) {
				// Left mouse button was clicked, create new object
				EGameObject newObj = new EGameObject(mx, my, selectedObjectType);
				currentGameObject = newObj;
				level.addGameObject(newObj);
			}
			else if (mouseButtonDown == 3) {
				// Right mouse button down, select closest object if close enough
				EGameObject closestGO = getClosestGameObject(mx, my);
				if (closestGO != null && closestGO.getPosition().getDistanceTo(new EVector(mx, my)) < snapDistance) {
					currentGameObject = closestGO;
				}
				else {
					// No object close enough found, search for terrain vertex instead
					EVector closestVert = getClosestVert(mx, my);
					if (closestVert != null && closestVert.getDistanceTo(new EVector(mx, my)) < snapDistance) {
						currentMoveVertex = closestVert;
						currentSelectedTerrainSection = getParentOfVertex(currentMoveVertex);
						selectedTool = Tool.TerrainTool;
					}
					
					// Nothing found, deselect all
					currentGameObject = null;
				}
			}
		}
		
		level.notifyLevelChanged();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		int mx = e.getX() + camX;
		int my = e.getY() + camY;
		
		// Middle mouse button, panning
		if (mouseButtonDown == 2) {
			
			camX += oldMouseX - mx + camX;
			camY += oldMouseY - my + camY;
			
		}
		
		/*
		 * TERRAIN SECTION DRAGGING
		 */
		if (selectedTool == Tool.TerrainTool) {
			if (mouseButtonDown == 1) {
				// Find close vertex and snap to it if close enough, else move to mouse.
				EVector closestVert = getClosestVertInOtherTS(mx, my, currentDrawTerrainSection);
				if (closestVert != null && closestVert.getDistanceTo(new EVector(mx, my)) < snapDistance) {
					currentDrawVertex.setX(closestVert.getX());
					currentDrawVertex.setY(closestVert.getY());
				}
				else {
					currentDrawVertex.setX(mx);
					currentDrawVertex.setY(my);
				}
			}
			else if (mouseButtonDown == 3) {

				if (currentMoveVertex != null) {
					// Currently moving a vertex
					EVector closestVert = getClosestVertInAnyTS(mx, my, currentMoveVertex);
					if (closestVert != null && closestVert.getDistanceTo(new EVector(mx, my)) < snapDistance) {
						// If close to other vertex, snap
						currentMoveVertex.setX(closestVert.getX());
						currentMoveVertex.setY(closestVert.getY());
					}
					else {
						// Else move to mouse
						currentMoveVertex.setX(mx);
						currentMoveVertex.setY(my);
					}
					
				}
			}
		}
		
		
		/*
		 * OBJECT DRAGGING
		 */
		if (selectedTool == Tool.ObjectTool) {
			
			if (mouseButtonDown == 1) {
				// Find close object and snap to it if close enough, else move to mouse.
				EGameObject closestGO = getClosestGameObject(mx, my, currentGameObject);
				if (closestGO != null && closestGO.getPosition().getDistanceTo(new EVector(mx, my)) < snapDistance) {
					currentGameObject.setX(closestGO.getX());
					currentGameObject.setY(closestGO.getY());
				}
				else {
					currentGameObject.setX(mx);
					currentGameObject.setY(my);	
				}
				
			}
			else if (mouseButtonDown == 3) {
				// Right mouse button, move object to mouse
				if (currentGameObject != null) {
					
					// No snapping between objects currently
					/* Should we snap?
					EGameObject closestGO = getClosestGameObject(mx, my, currentGameObject);
					if (closestGO != null && closestGO.getPosition().getDistanceTo(new EVector(mx, my)) < snapDistance) {
						currentGameObject.setX(closestGO.getX());
						currentGameObject.setY(closestGO.getY());
					}
					else {
						currentGameObject.setX(mx);
						currentGameObject.setY(my);
					}
					*/
					
					currentGameObject.setX(mx);
					currentGameObject.setY(my);
				}
			}
		}
		
		
		oldMouseX = e.getX();
		oldMouseY = e.getY();

		level.notifyLevelChanged();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Mouse up, reset mouseButtonDown
		mouseButtonDown = 0;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		oldMouseX = e.getX();
		oldMouseY = e.getY();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keypressed");
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			if (currentSelectedTerrainSection != null) {
				// Delete terrain section
				level.removeTerrainSection(currentSelectedTerrainSection);
			}
			if (currentGameObject != null) {
				// Delete current object
				level.removeGameObject(currentGameObject);
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			camX -= 25;
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			camX += 25;
		}
		else if (e.getKeyCode() == KeyEvent.VK_UP) {
			camY -= 25;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			camY += 25;
		}
		level.notifyLevelChanged();
		
	}

	/*
	 * Returns the closest EGameObject to x,y ignoring the specified one.
	 */
	private EGameObject getClosestGameObject(int x, int y, EGameObject ignored) {
		double closestDistSq = Double.MAX_VALUE;
		EGameObject closestGO = null;
		for (EGameObject go : level.getGameObjects()) {
			if (go == ignored)
				continue;
				double distSq = (x - go.getX())*(x - go.getX()) + (y - go.getY())*(y - go.getY());
				if (distSq < closestDistSq) {
					closestDistSq = distSq;
					closestGO = go;
				}
		}
		return closestGO;
	}

	/*
	 * Returns the EGameObject closest to x, y.
	 */
	private EGameObject getClosestGameObject(int x, int y) {
		return getClosestGameObject(x, y, null);
	}
	
	/*
	 * Returns the EVector (vertex) closest to x,y in the levels terrain sections, 
	 * ignoring the specified ETerrainSection.
	 */
	private EVector getClosestVertInOtherTS(int x, int y, ETerrainSection ignored) {
		double closestDistSq = Double.MAX_VALUE;
		EVector closestVert = null;
		for (ETerrainSection t : level.getTerrainSections()) {
			if (t == ignored)
				break;
			for (EVector v : t.getVerts()) {
				double distSq = (x - v.getX())*(x - v.getX()) + (y - v.getY())*(y - v.getY());
				if (distSq < closestDistSq) {
					closestDistSq = distSq;
					closestVert = v;
				}
			}
		}
		return closestVert;
	}
	
	/*
	 * Returns the EVector (vertex) closest to x,y in the levels terrain sections, 
	 * ignoring the specified EVector.
	 */
	private EVector getClosestVertInAnyTS(int x, int y, EVector ignored) {
		double closestDistSq = Double.MAX_VALUE;
		EVector closestVert = null;
		for (ETerrainSection t : level.getTerrainSections()) {
			
			for (EVector v : t.getVerts()) {
				if (v == ignored)
					break;
				double distSq = (x - v.getX())*(x - v.getX()) + (y - v.getY())*(y - v.getY());
				if (distSq < closestDistSq) {
					closestDistSq = distSq;
					closestVert = v;
				}
			}
		}
		return closestVert;
	}

	/*
	 * Returns the EVector (vertex) closest to x,y in the levels terrain sections.
	 */
	private EVector getClosestVert(int x, int y) {
		return getClosestVertInOtherTS(x, y, null);
	}
	
	/*
	 * Returns the ETerrainSection that the given EVector is part of, or null if none.
	 */
	private ETerrainSection getParentOfVertex(EVector v) {
		for (ETerrainSection t : level.getTerrainSections()) {
			for (EVector e : t.getVerts()) {
				if (e == v)
					return t;
			}
		}
		return null;
	}
	

	/*
	 * Getters and setters
	 */
	public int getCamX() {
		return camX;
	}

	public void setCamX(int camX) {
		this.camX = camX;
	}

	public int getCamY() {
		return camY;
	}

	public void setCamY(int camY) {
		this.camY = camY;
	}

	public ETerrainSection getSelectedTerrainSection() {
		return currentSelectedTerrainSection;
	}

	public EGameObject getSelectedGameObject() {
		return currentGameObject;
	}
	
	
	// Unused interface methods
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
}
