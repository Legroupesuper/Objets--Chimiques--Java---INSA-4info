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
	
	/**
	 * @return the chordImpl
	 */
	public ChordImpl getChordImpl() {
		return _chordImpl;
	}
	/**
	 * @param chordImpl the chordImpl to set
	 */
	public void setChordImpl(ChordImpl chordImpl) {
		this._chordImpl = chordImpl;
	}
	/**
	 * @return the chordNumber
	 */
	public ChordNumber getChordNumber() {
		return _chordNumber;
	}
	/**
	 * @param chordNumber the chordNumber to set
	 */
	public void setChordNumber(ChordNumber chordNumber) {
		this._chordNumber = chordNumber;
	}
	/**
	 * @return the num
	 */
	public int getNum() {
		return _num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this._num = num;
	}
	/**
	 * @return the max
	 */
	public Max getMax() {
		return _max;
	}
	/**
	 * @param max the max to set
	 */
	public void setMax(Max max) {
		this._max = max;
	}
	
	public Object[] computeResult() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean computeSelect() {
		// TODO Auto-generated method stub
		return false;
	}
	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return null;
	}



}