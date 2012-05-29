/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.solutionindentification.Temporary;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This reaction rule is used to remove a Temporary Bar.<br />
 * It's easy to match this kind of Bar because it contains a Temporary object.<br />
 */
public class GarbageRR implements ReactionRule{

	public Solution _temporarySolution;

	/**
	 *  Do nothing
	 *  @return It returns null
	 */
	public Object[] computeResult() {
		return new Object[]{};
	}

	/**
	 * It succeed only if the solution contains a Temporary object
	 */
	public boolean computeSelect() {
		for(Object o : this._temporarySolution){
			if(o instanceof Temporary){
				System.out.println("Compute select de GarbageRR : "+true);
				return true;
			}
		}
		System.out.println("Compute select de GarbageRR : "+false);
		return false;
	}

	/**
	 * @return the _temporarySolution
	 */
	public Solution get_temporarySolution() {
		return this._temporarySolution;
	}
	
	public Multiplicity getMultiplicity() {
		return ReactionRule.Multiplicity.ONE_SHOT;
	}

	/**
	 * @param _temporarySolution the _temporarySolution to set
	 */
	public void set_temporarySolution(Solution _temporarySolution) {
		this._temporarySolution = _temporarySolution;
	}
}