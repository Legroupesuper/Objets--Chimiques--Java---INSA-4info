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
package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.reactionrules.StartMelodicRR;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;



public class LittleTests {
	
	public static void testMelodic(){
		StartMelodicRR startMelodicRR = new StartMelodicRR();
		Pitch pitch = new Pitch(5, new DegreeImpl(1));
		BarNumber barNumber = new BarNumber(1);
		
		Solution subSol =  new Solution();
		subSol.add(new BarInCreation());
		subSol.add(new BarNumber(1));
		
		final Solution sol = new Solution();
		sol.add(startMelodicRR);
		sol.add(pitch);
		sol.add(barNumber);
		System.out.println("Before :");
		System.out.println(sol);
		sol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("After :");
				System.out.println(sol);
			}
		});
		sol.react();
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testMelodic();	
	}

}