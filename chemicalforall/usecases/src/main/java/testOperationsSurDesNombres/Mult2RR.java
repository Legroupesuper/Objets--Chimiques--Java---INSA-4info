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

import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;
/*
 * Cette règle de réaction multiplie par 2 les entiers
 * égaux à l'attribut _b
 */
public class Mult2RR implements ReactionRule{
	Integer _a;
	@Dontreact
	Integer _b;

	public Mult2RR(Integer i){
		this._b = i;
	}

	public Object[] computeResult() {
		return new Object[] {this._a*2};
	}

	public boolean computeSelect() {
		return this._a.equals(this._b);
	}

	public Integer get_a(){
		return this._a;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_a(Integer i){
		this._a = i;
	}


}