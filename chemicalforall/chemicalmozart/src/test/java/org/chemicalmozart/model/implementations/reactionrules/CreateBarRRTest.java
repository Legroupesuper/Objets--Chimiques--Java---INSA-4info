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

import junit.framework.TestCase;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.junit.Test;

import fr.insa.rennes.info.chemical.backend.Solution;

public class CreateBarRRTest extends TestCase {
	private String _debugValue= "";
	private boolean _isBarNumber2Present = false;
	private boolean _isBarNumber2Valid = false;
	private boolean _isBarNumberPresent = false;
	private boolean _isBarNumberValid = false;
	private boolean _isIntegerPresent = false;
	private boolean _isQuaterLeft2Present = false;
	private boolean _moveToResultPresent = false;
	private QuaterPerBar _quaterPerBar;
	private boolean _rrMissing = true;
	private Solution _sol;
	private boolean _solPresent = false;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this._sol = new Solution();
		BarNumber measureNumber = new BarNumber(1);
		this._quaterPerBar = new QuaterPerBar(4);
		this._sol.add(measureNumber);
		this._sol.add(this._quaterPerBar);
		this._sol.add(new CreateBarRR());

		this._sol.react();

		while(!this._sol.is_inert()) Thread.sleep(100);

		Iterator<Object> it = this._sol.iterator();
		while(it.hasNext()){
			Object o = it.next();
			if(o instanceof Solution){//Test if the bar in creation is in the contained solution
				Iterator<Object> it2 = ((Solution) o).iterator();
				while(it2.hasNext()){
					Object o2 = it2.next();
					if(o2 instanceof BarInCreation){
						this._solPresent = true;
						break;
					}
				}
				if(this._solPresent){
					for(Object o2 : ((Solution)o)){
						if(o2 instanceof QuaterLeft){
							this._isQuaterLeft2Present = true;
						}else if(o2 instanceof BarNumber){
							this._isBarNumber2Present = true;
							this._isBarNumber2Valid = ((BarNumber) o2).getValue()==1;
						}else if(o2 instanceof Integer){
							this._isIntegerPresent = true;
						}
					}
				}
			}else if(o instanceof CreateBarRR){//There must not be any CreateMeasureRR
				this._rrMissing = false;
			}else if(o instanceof MoveToResultRR){
				this._moveToResultPresent = true;
			}else if(o instanceof BarNumber){
				this._isBarNumberPresent = true;
				if(((BarNumber) o).getValue() == 2)
					this._isBarNumberValid = true;
				else
					this._debugValue = ""+((BarNumber) o).getValue();
			}
		}
	}


	@Test
	public void testBarNumberPresent(){
		assertTrue("Tests if the BarNumber is present in the main solution", this._isBarNumberPresent);
	}


	@Test
	public void testQuaterPerBar(){
		assertTrue("Tests if the quaterPerBar is still present in the main solution", this._sol.contains(this._quaterPerBar));
	}
}
