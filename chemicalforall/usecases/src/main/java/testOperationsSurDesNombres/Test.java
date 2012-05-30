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

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class Test {
	public static long time = 0;

	public static void main(String[] args) {
		Solution mainSol = new Solution();
		Solution sousSol = new Solution();

		sousSol.add(new Integer(2));
		sousSol.add(new Integer(5));
		sousSol.add(new Mult2RR(5));

		mainSol.add(new Integer(17));
		mainSol.add(new Add10_17());
		mainSol.add(new Affiche());
		mainSol.add(sousSol);

		mainSol.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				time = System.currentTimeMillis()-time;
				System.out.println("\n\nRéaction terminée :");
				System.out.println("Time : "+time+"ms");
				System.out.println(e.getSource());
			}
		});

		System.out.println(mainSol);
		time = System.currentTimeMillis();
		mainSol.react();
	}
}