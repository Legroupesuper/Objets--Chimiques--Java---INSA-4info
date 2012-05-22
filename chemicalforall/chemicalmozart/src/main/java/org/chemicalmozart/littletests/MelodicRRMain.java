package org.chemicalmozart.littletests;

import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.reactionrules.MelodicRR;
import org.chemicalmozart.model.implementations.rythme.EEQQQ4;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;

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
