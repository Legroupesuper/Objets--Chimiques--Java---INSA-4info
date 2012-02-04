package org.chemicalmozart.model.implementations;

import org.chemicalmozart.model.interfaces.Degree;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * Description of the class HarmonicRR.
 *
 * 
 *
 */
public class HarmonicRR implements ReactionRule{

	public Degree _degree1;
	public Degree _degree2;
	private SubSolution _sol;
	
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