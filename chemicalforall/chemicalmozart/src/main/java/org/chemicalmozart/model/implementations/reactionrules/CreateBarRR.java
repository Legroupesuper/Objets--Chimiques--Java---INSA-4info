/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This ReactionRule is used to create a new bar into the main Solution.
 * This is a one-shot rule, which means once it has reacted, the CreateBarRR
 * is no longer in the Solution.<br />
 * <br />
 * Here is how it works :<br />
 * It takes two reagents :<br />
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
	 * Represents the number of bars that the reaction must create
	 */
	private Integer _numberOfBars;
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
		solutionID.set_state(BarInCreationState.HARMONICRR);
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

		Object[] res = {barCreationSolution, this._quater, this._number, _numberOfBars-1, new MoveToResultRR()};

		return res;
	}

	/**
	 * Is not used
	 */
	@Dontuse
	public boolean computeSelect() {
		return _numberOfBars>0;
		//return true;
	}

	/**
	 * @return the _numberOfBars
	 */
	public Integer get_numberOfBars() {
		return _numberOfBars;
	}

	/**
	 * @param _numberOfBars the _numberOfBars to set
	 */
	public void set_numberOfBars(Integer _numberOfBars) {
		this._numberOfBars = _numberOfBars;
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