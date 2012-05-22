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
package org.chemicalmozart.model.implementations.reactionrules;

import org.chemicalmozart.model.implementations.Number;

import fr.insa.rennes.info.chemical.backend.SubSolution;



/**
 * Description of the class StructuralRR.
 *
 * 
 *
 */
public class StructuralRR {

	private SubSolution _sol;
	private Number _num;
	
	
	/**
	 * @return the sol
	 */
	public SubSolution getSol() {
		return _sol;
	}
	/**
	 * @param sol the sol to set
	 */
	public void setSol(SubSolution sol) {
		this._sol = sol;
	}
	/**
	 * @return the num
	 */
	public Number getNum() {
		return _num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Number num) {
		this._num = num;
	}



}