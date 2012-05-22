/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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

/**
 * Take a preposition from the prepositions solution and a place from the places solution
 * then concatenate the two and put it in the solution as a Complement.
 * It takes the following reagents: (they must appear in this order)
 * <ul>
 * 	<li>SubSolution _subSolPlaces
 * 		<ul>
 * 			<li>PlaceType : To identify the solution</li>
 * 			<li>String : a string which is a place</li>
 * 		</ul>
 *  </li>
 *  <li>Subsolution _subSolPrepositions
 *		<ul>
 *  		<li>PrepositionType : To identify the solution.</li>
 *  		<li>String :  a string which is a preposition</li>
 *  	</ul>
 *  </li>
 * </ul>
 */
public class ChooseComplementRR implements ReactionRule {

	private SubSolution<SubSolutionElements> _subSolPlaces;

	private SubSolution<SubSolutionElements> _subSolPrepositions;

	public ChooseComplementRR(){
		super();

		SubSolutionElements elts1 = new SubSolutionElements();
		List<Class<? extends Object>> list1 = new ArrayList<Class<? extends Object>>();
		list1.add(PlaceType.class);
		list1.add(String.class);
		elts1.setTypeList(list1);
		_subSolPlaces =  new SubSolution<SubSolutionElements>(elts1);

		SubSolutionElements elts2 = new SubSolutionElements();
		List<Class<? extends Object>> list2 = new ArrayList<Class<? extends Object>>();
		list2.add(PrepositionType.class);
		list2.add(String.class);
		elts2.setTypeList(list2);
		_subSolPrepositions =  new SubSolution<SubSolutionElements>(elts2);
	}

	public Object[] computeResult() {
		String place = (String)_subSolPlaces.getElements().get(1);
		String preposition = (String)_subSolPrepositions.getElements().get(1);
		String concat = preposition+" "+place;
		return new Object[]{new Complement(concat)};
	}

	@Dontuse
	public boolean computeSelect() {
		return true;
	}

	public Multiplicity getMultiplicity() {
		return null;
	}

	@Override
	public String toString() {
		return "ChooseComplementRR";
	}
	
	public SubSolution<SubSolutionElements> get_subSolPlaces() {
		return _subSolPlaces;
	}

	public void set_subSolPlaces(SubSolution<SubSolutionElements> _subSolPlaces) {
		this._subSolPlaces = _subSolPlaces;
	}


	public SubSolution<SubSolutionElements> get_subSolPrepositions() {
		return _subSolPrepositions;
	}

	public void set_subSolPrepositions(
			SubSolution<SubSolutionElements> _subSolPrepositions) {
		this._subSolPrepositions = _subSolPrepositions;
	}
}