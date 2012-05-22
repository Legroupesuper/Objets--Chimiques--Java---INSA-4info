package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.reactionrules.MelodicRR;
import org.chemicalmozart.model.implementations.reactionrules.RythmicRR;

import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class RythmicRRMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MozartSolutionFactoryImpl factory = new MozartSolutionFactoryImpl();
		Solution pull = factory.createRythmicPull();
		final Solution sol = new Solution();
		sol.add(pull);
		sol.add(new RythmicRR());
		sol.add((Integer)2);
		//sol.add(new MelodicRR());
//		ChordImpl c1 = new ChordImpl();
//		c1.set_degrees(new DegreeImpl(1));
//		c1.set_position(0);
//		c1.setDuration(Rythme.half);
//		
//		ChordImpl c2 = new ChordImpl();
//		c2.set_degrees(new DegreeImpl(4));
//		c2.set_position(1);
//		c2.setDuration(Rythme.half);
//		
//		sol.add(c1);sol.add(c2);
		System.out.println("Solution avant : ");
		System.out.println(sol);
		
		sol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Solution après : "+sol.is_inert());
				System.out.println(sol);
			}
		});
		System.out.println("test");
		sol.react();
	}

}
