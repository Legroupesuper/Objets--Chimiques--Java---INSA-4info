package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;

public class HarmonicRR2Test extends TestCase{
	private boolean _isSolutionPresent = false;
	private boolean _isSolutionValid = false;
	private boolean _isPickOneRRPresent = false;
	private boolean _isRythmeRRPresent = false;
	private boolean _isBarInCreationPresent = false;

	private Solution setRR(HarmonicRR2 rr){
		rr.set_degree(new DegreeImpl(1));
		//create the BarInCreation solution
		Solution sol = new Solution();
		BarInCreation bic = new BarInCreation();
		sol.add(bic);
		sol.add(new QuaterLeft(4));
		sol.add(new BarNumber(1));

		//Instantiate a SubSolution object for the RR
		SubSolutionElements e = new  SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		e.setTypeList(l);
		List<Object> ll = new ArrayList<Object>();
		ll.add(sol);
		ll.add(bic);
		e.setElements(ll);
		SubSolution<SubSolutionElements> subsol = new SubSolution<SubSolutionElements>(e);
		rr.set_barInCreationSolution(subsol);
		subsol.setSolution(sol);
		return sol;
	}

	@Test
	public void testBarInCreationPresent(){
		HarmonicRR2 rr = new HarmonicRR2();
		Solution sol = this.setRR(rr);

		rr.computeResult();
		this._isBarInCreationPresent = false;
		//Test the content of the BarInCreation Solution
		for(Object o : sol){
			if(o instanceof BarInCreation)
				this._isBarInCreationPresent = true;
		}
		assertTrue("The RR doesn't put the BarInCreation object back into the BarInCreation solution", this._isBarInCreationPresent);
	}

	@Test
	public void testPickOneRRPresent(){
		HarmonicRR2 rr = new HarmonicRR2();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		this._isPickOneRRPresent = false;
		//Test the returned elements
		for(Object o : result){
			if(o instanceof PickOneRR){
				this._isPickOneRRPresent = true;
				break;
			}
		}
		assertTrue("The RR doesn't return a PickOneRR", this._isPickOneRRPresent);
	}

	@Test
	public void testRythmeRRPresent(){
		HarmonicRR2 rr = new HarmonicRR2();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		this._isRythmeRRPresent = false;
		//Test the returned elements
		for(Object o : result){
			if(o instanceof RythmeHRR){
				this._isRythmeRRPresent = true;
				break;
			}
		}
		assertTrue("The RR doesn't return a RythmeRR", this._isRythmeRRPresent);
	}

	@Test
	public void testSolutionPresent(){
		HarmonicRR2 rr = new HarmonicRR2();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		//Test the returned elements
		this._isSolutionPresent = false;
		for(Object o : result){
			if(o instanceof Solution){
				this._isSolutionPresent = true;
				break;
			}
		}
		assertTrue("The Solution which contains all possible Degree is not present", this._isSolutionPresent);
	}

	@Test
	public void testSolutionValid(){
		HarmonicRR2 rr = new HarmonicRR2();
		this.setRR(rr);

		Object[] result = rr.computeResult();
		this._isSolutionValid = false;
		for(Object o : result){
			if(o instanceof Solution){
				Iterator<Object> it = ((Solution) o).iterator();
				while(it.hasNext()){
					Object o2 = it.next();
					if(o2 instanceof DegreeImpl){
						this._isSolutionValid = true;
						break;
					}
				}
			}
		}
		assertTrue("The Solution which contains all possible Degree is not valid", this._isSolutionValid);
	}
}
