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
package functionnaltests;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class DeleteRR implements ReactionRule{
	Solution _sol;
	
	public Solution get_sol() {
		return _sol;
	}

	public void set_sol(Solution _sol) {
		this._sol = _sol;
	}

	public Object[] computeResult() {
		return new Object[]{new DeleteRR()};
	}

	public boolean computeSelect() {
		// TODO Auto-generated method stub
		return _sol.size() == 0;
	}

	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
		return Multiplicity.ONE_SHOT;
	}

}