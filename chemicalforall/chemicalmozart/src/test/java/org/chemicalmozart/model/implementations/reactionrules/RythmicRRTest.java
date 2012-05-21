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
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;
import org.chemicalmozart.model.interfaces.Rythme;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class RythmicRRTest extends TestCase{
	Object[] _tabResult = null;
	private SubSolution<SubSolutionElements> _rythmeSolution;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
		RythmicRR rr = new RythmicRR();
		Solution s = factory.createRythmicPull();
		//set the manually the subsolution (ouch !)
		List<Object> elements = new ArrayList<Object>();
		elements.add(new RythmePull());
		Object[] table = s.toArray();
		for(Object o : table){
			if(Arrays.asList(o.getClass().getInterfaces()).contains(Rythme.class)){
				elements.add(o);
				break;
			}
		}
		SubSolutionElements e = new SubSolutionElements();
		e.setSolution(s);
		e.setElements(elements);
		this._rythmeSolution = new SubSolution<SubSolutionElements>(e);

		ChordImpl chord = new ChordImpl();
		chord.set_degrees(new DegreeImpl(1));
		chord.set_position(0);

		rr.set_melodicRR(new MelodicRR());
		rr.set_rythmeSolution(this._rythmeSolution);
		rr.setChordImpl(chord);
		rr.setNum(2);
		rr.set_max(0);
		rr.set_chordNumber(0);
		this._tabResult = rr.computeResult();
	}

	@Test
	public void testComputeResult1() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		boolean valueCorrect = false;
		for(Object o : l){
			if(o instanceof Integer){
				bool = true;
				if((Integer)o == 2)
					valueCorrect = true;
			}
		}
		assertTrue("No integer wasn't found in the computeResult", bool);
		assertTrue("The value of the integer is not correct", valueCorrect);
	}

	@Test
	public void testComputeResult2() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof ChordImpl){
				bool = true;
			}
		}
		assertTrue("No ChordImpl wasn't found in the computeResult", bool);
	}

	@Test
	public void testComputeResult3() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		boolean valueCorrect = false;
		for(Object o : l){
			if(o instanceof Solution){
				bool = true;
				for(Object o2 : ((Solution) o).toArray()){
					if(o2 instanceof RythmePull)
						valueCorrect = true;
				}

			}
		}
		assertTrue("No solution was found in the computeResult", bool);
		assertTrue("The RythmicPull solution wasn't found in the computeResult ", valueCorrect);
	}

	@Test
	public void testComputeResult4() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof MelodicRR){
				bool = true;
			}
		}
		assertTrue("No MelodicRR wassn't found in the computeResult", bool);
	}

	@Test
	public void testComputeResult5() {
		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
		RythmicRR rr = new RythmicRR();
		Solution s = factory.createRythmicPull();
		//set the manually the subsolution (ouch !)
		List<Object> elements = new ArrayList<Object>();
		elements.add(new RythmePull());
		Object[] table = s.toArray();
		for(Object o : table){
			if(Arrays.asList(o.getClass().getInterfaces()).contains(Rythme.class)){
				elements.add(o);
				break;
			}
		}
		SubSolutionElements e = new SubSolutionElements();
		e.setSolution(s);
		e.setElements(elements);
		SubSolution<SubSolutionElements> rythmeSolution = new SubSolution<SubSolutionElements>(e);

		ChordImpl chord = new ChordImpl();
		chord.set_degrees(new DegreeImpl(1));
		chord.set_position(1);

		rr.set_melodicRR(new MelodicRR());
		rr.set_rythmeSolution(rythmeSolution);
		rr.setChordImpl(chord);
		rr.setNum(2);
		rr.set_max(0);
		rr.set_chordNumber(1);

		Object[] tabResult = rr.computeResult();
		boolean solPresent = false;
		boolean ryPresent = false;
		for(Object o : tabResult){
			if(o instanceof Solution)
				solPresent = true;
		}

		for(Object o : tabResult){
			if(o instanceof RythmicRR)
				ryPresent = true;
		}


		assertFalse("The RythmePull solution shouldn't be present", solPresent);
		assertFalse("The RythmicRR shouldn't be present", ryPresent);
	}

	@Test
	public void testComputeResult6() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof RythmicRR){
				bool = true;
			}
		}
		assertTrue("No RythmicRR was found in the computeResult", bool);
	}

	@Test
	public void testComputeSelect1() {
		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
		RythmicRR rr = new RythmicRR();
		Solution s = factory.createRythmicPull();
		//set the manually the subsolution (ouch !)
		List<Object> elements = new ArrayList<Object>();
		elements.add(new RythmePull());
		Object[] table = s.toArray();
		for(Object o : table){
			if(Arrays.asList(o.getClass().getInterfaces()).contains(Rythme.class)){
				elements.add(o);
				break;
			}
		}
		SubSolutionElements e = new SubSolutionElements();
		e.setSolution(s);
		e.setElements(elements);
		SubSolution<SubSolutionElements> rythmeSolution = new SubSolution<SubSolutionElements>(e);

		ChordImpl chord = new ChordImpl();
		chord.set_degrees(new DegreeImpl(1));
		chord.set_position(0);

		rr.set_melodicRR(new MelodicRR());
		rr.set_rythmeSolution(rythmeSolution);
		rr.setChordImpl(chord);
		rr.setNum(2);
		rr.set_max(0);
		rr.set_chordNumber(0);
		assertTrue("The compute select should pass in this configuration", rr.computeSelect());
	}

	@Test
	public void testComputeSelect2() {
		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
		RythmicRR rr = new RythmicRR();
		Solution s = factory.createRythmicPull();
		//set the manually the subsolution (ouch !)
		List<Object> elements = new ArrayList<Object>();
		elements.add(new RythmePull());
		Object[] table = s.toArray();
		for(Object o : table){
			if(Arrays.asList(o.getClass().getInterfaces()).contains(Rythme.class)){
				elements.add(o);
				break;
			}
		}
		SubSolutionElements e = new SubSolutionElements();
		e.setSolution(s);
		e.setElements(elements);
		SubSolution<SubSolutionElements> rythmeSolution = new SubSolution<SubSolutionElements>(e);

		ChordImpl chord = new ChordImpl();
		chord.set_degrees(new DegreeImpl(1));
		chord.set_position(0);

		rr.set_melodicRR(new MelodicRR());
		rr.set_rythmeSolution(rythmeSolution);
		rr.setChordImpl(chord);
		rr.setNum(2);
		rr.set_max(0);
		rr.set_chordNumber(1);
		assertFalse("The compute select shouldn't pass in this configuration, ChordNumber and the ChordImpl position doesn't correspond", rr.computeSelect());
	}



}