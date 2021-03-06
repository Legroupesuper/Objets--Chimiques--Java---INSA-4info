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

import java.math.BigInteger;

import junit.framework.TestCase;

/**
 * @author ArthurTemple
 * Unit tests for RandomIncrementStrategy
 */

public class RandomIncrementStrategyTest extends TestCase {

	private RandomIncrementStrategy testRandomIncrementStrategy;
	
	/**
	 * @param name
	 */
	public RandomIncrementStrategyTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		testRandomIncrementStrategy = new RandomIncrementStrategy(BigInteger.valueOf(12));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link RandomIncrementStrategy#RandomIncrementStrategy()}.
	 */
	public void testConstructor() {
		assertTrue("RandomIncrementStrategy should be an IncrementStrategy", testRandomIncrementStrategy instanceof IncrementStrategy);
	}

}