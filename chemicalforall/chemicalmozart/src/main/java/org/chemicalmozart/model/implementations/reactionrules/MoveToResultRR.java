package org.chemicalmozart.model.implementations.reactionrules;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to put a bar into the result solution.
 * It takes the following reactives: (they must appear in this order)
 * <ul>
 * 	<li>SubSolution "barInCreaction"
 * 		<ul>
 * 			<li>BarInCreation : To identify the bar in creation</li>
 * 			<li>QuaterLeft : To test in the computeSelect if the bar is full.</li>
 * 		</ul>
 *  </li>
 *  <li>Subsolution "result"
 *  	<ul>
 *  		<li>Result : The object that identify the result solution.</li>
 *  	</ul>
 *  </li>
 * </ul>
 * It must put the first subsolution into the second one.
 */
public class MoveToResultRR implements ReactionRule{

	/**
	 * This subsolution represents the BarInCreation solution
	 */
	private SubSolution<SubSolutionElements> _subSolInCreation;
	/**
	 * This subsolution represents the result solution
	 */
	private SubSolution<SubSolutionElements> _subSolResult;

	/**
	 * The compute result must add the BarInCreation solution into the Result solution.
	 * <br />
	 * It must put back the BarInCreation and the QuaterLeft in the BarInCreation solution and then add the BarInCreationSolution into
	 * the Result solution. It must also put back the Result object into the Result solution.
	 * @return The Result solution as describe
	 */
	public Object[] computeResult() {
		return null;
	}

	/**
	 * The computeSelect has in charge to check that the configuration of the BarInCreation is correct.
	 * This means that it must contains a Quaterleft object with a value of 0. It must also contain at least
	 * one or several ChordImpl.
	 */
	public boolean computeSelect() {
		return false;
	}

	public SubSolution<SubSolutionElements> get_subSolInCreation() {
		return this._subSolInCreation;
	}
	public SubSolution<SubSolutionElements> get_subSolResult() {
		return this._subSolResult;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}
	public void set_subSolInCreation(SubSolution<SubSolutionElements> _subSolInCreation) {
		this._subSolInCreation = _subSolInCreation;
	}
	public void set_subSolResult(SubSolution<SubSolutionElements> _subSolResult) {
		this._subSolResult = _subSolResult;
	}
}