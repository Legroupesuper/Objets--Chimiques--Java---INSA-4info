package org.chemicalmozart.model.implementations.reactionrules;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to put a bar into the result solution.
 * It takes the following reactives:
 * <ul>
 * 	<li>SubSolution
 * 		<ul>
 * 			<li>Temp : To identify the bar in creation</li>
 * 			<li>QuaterLeft : To test in the computeSelect if the bar is full.</li>
 * 		</ul>
 *  </li>
 *  <li>Solution : It represents the bar in creation. We need a second reference to delete the bar from the parent solution</li>
 *  <li>Subsolution
 *  	<ul>
 *  		<li>Result : The object that identify the result solution.</li>
 *  	</ul>
 *  </li>
 * </ul>
 */
public class MoveToResultRR implements ReactionRule{

	private SubSolution _sol1;
	private SubSolution _sol2;
	
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