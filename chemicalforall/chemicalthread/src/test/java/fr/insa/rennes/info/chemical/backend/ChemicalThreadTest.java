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
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for ChemicalThread
 */

public class ChemicalThreadTest extends TestCase {

	private ChemicalThread testChemicalThread;
	private ReactionRule testReactionRule;
	private Solution testSolution;

	/**
	 * @param name
	 */
	public ChemicalThreadTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.testSolution = new Solution();
		this.testReactionRule = new ReactionRule(){
			public Object[] computeResult(){
				return null;
			}
			public boolean computeSelect(){
				return true;
			}
			public ReactionRule.Multiplicity getMultiplicity(){
				return ReactionRule.Multiplicity.ONE_SHOT;
			}
			@SuppressWarnings("unused")
			public void setThis$0(Object t){}
		};
		this.testSolution.add(this.testReactionRule);

		this.testChemicalThread = new ChemicalThread(this.testReactionRule, this.testSolution, new ThreadGroup("test"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link ChemicalThread#ChemicalThread()}.
	 */
	public void testConstructor() {
		assertTrue("A ChemicalThread must be a Thread", testChemicalThread instanceof Thread);
	}

	/**
	 * Test method for {@link ChemicalThread#run()}.
	 */
	@SuppressWarnings("deprecation")
	public void testRun() {
		testChemicalThread.start();
		assertTrue("A ChemicalThread must be runnable", testChemicalThread.isAlive());
		testChemicalThread.stop();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse("A ChemicalThread must be stoppable (with JVM behavior)", testChemicalThread.isAlive());
	}

	/**
	 * Test method for {@link ChemicalThread#stopTheThread()}.
	 */
	public void testStopTheThread() {
		testChemicalThread.start();
		testChemicalThread.stopTheThread();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertFalse("A ChemicalThread must be stoppable (with custom behavior)", testChemicalThread.isAlive());
	}

	/**
	 * Test method for {@link ChemicalThread#toString()}.
	 */
	public void testToString() {
		assertTrue("A ChemicalThread should be printed with the ReactionRule it is associated with", testChemicalThread.toString().toLowerCase().contains(this.testReactionRule.toString().toLowerCase()));
	}
}