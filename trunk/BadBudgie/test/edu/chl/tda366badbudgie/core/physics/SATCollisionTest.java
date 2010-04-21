package edu.chl.tda366badbudgie.core.physics;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.chl.tda366badbudgie.core.content.Polygon;
import edu.chl.tda366badbudgie.core.content.Vector;

public class SATCollisionTest {

	private Polygon a, b, c;
	private Physics p;
	
	@Before
	public void init() {
		p = new Physics();
		
		ArrayList<Vector> aVerts = new ArrayList<Vector>();
		aVerts.add(new Vector(0,0));
		aVerts.add(new Vector(2,0));
		aVerts.add(new Vector(2,2));
		aVerts.add(new Vector(0,2));
		a = new Polygon(aVerts);
		
		ArrayList<Vector> bVerts = new ArrayList<Vector>();
		bVerts.add(new Vector(1,1));
		bVerts.add(new Vector(3,1));
		bVerts.add(new Vector(3,3));
		bVerts.add(new Vector(1,3));
		b = new Polygon(bVerts);
		
		ArrayList<Vector> cVerts = new ArrayList<Vector>();
		cVerts.add(new Vector(4,1));
		cVerts.add(new Vector(4,4));
		cVerts.add(new Vector(1,4));
		c = new Polygon(cVerts);
		
		/*
		 * |        _____
		 * |        \  c |
		 * |     ----\-- |
		 * |     |    \| |
		 * |------- b  \ |
		 * |   a -|-----\|
		 * |      |
		 * |--------------------
		 * 
		 */
		
	}
	
	@Test
	public void testCheckCollisionSAT() {
		
		Vector abCol = p.checkCollisionSAT(a, b);
		Vector bcCol = p.checkCollisionSAT(b, c);
		Vector acCol = p.checkCollisionSAT(a, c);
		
		assertNotNull(abCol);
		assertNotNull(bcCol);
		assertNull(acCol);
		
		assertTrue(bcCol.dotProduct(new Vector(1,1)) < 0); //
		
	}

}