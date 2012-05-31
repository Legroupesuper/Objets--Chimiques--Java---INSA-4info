/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLOE.

    ChLOE is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLOE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLOE.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for Pair
 */

public class PairTest extends TestCase {

	/**
	 * @param name
	 */
	public PairTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link Pair#Pair()}.
	 */
	public void testConstructor() {
		Pair<?, ?> testPair;
		testPair = new Pair<Object, String>(new Object(), "foo");
		assertTrue("The constructor for Pair should not be fatal", testPair instanceof Pair<?, ?>);
	}

	/**
	 * Test method for Pair's attribute getters.
	 */
	public void testGetters() {
		Pair<?, ?> testPair;
		String s2 = "qux";
		Object o = new Object();
		testPair = new Pair<String, Object>(s2, o);
		assertTrue("Access to the first element of a Pair should be correct", testPair.get_first().equals(s2));
		assertTrue("Access to the second element of a Pair should be correct", testPair.get_second().equals(o));
	}

	/**
	 * Test method for Pair's attribute setters.
	 */
	public void testSetters() {
		Pair<String, Object> testPair;
		
		String s1 = "baz";
		Object o = new Object();
		testPair = new Pair<String, Object>("bam", "boum");

		testPair.set_first(s1);
		testPair.set_second(o);
		
		assertTrue("Modifying the first element of a Pair should be relevant", testPair.get_first().equals(s1));
		assertTrue("Modifying the second element of a Pair should be relevant", testPair.get_second().equals(o));
	}
}