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

public class ChooseSubjectRR implements ReactionRule{
	
	/*
	 * ChooseSubject prend un élément dans la solution qui contient l'identificateur SubjectType
	 */

	private SubSolution<SubSolutionElements> _subSol;
	
	public ChooseSubjectRR() {
		super();
		SubSolutionElements e = new SubSolutionElements();
		_subSol = new SubSolution<SubSolutionElements>(e);
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(SubjectType.class);
		_subSol.setTypeList(l);
	}
	

	public Object[] computeResult() {
		String s = (String)_subSol.getElements().get(0);

		//int a = (int)Math.random()*2; //mets au pluriel avec 50% de chances
		//if(a==1)
		//	s += "s";
		//
		return new Object[]{s};
	}

	@Dontuse
	public boolean computeSelect() {
		return false;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

}