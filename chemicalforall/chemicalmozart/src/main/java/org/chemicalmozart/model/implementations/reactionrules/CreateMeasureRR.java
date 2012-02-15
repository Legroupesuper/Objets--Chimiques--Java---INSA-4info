package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.Count;
import org.chemicalmozart.model.implementations.MeasureNumber;
import org.chemicalmozart.model.implementations.QuaterPerMeasure;

import fr.insa.rennes.info.chemical.backend.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This ReactionRule is used to create a new measure into the main Solution.
 * This is a one-shot rule. This means that once it has react, the CreateMeasureRR
 * is no more in the Solution.<br />
 * <br />
 * Here is how it works :<br />
 * It takes two reactives :<br />
 * <ul>
 * 	<li>MeasureNumber _number</li>
 *  <li>QuaterPerMeasure _quater</li>
 *  </ul>
 * The computeSelect is not used.<br />
 */
public class CreateMeasureRR implements ReactionRule{

	/**
	 * _number represents the position of the measure to create.
	 */
	private MeasureNumber _number;
	/**
	 * _quater represents the number of quater per measure of the current music.
	 */
	private QuaterPerMeasure _quater;
	
	/**
	 * @return the _number
	 */
	public MeasureNumber get_number() {
		return _number;
	}

	/**
	 * @param _number the _number to set
	 */
	public void set_number(MeasureNumber _number) {
		this._number = _number;
	}

	/**
	 * @return the _quater
	 */
	public QuaterPerMeasure get_quater() {
		return _quater;
	}

	/**
	 * @param _quater the _quater to set
	 */
	public void set_quater(QuaterPerMeasure _quater) {
		this._quater = _quater;
	}

	/**
	 * Create a new solution which represents a new measure.
	 * The computeResult must create a new Solution which contains a copy of _number and a copy of
	 * _quater. Then it must increase _number by one.<br />
	 * It must also return a MoveToReasultRR
	 * @return a table which contains the new solution, the initial _quater, and _number increased by one.
	 * It also returns a MoveToResultRR
	 */
	public Object[] computeResult() {
		/*
		 * must be completed
		 */
		return null;
	}

	/**
	 * Is not used
	 */
	@Dontuse
	public boolean computeSelect() {
		return true;
	}

	/**
	 * This is a one-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
}