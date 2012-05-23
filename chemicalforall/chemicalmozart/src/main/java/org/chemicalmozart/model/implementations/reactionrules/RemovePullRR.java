package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.rythme.RythmPattern;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class RemovePullRR implements ReactionRule{
	/**
	 * The solution which contains the rhythmic patterns and identified by a RythmePull object
	 */
	private Solution _rythmeSolution;
	
	public Solution get_rythmeSolution() {
		return _rythmeSolution;
	}

	public void set_rythmeSolution(Solution _rythmeSolution) {
		this._rythmeSolution = _rythmeSolution;
	}

	public Object[] computeResult() {
		return null;
	}

	public boolean computeSelect() {
		for(Object o : _rythmeSolution)
			if(o instanceof RythmePull)
				return true;
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
}
