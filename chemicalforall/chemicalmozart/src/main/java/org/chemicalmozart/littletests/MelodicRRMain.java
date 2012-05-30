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

import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.reactionrules.MelodicRR;
import org.chemicalmozart.model.implementations.rythme.EEQQQ4;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class MelodicRRMain {
	public static void main(String[] args) {
		MozartSolutionFactoryImpl factory = new MozartSolutionFactoryImpl();
		System.out.println("avant : ");
		
		MelodicRR rr = new MelodicRR();
		rr.set_activated(true);
		rr.set_max(5);
		rr.set_melodicNumber(0);
		final Solution s = new Solution();
		s.add(rr);
		s.add(new Pitch(1, new DegreeImpl(1)));
		EEQQQ4 rp = new EEQQQ4();
		List<Note> ln = rp.getListNotes();
		ChordImpl c = new ChordImpl();
		c.set_degrees(new DegreeImpl(1));
		c.set_position(0);
		
		for(Note n : ln){
			n.set_chord(c);
		}
		s.addAll(ln);
		
		s.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Après : ");
				System.out.println(s);
			}
		});
		System.out.println(s);
		s.react();
	}
}