package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class RythmeHRRTest extends TestCase{
	private Object[] _tabResult;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
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
				if((Integer)o == 1)
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
		boolean valueCorrect = false;
		for(Object o : l){
			if(o instanceof ChordImpl){
				bool = true;
				if(((ChordImpl) o).get_position() == 0)
					valueCorrect = true;
			}
		}
		assertTrue("No ChordImpl wasn't found in the computeResult", bool);
		assertTrue("The value of the position is not correct", valueCorrect);
	}

	@Test
	public void testComputeResult3() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		boolean valueCorrect = false;
		for(Object o : l){
			if(o instanceof QuaterLeft){
				bool = true;
				if(((QuaterLeft) o).getValue()<4)
					valueCorrect = true;
			}
		}
		assertTrue("No QuaterLeft wasn't found in the computeResult", bool);
		assertTrue("The value of the QuaterLeft is not correct", valueCorrect);
	}

	@Test
	public void testComputeResult4() {
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof BarInCreation)
				bool = true;
		}
		assertTrue("No BarInCreation wasn't	found in the computeResult", bool);
	}

	@Test
	public void testComputeSelect1() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertTrue("Compute select didn't passed the test", rr.computeSelect());
	}

	@Test
	public void testComputeSelect2() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.HARMONICRR);
		l.add(b);
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("The computeSelect shhouldn't pass with state of BarInCreation setted to HARMONICRR", rr.computeSelect());
	}

	@Test
	public void testComputeSelect3() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("ComputeSelect shouldn't passed the test, int is missing", rr.computeSelect());
	}

	@Test
	public void testComputeSelect4() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("ComputeSelect shouldn't passed the test, BarInCreation is missing", rr.computeSelect());
	}

	@Test
	public void testComputeSelect5() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("ComputeSelect shouldn't passed the test, BarInCreation is missing", rr.computeSelect());
	}

	@Test
	public void testComputeSelect6() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(0);
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("ComputeSelect shouldn't passed the test, DegreeImpl is missing", rr.computeSelect());
	}
	@Test
	public void testComputeSelect7() {
		RythmeHRR rr = new RythmeHRR();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> listType = rr.getSol().getTypeList();
		e.setTypeList(listType);
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(0);
		l.add(new DegreeImpl(1));
		l.add(new QuaterLeft(4));
		e.setElements(l);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.setSol(subsol);
		assertFalse("ComputeSelect shouldn't passed the test, QuaterLeft is missing", rr.computeSelect());
	}
	@Test
	public void testComputeSelect8() {
		RythmeHRR rr = new RythmeHRR();
		assertFalse("ComputeSelect shouldn't passed the test, Nothing is initialized", rr.computeSelect());
	}
}
