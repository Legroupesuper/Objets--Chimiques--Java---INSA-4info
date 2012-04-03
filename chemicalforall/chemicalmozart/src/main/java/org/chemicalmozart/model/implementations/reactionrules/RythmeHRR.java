package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.ChordImpl;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to generate the harmonic part of a measure.<br />
 * Starting from a DegreeImpl, it generates a ChordImpl. <br />
 * A ChordImpl is quite similar to a DegreeImpl but it also contains the notion of duration.<br />
 * Here is how it works, it takes the following reactives.<br />
 * <ul>
 * 	<li>Subsolution : (the order must be respected for the tests)
 * 		<ul>
 * 			<li>BarInCreation : An object that identify the current bar in creation</li>
 * 			<li>int : Which is the current number of ChordImpl in the subsolution</li>
 * 			<li>DegreeImpl : The current DegreeImpl</li>
 * 			<li>QuaterLeft : The number of quaters still free for the new ChordImpl</li>
 * 		</ul>
 *  </li>
 * </ul><br /><br />
 * It puts back in the subsolution :<br />
 * <ul>
 * 			<li>a int</li>
 * 			<li>ChordImpl</li>
 * 			<li>QuaterLeft</li>
 * 			<li>BarInCreation</li>
 * 		</ul>
 */
public class RythmeHRR implements ReactionRule{

	private SubSolution<SubSolutionElements> _sol;

	/**
	 * The constructor is used to fill the types we want to match in the subsolution
	 */
	public RythmeHRR() {
		super();
		/*
		 * Must be completed
		 */
	}

	/**
	 * The compute result must choose a duration for the ChordImpl that we are going to generate.
	 * It must be a random choice between 2 or 4 quaters. It must take in consideration the elapsed time in QuaterLeft.
	 * Once the duration is chosen, it creates a ChordImpl based on the current DegreeImpl.
	 * @return The new ChordImpl well initialized @see {@link ChordImpl}, the int increased by one, the QuaterLeft decreased by the duration.
	 */
	public Object[] computeResult() {
		/*
		 * Must be completed
		 */
		return null;
	}

	/**
	 * Must check that the BarInCreation object is in the good state and that all objects are present.
	 */
	public boolean computeSelect() {
		/*
		 * Must be completed
		 */
		return false;
	}

	public Multiplicity getMultiplicity() {
		return null;
	}

	/**
	 * @return the sol
	 */
	public SubSolution<SubSolutionElements> getSol() {
		return this._sol;
	}
	/**
	 * @param sol the sol to set
	 */
	public void setSol(SubSolution<SubSolutionElements> sol) {
		this._sol = sol;
	}



}
