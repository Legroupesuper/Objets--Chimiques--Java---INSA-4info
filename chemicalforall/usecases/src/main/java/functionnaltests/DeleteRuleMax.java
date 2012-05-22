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
package functionnaltests;

import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.user.Dontputback;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeleteRuleMax implements ReactionRule{
	@Dontputback
	SubSolution<SubSolutionElements> _subSol;
	
	public DeleteRuleMax() {
		super();
		SubSolutionElements e = new SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(Integer.class);
		l.add(Integer.class);
		_subSol = new SubSolution<SubSolutionElements>(e);
		_subSol.setTypeList(l);
	}

	public SubSolution<SubSolutionElements> get_subSol() {
		return _subSol;
	}

	public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
		this._subSol = _subSol;
	}

	public Object[] computeResult() {
		List<Object> l = _subSol.getElements();
		int a = (Integer) l.get(0);
		int b = (Integer) l.get(1);
		if(a<b){
			return new Object[]{b};
		}
		return new Object[]{a};
	}

	public boolean computeSelect() {
		return true;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

}