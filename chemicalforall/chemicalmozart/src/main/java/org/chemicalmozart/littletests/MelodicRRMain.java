package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.reactionrules.MelodicRR;
import org.chemicalmozart.model.implementations.rythme.EEQQQ4;
import org.chemicalmozart.model.interfaces.RythmPattern;
import org.chemicalmozart.model.interfaces.factory.MozartSolutionFactory;

import fr.insa.rennes.info.chemical.backend.Solution;

public class MelodicRRMain {
	public static void main(String[] args) {
		MozartSolutionFactoryImpl factory = new MozartSolutionFactoryImpl();
		MelodicRR rr = new MelodicRR();
		rr.set_activated(true);
		rr.set_max(5);
		
		Solution s = new Solution();
		s.add(rr);
		RythmPattern rp = new EEQQQ4();
		s.addAll(rp.getListNotes());
		
	}
}
