package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.reactionrules.StartMelodicRR;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class StartMelodicRRTest {
	public static void main(String[] args) {
		Solution mainSol = new Solution();
		mainSol.add(new BarNumber(0));
		mainSol.add(new Pitch(2, new DegreeImpl(1)));
		Solution mesure1 = new Solution();
		mesure1.add(2);
		
		ChordImpl c1 = new ChordImpl();
		c1.set_degrees(new DegreeImpl(1));
		c1.set_position(0);
		c1.setDuration(Rythme.half);
		mesure1.add(c1);
		
		ChordImpl c2 = new ChordImpl();
		c2.set_degrees(new DegreeImpl(4));
		c2.set_position(1);
		c2.setDuration(Rythme.half);
		mesure1.add(c2);
		mesure1.add(new BarNumber(0));
		mesure1.add(new BarInCreation());
		
		mainSol.add(new StartMelodicRR());
		mainSol.add(mesure1);
		mainSol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println(e.getSource());
			}
		});
		mainSol.react();
	}
}
