package org.chemicalmozart.model.implementations.reactionrules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;

public class PickOneRRTest {

	@Test
	public void testComputeResult() {
		boolean isGarbageRRPresent = false;
		boolean isDegreeImplPresent = false;
		boolean isBarInCreationPresent = false;
		boolean isQuaterLeftPresent = false;
		boolean isBarNumberPresent = false;
		
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
		
		ElementList e1 = new ElementList();
		List<Object> l1 = new ArrayList<Object>();
		l1.add(temporarySolution);
		l1.add(temp);
		l1.add(deg);
		e1.setElementList(l1);
		SubSolution<ElementList> temporaryBar = new SubSolution<ElementList>(e1);
		
		ElementList e2 = new ElementList();
		List<Object> l2 = new ArrayList<Object>();
		l2.add(BarInCreation.class);
		e2.setElementList(l2);
		SubSolution<ElementList> barInCreation = new SubSolution<ElementList>(e2);
		
		PickOneRR rr = new PickOneRR();
		rr.set_barInCreation(barInCreation);
		rr.set_temporaryBar(temporaryBar);
		
		//Now the RR is instantiated and ready for being tested
		Object[] result = rr.computeResult();
		
		for(Object o : result){
			if(o instanceof GarbageRR){
				isGarbageRRPresent = true;
			}else if(o instanceof DegreeImpl){
				isDegreeImplPresent = true;
			}
		}
		
		for(Object o : barInCreationSolution){
			if(o instanceof QuaterLeft){
				isQuaterLeftPresent = true;
			}else if(o instanceof BarInCreation){
				isBarInCreationPresent = true;
			}else if(o instanceof BarNumber){
				isBarNumberPresent = true;
			}
		}
		
		assertTrue("GarbageRR is not present", isGarbageRRPresent);
		assertTrue("DegreeImpl is not present", isDegreeImplPresent);
		assertTrue("QuaterLeft is no more present", isQuaterLeftPresent);
		assertTrue("Bar in creation is not present in the BarInCreation Solution", isBarInCreationPresent);
		assertTrue("BarNumber is not present", isBarNumberPresent);
	}

}
