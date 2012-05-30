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
package indexproviderproof;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReaction2 implements ReactionRule {
	@Dontreact
	private int nbCombination;
	
	private String a;
	private Integer b;
	
	public DummyReaction2() {
		nbCombination = 0;
	}
	
	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		nbCombination++;
		System.out.println("Dummy2 : Test a = "+a+", b = "+b+", combination #"+nbCombination);
		
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(String a) {
		this.a = a;
	}

	public void setB(Integer b) {
		this.b = b;
	}
}