package org.chemicalmozart.model.implementations.reactionrules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;

public class HarmonicRR1Test {

	@Test
	public void testComputeResult() {
		boolean isSolutionPresent = false;
		boolean isSolutionValid = false;
		boolean isPickOneRRPresent = false;
		boolean isRythmeRRPresent = false;
		boolean isBarInCreationPresent = false;
		
		HarmonicRR1 rr = new HarmonicRR1();
		rr.set_degree(new DegreeImpl(1));
		
		//create the BarInCreation solution
		Solution sol = new Solution();
		BarInCreation bic = new BarInCreation();
		sol.add(bic);
		sol.add(new QuaterLeft(4));
		sol.add(new BarNumber(1));
		
		//Instanciate a SubSolution object for the RR
		ElementList e = new  ElementList();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		e.setTypeList(l);
		List<Object> ll = new ArrayList<Object>();
		ll.add(sol);
		ll.add(bic);
		e.setElementList(ll);
		SubSolution<ElementList> subsol = new SubSolution<ElementList>(e);
		rr.set_barInCreationSolution(subsol);
		
		assertTrue("The computeSelect doesn't validate some good parameters", rr.computeSelect());
		Object[] result = rr.computeResult();
		//Test the returned elements
		for(Object o : result){
			if(o instanceof Solution){
				isSolutionPresent = true;
				Iterator<Object> it = ((Solution) o).iterator();
				while(it.hasNext()){
					Object o2 = it.next();
					if(o2 instanceof DegreeImpl){
						isSolutionValid = true;
						break;
					}
				}
			}else if(o instanceof PickOneRR){
				isPickOneRRPresent = true;
			}else if(o instanceof RythmeHRR){
				isRythmeRRPresent = true;
			}
		}
		
		//Test the content of the BarInCreation Solution
		for(Object o : sol){
			if(o instanceof BarInCreation)
				isBarInCreationPresent = true;
		}
		
		assertTrue("The Solution which contains all possible Degree is not present", isSolutionPresent);
		assertTrue("The Solution which contains all possible Degree is not valid", isSolutionValid);
		assertTrue("The RR doesn't return a PickOneRR", isPickOneRRPresent);
		assertTrue("The RR doesn't return a RythmeRR", isRythmeRRPresent);
		assertTrue("The RR doesn't put the BarInCreation object back into the BarInCreation solution", isBarInCreationPresent);
	}

	@Test
	public void testComputeSelect() {
		HarmonicRR1 rr = new HarmonicRR1();
		assertFalse("The computeSelect shouldn't accept this configuration", rr.computeSelect());
		
		//We instantiate the RR with a valid DegreeImpl and a valid Solution which represents a BarInCreation
		//We test some different configurations
		rr.set_degree(new DegreeImpl(1));
		assertFalse("The computeSelect shouldn't accept this configuration", rr.computeSelect());
		Solution sol = new Solution();
		BarInCreation bic = new BarInCreation();
		
		//Instanciate a SubSolution object for the RR
		ElementList e = new  ElementList();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		e.setTypeList(l);
		List<Object> ll = new ArrayList<Object>();
		ll.add(sol);
		ll.add(bic);
		e.setElementList(ll);
		SubSolution<ElementList> subsol = new SubSolution<ElementList>(e);
		rr.set_barInCreationSolution(subsol);
		assertFalse("The computeSelect shouldn't accept this configuration", rr.computeSelect());
		sol.add(bic);
		assertFalse("The computeSelect shouldn't accept this configuration", rr.computeSelect());
		sol.add(new QuaterLeft(4));
		assertFalse("The computeSelect shouldn't accept this configuration", rr.computeSelect());
		sol.add(new BarNumber(1));
		assertTrue("The computeSelect doesn't validate some good parameters", rr.computeSelect());
	}
}
