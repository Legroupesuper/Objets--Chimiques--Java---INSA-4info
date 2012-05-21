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

import junit.framework.TestCase;

import org.junit.Test;

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

	@Test
	public void testPourCompiler(){
		assertTrue(true);
	}

	/**
	 * Test method for {@link ChemicalThread#get_reactionRule()}.
	 */
	/*public void testGet_reactionRule() {
		assertTrue(testReactionRule == testChemicalThread.get_reactionRule());
	}*/

	/**
	 * Test method for {@link ChemicalThread#get_solutionContainer()}.
	 */
	/*public void testGet_solutionContainer() {
		assertTrue(testSolution == testChemicalThread.get_solutionContainer());
	}*/

	/**
	 * Test method for {@link ChemicalThread#set_reactionRule(ReactionRule)}.
	 */
	/*public void testSet_reactionRule() {
		ReactionRule rr = new ReactionRule(){
			public Object[] computeResult(){
				return null;
			}
			public boolean computeSelect(){
				return true;
			}
			public ReactionRule.Multiplicity getMultiplicity(){
				return ReactionRule.Multiplicity.INFINITY_SHOT;
			}
		};
		testChemicalThread.set_reactionRule(rr);
		assertTrue(rr == testChemicalThread.get_reactionRule());
	}*/

	/**
	 * Test method for {@link ChemicalThread#set_solutionContainer(Solution)}.
	 */
	/*public void testSet_solutionContainer() {
		Solution s = new Solution();
		testChemicalThread.set_solutionContainer(s);
		assertTrue(s == testChemicalThread.get_solutionContainer());
	}*/
}