/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.model.implementations.reactionrules;

import java.util.Iterator;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.reactionrules.CreateBarRR;
import org.chemicalmozart.model.implementations.reactionrules.MoveToResultRR;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;
import junit.framework.TestCase;

public class CreateBarRRTest extends TestCase {
	private boolean _rrMissing = true;
	private boolean _solPresent = false;
	private boolean _moveToResultPresent = false;
	private boolean _isBarNumberPresent = false;
	private boolean _isBarNumberValid = false;
	private boolean _isBarNumber2Valid = false;
	private boolean _isBarNumber2Present = false;
	private boolean _isQuaterLeft2Present = false;
	private boolean _isIntegerPresent = false;
	private Solution _sol;
	private QuaterPerBar _quaterPerBar;
	private String _debugValue= "";
	
	protected void setUp() throws Exception {
		super.setUp();
		_sol = new Solution();
		BarNumber measureNumber = new BarNumber(1);
		 _quaterPerBar = new QuaterPerBar(4);
		_sol.add(measureNumber);
		_sol.add(_quaterPerBar);
		_sol.add(new CreateBarRR());
		
		_sol.react();
		
		while(!_sol.is_inert()) Thread.sleep(100);
		
		Iterator<Object> it = _sol.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if(o instanceof Solution){//Test if the bar in creation is in the contained solution
				Iterator<Object> it2 = ((Solution) o).iterator();
				while(it2.hasNext()){
					Object o2 = it2.next();
					if(o2 instanceof BarInCreation){
						_solPresent = true;
						break;
					}
				}
				if(_solPresent){
					for(Object o2 : ((Solution)o)){
						if(o2 instanceof QuaterLeft){
							_isQuaterLeft2Present = true;
						}else if(o2 instanceof BarNumber){
							_isBarNumber2Present = true;
							_isBarNumber2Valid = ((BarNumber) o2).getValue()==1;
						}else if(o2 instanceof Integer){
							_isIntegerPresent = true;
						}
					}
				}
			}else if(o instanceof CreateBarRR){//There must not be any CreateMeasureRR
				_rrMissing = false;
			}else if(o instanceof MoveToResultRR){
				_moveToResultPresent = true;
			}else if(o instanceof BarNumber){
				_isBarNumberPresent = true;
				if(((BarNumber) o).getValue() == 2)
					_isBarNumberValid = true;
				else
					_debugValue = ""+((BarNumber) o).getValue();
			}
		}
	}
	
	@Test
	public void testIntegerPresent() {
		assertTrue("The integer which represent the number of ChordImplement is not in the returned solution", _isIntegerPresent);
	}

	@Test
	public void testRRMissing(){
		assertTrue("Tests if CreateBarRR has been deleted", _rrMissing);
	}
	
	@Test
	public void testNewBarPresent(){
		assertTrue("Tests if the new bar has been found", _solPresent);
	}
	
	@Test
	public void testMoveToResultPresent(){
		assertTrue("Tests if the MoveToResultRR is present", _moveToResultPresent);
	}
	
	@Test
	public void testQuaterPerBar(){
		assertTrue("Tests if the quaterPerBar is still present in the main solution", _sol.contains(_quaterPerBar));
	}
	
	@Test
	public void testBarNumberPresent(){
		assertTrue("Tests if the BarNumber is present in the main solution", _isBarNumberPresent);
	}
	
	@Test
	public void testBarNumerValid(){
		assertTrue("Tests if the BarNumber is valid in the main solution : "+_debugValue, _isBarNumberValid);
	}
	
	@Test
	public void testQuaterLeftPresent(){
		assertTrue("QuaterLeft2 is not present in the returned solution", _isQuaterLeft2Present);
	}
	
	@Test
	public void testBarNumber2Present(){
		assertTrue("BarNumber2 is not present in the returned solution", _isBarNumber2Present);
	}
	
	@Test
	public void testBarNumber2Valid(){
		assertTrue("Tests if the BarNumber2 is valid in the main solution", _isBarNumber2Valid);
	}
}