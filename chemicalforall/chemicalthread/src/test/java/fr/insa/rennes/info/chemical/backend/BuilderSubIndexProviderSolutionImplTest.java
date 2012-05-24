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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import junit.framework.TestCase;
import fr.insa.rennes.info.chemical.user.ReactionRule;

/**
 * @author ArthurTemple
 * Unit tests for BuilderSubIndexProviderSolutionImpl
 */

public class BuilderSubIndexProviderSolutionImplTest extends TestCase {

	private BuilderSubIndexProviderSolutionImpl testBSIPSI;
	private ReactionRule fooRR;
	private Solution fooSol;
	private Field[] fooFs;
	private ParameterizedType fooPT;
	private Field fooF;
	
	
	/**
	 * @param name
	 */
	public BuilderSubIndexProviderSolutionImplTest(String name) {
		super(name);
		fooRR = new ReactionRule(){
			
			int a;
			
			@SuppressWarnings("unused")
			public void setA(int aa){
				this.a = aa;
			}
			@SuppressWarnings("unused")
			public int getA(){
				return a;
			}
			public Object[] computeResult() {
				return new Object[]{a+1};
			}
			public boolean computeSelect() {
				return true;
			}
			public Multiplicity getMultiplicity() {
				return Multiplicity.ONE_SHOT;
			}
			
		};
		fooSol = new Solution();
		fooPT = new ParameterizedType() {
			private Type foobaz = new Type() {};
			public Type getRawType() {
				return foobaz;
			}
			public Type getOwnerType() {
				return foobaz;
			}
			public Type[] getActualTypeArguments() {
				return new Type[]{foobaz};
			}
		};
		fooFs = Integer.class.getFields();
		fooF = fooFs[0];
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link BuilderSubIndexProviderSolutionImpl#BuilderSubIndexProviderSolutionImpl()}.
	 */
	public void testConstructor() {
		assertTrue("A BuilderSubIndexProviderSolutionImpl should be an implementation of BuilderSubIndexProviderSolution", this.testBSIPSI instanceof BuilderSubIndexProviderSolution);
	}

	/**
	 * Test method for {@link BuilderSubIndexProviderSolutionImpl#GetProduct()}.
	 * @throws ChemicalException 
	 */
	public void testGetProduct(){
		boolean correct = false;
		try {
			this.testBSIPSI.getProduct();
		} catch (ChemicalException e) {
			correct = true;
		}
		assertTrue("Calling getProduct before completion should raise a ChemicalException", correct);

		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setReactionRuleFields(fooFs);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
			assertTrue("Calling getProduct after completion should return a SubIndexProviderSolution", this.testBSIPSI.getProduct() instanceof SubIndexProviderSolution);
		} catch (ChemicalException e) {
			fail("Building should not raise any ChemicalException");
		}
		
	}

	/**
	 * Test method for {@link BuilderSubIndexProviderSolutionImpl#GetProduct()}.
	 * @throws ChemicalException 
	 */
	public void testBuild(){
		boolean correct = true;
		this.testBSIPSI.setReactionRuleFields(fooFs);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a ReactionRule should raise a ChemicalException", correct);

		correct = true;
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without ReactionRule Fields should raise a ChemicalException", correct);
		
		correct = true;
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setReactionRuleFields(fooFs);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a SubSolution Field should raise a ChemicalException", correct);

		correct = true;
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setReactionRuleFields(fooFs);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a Solution should raise a ChemicalException", correct);

		correct = true;
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setReactionRuleFields(fooFs);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			correct = false;
		}
		assertFalse("Calling build without a ParameterizedType should raise a ChemicalException", correct);

		correct = true;
		this.testBSIPSI = new BuilderSubIndexProviderSolutionImpl();
		this.testBSIPSI.setReactionRule(fooRR);
		this.testBSIPSI.setReactionRuleFields(fooFs);
		this.testBSIPSI.setSolution(fooSol);
		this.testBSIPSI.setParamType(fooPT);
		this.testBSIPSI.setSubSolutionField(fooF);
		try {
			this.testBSIPSI.build();
		} catch (ChemicalException e) {
			fail("Building with all appropriate parameters should not raise any ChemicalException");
		}
		
	}
	
	
}