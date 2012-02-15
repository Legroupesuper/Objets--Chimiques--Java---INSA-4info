package org.chemicalmozart.model.implementations.reactionrules;


import org.chemicalmozart.model.implementations.Number;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * For the moment, this class is not used
 *
 *
 */
public class CopyMeasureRR implements ReactionRule{

	private SubSolution _sol;
	public Number _measureNum;
	
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