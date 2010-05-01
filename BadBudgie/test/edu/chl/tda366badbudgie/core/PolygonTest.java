package edu.chl.tda366badbudgie.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.After;

/**
 * Checks validity of polygon setups.
 * 
 * @author jesper
 * 
 */
public class PolygonTest {

	List<Vector> vertices;

	Vector v1, v2, v3, v4, v5;

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

		assertFalse(Polygon.checkConvexity(vertices));
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

		assertTrue("Valid convex triangle.", Polygon.checkConvexity(vertices));
		assertTrue("Counter clockwise order.", Polygon.checkCCW(vertices));

		Collections.reverse(vertices);

		assertFalse("Clockwise order", Polygon.checkCCW(vertices));
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

		assertTrue("", Polygon.checkConvexity(vertices));
		assertTrue("", Polygon.checkCCW(vertices));
		
		vertices.add(v2);
		
		assertFalse(Polygon.checkConvexity(vertices));
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
		
		assertFalse(Polygon.checkConvexity(vertices));
		
		
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
		
		assertFalse(Polygon.checkConvexity(vertices));
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
		
		assertTrue(Polygon.checkConvexity(vertices));
	}

}
