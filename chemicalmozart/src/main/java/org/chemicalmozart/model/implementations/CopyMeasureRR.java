package org.chemicalmozart.model.implementations;


import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * Description of the class CopyMeasureRR.
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