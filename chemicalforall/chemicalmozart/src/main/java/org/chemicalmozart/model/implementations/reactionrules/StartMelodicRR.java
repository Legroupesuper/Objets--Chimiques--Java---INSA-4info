package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule needs the following elements to react :<br />
 * <ul>
 * 	<li>SubSolution "BarInCreation"
 * 		<ul>
 * 			<li>BarInCreation : Object that indicates that the current solution represents a bar in creation.</li>
 * 			<li>BarNumber : The BarNumber of the current selected solution.</li>
 * 		</ul>
 *  </li>
 *  <li>BarNumber : Represent the next BarNumber which will be used for the melodic generation.</li>
 *  <li>Pitch : Represents the last note played.</li>
 * </ul>
 * <br />
 * This reaction rule adds to the bar in creation a RythmicRR and a MelodicRR. It must also add a Solution which contains some
 * available rhythm for 2 and 4 quater per time. This solution can be obtained by the MozartSolutionFactory.createRythmicPull().
 * <br />
 * The pitch must also be added to the BarInCreation. It will be used to determine the last note played.
 */
public class StartMelodicRR implements ReactionRule{
	/**
	 * The measure number is the result solution
	 */
	private BarNumber _measureNumber;
	/**
	 * The matched subsolution
	 */
	private SubSolution<SubSolutionElements> _sol;
	/**
	 * The pitch of the last note played.
	 */
	private Pitch _pitch;

	/**
	 * The default constructor initialize the type list of the Subsolution.
	 */
	public StartMelodicRR() {
		super();
		_sol = new SubSolution<SubSolutionElements>(new SubSolutionElements());
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		_sol.setTypeList(l);
	}

	/**
	 * Adds to the solution contained in _sol a RythmicRR and a MelodicRR and _pitch.
	 * It also add the result of MozartSolutionFactory.createRythmicPull() to _sol.<br />
	 * There is no need to put back BarInCreation in _sol. We will not use it in the future. On the contrary, it's important to
	 * put back the BarNumber in _sol.<br />
	 * It must also return directly a BarNumber after having increased its value.
	 * @returns the solution contained in _sol as describe before, the increased BarNumber
	 */
	public Object[] computeResult() {
		Solution temp = _sol.getSolution();
		temp.add(new RythmicRR());
		temp.add(new MelodicRR());
		temp.add(_pitch);
		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
		temp.add(factory.createRythmicPull());
		temp.add(_sol.getElements().get(1));
		_sol.setSolution(temp);
		BarNumber tempBarNumber = _measureNumber;
		tempBarNumber.increment();
		return new Object[]{_sol, tempBarNumber};
	}

	/**
	 * Controls that the good elements are matched. It must verify that the BarNumber object has the same value as the
	 * BarNumber object in the subsolution. If this is the case, the function returns true.
	 */
	public boolean computeSelect() {
		boolean solContainsBarInCreationIn0 = _sol.getElements().get(0) instanceof BarInCreation;
		boolean barNumbersHaveSameValue = false;
		if (_sol.getElements().get(1) instanceof BarNumber)
			barNumbersHaveSameValue = _measureNumber.getValue() == ((BarNumber)_sol.getElements().get(1)).getValue();
		return solContainsBarInCreationIn0 && barNumbersHaveSameValue;
	}
	/**
	 * @return the _measureNumber
	 */
	public BarNumber get_measureNumber() {
		return this._measureNumber;
	}
	/**
	 * @return the _pitch
	 */
	public Pitch get_pitch() {
		return this._pitch;
	}
	/**
	 * @return the _sol
	 */
	public SubSolution<SubSolutionElements> get_sol() {
		return this._sol;
	}
	/**
	 * This rule is in infinity-shot
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
	/**
	 * @param _measureNumber the _measureNumber to set
	 */
	public void set_measureNumber(BarNumber _measureNumber) {
		this._measureNumber = _measureNumber;
	}
	/**
	 * @param _pitch the _pitch to set
	 */
	public void set_pitch(Pitch _pitch) {
		this._pitch = _pitch;
	}

	/**
	 * @param _sol the _sol to set
	 */
	public void set_sol(SubSolution<SubSolutionElements> _sol) {
		this._sol = _sol;
	}
}