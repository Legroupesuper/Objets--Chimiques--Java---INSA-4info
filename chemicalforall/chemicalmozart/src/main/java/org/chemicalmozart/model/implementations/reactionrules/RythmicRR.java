package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.ChordNumber;
import org.chemicalmozart.model.implementations.Max;

import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * Description of the class RythmicRR.
 *
 *
 */
public class RythmicRR implements ReactionRule{

	private ChordImpl _chordImpl;
	private ChordNumber _chordNumber;
	private int _num;
	public Max _max;

	public Object[] computeResult() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean computeSelect() {
		// TODO Auto-generated method stub
		return false;
	}
	/**
	 * @return the chordImpl
	 */
	public ChordImpl getChordImpl() {
		return this._chordImpl;
	}
	/**
	 * @return the chordNumber
	 */
	public ChordNumber getChordNumber() {
		return this._chordNumber;
	}
	/**
	 * @return the max
	 */
	public Max getMax() {
		return this._max;
	}
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
	 * @param chordImpl the chordImpl to set
	 */
	public void setChordImpl(ChordImpl chordImpl) {
		this._chordImpl = chordImpl;
	}

	/**
	 * @param chordNumber the chordNumber to set
	 */
	public void setChordNumber(ChordNumber chordNumber) {
		this._chordNumber = chordNumber;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Max max) {
		this._max = max;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this._num = num;
	}



}