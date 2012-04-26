package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.Number;

import fr.insa.rennes.info.chemical.backend.SubSolution;



/**
 * Description of the class StructuralRR.
 *
 * 
 *
 */
public class StructuralRR {

	private SubSolution _sol;
	private Number _num;
	
	
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
	/**
	 * @return the num
	 */
	public Number getNum() {
		return _num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Number num) {
		this._num = num;
	}



}