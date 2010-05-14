package edu.chl.tda366badbudgie.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StaticUtilsTest {

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
		
		assertTrue(StaticUtils.isPointInPolygon(p1, polygon));
		assertFalse(StaticUtils.isPointInPolygon(p2, polygon));
		
	}

}
