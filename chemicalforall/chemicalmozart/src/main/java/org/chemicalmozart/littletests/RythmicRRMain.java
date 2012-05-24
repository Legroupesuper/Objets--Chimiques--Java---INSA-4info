package org.chemicalmozart.littletests;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.factory.MozartSolutionFactoryImpl;
import org.chemicalmozart.model.implementations.reactionrules.MelodicRR;
import org.chemicalmozart.model.implementations.reactionrules.RythmicRR;
import org.chemicalmozart.utils.MusicWriter;
import org.chemicalmozart.utils.MusicWriter.NoteValues;
import org.chemicalmozart.utils.WriteChordRR;
import org.chemicalmozart.utils.WriteMelodyRR;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class RythmicRRMain {

	/**
	 * @param args
	 * @throws InvalidMidiDataException 
	 * @throws ChemicalException 
	 */
	public static void main(String[] args) throws ChemicalException, InvalidMidiDataException {
		MozartSolutionFactoryImpl factory = new MozartSolutionFactoryImpl();
		final MusicWriter writer = new MusicWriter(60, NoteValues.DO, "FichierOut.mid");
		Solution pull = factory.createRythmicPull();
		Solution sol = new Solution();
		sol.add(pull);
		//System.err.println("is inert ?"+pull.is_inert());
		sol.add(new RythmicRR());
		sol.add(4);
		MelodicRR m = new MelodicRR();
		m.set_activated(false);
		sol.add(m);
		ChordImpl c1 = new ChordImpl();
		c1.set_degrees(new DegreeImpl(1));
		c1.set_position(0);
		c1.setDuration(Rythme.half);
		sol.add(c1);
		
		ChordImpl c2 = new ChordImpl();
		c2.set_degrees(new DegreeImpl(4));
		c2.set_position(1);
		c2.setDuration(Rythme.half);
		sol.add(c2);
		
		ChordImpl c3 = new ChordImpl();
		c3.set_degrees(new DegreeImpl(5));
		c3.set_position(2);
		c3.setDuration(Rythme.half);
		sol.add(c3);
		
		ChordImpl c4 = new ChordImpl();
		c4.set_degrees(new DegreeImpl(1));
		c4.set_position(3);
		c4.setDuration(Rythme.half);
		sol.add(c4);
		
		Pitch p = new Pitch(3, new DegreeImpl(1));
		sol.add(p);
		System.out.println("Solution avant : ");
		System.out.println(sol);
		
		sol.addInertEventListener(new InertEventListener(){
			
			public void isInert(InertEvent e) {
				//System.out.println("Solution après : "+((Solution)e.getSource()).is_inert());
				Solution s = (Solution)e.getSource();
				System.out.println((s));
				s.add(writer);
				s.add(new WriteMelodyRR(0));
				s.add(new WriteChordRR(0));
//				System.out.println("On passe ici");
//				s.react();
				s.addInertEventListener(new InertEventListener() {
					
					public void isInert(InertEvent e) {
						try {
							writer.writeFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidMidiDataException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});
				s.react();
			}
		});
		sol.react();
	}

}
