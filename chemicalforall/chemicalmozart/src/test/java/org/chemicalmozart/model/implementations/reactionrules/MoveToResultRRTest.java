package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;
import org.chemicalmozart.model.implementations.solutionindentification.Result;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class MoveToResultRRTest extends TestCase{
	Object[] _tabResult;
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(new QuaterLeft(0));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);
		this._tabResult = rr.computeResult();
	}
	@Test
	public void testComputeResult1(){
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof Result)
				bool = true;
		}
		assertTrue("No Result object was found in the computeResult", bool);
	}

	@Test
	public void testComputeResult2(){
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof Solution){//Solution result
				for(Object ob : ((Solution) o).toArray()){
					if(ob instanceof Solution){//Solution BarInCreation
						for(Object obj : ((Solution) ob).toArray()){
							if(obj instanceof BarInCreation)
								bool = true;
						}

					}
				}

			}

		}
		assertTrue("No BarInCreation object was found in the computeResult subsubsolution", bool);
	}

	@Test
	public void testComputeResult3(){
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof Solution){//Solution result
				for(Object ob : ((Solution) o).toArray()){
					if(ob instanceof Solution){//Solution BarInCreation
						for(Object obj : ((Solution) ob).toArray()){
							if(obj instanceof QuaterLeft)
								bool = true;
						}

					}
				}

			}

		}
		assertTrue("No QuaterLeft object was found in the computeResult subsubsolution", bool);
	}

	@Test
	public void testComputeResult4(){
		List<Object> l = new ArrayList<Object>(Arrays.asList(this._tabResult));
		boolean bool = false;
		for(Object o : l){
			if(o instanceof Solution){//Solution result
				for(Object ob : ((Solution) o).toArray()){
					if(ob instanceof Result){//Solution BarInCreation
						bool = true;
					}
				}

			}

		}
		assertTrue("No Result object was found in the computeResult solution returned", bool);
	}

	@Test
	public void testComputeSelect1() {
		MoveToResultRR rr = new MoveToResultRR();
		assertFalse("An unconfigured reactionrule shouldn't can not pass this test Mouahahahahahaha !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect2() {
		MoveToResultRR rr = new MoveToResultRR();
		assertTrue("The type list shouldn't be null in the subsolution BarInCreation Mouahahahahahaha !", rr.get_subSolInCreation().getTypeList() != null);
	}

	@Test
	public void testComputeSelect3() {
		MoveToResultRR rr = new MoveToResultRR();
		assertTrue("The type list shouldn't be null in the subsolution Result Mouahahahahahaha !", rr.get_subSolResult().getTypeList() != null);
	}

	@Test
	public void testComputeSelect4() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(new QuaterLeft(0));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect5() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(new QuaterLeft(2));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test, QuaterLeft != 0 !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect6() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test, QuaterLeft is missing !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect7() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.HARMONICRR);
		l.add(b);
		l.add(new QuaterLeft(0));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test, BarInCreation has the wrong state !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect8() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		l.add(new QuaterLeft(0));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(new Result());
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test, BarInCreation is not present !", rr.computeSelect());
	}

	@Test
	public void testComputeSelect9() {
		MoveToResultRR rr = new MoveToResultRR();
		SubSolutionElements elementsBIC = new SubSolutionElements();
		List<Object> l = new ArrayList<Object>();
		BarInCreation b = new BarInCreation();
		b.set_state(BarInCreationState.RYTHMEHRR);
		l.add(b);
		l.add(new QuaterLeft(0));
		elementsBIC.setElements(l);
		SubSolution<SubSolutionElements> barInCreationSubSolution = new SubSolution<SubSolutionElements>(elementsBIC);
		SubSolutionElements elementsResult = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		elementsResult.setElements(l2);
		SubSolution<SubSolutionElements> resultSubSolution = new SubSolution<SubSolutionElements>(elementsResult);
		rr.set_subSolInCreation(barInCreationSubSolution);
		rr.set_subSolResult(resultSubSolution);

		assertTrue("This configuration should pass the test, Result is missing in the result subsolution !", rr.computeSelect());
	}
}
