package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.DegreeImpl;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * Description of the class RythmeHRR.
 *
 *
 */
public class RythmeHRR implements ReactionRule{

	private int _value;
	private DegreeImpl _degree;
	private SubSolution _sol;
	
	/**
	 * @return the value
	 */
	public int getValue() {
		return _value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this._value = value;
	}
	/**
	 * @return the degree
	 */
	public DegreeImpl getDegree() {
		return _degree;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(DegreeImpl degree) {
		this._degree = degree;
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
		return null;
	}
	public boolean computeSelect() {
		return false;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}



}