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

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for SubSolution
 */

public class SubSolutionTest extends TestCase {

	private SubSolution<?> testSubSolution;
	
	/**
	 * @param name
	 */
	public SubSolutionTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Solution s = new Solution();
		char[] content = "Super toto_lala".toCharArray();
		for(char c : content)
			s.add(c);
		
		testSubSolution = new SubSolution<SubSolutionReagentsAccessor>();
		testSubSolution.setSolution(s);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		
	}

	/**
	 * Test method for {@link SubSolution#SubSolution()}.
	 */
	public void testConstructor() {
		testSubSolution = new SubSolution<SubSolution<?>>();
		assertTrue("A SubSolution built with first constructor should implement SubSolutionReagentsAccessor", testSubSolution instanceof SubSolutionReagentsAccessor);
		testSubSolution = new SubSolution<SubSolutionElements>(new SubSolutionElements());
		assertTrue("A SubSolution built with second constructor should implement SubSolutionReagentsAccessor", testSubSolution instanceof SubSolutionReagentsAccessor);
//		assertTrue("A SubSolution initialized with the SubSolution type should contain a SubSolution object", testSubSolution.);
//		assertTrue("A SubSolution initialized with a non-SubSolution type should contain a SubSolutionElements object", );
	}
}