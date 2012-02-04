package org.chemicalmozart.model.implementations;

import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * Description of the class MelodicRR.
 *
 *
 */
public class MelodicRR implements ReactionRule{

	private Max _max;
	private PrevNote _prevNote;
	private Note _note;
	private Pitch _pitch;
	private MelodicNumber _melodicNumber;
	
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