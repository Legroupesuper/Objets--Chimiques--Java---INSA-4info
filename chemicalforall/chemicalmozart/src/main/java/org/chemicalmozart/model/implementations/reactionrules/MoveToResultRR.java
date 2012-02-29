package org.chemicalmozart.model.implementations.reactionrules;

import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to put a bar into the result solution.
 * It takes the following reactives:
 * <ul>
 * 	<li>SubSolution
 * 		<ul>
 * 			<li>BarInCreation : To identify the bar in creation</li>
 * 			<li>QuaterLeft : To test in the computeSelect if the bar is full.</li>
 * 		</ul>
 *  </li>
 *  <li>Subsolution
 *  	<ul>
 *  		<li>Result : The object that identify the result solution.</li>
 *  	</ul>
 *  </li>
 * </ul>
 */
public class MoveToResultRR implements ReactionRule{

	private SubSolution<SubSolutionElements> _subSolInCreation;
	private SubSolution<SubSolutionElements> _subSolResult;
	
	public SubSolution<SubSolutionElements> get_subSolInCreation() {
		return _subSolInCreation;
	}
	public void set_subSolInCreation(SubSolution<SubSolutionElements> _subSolInCreation) {
		this._subSolInCreation = _subSolInCreation;
	}
	public SubSolution<SubSolutionElements> get_subSolResult() {
		return _subSolResult;
	}
	public void set_subSolResult(SubSolution<SubSolutionElements> _subSolResult) {
		this._subSolResult = _subSolResult;
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