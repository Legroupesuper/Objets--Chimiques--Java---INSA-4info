package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.Max;
import org.chemicalmozart.model.implementations.MelodicNumber;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.PrevNote;

import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 *
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