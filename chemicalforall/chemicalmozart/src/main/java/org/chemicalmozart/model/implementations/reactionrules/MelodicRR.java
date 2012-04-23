package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.MelodicNumber;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.PrevNote;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 *
 *
 *
 */
public class MelodicRR implements ReactionRule{
	@Dontreact
	private int _max;
	private PrevNote _prevNote;
	private Note _note;
	private Pitch _pitch;
	private MelodicNumber _melodicNumber;
	private boolean RythmicRRAllowedtoReact;

	public Object[] computeResult() {
		return null;
	}
	public boolean computeSelect() {
		return false;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}
	public int get_max() {
		return _max;
	}
	public void set_max(int _max) {
		this._max = _max;
	}
	public boolean isRythmicRRAllowedtoReact() {
		return RythmicRRAllowedtoReact;
	}
	public void setRythmicRRAllowedtoReact(boolean rythmicRRAllowedtoReact) {
		RythmicRRAllowedtoReact = rythmicRRAllowedtoReact;
	}
}