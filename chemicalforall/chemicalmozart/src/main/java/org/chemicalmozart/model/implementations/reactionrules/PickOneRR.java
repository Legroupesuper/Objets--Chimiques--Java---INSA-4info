package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;

import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to pick a Degree in the temporary solution and move it
 * to the main solution. It also create a GarbageRR to remove the Temporary Bar from the main Solution
 * This rule disappears once it has react.
 */
public class PickOneRR implements ReactionRule{

	/**
	 * This value represents the temporary bar which contains the possible following degrees.
	 * It must contain :
	 * <ul>
	 * 	<li>a <b>Temporary</b> object as identifier</li>
	 * 	<li>At least, one <b>DegreeImpl</b></li>
	 * </ul>
	 */
	private SubSolution<ElementList> _temporaryBar;
	/**
	 * This value represents the current bar in creation. This bar is a solution which must contain :
	 * <ul>
	 * 	<li>a <BarInCreation</b> object as identifier</li>
	 * </ul>
	 */
	private SubSolution<ElementList> _barInCreation;
	
	

	/**
	 * @return the _temporaryBar
	 */
	public SubSolution<ElementList> get_temporaryBar() {
		return _temporaryBar;
	}

	/**
	 * @param _temporaryBar the _temporaryBar to set
	 */
	public void set_temporaryBar(SubSolution<ElementList> _temporaryBar) {
		this._temporaryBar = _temporaryBar;
	}

	/**
	 * @return the _barInCreation
	 */
	public SubSolution<ElementList> get_barInCreation() {
		return _barInCreation;
	}

	/**
	 * @param _barInCreation the _barInCreation to set
	 */
	public void set_barInCreation(SubSolution<ElementList> _barInCreation) {
		this._barInCreation = _barInCreation;
	}

	/**
	 * The constructor is used to set the type of the element we want to match in
	 * the subsolutions _temporaryBar and _barInCreation.
	 */
	public PickOneRR(){
		_temporaryBar = new SubSolution<ElementList>(new ElementList());
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(Temporary.class);
		l.add(DegreeImpl.class);
		_temporaryBar.setTypeList(l);
		
		/*
		 * Do quite the same thing to get the bar in creation solution
		 */
	}
	
	/**
	 * It just returns the chosen DegreeImpl into the parent solution and a GarbageRR to remove the temporary solution.
	 * It must also put back the BarInCreation into the concerned solution because due to the reaction, it will be consumed.
	 * @return the Choosen DegreeImpl, a GarbageRR
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