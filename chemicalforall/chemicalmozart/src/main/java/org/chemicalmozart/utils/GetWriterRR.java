package org.chemicalmozart.utils;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class GetWriterRR implements ReactionRule{
	SubSolution<SubSolutionElements> _subSol;
	
	public SubSolution<SubSolutionElements> get_subSol() {
		return _subSol;
	}

	public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
		this._subSol = _subSol;
	}
	
	public GetWriterRR(){
		_subSol = new SubSolution<SubSolutionElements>();
		_subSol.addType(MusicWriter.class);
	}

	public Object[] computeResult() {
		return new Object[]{_subSol.getElements().get(0)};
	}

	public boolean computeSelect() {
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

}
