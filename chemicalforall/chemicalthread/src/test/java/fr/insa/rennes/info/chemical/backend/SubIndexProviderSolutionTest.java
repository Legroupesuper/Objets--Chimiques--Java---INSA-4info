/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
 */
package fr.insa.rennes.info.chemical.backend;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for SubIndexProviderSolution
 */

public class SubIndexProviderSolutionTest extends TestCase {

	private SubIndexProviderSolution testSubIndexProviderSolution;
	private List<List<SubIndexProvider>> testListSIP;
	private List<List<Integer>> testDependentIndexes;

	/**
	 * @param name
	 */
	public SubIndexProviderSolutionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		testListSIP = new LinkedList<List<SubIndexProvider>>();
		testListSIP.add(new LinkedList<SubIndexProvider>());
		testDependentIndexes = new LinkedList<List<Integer>>();
		testDependentIndexes.add(new LinkedList<Integer>());
		testDependentIndexes.get(0).add(0);
		testSubIndexProviderSolution = new SubIndexProviderSolution(testListSIP, testDependentIndexes);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link SubIndexProviderSolution#SubIndexProviderSolution()}.
	 */
	public void testConstructor() {
		assertTrue("A SubIndexProviderSolution should be an instance of SubIndexProvider", testSubIndexProviderSolution instanceof SubIndexProvider);
	}

	/**
	 * Test method for {@link SubIndexProviderSolution#get_listSubIP()}.
	 */
	public void testGet_listSubIP() {
		assertTrue("get_listSubIP should return a List", testSubIndexProviderSolution.get_listSubIP() instanceof List);
		assertSame("get_listSubIP should return the correct List", testListSIP.get(0), testSubIndexProviderSolution.get_listSubIP());
	}

	/**
	 * Test method for {@link SubIndexProviderSolution#init()}.
	 */
	public void testInit() {
		testSubIndexProviderSolution.init();
		assertEquals("get_listSubIP should return 0 for a freshly initialized SubIndexProviderSolution", 0, testSubIndexProviderSolution.getValue());
	}
//
//	/**
//	 * Test method for {@link SubIndexProviderSolution#isValid()}.
//	 */
//	public void testIsValid() {
//		assertTrue("A correct SubIndexProviderSolution should be valid", testSubIndexProviderSolution.isValid());
//	}
}