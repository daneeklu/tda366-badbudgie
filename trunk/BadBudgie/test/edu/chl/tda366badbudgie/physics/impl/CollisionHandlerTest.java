package edu.chl.tda366badbudgie.physics.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import edu.chl.tda366badbudgie.physics.impl.CollisionHandler;
import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

public class CollisionHandlerTest {

	private Polygon a, b, c;
	private CollisionHandler ch;
	
	@Before
	public void init() {
		ch = new CollisionHandler();
		
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
	public void testGetCollisionSAT() {
		
		Vector abCol = ch.getCollisionSAT(a, b);
		Vector bcCol = ch.getCollisionSAT(b, c);
		Vector acCol = ch.getCollisionSAT(a, c);
		
		assertTrue(!abCol.hasZeroLength());
		assertTrue(!bcCol.hasZeroLength());
		assertTrue(acCol.hasZeroLength());
		
		assertTrue(bcCol.dotProduct(new Vector(1,1)) < 0);
		
	}


}
