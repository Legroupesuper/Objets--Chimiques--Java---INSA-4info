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
package nbPremiers;

import fr.insa.rennes.info.chemical.user.ReactionRule;

public class calculNbPremierRR implements ReactionRule {

	private Integer _a;
	private Integer _b;

	public Object[] computeResult() {
		return new Object[] {this._b};
	}

	public boolean computeSelect() {
		return (this._a%this._b == 0);
	}

	public Integer get_a(){
		return this._a;
	}
	public Integer get_b(){
		return this._b;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public void set_a(Integer i){
		this._a = i;
	}

	public void set_b(Integer i){
		this._b = i;
	}

}