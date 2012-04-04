package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This rule compute the degree of harmony that will be played after the current degree.
 * It creates a temporary solution in which all the possible degrees are present. The created solution is identified with a Temporary object.
 * It also creates a PickOneRR and a RythmeHRR.
 * <br />
 * Here is how it works :<br />
 * It takes two reactives :<br />
 * <ul>
 * 	<li>DegreeImpl _degree</li>
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
	private SubSolution<SubSolutionElements> _barInCreationSolution;

	/**
	 * The constructor is used by the library to instantiate the _barInCreationSolution element.
	 */
	public HarmonicRR1() {
		super();
		/*
		 *must be completed
		 */
	}
	/**
	 * It musn't return _degree into the main solution !<br />
	 * After a 1st degree, you can go :<br />
	 * <ul>
	 * <li> On a 1nd degree on 10%</li>
	 * <li> On a 2nd degree on 10%</li>
	 * <li> On a 3rd degree on 10%</li>
	 * <li> On a 4th degree on 25%</li>
	 * <li> On a 5th degree on 30%</li>
	 * <li> On a 6th degree on 15%</li>
	 *  </ul><br />
	 * <br />
	 * ! _degree must be added to _barInCreationSolution !<br />
	 * <br />
	 * The computResult add a temporary solution into the main solution. To identify this solution, you must add a
	 * Temporary object into the solution. This object will be used later.
	 * <br /><br />
	 * It also add the _degree in the solution identified by a BarInCreationObject. It must also put back the BarInCreation object into
	 *  _barInCreationSolution because, due to the reaction, it's no longer in _barInCreationSolution. Before adding the BarInCreation object,
	 *  it's important to change it's state to RYTHMEHRR.<br />
	 * <br /><br />
	 * @return a Solution identified by a HarmonicSol object with a proportional number of each possible degrees, <br />
	 * a PickOneRR and a RythmeHRR.<br />
	 * @see BarInCreationState
	 */
	public Object[] computeResult() {
		/*
		 * must be completed
		 */
		return null;
	}

	/**
	 * This method must succeed if :<br />
	 *  _barInCreationSolution has a valid BarInCreation element. This means that the state of the BarInCreation object is set to HARMONICRR <br />
	 *  && _degree.getValue() is equal to 1 (This is HarmonicRR1)
	 */
	public boolean computeSelect() {
		/*
		 * must be completed
		 */
		return false;
	}

	/**
	 * @return the _barInCreationSolution
	 */
	public SubSolution<SubSolutionElements> get_barInCreationSolution() {
		return this._barInCreationSolution;
	}

	/**
	 * @return the _degree
	 */
	public DegreeImpl get_degree() {
		return this._degree;
	}

	/**
	 * This is an infinity-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	/**
	 * @param _barInCreationSolution the _barInCreationSolution to set
	 */
	public void set_barInCreationSolution(
			SubSolution<SubSolutionElements> _barInCreationSolution) {
		this._barInCreationSolution = _barInCreationSolution;
	}

	/**
	 * @param _degree the _degree to set
	 */
	public void set_degree(DegreeImpl _degree) {
		this._degree = _degree;
	}
}