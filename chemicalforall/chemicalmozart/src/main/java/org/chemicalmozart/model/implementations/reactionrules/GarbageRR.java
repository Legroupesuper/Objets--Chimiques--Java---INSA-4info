package org.chemicalmozart.model.implementations.reactionrules;

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
		return null;
	}

	/**
	 * It succeed only if the solution contains a Temporary object
	 */
	public boolean computeSelect() {
		return false;
	}

	/**
	 * @return the _temporarySolution
	 */
	public Solution get_temporarySolution() {
		return this._temporarySolution;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}

	/**
	 * @param _temporarySolution the _temporarySolution to set
	 */
	public void set_temporarySolution(Solution _temporarySolution) {
		this._temporarySolution = _temporarySolution;
	}
}