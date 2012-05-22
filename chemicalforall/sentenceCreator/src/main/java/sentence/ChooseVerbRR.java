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
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class ChooseVerbRR implements ReactionRule{
	
	/**
	 * Take a verb from the verbs solution then put it in the solution.
	 * It takes the following reagents: (they must appear in this order)
	 * <ul>
	 * 	<li>SubSolution _subSol
	 * 		<ul>
	 * 			<li>SubjectType : To identify the solution</li>
	 * 			<li>Verb : a verb</li>
	 * 		</ul>
	 *  </li>
	 * </ul>
	 */
	private SubSolution<SubSolutionElements> _subSol;
	
	public SubSolution<SubSolutionElements> get_subSol() {
		return _subSol;
	}

	public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
		this._subSol = _subSol;
	}

	public ChooseVerbRR() {
		super();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(SubjectType.class);
		l.add(Verb.class);
		e.setTypeList(l);
		_subSol = new SubSolution<SubSolutionElements>(e);
	}
	
	public Object[] computeResult() {
		Verb v = (Verb)_subSol.getElements().get(1);
		return new Object[]{v};
	}

	public boolean computeSelect() {
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
	
	@Override
	public String toString() {
		return "ChooseVerbRR";
	}

}