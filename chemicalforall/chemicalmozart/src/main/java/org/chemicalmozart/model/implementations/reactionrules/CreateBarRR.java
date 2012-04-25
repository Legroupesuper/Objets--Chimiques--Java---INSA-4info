package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This ReactionRule is used to create a new bar into the main Solution.
 * This is a one-shot rule, which means once it has reacted, the CreateBarRR
 * is no longer in the Solution.<br />
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
	 * _number represents the position of the bar to create.
	 */
	private BarNumber _number;
	/**
	 * _quater represents the number of quater per bar of the current music.
	 */
	private QuaterPerBar _quater;

	/**
	 * Create a new solution which represents a new bar. This solution must contains a BarInCreation object to be easily identifiable.<br />
	 * The computeResult must create a new Solution which contains a copy of _number and a copy of
	 * _quater. Then it must increase _number by one. It must also contains an integer initialized to 0 which represents the current number of
	 * ChordImpl in the bar<br />
	 * It must also return a MoveToResultRR and a QuaterLeft
	 * @return a table which contains the new solution described before, the initial _quater, and a new BarNumber with the value
	 * of _number increased by one.<br />
	 * It also returns a MoveToResultRR and a QuaterLeft
	 */
	public Object[] computeResult() {

		Solution barCreationSolution = new Solution();

		BarInCreation solutionID = new BarInCreation();
		barCreationSolution.add(solutionID);

		QuaterLeft quaterLeft = new QuaterLeft(this._quater.getValue());
		barCreationSolution.add(quaterLeft);

		BarNumber numberCopy = new BarNumber(this._number);
		QuaterPerBar quarterCopy = new QuaterPerBar(this._quater);
		barCreationSolution.add(numberCopy);
		barCreationSolution.add(quarterCopy);

		this._number.increment();

		Integer currentNumberOfChordImpl = 0;
		barCreationSolution.add(currentNumberOfChordImpl);

		MoveToResultRR RR = new MoveToResultRR();

		Object[] res = {barCreationSolution, RR, this._quater, this._number};

		return res;
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