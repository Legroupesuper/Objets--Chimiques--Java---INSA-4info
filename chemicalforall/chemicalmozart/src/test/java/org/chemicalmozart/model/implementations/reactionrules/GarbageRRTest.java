/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
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