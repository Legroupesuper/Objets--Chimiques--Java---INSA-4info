/*
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License

    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.chemicalmozart.model.implementations.reactionrules.CreateBarRRTest;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR1Test;

/**
 * Unit test for simple App.
 */
public class AppTest
extends TestCase
{
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		TestSuite testSuite = new TestSuite();
		testSuite.addTest(new TestSuite(CreateBarRRTest.class));
		testSuite.addTest(new TestSuite(HarmonicRR1Test.class));
		return testSuite;
	}

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public AppTest( String testName )
	{
		super( testName );
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp()
	{
		assertTrue( true );
	}
}
