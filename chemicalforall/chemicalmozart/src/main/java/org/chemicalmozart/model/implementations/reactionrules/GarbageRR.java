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
	 *  @return It returns an empty array
	 */
	public Object[] computeResult() {
		return new Object[1];
	}

	/**
	 * It succeed only if the solution contains a Temporary object
	 */
	public boolean computeSelect() {
		boolean found = false;
		for(Object o : this._temporarySolution){
			if(o instanceof Temporary){
				found = true;
			}
		}
		//return _temporarySolution.containsType(Temporary.class);
		return found;
	}

	/**
	 * @return the _temporarySolution
	 */
	public Solution get_temporarySolution() {
		return this._temporarySolution;
	}
	public Multiplicity getMultiplicity() {
		return ReactionRule.Multiplicity.INFINITY_SHOT;
	}

	/**
	 * @param _temporarySolution the _temporarySolution to set
	 */
	public void set_temporarySolution(Solution _temporarySolution) {
		this._temporarySolution = _temporarySolution;
	}
}