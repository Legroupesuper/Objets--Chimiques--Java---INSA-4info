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

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DummyReactionSubSub implements ReactionRule {
	private SubSolution<SubSolution<SubSolutionElements>> c;
	private SubSolution<SubSolution<SubSolution<SubSolutionElements>>> d;
	
	@Dontreact
	private int nbCombination;

	public DummyReactionSubSub() {

		this.c = new SubSolution<SubSolution<SubSolutionElements>>();
		this.c.addType(String.class);

		this.d = new SubSolution<SubSolution<SubSolution<SubSolutionElements>>>();
		this.d.addType(String.class);
		this.d.addType(Integer.class);

		this.nbCombination = 0;
	}

	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		this.nbCombination++;
		System.out.println("DummySub : Test c = "+this.c.getElements().get(0)+", d0 = "+this.d.getElements().get(0)+", d1 = "+this.d.getElements().get(1)+", combination #"+this.nbCombination);

		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return false;
	}



	public SubSolution<SubSolution<SubSolutionElements>> getC() {
		return this.c;
	}

	public SubSolution<SubSolution<SubSolution<SubSolutionElements>>> getD() {
		return this.d;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setC(SubSolution<SubSolution<SubSolutionElements>> c) {
		this.c = c;
	}

	public void setD(SubSolution<SubSolution<SubSolution<SubSolutionElements>>> d) {
		this.d = d;
	}
}