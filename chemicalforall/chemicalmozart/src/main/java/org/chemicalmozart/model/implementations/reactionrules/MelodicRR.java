package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Note.Type;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.interfaces.Degree;

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * This ReactionRule is used to create the melodic part of a bar. It takes the following parameters :
 * <br />
 * <ul>
 * 		<li>Note : Which is the Note we need to set</li>
 * 		<li>Pitch : Which is the pitch of the last note set.</li>
 * </ul>
 */
public class MelodicRR implements ReactionRule{
	/**
	 * The number of music notes in the current solution. This attribute must not react. It is set by the RythmicRR.
	 */
	@Dontreact private int _max;
	/**
	 * The current note to set. This note must have the position equal to _melodicNumber.
	 */
	private Note _note;
	/**
	 * The pitch is used to remember the last pitch we used in the melody. This allows the program to create some interesting melodic
	 * patterns.
	 */
	private Pitch _pitch;
	/**
	 * Represent the position of the current note that must be set. Every time the MelodiccRR reacts, this attribute must be increased.
	 */
	@Dontreact private int _melodicNumber;
	/**
	 * The current state of the reaction rule. The reaction rule must not succeed the compute select if this parameter is set to false
	 */
	@Dontreact	private boolean _activated;

	/**
	 * The compute result must assign the pitch of the Note. Let's say that the degree of ChordImpl is named deg. 
	 * There is for the moment 2 different cases :<br />
	 * - _note.get_type()==STRONG : The pitch must be chosen between the following degrees :
	 * 		<ul>
	 *	 		<li>deg</li>
	 *	 		<li>(deg+2)%8 + ((int)(deg+2)/(int)8)</li>
	 *			<li>(deg+4)%8 + ((int)(deg+4)/(int)8)</li>
	 *	 	</ul>
	 *	 	Then, the octave must be chosen to give the closest possible note with the new degree from _pitch.
	 * - In all the other cases :
	 * 		-> Choose a degree randomly
	 * 		-> Choose an octave close to the old one
	 */
	public Object[] computeResult() {
		if(_note.get_type()==Type.STRONG){
			DegreeImpl[] possibleDegrees;
			int choicesNumber = 3;
			
			DegreeImpl deg = _note.get_chord().get_degrees();
			possibleDegrees = new DegreeImpl[3];
			possibleDegrees[0]=deg;
			
			int deg2value = (deg.get_value()+2)%8 + ((int)(deg.get_value()+2)/(int)8);
			possibleDegrees[1]= new DegreeImpl(deg2value);
			
			int deg3value = (deg.get_value()+4)%8 + ((int)(deg.get_value()+4)/(int)8);
			possibleDegrees[2]= new DegreeImpl(deg3value);

			int degChosenIndex =  (int)(Math.random()*3);
			DegreeImpl chosenDegree = (DegreeImpl) possibleDegrees[degChosenIndex];
			ChordImpl chord = new ChordImpl();
			chord.set_degrees(chosenDegree);
			_note.set_chord(chord);
			Pitch pitch = new Pitch();
			pitch.setDegree(chosenDegree);
			_note.set_pitch(pitch);
		}
		else{
			int degChosenIndex =  (int)(Math.random()*6);
			// J'pige que dalle au delbor ^^
		}
		
		return null;
	}
	/**
	 * Succeeds if the position of _note is equal to the melodic number and the MelodicRR is activated
	 */
	public boolean computeSelect() {
		return false;
	}

	/**
	 * @return the _max
	 */
	public int get_max() {
		return this._max;
	}
	/**
	 * @return the _melodicNumber
	 */
	public int get_melodicNumber() {
		return this._melodicNumber;
	}
	/**
	 * @return the _note
	 */
	public Note get_note() {
		return this._note;
	}
	/**
	 * @return the _pitch
	 */
	public Pitch get_pitch() {
		return this._pitch;
	}
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
	/**
	 * @return the state of the MelodicRR
	 */
	public boolean is_activated() {
		return this._activated;
	}

	/**
	 * @param state of the MelodicRR to set
	 */
	public void set_activated(boolean _activated) {
		this._activated = _activated;
	}
	/**
	 * @param _max the _max to set
	 */
	public void set_max(int _max) {
		this._max = _max;
	}
	/**
	 * @param _melodicNumber the _melodicNumber to set
	 */
	public void set_melodicNumber(int _melodicNumber) {
		this._melodicNumber = _melodicNumber;
	}
	/**
	 * @param _note the _note to set
	 */
	public void set_note(Note _note) {
		this._note = _note;
	}
	/**
	 * @param _pitch the _pitch to set
	 */
	public void set_pitch(Pitch _pitch) {
		this._pitch = _pitch;
	}
}
