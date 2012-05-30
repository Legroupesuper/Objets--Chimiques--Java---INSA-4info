/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package testOperationsSurDesNombres;

import fr.insa.rennes.info.chemical.user.Dontuse;
import fr.insa.rennes.info.chemical.user.ReactionRule;
/*
 * Cette règle de réaction affiche toutes les String
 * présentes dans la solution et les enlève de celle-ci
 */
public class Affiche implements ReactionRule {
	private String _s;

	public Object[] computeResult() {
		System.out.println(this._s);
		return new Object[]{};
	}

	@Dontuse
	public boolean computeSelect() {
		return true;
	}

	public String get_s(){
		return this._s;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_s(String str){
		this._s = str;
	}

}