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

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Result;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to put a bar into the result solution.
 * It takes the following reagents: (they must appear in this order)
 * <ul>
 * 	<li>SubSolution "barInCreaction"
 * 		<ul>
 * 			<li>BarInCreation : To identify the bar in creation</li>
 * 			<li>QuaterLeft : To test in the computeSelect if the bar is full.</li>
 * 		</ul>
 *  </li>
 *  <li>Subsolution "result"
 *  	<ul>
 *  		<li>Result : The object that identify the result solution.</li>
 *  	</ul>
 *  </li>
 * </ul>
 * It must put the first subsolution into the second one.
 */
public class MoveToResultRR implements ReactionRule{

	/**
	 * This subsolution represents the BarInCreation solution
	 */
	private Solution _subSolInCreation;
	/**
	 * This subsolution represents the result solution
	 */
	private SubSolution<SubSolutionElements> _subSolResult;

	/**
	 * The constructor is used by the library to instantiate _subSolInCreation and _subSolResult.
	 */
	public MoveToResultRR() {
		super();
		_subSolResult = new SubSolution<SubSolutionElements>();
		_subSolResult.addType(Result.class);
	}

	/**
	 * The computeResult must add the BarInCreation solution into the Result solution.
	 * <br />
	 * It must put back the BarInCreation and the QuaterLeft in the BarInCreation solution and then add the BarInCreationSolution into
	 * the Result solution. It must also put back the Result object into the Result solution.
	 * @return Nothing
	 */
	public Object[] computeResult() {
		System.out.println("On est dans le compute result");
		Solution resultSolution = _subSolResult.getSolution();
		Result resultID = new Result();
		resultSolution.add(resultID);
		resultSolution.add(_subSolInCreation);
		resultSolution.react();
		return new Object[]{};
	}

	/**
	 * The computeSelect has in charge to check that the configuration of the BarInCreation is correct.
	 * This means that it must contains a Quaterleft object with a value of 0. It must also contain at least
	 * one or several ChordImpl.
	 */
	public boolean computeSelect() {
		System.out.println("On est dans le compute select de moveToResult");
		System.out.println(_subSolResult.getSolution());
		boolean barInCreationFound = false;
		boolean quaterLeftValid = false;
		for(Object o : _subSolInCreation){
			if(o instanceof BarInCreation)
				barInCreationFound = true;
			else if(o instanceof QuaterLeft)
				quaterLeftValid = ((QuaterLeft) o).getValue()==0;
		}
		System.out.println(barInCreationFound && quaterLeftValid);
		return barInCreationFound && quaterLeftValid;
	}

	public SubSolution<SubSolutionElements> get_subSolResult() {
		return this._subSolResult;
	}
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
	public void set_subSolResult(SubSolution<SubSolutionElements> _subSolResult) {
		this._subSolResult = _subSolResult;
	}

	public Solution get_subSolInCreation() {
		return _subSolInCreation;
	}

	public void set_subSolInCreation(Solution _subSolInCreation) {
		this._subSolInCreation = _subSolInCreation;
	}
	
}
