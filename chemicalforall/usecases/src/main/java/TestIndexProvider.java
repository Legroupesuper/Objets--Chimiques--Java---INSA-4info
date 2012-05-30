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
import java.util.Arrays;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;
import fr.insa.rennes.info.chemical.user.ReactionRule.Multiplicity;


public class TestIndexProvider {
	public static void main(String[] args) {
		Solution main = new Solution();
		Solution subsol = new Solution();
		int[] val = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
		String [] val2 = new String[]{"toto", "tata", "tutu", "tonton", "toutou", "titi"};
		subsol.addAll(Arrays.asList(val));
		subsol.addAll(Arrays.asList(val2));
		main.add(subsol);
		
		ReactionRule rr = new ReactionRule() {
			private SubSolution<SubSolutionElements> _subSol;

			public SubSolution<SubSolutionElements> get_subSol() {
				return _subSol;
			}

			public void set_subSol(SubSolution<SubSolutionElements> _subSol) {
				this._subSol = _subSol;
			}

			public Multiplicity getMultiplicity() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean computeSelect() {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Object[] computeResult() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		main.add(rr);
	}
}