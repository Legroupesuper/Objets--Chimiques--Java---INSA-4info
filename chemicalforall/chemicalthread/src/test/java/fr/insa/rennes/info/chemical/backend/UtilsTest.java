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

import java.util.logging.Logger;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for Utils
 */

public class UtilsTest extends TestCase {
	
	private String packageName = "fr.insa.rennes.info.chemical";
	
	/**
	 * @param name
	 */
	public UtilsTest(String name) {
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
	 * Test method for {@link Utils#logger}.
	 */
	public void testLogger() {
		assertFalse("Logger should not be anonymous", Utils.logger.equals(Logger.getAnonymousLogger()));
		assertTrue("Logger should be associated to the right package", Utils.logger.equals(Logger.getLogger(packageName)));
	}

	/**
	 * Test method for {@link Utils#getMethodFromReactionRule(ReactionRule, String, Field).
	 */
	public void testGetMethodFromReactionRule() {
		ReactionRule rr = new ReactionRule() {
			
			int foo;
			
			@SuppressWarnings("unused")
			public void setFoo(int aFoo){
				this.foo = aFoo;
			}
			
			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}
			
			public boolean computeSelect() {
				return true;
			}
			
			public Object[] computeResult() {
				return new Object[]{foo+12};
			}
		};
		try {
			assertTrue("The method extracted from the ReactionRule should at least have the right name", Utils.getMethodFromReactionRule(rr, "set", rr.getClass().getDeclaredField("foo")).getName().equalsIgnoreCase("setFoo"));
		} catch (NoSuchFieldException e) {
			fail("NoSuchFieldException");
		} catch (SecurityException e) {
			fail("SecurityException");
		}
	}
}