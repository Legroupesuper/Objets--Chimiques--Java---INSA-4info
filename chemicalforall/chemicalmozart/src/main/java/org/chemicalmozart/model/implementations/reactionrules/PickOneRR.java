package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;

import fr.insa.rennes.info.chemical.backend.ChemicalElement;
import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to pick a Degree in the temporary solution and move it
 * to the main solution.
 * This rule disappears once it has react.
 */
public class PickOneRR implements ReactionRule{

	private SubSolution<ElementList> _subSol;
	private Solution _sol;
	
	/**
	 * @return the _subSol
	 */
	public SubSolution<ElementList> get_subSol() {
		return _subSol;
	}

	/**
	 * @param _subSol the _subSol to set
	 */
	public void set_subSol(SubSolution<ElementList> _subSol) {
		this._subSol = _subSol;
	}

	/**
	 * @return the _sol
	 */
	public Solution get_sol() {
		return _sol;
	}

	/**
	 * @param _sol the _sol to set
	 */
	public void set_sol(Solution _sol) {
		this._sol = _sol;
	}

	/**
	 * The constructor is used to set the type of the element we want to match in
	 * the subsolution _subSol.
	 */
	public PickOneRR(){
		_subSol = new SubSolution<ElementList>(new ElementList());
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(Temporary.class);
		l.add(DegreeImpl.class);
		_subSol.setTypeList(l);
	}
	
	/**
	 * It just returns the chosen DegreeImpl into the parent solution and a garbage reaction rule to remove the temporary solution.
	 * The rule used to clean is named GarbageRR
	 */
	public Object[] computeResult() {
		/*
		 * must be completed
		 */
		return null;
	}
	
	/**
	 * The computeSelect always succeed
	 * @TODO check that the trick is working
	 */
	public boolean computeSelect() {
		return true;
	}
	
	/**
	 * This is a one-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
}