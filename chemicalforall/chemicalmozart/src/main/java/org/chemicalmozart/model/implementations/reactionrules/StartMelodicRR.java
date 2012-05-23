/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.Pitch;
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
 *  <li>BarNumber : Represents the next BarNumber which will be used for the melodic generation.</li>
 *  <li>Pitch : Represents the last note played.</li>
 * </ul>
 * <br />
 * This reaction rule adds to the bar in creation a RythmicRR and a MelodicRR. It must also add a Solution which contains some
 * available rhythm for 2 and 4 quaters per time. This solution can be obtained by the MozartSolutionFactory.createRythmicPull().
 * <br />
 * The pitch must also be added to the BarInCreation. It will be used to determine the last note played.
 */
public class StartMelodicRR implements ReactionRule{
	/**
	 * The number is the result solution
	 */
	private BarNumber _barNumber;
	
	public BarNumber get_barNumber() {
		return _barNumber;
	}

	public void set_barNumber(BarNumber _barNumber) {
		this._barNumber = _barNumber;
	}

	/**
	 * The matched subsolution
	 */
	private SubSolution<SubSolutionElements> _sol;
	
	public SubSolution<SubSolutionElements> get_sol() {
		return _sol;
	}

	public void set_sol(SubSolution<SubSolutionElements> _sol) {
		this._sol = _sol;
	}

	/**
	 * The pitch of the last note played.
	 */
	private Pitch _pitch;

	/**
	 * The default constructor initialize the type list of the Subsolution.
	 */
	public StartMelodicRR() {
		super();
		_sol = new SubSolution<SubSolutionElements>();
		_sol.addType(BarInCreation.class);
		_sol.addType(BarNumber.class);
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
		System.out.println("On est dans le compute result");
//		Solution sol = _sol.getSolution();
//		sol.add(new RythmicRR());
//		sol.add(new MelodicRR());
//		sol.add(_pitch);
//		MozartSolutionFactory factory = new MozartSolutionFactoryImpl();
//		sol.add(factory.createRythmicPull());
//		sol.add(_sol.getElements().get(1));
//
//		BarNumber tempBarNumber = _barNumber;
//		tempBarNumber.increment();
//		return new Object[]{sol, tempBarNumber};
		return null;
	}

	/**
	 * Controls that the good elements are matched. It must verify that the BarNumber object has the same value as the
	 * BarNumber object in the subsolution. If this is the case, the function returns true.
	 */
	public boolean computeSelect() {
		System.out.println("Compute select");
		Integer bn = (Integer) _sol.getElements().get(1);
		System.out.println("On est dans le compute select "+_barNumber.getValue()+" == "+bn);
		return _barNumber.getValue() == bn.intValue();
	}

	/**
	 * @return the _pitch
	 */
	public Pitch get_pitch() {
		return this._pitch;
	}
	
	/**
	 * This rule is in infinity-shot
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	/**
	 * @param _pitch the _pitch to set
	 */
	public void set_pitch(Pitch _pitch) {
		this._pitch = _pitch;
	}

	/** toString
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StartMelodicRR [_barNumber=" + _barNumber + ", _sol="// + _sol
				+ ", _pitch=" + _pitch + "]";
	}
	
	
	
}
