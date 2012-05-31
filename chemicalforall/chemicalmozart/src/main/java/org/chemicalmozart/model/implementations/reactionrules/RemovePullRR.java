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

import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class RemovePullRR implements ReactionRule{
	/**
	 * The solution which contains the rhythmic patterns and identified by a RythmePull object
	 */
	private Solution _rythmeSolution;
	
	public Solution get_rythmeSolution() {
		return _rythmeSolution;
	}

	public void set_rythmeSolution(Solution _rythmeSolution) {
		this._rythmeSolution = _rythmeSolution;
	}

	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		for(Object o : _rythmeSolution)
			if(o instanceof RythmePull)
				return true;
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
}