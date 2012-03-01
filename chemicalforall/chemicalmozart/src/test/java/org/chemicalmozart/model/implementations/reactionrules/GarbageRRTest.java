package org.chemicalmozart.model.implementations.reactionrules;


import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.solutionindentification.Temporary;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;

public class GarbageRRTest extends TestCase {
	private GarbageRR _rr1;
	private GarbageRR _rr2;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this._rr1 = new GarbageRR();
		Solution sol1 = new Solution();
		sol1.add(new Temporary());
		this._rr1.set_temporarySolution(sol1);

		this._rr2 = new GarbageRR();
		Solution sol2 =new Solution();
		this._rr2.set_temporarySolution(sol2);
	}

	@Test
	public void testComputeResult1(){
		Object[] res = this._rr1.computeResult();
		assertTrue("computeResult should return null !", res != null);
	}

	@Test
	public void testComputeResult2(){
		Object[] res = this._rr1.computeResult();
		if(res == null)
			fail("computeResult shouldn't return null");
		else
			assertTrue("computeResult should an empty array !", res.length != 0);
	}

	@Test
	public void testComputeSelect() {
		assertTrue("computeSelect should work !", this._rr1.computeSelect());
	}

	@Test
	public void testComputeSelectFail(){
		assertFalse("computeSelect shouldn't work !", this._rr2.computeSelect());
	}
}
