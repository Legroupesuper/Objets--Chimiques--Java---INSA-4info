package org.chemicalmozart.model.implementations.reactionrules;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;

public class PickOneRRTest extends TestCase{
	private boolean _isGarbageRRPresent = false;
	private boolean _isDegreeImplPresent = false;
	private boolean _isBarInCreationPresent = false;
	private boolean _isQuaterLeftPresent = false;
	private boolean _isBarNumberPresent = false;
	
	protected void setUp() throws Exception {
		super.setUp();
		//Instantiate the needed parameters
		Solution temporarySolution = new Solution();
		Temporary temp = new Temporary();
		temporarySolution.add(temp);
		DegreeImpl deg = new DegreeImpl(1);
		temporarySolution.add(deg);
		temporarySolution.add(new DegreeImpl(2));
		temporarySolution.add(new DegreeImpl(3));

		Solution barInCreationSolution = new Solution();
		BarInCreation bic = new BarInCreation();
		barInCreationSolution.add(bic);
		barInCreationSolution.add(new QuaterLeft(4));
		barInCreationSolution.add(new BarNumber(1));

		SubSolutionElements e1 = new SubSolutionElements();
		List<Object> l1 = new ArrayList<Object>();
		l1.add(temporarySolution);
		l1.add(temp);
		l1.add(deg);
		e1.setElements(l1);
		SubSolution<SubSolutionElements> temporaryBar = new SubSolution<SubSolutionElements>(e1);

		SubSolutionElements e2 = new SubSolutionElements();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(BarInCreation.class);
		e2.setElements(l2);
		SubSolution<SubSolutionElements> barInCreation = new SubSolution<SubSolutionElements>(e2);

		PickOneRR rr = new PickOneRR();
		rr.set_barInCreation(barInCreation);
		rr.set_temporaryBar(temporaryBar);

		//Now the RR is instantiated and ready for being tested
		Object[] result = rr.computeResult();

		for(Object o : result){
			if(o instanceof GarbageRR){
				_isGarbageRRPresent = true;
			}else if(o instanceof DegreeImpl){
				_isDegreeImplPresent = true;
			}
		}

		for(Object o : barInCreationSolution){
			if(o instanceof QuaterLeft){
				_isQuaterLeftPresent = true;
			}else if(o instanceof BarInCreation){
				_isBarInCreationPresent = true;
			}else if(o instanceof BarNumber){
				_isBarNumberPresent = true;
			}
		}
	}

	@Test
	public void testGarbageRRPresent() {
		assertTrue("GarbageRR is not present", _isGarbageRRPresent);
	}

	@Test
	public void testDegreeImplPresent(){
		assertTrue("DegreeImpl is not present", _isDegreeImplPresent);
	}
	
	@Test
	public void testQuaterLeftPresent(){
		assertTrue("QuaterLeft is no more present", _isQuaterLeftPresent);
	}
	
	@Test
	public void testBarInCreationPresent(){
		assertTrue("Bar in creation is not present in the BarInCreation Solution", _isBarInCreationPresent);
	}
	
	@Test
	public void testBarNumberPresent(){
		assertTrue("BarNumber is not present", _isBarNumberPresent);
	}
}
