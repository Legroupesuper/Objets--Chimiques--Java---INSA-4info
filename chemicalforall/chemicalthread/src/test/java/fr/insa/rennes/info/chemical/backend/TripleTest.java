/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for Triple
 */

public class TripleTest extends TestCase {

	
	/**
	 * @param name
	 */
	public TripleTest(String name) {
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
	 * Test method for {@link Triple#Triple()}.
	 */
	public void testConstructor() {
		Triple<?, ?, ?> testTriple;
		testTriple = new Triple<Integer, Object, String>(12, new Object(), "foo");
		assertTrue("The constructor for Triple should not be fatal", testTriple instanceof Triple<?, ?, ?>);
	}

	/**
	 * Test method for Triple's attribute getters.
	 */
	public void testGetters() {
		Triple<?, ?, ?> testTriple;
		String s1 = "baz";
		String s2 = "qux";
		Object o = new Object();
		testTriple = new Triple<String, String, Object>(s1, s2, o);
		assertTrue("Access to the first element of a Triple should be correct", testTriple.get_first().equals(s1));
		assertTrue("Access to the second element of a Triple should be correct", testTriple.get_second().equals(s2));
		assertTrue("Access to the third element of a Triple should be correct", testTriple.get_triple().equals(o));
	}

	/**
	 * Test method for Triple's attribute setters.
	 */
	public void testSetters() {
		Triple<String, String, Object> testTriple;
		
		String s1 = "baz";
		String s2 = "qux";
		Object o = new Object();
		testTriple = new Triple<String, String, Object>("bim", "bam", "boum");

		testTriple.set_first(s1);
		testTriple.set_second(s2);
		testTriple.set_triple(o);
		
		assertTrue("Modifying the first element of a Triple should be relevant", testTriple.get_first().equals(s1));
		assertTrue("Modifying the second element of a Triple should be relevant", testTriple.get_second().equals(s2));
		assertTrue("Modifying the third element of a Triple should be relevant", testTriple.get_triple().equals(o));
	}
}