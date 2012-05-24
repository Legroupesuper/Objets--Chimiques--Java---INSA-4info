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

import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * Description of the class GetPitchRR.
 *
 *
 */
public class GetPitchRR implements ReactionRule{
	SubSolution<SubSolutionElements> _subSol;
	
	public SubSolution<SubSolutionElements> get_subSol() {
		return _subSol;
	}

	public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
		this._subSol = _subSol;
	}

	public GetPitchRR(){
		System.out.println("On appelle le constructeur");
		_subSol = new SubSolution<SubSolutionElements>();
		_subSol.addType(BarInCreation.class);
		_subSol.addType(Pitch.class);
	}
	
	public Object[] computeResult() {
		return new Object[]{_subSol.getElements().get(1)};
	}

	public boolean computeSelect() {
		System.out.println("Inerte ? "+_subSol.getSolution().is_inert());
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}




}