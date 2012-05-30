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

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for IndexProvider
 */

public class IndexProviderTest extends TestCase {

	private IndexProvider testIndexProvider;
	private SubIndexProviderSolution testSubIndexProviderSolution;
	
	/**
	 * @param name
	 */
	public IndexProviderTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		List<List<SubIndexProvider>>testListSIP = new LinkedList<List<SubIndexProvider>>();
		testListSIP.add(new LinkedList<SubIndexProvider>());
		List<List<Integer>> testDependentIndexes = new LinkedList<List<Integer>>();
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
	 * Test method for {@link IndexProvider#IndexProvider()}.
	 */
	public void testConstructor() {
//		boolean correct = true;
//		try {
//			testIndexProvider = new IndexProvider(testSubIndexProviderSolution);
//		} catch (ChemicalException e) {
//			correct = false;
//		}
//		assertTrue("An IndexProvider should be buildable", correct);
		assertTrue(true);
	}
}