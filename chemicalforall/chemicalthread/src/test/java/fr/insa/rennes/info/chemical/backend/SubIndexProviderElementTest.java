/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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

import java.math.BigInteger;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for IndexProvider
 */

public class SubIndexProviderElementTest extends TestCase {

	private SubIndexProviderElement testSubIndexProviderElement;
	private final int numberElementsInSolution;
	
	/**
	 * @param name
	 */
	public SubIndexProviderElementTest(String name) {
		super(name);
		numberElementsInSolution = 12;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testSubIndexProviderElement = new SubIndexProviderElement(numberElementsInSolution);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link SubIndexProviderElement#get_nummberOfElementsInSolution()}.
	 */
	public void testGet_nummberElementsInSolution() {
		assertTrue(numberElementsInSolution == testSubIndexProviderElement.get_nummberOfElementsInSolution());
	}
	
	/**
	 * Test method for {@link SubIndexProviderElement#set_nummberElementsInSolution(int)}.
	 */
	/*public void testSet_nummberElementsInSolution() {
		int testInt = 42;
		testSubIndexProviderElement.set_nummberOfElementsInSolution(testInt);
		assertTrue(testInt == testSubIndexProviderElement.get_nummberOfElementsInSolution());
	}*/

	/**
	 * Test method for {@link SubIndexProviderElement#getValue()}.
	 */
	public void testGetValue() {
		assertNotNull(testSubIndexProviderElement.getValue());
	}

	/**
	 * Test method for {@link SubIndexProviderElement#init()}.
	 */
	public void testInit() {
		testSubIndexProviderElement.init();
		assertTrue(0 == testSubIndexProviderElement.getValue());
	}

	/**
	 * Test method for {@link SubIndexProviderElement#increment()}.
	 */
	public void testIncrement() {
		boolean overflow = false;
		for(int i = 0 ; i < numberElementsInSolution ; i++){
			overflow |= testSubIndexProviderElement.increment();
		}
		assertTrue(overflow);
		overflow = false;
		testSubIndexProviderElement.init();
		int localMax = numberElementsInSolution-1;	//Maximum acceptable value without overflow
		for(int i = 0 ; i < localMax ; i++){
			overflow |= testSubIndexProviderElement.increment();
		}
		assertFalse(overflow);
		assertTrue(localMax == testSubIndexProviderElement.getValue());
	}

	/**
	 * Test method for {@link SubIndexProviderElement#setValue(int)}.
	 */
	/*public void testSetValue() {
		int test = 42;
		testSubIndexProviderElement.setValue(test);
		assertTrue(test == testSubIndexProviderElement.getValue());
	}*/

	/**
	 * Test method for {@link SubIndexProviderElement#getNumberOfElements()}.
	 */
	public void testGetNumberOfElements() {
		assertTrue(BigInteger.valueOf(numberElementsInSolution) == testSubIndexProviderElement.getNumberOfElements());
	}

	/**
	 * Test method for {@link SubIndexProviderElement#isValid()}.
	 */
	public void testIsValid() {
		assertTrue(testSubIndexProviderElement.isValid());
	}
}