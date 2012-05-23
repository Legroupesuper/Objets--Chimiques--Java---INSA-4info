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
import java.util.ArrayList;
import java.util.List;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;



public class ConcatSubSolRR implements ReactionRule{
	private SubSolution<SubSolution<SubSolutionElements>> a;
	
	public ConcatSubSolRR() {
		/*SubSolutionElements ea = new SubSolutionElements();
		List<Class<? extends Object>> la = new ArrayList<Class<? extends Object>>();
		la.add(String.class);
		la.add(String.class);
		ea.setTypeList(la);
		
		SubSolution<SubSolutionElements> sa = new SubSolution<SubSolutionElements>(ea);*/
		a = new SubSolution<SubSolution<SubSolutionElements>>();
		a.addType(String.class);
		a.addType(String.class);
	}
	
	public Object[] computeResult() {
		String s = (String)a.getElements().get(0);
		String t = (String)a.getElements().get(1);
		
		System.out.println("J'ai s = "+s+", t = "+t);
		
		return new Object[]{s+" "+t};
	}
	public boolean computeSelect() {
		return true;
	}
	public SubSolution<SubSolution<SubSolutionElements>> getA() {
		return a;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void setA(SubSolution<SubSolution<SubSolutionElements>> a) {
		this.a = a;
	}

}