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
package sentence;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class ChooseComplementRR implements ReactionRule {

	/*
	 * Prend une preposition dans la sous-solution contenant l'identificateur des prepositions
	 * Prend un lieu dans la sous-solution contenant l'identificateur des lieux
	 * Rend la string qui est la concatenation de la preposition et du lieu
	 * 
	 */
	private SubSolution<SubSolutionElements> _subSolComplement;
	private SubSolution<SubSolutionElements> _subSolPlaces;
	private SubSolution<SubSolutionElements> _subSolPrepositions;
	
	public ChooseComplementRR(){
		super();
		
		
		SubSolutionElements eltsPlaces = new SubSolutionElements();
		_subSolPlaces = new SubSolution<SubSolutionElements>(eltsPlaces);
		List<Class<? extends Object>> listPlaces = new ArrayList<Class<? extends Object>>();
		listPlaces.add(PlaceType.class);
		_subSolPlaces.setTypeList(listPlaces);
		
		SubSolutionElements eltsPrepositions = new SubSolutionElements();
		_subSolPrepositions = new SubSolution<SubSolutionElements>(eltsPrepositions);
		List<Class<? extends Object>> listPrepostions = new ArrayList<Class<? extends Object>>();
		listPrepostions.add(PrepositionType.class);
		_subSolPrepositions.setTypeList(listPrepostions);
	}
	
	public Object[] computeResult() {
		String place = (String)_subSolPlaces.getElements().get(0);
		String preposition = (String)_subSolPrepositions.getElements().get(0);
		return new Object[]{preposition+" "+place};
	}

	@Dontuse
	public boolean computeSelect() {
		return false;
	}

	public Multiplicity getMultiplicity() {
		return null;
	}

	
}