package edu.chl.tda366badbudgie.util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.chl.tda366badbudgie.util.Vector;

public class VectorTest {

	
	private Vector a, b, c, d, e;
	
	@Before
	public void init() {
		a = new Vector(0, 2);
		b = new Vector(2, 0);
		c = new Vector(2, 2);
		d = new Vector(-1, 0);
		e = new Vector(0, 0);
	}
	
	@After
	public void uninit() {
		a = b = c = d = e = null;
	}
	
	@Test
	public void testGetLength() {
		
		assertTrue(a.getLength() > 0);
		assertTrue(b.getLength() > 0);
		assertTrue(c.getLength() > 0);
		assertTrue(d.getLength() > 0);
		assertTrue(e.getLength() == 0);
		
	}
	
	@Test
	public void testHasZeroLength() {

		assertFalse(a.hasZeroLength());
		assertFalse(b.hasZeroLength());
		assertFalse(c.hasZeroLength());
		assertFalse(d.hasZeroLength());
		assertTrue(e.hasZeroLength());
		
	}
	

	@Test
	public void testDotProduct() {
		
		double ab = a.dotProduct(b);
		assertTrue(ab == 0);
		
		double bc = b.dotProduct(c);
		assertTrue(bc == 4);
		
	}

	@Test
	public void testProject() {
		
		Vector ab = a.project(b);
		assertTrue(ab.getX() == 0);
		assertTrue(ab.getY() == 0);
		
		
		Vector cb = b.project(b);
		
		assertTrue(cb.getLength() == 2);
		
	}

	@Test
	public void testAdd() {
		
		Vector ab = a.add(b);
		
		assertTrue(ab.getX() == 2);
		assertTrue(ab.getY() == 2);
		
		Vector bc = b.add(c);
		
		assertTrue(bc.getX() == 4);
		assertTrue(bc.getY() == 2);
		
	}

	@Test
	public void testSubtract() {
		
		Vector ab = a.subtract(b);
		
		assertTrue(ab.getX() == -2);
		assertTrue(ab.getY() == 2);
		
		Vector bc = b.subtract(c);
		
		assertTrue(bc.getX() == 0);
		assertTrue(bc.getY() == -2);
		
	}

	@Test
	public void testNormalize() {

		Vector cn = c.normalize();
		
		assertTrue(Math.abs(cn.getLength() - 1.0) < 0.000000000000001); // not equal because of rounding errors
		
	}
	
	@Test
	public void testCrossProduct() {

		double vpAB = a.crossProduct(b);
		double vpBA = b.crossProduct(a);
		
		assertTrue(vpAB < 0);
		assertTrue(vpBA > 0);
		
	}
	
	@Test
	public void testDirection() {
		
		Vector aa = new Vector(0, 0);
		Vector bb = new Vector(3, 3);
		Vector cc = new Vector(0, 3);
		
		assertFalse(aa.sameDirection(cc));
		assertTrue(bb.sameDirection(bb.add(bb)));
		assertTrue(bb.oppositeDirection(bb.scalarMultiplication(-2)));
		
	}

}
