/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for SubSolutionElements
 */

public class SubSolutionElementsTest extends TestCase {

	private SubSolutionElements testSubSolutionElements;
	
	/**
	 * @param name
	 */
	public SubSolutionElementsTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		testSubSolutionElements = new SubSolutionElements();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link SubSolutionElements#SubSolutionElements()}.
	 */
	public void testConstructor() {
		this.testSubSolutionElements = new SubSolutionElements();
		assertTrue("SubSolutionElements should implement SubSolutionReagentsAccessor", this.testSubSolutionElements instanceof SubSolutionReagentsAccessor);
	}

	/**
	 * Test method for {@link SubSolutionElements#getElements()}
	 * and {@link SubSolutionElements#setElements(List<Object>)}.
	 */
	public void testElements() {
		List<Object> l = new LinkedList<Object>();
		l.add(new Object());
		l.add("65");
		testSubSolutionElements.setElements(l);
		assertTrue("getElements should return a List", testSubSolutionElements.getElements() instanceof List);
		assertTrue("getElements should return the correct Object List", testSubSolutionElements.getElements().equals(l));
	}

	/**
	 * Test method for {@link SubSolutionElements#getTypeList()}
	 * and {@link SubSolutionElements#setTypeList(List<Class<? extends Object>>)}.
	 */
	public void testTypeList() {
		assertTrue("getTypeList should return a List", testSubSolutionElements.getTypeList() instanceof List);
		List<Class<?>> l = new LinkedList<Class<?>>();
		testSubSolutionElements.setTypeList(l);
		assertTrue("getTypeList should return the correct Type List", testSubSolutionElements.getTypeList().equals(l));
		testSubSolutionElements.setTypeList(new ArrayList<Class<?>>());
		testSubSolutionElements.addType(getClass());
		assertTrue("addType should be effective", testSubSolutionElements.getTypeList().contains(getClass()));
	}

	/**
	 * Test method for {@link SubSolutionElements#getSolution()}
	 * and {@link SubSolutionElements#setSolution(Solution)}.
	 */
	public void testSolution() {
		
		Solution s = new Solution();
		char[] content = "Super toto_lala".toCharArray();
		for(char c : content)
			s.add(c);
		
		testSubSolutionElements.setSolution(s);
		assertTrue("getSolution should return a Solution", testSubSolutionElements.getSolution() instanceof Solution);
		assertTrue("getSolution should return the correct Solution", testSubSolutionElements.getSolution().equals(s));
	}
}