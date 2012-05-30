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
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;


public class TestALaCon {
	public static void main(String[] args) {
		final Solution sol = new Solution();
		sol.add(1);
		sol.add(2);
		sol.add(3);
		sol.add(4);
		sol.add(5);
		sol.add(6);
		sol.add(7);
		sol.add(new MaxIntRR(0));
		System.out.println("Avant : ");
		System.out.println(sol);
		sol.addInertEventListener(new InertEventListener() {
			public void isInert(InertEvent e) {
				System.out.println("Apres");
				System.out.println(sol);
			}
		});
		sol.react();
	}
}