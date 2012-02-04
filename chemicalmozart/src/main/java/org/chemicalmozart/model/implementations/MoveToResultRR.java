package org.chemicalmozart.model.implementations;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * Description of the class MoveToResultRR.
 *
 *
 */
public class MoveToResultRR implements ReactionRule{

	private SubSolution _sol1;
	private SubSolution _sol2;
	
	public Object[] computeResult() {
		return null;
	}
	public boolean computeSelect() {
		return false;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}
}