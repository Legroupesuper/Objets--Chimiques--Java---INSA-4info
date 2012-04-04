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

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class StartMelodicRRTest extends TestCase{
	Object[] _tabResult;
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
		rr.set_measureNumber(new BarNumber(1));
		rr.set_sol(subsol);

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
		rr.set_measureNumber(new BarNumber(1));
		rr.set_sol(subsol);

		assertTrue("The test should pass, check the compute result or look at the other tests to have more informations", rr.computeSelect());
	}

	@Test
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
		rr.set_measureNumber(new BarNumber(2));
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
		rr.set_measureNumber(new BarNumber(1));
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
		rr.set_measureNumber(new BarNumber(1));
		rr.set_sol(subsol);

		assertFalse("The test shouldn't pass, The subsolution doesn't contain a BarNumber object", rr.computeSelect());
	}
}
