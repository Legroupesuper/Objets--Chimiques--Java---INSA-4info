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
		Solution sol = new Solution();
		sol.add(pull);
		//System.err.println("is inert ?"+pull.is_inert());
		sol.add(new RythmicRR());
		sol.add(2);
		MelodicRR m = new MelodicRR();
		m.set_activated(false);
		sol.add(m);
		ChordImpl c1 = new ChordImpl();
		c1.set_degrees(new DegreeImpl(1));
		c1.set_position(0);
		c1.setDuration(Rythme.half);
		
		ChordImpl c2 = new ChordImpl();
		c2.set_degrees(new DegreeImpl(4));
		c2.set_position(1);
		c2.setDuration(Rythme.half);
		
		sol.add(c1);sol.add(c2);
		System.out.println("Solution avant : ");
		System.out.println(sol);
		
		sol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Solution après : "+((Solution)e.getSource()).is_inert());
				System.out.println(((Solution)e.getSource()));
//				while(true){
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					System.out.println(((Solution)e.getSource()));
//				}
			}
		});
		sol.react();
	}

}
