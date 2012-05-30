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

package sentence;

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
		this._subSolPlaces = new SubSolution<SubSolutionElements>();
		this._subSolPlaces.addType(PlaceType.class);
		this._subSolPlaces.addType(String.class);

		this._subSolPrepositions = new SubSolution<SubSolutionElements>();
		this._subSolPrepositions.addType(PrepositionType.class);
		this._subSolPrepositions.addType(String.class);
	}

	public Object[] computeResult() {
		String place = (String)this._subSolPlaces.getElements().get(1);
		String preposition = (String)this._subSolPrepositions.getElements().get(1);
		String concat = preposition+" "+place;
		return new Object[]{new Complement(concat)};
	}

	@Dontuse
	public boolean computeSelect() {
		return true;
	}

	public SubSolution<SubSolutionElements> get_subSolPlaces() {
		return this._subSolPlaces;
	}

	public SubSolution<SubSolutionElements> get_subSolPrepositions() {
		return this._subSolPrepositions;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	public void set_subSolPlaces(SubSolution<SubSolutionElements> _subSolPlaces) {
		this._subSolPlaces = _subSolPlaces;
	}


	public void set_subSolPrepositions(
			SubSolution<SubSolutionElements> _subSolPrepositions) {
		this._subSolPrepositions = _subSolPrepositions;
	}

	@Override
	public String toString() {
		return "ChooseComplementRR";
	}
}