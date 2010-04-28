package edu.chl.tda366badbudgie.core.content;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.chl.tda366badbudgie.core.Vector;

public class VectorTest {

	
	private Vector a, b, c;
	
	@Before
	public void init() {
		a = new Vector(0,2);
		b = new Vector(2,0);
		c = new Vector(2,2);
		
	}
	
	@After
	public void uninit() {
		a = b = c = null;
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

}
