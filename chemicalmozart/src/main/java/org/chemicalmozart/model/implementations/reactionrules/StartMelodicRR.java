package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.MeasureNumber;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * Description of the class StartMelodicRR.
 *
 *
 */
public class StartMelodicRR implements ReactionRule{

	private MeasureNumber _measureNumber;
	private SubSolution _sol;
	/**
	 * @return the measureNumber
	 */
	public MeasureNumber getMeasureNumber() {
		return _measureNumber;
	}
	/**
	 * @param measureNumber the measureNumber to set
	 */
	public void setMeasureNumber(MeasureNumber measureNumber) {
		this._measureNumber = measureNumber;
	}
	/**
	 * @return the sol
	 */
	public SubSolution getSol() {
		return _sol;
	}
	/**
	 * @param sol the sol to set
	 */
	public void setSol(SubSolution sol) {
		this._sol = sol;
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