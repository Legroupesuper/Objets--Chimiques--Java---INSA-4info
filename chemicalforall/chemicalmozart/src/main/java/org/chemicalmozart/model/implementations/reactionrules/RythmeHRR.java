package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;

import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to generate the harmonic part of a measure.
 * Starting from a DegreeImpl, it generates a ChordImpl. 
 * A ChordImpl is quite similar to a DegreeImpl but it also contains the notion of duration.
 * Here is how it works, it takes the following reactives.
 * <ul>
 * 	<li>Subsolution : 
 * 		<ul>
 * 			<li>int : Which is the current number of ChordImpl in the subsolution</li>
 * 			<li>DegreeImpl : The current DegreeImpl</li>
 * 			<li>QuaterLeft : The number of quaters still free for the new ChordImpl</li>
 * 		</ul>
 *  </li>
 * </ul>
 * It puts back in the subsolution :
 * <ul>
 * 			<li>a int</li>
 * 			<li>CordImpl</li>
 * 			<li>QuaterLeft</li>
 * 		</ul>
 */
public class RythmeHRR implements ReactionRule{
	
	private SubSolution<SubSolutionElements> _sol;
	
	/**
	 * The constructor is used to fill the types we want to match in the subsolution
	 */
	public RythmeHRR() {
		super();
		_sol = new SubSolution<SubSolutionElements>(new SubSolutionElements());
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(Integer.class);
		l.add(DegreeImpl.class);
		l.add(QuaterLeft.class);
		_sol.setTypeList(l);
	}
	
	/**
	 * @return the sol
	 */
	public SubSolution<SubSolutionElements> getSol() {
		return _sol;
	}
	/**
	 * @param sol the sol to set
	 */
	public void setSol(SubSolution<SubSolutionElements> sol) {
		this._sol = sol;
	}
	
	/**
	 * The compute result must choose a duration for the ChordImpl that we are going to generate.
	 * It must be a random choice between 2 or 4 quaters. It must take in consideration the elapsed time in QuaterLeft.
	 * Once the duration is chosen, it creates a ChordImpl based on the current DegreeImpl.
	 * @return The new ChordImpl, the int increased by one, the QuaterLeft decreased by the duration.
	 */
	public Object[] computeResult() {
		return null;
	}
	
	/**
	 * Not used here
	 */
	public boolean computeSelect() {
		return true;
	}
	public Multiplicity getMultiplicity() {
		return null;
	}



}
