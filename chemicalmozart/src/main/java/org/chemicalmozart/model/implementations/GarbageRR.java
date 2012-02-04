package org.chemicalmozart.model.implementations;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * Description of the class GarbageRR.
 *
 *
 */
public class GarbageRR implements ReactionRule{

	public SubSolution _subSolutionMeasure;
	public SubSolution _subSolutionResult;
	
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