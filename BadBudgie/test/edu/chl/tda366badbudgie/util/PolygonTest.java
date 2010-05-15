package edu.chl.tda366badbudgie.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import edu.chl.tda366badbudgie.util.Polygon;
import edu.chl.tda366badbudgie.util.Vector;

/**
 * Checks validity of polygon setups.
 * 
 * @author jesper, kvarfordt
 * 
 */
public class PolygonTest {

	List<Vector> vertices;

	Vector v1, v2, v3, v4, v5;

	Polygon polygon;
	Vector p1;
	Vector p2;
	
	@Before
	public void setUp() throws Exception {
		polygon = new Polygon(new LinkedList<Vector>(Arrays.asList(new Vector(0,0), new Vector(1,0), new Vector(0,1))));
		p1 = new Vector(0.2, 0.2);
		p2 = new Vector(0.8, 0.8);
	}

	@Test
	public void testIsPointInPolygon() {
		
		assertTrue(Polygon.isPointInPolygon(p1, polygon));
		assertFalse(Polygon.isPointInPolygon(p2, polygon));
		
	}
	
	@After
	public void tearDown() throws Exception {
		vertices = null;
		v1 = v2 = v3 = v4 = v5 = null;
	}

	@Test
	public void areaTests() {
		vertices = new ArrayList<Vector>(3);

		v1 = new Vector(0, 0);
		v2 = new Vector(1, 0);
		v3 = new Vector(2, 0);

		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);

		assertFalse(Polygon.checkConvexity(new Polygon(vertices)));
	}

	@Test
	public void triangleTests() {

		vertices = new ArrayList<Vector>(3);

		// Valid triangle
		v1 = new Vector(0, 0);
		v2 = new Vector(1, 0);
		v3 = new Vector(1, 1);

		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);

		Polygon p = new Polygon(vertices);
		
		assertTrue("Valid convex triangle.", Polygon.checkConvexity(p));
		assertTrue("Counter clockwise order.", Polygon.checkCCW(p));

		Collections.reverse(vertices);

		assertFalse("Clockwise order", Polygon.checkCCW(p));
	}

	@Test
	public void cubeTests() {

		vertices = new ArrayList<Vector>(4);

		// Cube vertices
		v1 = new Vector(0, 0);
		v2 = new Vector(1, 0);
		v3 = new Vector(1, 1);
		v4 = new Vector(0, 1);

		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);

		Polygon p = new Polygon(vertices);
		
		assertTrue("", Polygon.checkConvexity(p));
		assertTrue("", Polygon.checkCCW(p));
		
		vertices.add(v2);
		
		assertFalse(Polygon.checkConvexity(p));
	}

	@Test
	public void nonConvexTest() {
		vertices = new ArrayList<Vector>(4);

		v1 = new Vector(-1, 0);
		v2 = new Vector(0, 0);
		v3 = new Vector(0, -1);
		v4 = new Vector(-0.1, -0.1);
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		
		assertFalse(Polygon.checkConvexity(new Polygon(vertices)));
		
		
	}

	@Test
	public void intersectingTest() {

		v1 = new Vector(0, 0);
		v2 = new Vector(1, 0);
		v3 = new Vector(1, 1);
		v4 = new Vector(0, 1);
		
		vertices = new ArrayList<Vector>(4);
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v4);
		vertices.add(v3);
		
		assertFalse(Polygon.checkConvexity(new Polygon(vertices)));
	}
	
	@Test
	public void pentagonTest(){
		System.out.println("Checking pentagon...");
		v1 = new Vector(0, 0);
		v2 = new Vector(1, -1);
		v3 = new Vector(2, -1);
		v4 = new Vector(3, 0);
		v5 = new Vector(1.2, 1);
		
		vertices = new ArrayList<Vector>(5);
		
		vertices.add(v1);
		vertices.add(v2);
		vertices.add(v3);
		vertices.add(v4);
		vertices.add(v5);
		
		assertTrue(Polygon.checkConvexity(new Polygon(vertices)));
	}

}
