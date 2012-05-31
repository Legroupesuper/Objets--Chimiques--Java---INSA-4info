/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.List;

import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;
import org.chemicalmozart.model.implementations.solutionindentification.Temporary;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;


/**
 * This rule computes the degree of harmony that will be played after the current degree.
 * It creates a temporary solution in which all the possible degrees are present. The created solution is identified with a Temporary object.
 * It also creates a PickOneRR and a RythmeHRR.
 * <br />
 * Here is how it works :<br />
 * It takes two reagents :<br />
 * <ul>
 * 	<li>DegreeImpl _degree</li>
 *  <li>SubSolution _barInCreationSolution</li>
 *  </ul>
 * The computeSelect is used, see the computeSelect and computeResult documentation for more informations
 */
public class HarmonicRR5 implements ReactionRule{

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
	 * @return the _barInCreationSolution
	 */
	public SubSolution<SubSolutionElements> get_barInCreationSolution() {
		return _barInCreationSolution;
	}

	/**
	 * @param _barInCreationSolution the _barInCreationSolution to set
	 */
	public void set_barInCreationSolution(
			SubSolution<SubSolutionElements> _barInCreationSolution) {
		this._barInCreationSolution = _barInCreationSolution;
	}

	/**
	 * The constructor is used by the library to instantiate the _barInCreationSolution element.
	 */
	public HarmonicRR5() {
		super();
		_barInCreationSolution = new SubSolution<SubSolutionElements>();
		_barInCreationSolution.addType(BarInCreation.class);
		_barInCreationSolution.addType(QuaterLeft.class);
	}

	/**
	 * It must not return _degree into the main solution !<br />
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
	 * It also add the _degree in the solution identified by a BarInCreation Object. It must also put back the BarInCreation object into
	 *  _barInCreationSolution because, due to the reaction, it's no longer in _barInCreationSolution. Before adding the BarInCreation object,
	 *  it's important to change it's state to RYTHMEHRR.<br />
	 * <br /><br />
	 * @return a Solution identified by a HarmonicSol object with a proportional number of each possible degrees, <br />
	 * a PickOneRR and a RythmeHRR.<br />
	 * @see BarInCreationState
	 */
	public Object[] computeResult() {
		//Temporary Solution which will be in the return statement
		Solution temporarySolution = new Solution();
		Temporary temporaryID = new Temporary();
		temporarySolution.add(temporaryID);
		

		//Percentages
		int chancesOfGoingOnADegree1 = 60;//cadence parfaite
		int chancesOfGoingOnADegree2 = 0;
		int chancesOfGoingOnADegree3 = 0;
		int chancesOfGoingOnADegree4 = 0;
		int chancesOfGoingOnADegree5 = 10;//prolongement de la quinte
		int chancesOfGoingOnADegree6 = 20;//cadence évitée

		//Modification of _barInCreationSolution
		Solution barInCreationSolution = _barInCreationSolution.getSolution();
		barInCreationSolution.add(_barInCreationSolution.getElements().get(1));
		barInCreationSolution.add(_degree);
		BarInCreation babar = new BarInCreation();
		babar.set_state(BarInCreationState.RYTHMEHRR);
		barInCreationSolution.add(babar);
		barInCreationSolution.add(_degree);
		barInCreationSolution.add(new RythmeHRR());
		//Creation of the harmonicSolution which will be in the return statement
		for(int i = 0; i<chancesOfGoingOnADegree1;i++){
			temporarySolution.add(new DegreeImpl(1));	
		}
		for(int i = 0; i<chancesOfGoingOnADegree2;i++){
			temporarySolution.add(new DegreeImpl(2));
		}
		for(int i = 0; i<chancesOfGoingOnADegree3;i++){
			temporarySolution.add(new DegreeImpl(3));
		}
		for(int i = 0; i<chancesOfGoingOnADegree4;i++){
			temporarySolution.add(new DegreeImpl(4));
		}
		for(int i = 0; i<chancesOfGoingOnADegree5;i++){
			temporarySolution.add(new DegreeImpl(5));
		}
		for(int i = 0; i<chancesOfGoingOnADegree6;i++){
			temporarySolution.add(new DegreeImpl(6));
		}

		//Return statement
		return new Object[]{temporarySolution,new PickOneRR()};

	}

	/**
	 * This method must succeed if :<br />
	 *  _barInCreationSolution has a valid BarInCreation element. This means that the state of the BarInCreation object is set to HARMONICRR <br />
	 *  && _degree.getValue() is equal to 1 (This is HarmonicRR1)
	 */
	public boolean computeSelect() {
		List<Object> barInCreationElements = _barInCreationSolution.getElements();
		boolean barInCreationInGoodState = false;
		boolean quaterLeftInGoodState = false;
		quaterLeftInGoodState = ((QuaterLeft)barInCreationElements.get(1)).getValue()>0;
		barInCreationInGoodState = 
				((BarInCreation)barInCreationElements.get(0)).get_state().equals(BarInCreation.BarInCreationState.HARMONICRR);
		return barInCreationInGoodState && (_degree.get_value()==5) && quaterLeftInGoodState;
	}


	/**
	 * This is an infinity-shot rule
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}
}