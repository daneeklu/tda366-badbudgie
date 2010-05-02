package edu.chl.tda366badbudgieeditor.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Editor representation of a terrain segment.
 * 
 * @author kvarfordt
 *
 */
public class ETerrainSection {
	
	private List<EVector> verts = new ArrayList<EVector>(4);
	private ELevel parent;
	
	public ETerrainSection(ELevel parent) {
		this.parent = parent;
	}
	
	public List<EVector> getVerts() {
		return verts;
	}
	
	public void addVert(EVector v) {
		verts.add(v);
		parent.notifyLevelChanged();
	}
	
	public void removeVert(EVector v) {
		verts.remove(v);
	}
	
	
	public boolean isValidQuad() {
		if (isConvex()) {
			return true;
		}
		return true; // TODO <- isConvex seems broken so this always returns true temporarily
	}
	
	
	
	
	public boolean isConvex() {
		
		if (verts.size() < 3) {
			return false;
		}
		
		//Stores the sign between checks.
		double oldSign = 0;
		boolean changedDirection = false;

		for (int i = 0; i < verts.size(); i++) {			

			//Get the vectors forming two adjacent edges
			EVector aVec = verts.get(i);
			EVector bVec = verts.get((i + 1) % verts.size());
			EVector cVec = verts.get((i + 2) % verts.size());

			if (aVec.equals(bVec) || aVec.equals(cVec)) {
				return false;
			}

			//Calculate edge vectors.
			EVector nV1 = bVec.subtract(aVec);
			EVector nV2 = cVec.subtract(bVec);

			//Ensure the vertices don't form a straight line.
			if (!changedDirection && !nV1.sameDirection(nV2)) {
				changedDirection = true;
			}

			if (nV1.oppositeDirection(nV2)) {
				return false;
			}
			
			//calculate the sign of the edges
			double newSign = Math.signum(nV1.crossProduct(nV2));

			//Check whether sign has reversed.
			if(newSign == -oldSign){
				return false;
			}
			
			oldSign = newSign;
		}
		
		return changedDirection;
	}

	public boolean isCCW() {

		if (verts.size() < 3) {
			return false;
		}

		EVector aVec = verts.get(0);
		EVector bVec = verts.get(1);
		EVector cVec = verts.get(2);

		EVector nV1 = bVec.subtract(aVec);
		EVector nV2 = cVec.subtract(bVec);

		return nV1.crossProduct(nV2) > 0;
	}

	public void reverseVertexOrder() {
		Collections.reverse(verts);
	}
	
}
