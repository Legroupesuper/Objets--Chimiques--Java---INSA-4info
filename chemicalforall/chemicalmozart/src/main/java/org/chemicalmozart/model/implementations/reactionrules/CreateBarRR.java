package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterPerBar;

import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This ReactionRule is used to create a new measure into the main Solution.
 * This is a one-shot rule. This means that once it has react, the CreateMeasureRR
 * is no more in the Solution.<br />
 * <br />
 * Here is how it works :<br />
 * It takes two reactives :<br />
 * <ul>
 * 	<li>BarNumber _number</li>
 *  <li>QuaterPerBar _quater</li>
 *  </ul>
 * The computeSelect is not used.<br />
 */
public class CreateBarRR implements ReactionRule{
	/**
	 * _number represents the position of the measure to create.
	 */
	private BarNumber _number;
	/**
	 * _quater represents the number of quater per measure of the current music.
	 */
	private QuaterPerBar _quater;

	/**
	 * Create a new solution which represents a new bar. This solution must contains a BarInCreation object to be easily identifiable.<br />
	 * The computeResult must create a new Solution which contains a copy of _number and a copy of
	 * _quater. Then it must increase _number by one. It must also contains an integer initialised to 0 which represents the current number of
	 * ChordImpl in the bar<br />
	 * It must also return a MoveToReasultRR
	 * @return a table which contains the new solution described before, the initial _quater, and a new BarNumber with the value
	 * of _number increased by one.<br />
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
	 * @return the _number
	 */
	public BarNumber get_number() {
		return this._number;
	}

	/**
	 * @return the _quater
	 */
	public QuaterPerBar get_quater() {
		return this._quater;
	}

	/**
	 * This is a one-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	/**
	 * @param _number the _number to set
	 */
	public void set_number(BarNumber _number) {
		this._number = _number;
	}

	/**
	 * @param _quater the _quater to set
	 */
	public void set_quater(QuaterPerBar _quater) {
		this._quater = _quater;
	}
}