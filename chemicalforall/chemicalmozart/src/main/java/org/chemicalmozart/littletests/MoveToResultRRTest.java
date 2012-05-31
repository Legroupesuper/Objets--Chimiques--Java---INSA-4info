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
package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.reactionrules.GetPitchRR;
import org.chemicalmozart.model.implementations.reactionrules.MoveToResultRR;
import org.chemicalmozart.model.implementations.reactionrules.StartMelodicRR;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.Result;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class MoveToResultRRTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Solution sol = new Solution();
		Solution solBarInCreation = new Solution();
		Solution solResult = new Solution();
		solResult.add(new Result());
		solResult.add(new StartMelodicRR());
		solResult.add(new GetPitchRR());
		solResult.add(new BarNumber(0));
		solResult.add(new Pitch(1, new DegreeImpl(1)));
		sol.add(solResult);
		solBarInCreation.add(new BarInCreation());
		ChordImpl c1 = new ChordImpl();
		c1.set_degrees(new DegreeImpl(1));
		c1.set_position(0);
		c1.setDuration(Rythme.half);
		solBarInCreation.add(c1);	
		ChordImpl c2 = new ChordImpl();
		c2.set_degrees(new DegreeImpl(4));
		c2.set_position(1);
		c2.setDuration(Rythme.half);
		solBarInCreation.add(c2);
		solBarInCreation.add(new QuaterLeft(0));
		solBarInCreation.add(new BarNumber(0));
		solBarInCreation.add(2);
		sol.add(solBarInCreation);
		sol.add(new MoveToResultRR());
		sol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Apres : ");
				System.out.println(e.getSource());
			}
		});
		System.out.println("Avant : ");
		System.out.println(sol);
		sol.react();
	}

}