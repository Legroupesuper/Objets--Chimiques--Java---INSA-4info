package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.DegreeImpl;

import fr.insa.rennes.info.chemical.backend.ElementList;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This rule compute the degree of harmony that will be played after the current degree.
 * It creates a temporary solution in which all the possible degrees are present. The created solution is identified with a Temporary object.
 * It also creates a PickOneRR and a RythmeHRR. 
 * <br />
 * Here is how it works :<br />
 * It takes two reactives :<br />
 * <ul>
 * 	<li>DegreeImpl _degree1</li>
 *  <li>SubSolution _barInCreationSolution</li>
 *  </ul>
 * The computeSelect is used, see the computeSelect and computeResult documentation for more informations
 */
public class HarmonicRR1 implements ReactionRule{

	/**
	 * Represents the current Degree that we will use for the next chord.
	 */
	public DegreeImpl _degree;
	/**
	 * Represents the BarInCreation Solution. It must contain :
	 * <ul>
	 * 	<li>a <b>BarInCreation</b> object to identify the good solution</li>
	 * </ul>
	 */
	private SubSolution<ElementList> _barInCreationSolution;
	
	/**
	 * @return the _barInCreationSolution
	 */
	public SubSolution<ElementList> get_barInCreationSolution() {
		return _barInCreationSolution;
	}

	/**
	 * @param _barInCreationSolution the _barInCreationSolution to set
	 */
	public void set_barInCreationSolution(
			SubSolution<ElementList> _barInCreationSolution) {
		this._barInCreationSolution = _barInCreationSolution;
	}

	/**
	 * @return the _degree
	 */
	public DegreeImpl get_degree() {
		return _degree;
	}

	/**
	 * @param _degree the _degree to set
	 */
	public void set_degree(DegreeImpl _degree) {
		this._degree = _degree;
	}



	/**
	 * It musn't return _degree into the main solution !
	 * After a 1st degree, you can go :
	 * <ul>
	 * <li> On a 2nd degree on 15%</li>
	 * <li> On a 3rd degree on 15%</li>
	 * <li> On a 4th degree on 25%</li>
	 * <li> On a 5th degree on 30%</li>
	 * <li> On a 6th degree on 15%</li>
	 *  </ul><br />
	 *  
	 * ! _degree must be added to _sol !
	 * The computResult add a temporary solution into the main solution. To identify this solution, you must add a
	 * Temporary object into the solution. This object will be used later.
	 * 
	 * It also add the _degree in the solution identified by a BarInCreationObject. It must also put back the BarInCreation object into
	 *  _barInCreationSolution because, due to the reaction, it's no longer in _barInCreationSolution.
	 * 
	 * @return a Solution identified by a HarmonicSol object with a proportional number of each possible degrees, <br />
	 * a PickOneRR and a RythmeHRR.

	 */
	public Object[] computeResult() {
		/*
		 * must be completed
		 */
		return null;
	}
	
	/**
	 * This method must succeed if :
	 *  _barInCreationSolution is a bar in creation solution <br />
	 *  && _degree.getValue() is equal to 1 (This is HarmonicRR1)
	 */
	public boolean computeSelect() {
		/*
		 * must be completed
		 */
		return false;
	}
	
	/**
	 * This is an infinity-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
}