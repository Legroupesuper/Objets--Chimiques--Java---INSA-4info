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
package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;
/*
 * Cette règle de réaction prend en paramètre 1 entier égal à 17
 * dans la solution, et dans une sous-solution, un entier égal à 10
 * et une règle de réaction de type Mult2RR
 */
public class Add10_17 implements ReactionRule{
	private Integer _attr17;
	private SubSolution<SubSolutionElements> _subSolution;

	public Add10_17(){
		super();
		this._subSolution = new SubSolution<SubSolutionElements>();
		this._subSolution.addType(Integer.class);
		this._subSolution.addType(Mult2RR.class);
	}

	public Object[] computeResult() {
		Integer i = (Integer)this._subSolution.getElements().get(0) + this._attr17;
		String s = "Cédric a "+i+" ans.";
		return new Object[] {s};
	}
	public boolean computeSelect() {
		boolean bool = ((Integer)this._subSolution.getElements().get(0)).equals(10);
		return bool && this._attr17.equals(17);
	}

	public Integer get_attr17(){
		return this._attr17;
	}

	public SubSolution<SubSolutionElements> get_subSolution(){
		return this._subSolution;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	public void set_attr17(Integer i){
		this._attr17 = i;
	}

	public void set_subSolution(SubSolution<SubSolutionElements> s){
		this._subSolution = s;
	}
}