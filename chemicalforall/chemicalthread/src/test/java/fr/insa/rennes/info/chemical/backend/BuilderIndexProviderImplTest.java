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

import java.lang.reflect.Field;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.backend.Solution.Strategy;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for BuilderIndexProviderImpl
 */

public class BuilderIndexProviderImplTest extends TestCase {

	private BuilderIndexProviderImpl testBIPI;
	private ReactionRule fooRR;
	private Solution fooSol;
	private Field[] fooF;
	private Strategy fooStra;
	
	/**
	 * @param name
	 */
	public BuilderIndexProviderImplTest(String name) {
		super(name);
		fooRR = new ReactionRule(){
			public Object[] computeResult() {
				return new Object[]{};
			}
			public boolean computeSelect() {
				return true;
			}
			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}
			
		};
		fooSol = new Solution();
		fooF = new Field[]{};
		fooStra = Strategy.ORDERED;
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testBIPI = new BuilderIndexProviderImpl();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link BuilderIndexProviderImpl#BuilderIndexProviderImpl()}.
	 */
	public void testConstructor() {
		assertTrue("A BuilderIndexProviderImpl should be an implementation of BuilderIndexProvider", this.testBIPI instanceof BuilderIndexProvider);
	}

	/**
	 * Test method for {@link BuilderIndexProviderImpl#GetProduct()}.
	 * @throws ChemicalException 
	 */
	public void testGetProduct(){
		boolean correct = false;
		try {
			this.testBIPI.getProduct();
		} catch (ChemicalException e) {
			correct = true;
		}
		assertTrue("Calling getProduct before completion should raise a ChemicalException", correct);
		
		try {
			this.testBIPI.build();
			assertTrue("Calling getProduct after completion should return an IndexProvider", this.testBIPI.getProduct() instanceof IndexProvider);
		} catch (ChemicalException e) {
			fail("Building should not raise any ChemicalException");
		}
		
		
	}

	/**
	 * Test method for {@link BuilderIndexProviderImpl#GetProduct()}.
	 * @throws ChemicalException 
	 */
	public void testBuild(){
		boolean correct = true;
		this.testBIPI.setReactionRuleFields(fooF);
		this.testBIPI.setSolution(fooSol);
		this.testBIPI.setStrategy(fooStra);
		try {
			this.testBIPI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a ReactionRule should raise a ChemicalException", correct);

		correct = true;
		this.testBIPI = new BuilderIndexProviderImpl();
		this.testBIPI.setReactionRule(fooRR);
		this.testBIPI.setSolution(fooSol);
		this.testBIPI.setStrategy(fooStra);
		try {
			this.testBIPI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without ReactionRule Fields should raise a ChemicalException", correct);

		correct = true;
		this.testBIPI = new BuilderIndexProviderImpl();
		this.testBIPI.setReactionRule(fooRR);
		this.testBIPI.setReactionRuleFields(fooF);
		this.testBIPI.setStrategy(fooStra);
		try {
			this.testBIPI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a Solution should raise a ChemicalException", correct);

		correct = true;
		this.testBIPI = new BuilderIndexProviderImpl();
		this.testBIPI.setReactionRule(fooRR);
		this.testBIPI.setReactionRuleFields(fooF);
		this.testBIPI.setSolution(fooSol);
		try {
			this.testBIPI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a Strategy should raise a ChemicalException", correct);

		correct = true;
		this.testBIPI = new BuilderIndexProviderImpl();
		this.testBIPI.setReactionRule(fooRR);
		this.testBIPI.setReactionRuleFields(fooF);
		this.testBIPI.setSolution(fooSol);
		this.testBIPI.setStrategy(fooStra);
		try {
			this.testBIPI.build();
		} catch (ChemicalException e) {
			fail("Building with all appropriate parameters should not raise any ChemicalException");
		}
		
	}
	
	
}