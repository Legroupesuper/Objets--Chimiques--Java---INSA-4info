/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;


public class Test2 {
	public static void main(String[] args) {
		final Solution mainSol = new Solution();
		Solution innerSol = new Solution();
		Solution innerSol2 = new Solution();
		innerSol.add(1);
		innerSol.add(2);
		innerSol.add(3);
		innerSol.add(4);
		mainSol.add(new DeleteRR());
		mainSol.add(innerSol);
		mainSol.add(new DeleteRuleMax());
		mainSol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Après : ");
				System.out.println(mainSol);
			}
		});
		
		System.out.println("Avant : ");
		System.out.println(mainSol);
		mainSol.react();
	}
}