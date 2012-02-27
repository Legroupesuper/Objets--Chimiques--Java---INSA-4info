package org.chemicalmozart.model.implementations.reactionrules;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This reaction rule is used to remove a Temporary Bar.
 * It's easy to match this kind of Bar because it contains a Temporary object.
 */
public class GarbageRR implements ReactionRule{

	public Solution _sol;
	
	public Solution get_sol() {
		return _sol;
	}
	public void set_sol(Solution _sol) {
		this._sol = _sol;
	}
	
	/**
	 *  Return nothing
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
	public Multiplicity getMultiplicity() {
		return null;
	}
}