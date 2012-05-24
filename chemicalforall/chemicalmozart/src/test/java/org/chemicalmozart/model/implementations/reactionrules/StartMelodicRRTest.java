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
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class StartMelodicRRTest extends TestCase{
	/*Object[] _tabResult;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		StartMelodicRR rr = new StartMelodicRR();

		Pitch pitch = new Pitch();
		pitch.setDegree(new DegreeImpl(1));
		pitch.setOctave(5);

		SubSolutionElements elements = new SubSolutionElements();

		List<Object> l = new ArrayList<Object>();
		l.add(new BarInCreation());
		l.add(new BarNumber(1));

		elements.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(elements);

		rr.set_pitch(pitch);
		rr.set_barNumber(new BarNumber(1));
		rr.set_sol(subsol);
		rr.get_sol().setSolution(new Solution());

		this._tabResult = rr.computeResult();
	}

	@Test
	public void testComputeResult1() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		boolean bool2 = false;
		for(Object o : l){
			if(o instanceof BarNumber && ((BarNumber) o).getValue()==2){
				bool = true;
				bool2 = true;
			}else if(o instanceof BarNumber){
				bool2 = true;
			}
		}
		assertTrue("No BarNumber was found in the computeResult", bool2);
		assertTrue("The value of the BarNumber was expected to be 2", bool);
	}

	/*
	@Test
	public void testComputeSelect1() {
		StartMelodicRR rr = new StartMelodicRR();

		Pitch pitch = new Pitch();
		pitch.setDegree(new DegreeImpl(1));
		pitch.setOctave(5);

		SubSolutionElements elements = new SubSolutionElements();

		List<Object> l = new ArrayList<Object>();
		l.add(new BarInCreation());
		l.add(new BarNumber(1));

		List<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>();
		types.add(BarInCreation.class);
		types.add(BarNumber.class);

		elements.setElements(l);
		elements.setTypeList(types);

		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(elements);

		rr.set_pitch(pitch);
		rr.set_barNumber(new BarNumber(1));
		rr.set_sol(subsol);

		assertTrue("The test should pass, check the compute result or look at the other tests to have more informations", rr.computeSelect());
	}
	*/

/*	@Test
	public void testComputeSelect2() {
		StartMelodicRR rr = new StartMelodicRR();

		Pitch pitch = new Pitch();
		pitch.setDegree(new DegreeImpl(1));
		pitch.setOctave(5);

		SubSolutionElements elements = new SubSolutionElements();

		List<Object> l = new ArrayList<Object>();
		l.add(new BarInCreation());
		l.add(new BarNumber(1));

		List<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>();
		types.add(BarInCreation.class);
		types.add(BarNumber.class);

		elements.setElements(l);
		elements.setTypeList(types);

		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(elements);

		rr.set_pitch(pitch);
		rr.set_barNumber(new BarNumber(2));
		rr.set_sol(subsol);

		assertFalse("The test shouldn't pass, BarNumber must correspond", rr.computeSelect());
	}

	@Test
	public void testComputeSelect3() {
		StartMelodicRR rr = new StartMelodicRR();

		Pitch pitch = new Pitch();
		pitch.setDegree(new DegreeImpl(1));
		pitch.setOctave(5);

		SubSolutionElements elements = new SubSolutionElements();

		List<Object> l = new ArrayList<Object>();
		l.add(new BarNumber(1));

		List<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>();
		types.add(BarInCreation.class);
		types.add(BarNumber.class);

		elements.setElements(l);
		elements.setTypeList(types);

		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(elements);

		rr.set_pitch(pitch);
		rr.set_barNumber(new BarNumber(1));
		rr.set_sol(subsol);

		assertFalse("The test shouldn't pass, The sub solution dosen't contain a BarInCreation", rr.computeSelect());
	}

	@Test
	public void testComputeSelect4() {
		StartMelodicRR rr = new StartMelodicRR();

		Pitch pitch = new Pitch();
		pitch.setDegree(new DegreeImpl(1));
		pitch.setOctave(5);

		SubSolutionElements elements = new SubSolutionElements();

		List<Object> l = new ArrayList<Object>();
		l.add(new BarInCreation());

		List<Class<? extends Object>> types = new ArrayList<Class<? extends Object>>();
		types.add(BarInCreation.class);
		types.add(BarNumber.class);

		elements.setElements(l);
		elements.setTypeList(types);

		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(elements);

		rr.set_pitch(pitch);
		rr.set_barNumber(new BarNumber(1));
		rr.set_sol(subsol);

		assertFalse("The test shouldn't pass, The subsolution doesn't contain a BarNumber object", rr.computeSelect());
	}*/
	


	@Test
	public void testConstructor() {
		assertFalse(false);
	}
}
