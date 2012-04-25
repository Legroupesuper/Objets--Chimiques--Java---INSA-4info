package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.ChordImpl;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * This ReactionRule is used to select a rhythmic pattern in a SubSolution identified by a RhythmePull object. It's a one-shot rule.<br />
 * It takes the following parameters : (in this order)
 * <ul>
 * 	<li>SubSolution "RythmePull"
 * 		<ul>
 * 			<li>RythmePull : Object that identify the solution</li>
 * 			<li>org.chemicalmozart.model.interfaces.Rythme : A rhythm</li>
 * 		</ul>
 * </li>
 * <li>ChordImpl : the current chordImpl</li>
 * <li>Integer : the total number of ChordImpl</li>
 * <li>MelodicRR : a MelodicRR</li>
 * </ul>
 */
public class RythmicRR implements ReactionRule{
	/**
	 * The solution which contains the rhythmic patterns and identified by a RythmePull object
	 */
	private SubSolution<SubSolutionElements> _rythmeSolution;
	/**
	 * The current selected ChordImpl
	 */
	private ChordImpl _chordImpl;
	/**
	 * The current ChordNumber.<br />
	 * This parameter doesn't react and the same instance will be used for a same RythmicRR. The constructor must initialize it to 0.
	 */
	@Dontreact
	private int _chordNumber;
	/**
	 * The total number of ChordImpl in the current bar
	 */
	private int _num;
	/**
	 * This number represents the current number of note that already exists in the Bar.<br />
	 * It will be used to set the position of every new note in the bar.<br />
	 * This parameter doesn't react. The constructor must initialize it to 0.
	 */
	@Dontreact
	private int _max;

	/**
	 * It starts to become META ! :)<br />
	 * _melodicRR contains a parameter that is set to false (not activated). This ReactionRule (melodicRR) can't react as long it has not been set to activated.
	 * <br />
	 * Each time the rythmicRR react, it increases the max parameter of the MelodicRR.
	 */
	private MelodicRR _melodicRR;

	/**
	 * This method will be processed in 2 steps whereas the position of the ChordImpl is smaller than _num or not.
	 * <br /><br />
	 * If the position of the ChordImpl is smaller than _num-1, it must return :<br />
	 * <ul>
	 * 	<li>_num</li>
	 * 	<li>_chordImpl</li>
	 * 	<li>_rythmeSolution with the chosen element back into it</li>
	 * 	<li>_melodicRR with his max parameter increased by the number of notes returned by the chosen element in _rythmeSolution</li>
	 * 	<li>this</li>
	 * </ul>
	 * For each note returned by the chosen element in _rythmeSolution, it needs to increase its position by the value of _max and set its chordimpl to the current chordimpl. Once it's done,
	 * it must also set properly _chordNumber (increment by 1) and _max (increment by the number of notes returned by the chosen element in _rythmeSolution).
	 * <br />
	 * <br />
	 * If the position of the ChordImpl is equal to _num-1, it must return :<br />
	 * <ul>
	 * 	<li>_num</li>
	 * 	<li>_chordImpl</li>
	 * 	<li>_melodicRR with his max parameter increased by the number of notes returned by the chosen element in _rythmeSolution and <b>activated</b> set to true</li>
	 * </ul>
	 * For each note returned by the chosen element in _rythmeSolution, it needs to increase the position by the value of _max and set its chordimpl to the current chordimpl. Once it's done,
	 * it must also set properly _chordNumber (increment by 1) and _max (increment by the number of notes returned by the chosen element in _rythmeSolution).
	 */
	public Object[] computeResult() {
		Object[] result = null;

		/*
		 * If the position of the ChordImpl is smaller than _num
		 */


		/*
		 * If the position of the ChordImpl is equal to _num
		 */

		return result;
	}

	/**
	 * Must check that _chordNumber correspond to the position of the _chordImpl. It must also check that the selected rythm of the subsolution
	 * has the same duration than _chordImpl.
	 */
	public boolean computeSelect() {
		return false;
	}

	/**
	 * @return the _melodicRR
	 */
	public MelodicRR get_melodicRR() {
		return this._melodicRR;
	}

	/**
	 * @return the _rythmeSolution
	 */
	public SubSolution<SubSolutionElements> get_rythmeSolution() {
		return this._rythmeSolution;
	}

	/**
	 * @return the chordImpl
	 */
	public ChordImpl getChordImpl() {
		return this._chordImpl;
	}

	/**
	 * One-shot
	 */
	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return this._num;
	}

	/**
	 * @param _chordNumber the _chordNumber to set
	 */
	public void set_chordNumber(int _chordNumber) {
		this._chordNumber = _chordNumber;
	}

	/**
	 * @param _max the _max to set
	 */
	public void set_max(int _max) {
		this._max = _max;
	}
	/**
	 * @param _melodicRR the _melodicRR to set
	 */
	public void set_melodicRR(MelodicRR _melodicRR) {
		this._melodicRR = _melodicRR;
	}
	/**
	 * @param _rythmeSolution the _rythmeSolution to set
	 */
	public void set_rythmeSolution(SubSolution<SubSolutionElements> _rythmeSolution) {
		this._rythmeSolution = _rythmeSolution;
	}
	/**
	 * @param chordImpl the chordImpl to set
	 */
	public void setChordImpl(ChordImpl chordImpl) {
		this._chordImpl = chordImpl;
	}


	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this._num = num;
	}



}