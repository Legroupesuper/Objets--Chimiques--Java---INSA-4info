package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.reactionrules.GetPitchRR;
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
		mainSol.add(new GetPitchRR());
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
		
		
		Solution mesure2 = new Solution();
		mesure2.add(2);
		ChordImpl c3 = new ChordImpl();
		c3.set_degrees(new DegreeImpl(5));
		c3.set_position(0);
		c3.setDuration(Rythme.half);
		mesure2.add(c3);	
		ChordImpl c4 = new ChordImpl();
		c4.set_degrees(new DegreeImpl(1));
		c4.set_position(1);
		c4.setDuration(Rythme.half);
		mesure2.add(c4);
		mesure2.add(new BarNumber(1));
		mesure2.add(new BarInCreation());
		
		mainSol.add(mesure2);
		
		mainSol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Apres : ");
				System.out.println(e.getSource());
			}
		});
		System.out.println("Avant : ");
		System.out.println(mainSol);
		mainSol.react();
	}
}
