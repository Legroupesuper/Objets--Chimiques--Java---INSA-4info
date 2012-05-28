package org.chemicalmozart.littletests;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.reactionrules.CreateBarRR;
import org.chemicalmozart.model.implementations.reactionrules.GetPitchRR;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR1;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR2;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR3;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR4;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR5;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR6;
import org.chemicalmozart.model.implementations.reactionrules.StartMelodicRR;
import org.chemicalmozart.model.implementations.solutionindentification.Result;
import org.chemicalmozart.utils.GetWriterRR;
import org.chemicalmozart.utils.MusicWriter;
import org.chemicalmozart.utils.SolutionWriterRR;
import org.chemicalmozart.utils.StartToWriteRR;
import org.chemicalmozart.utils.MusicWriter.NoteValues;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.Utils;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class FromTheStartTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Solution mainSol = new Solution();
		mainSol.add(new CreateBarRR());
		mainSol.add(new QuaterPerBar(4));
		mainSol.add(5);
		mainSol.add(new BarNumber(0));
		mainSol.add(new DegreeImpl(1));
		mainSol.add(new HarmonicRR1());
		mainSol.add(new HarmonicRR2());
		mainSol.add(new HarmonicRR3());
		mainSol.add(new HarmonicRR4());
		mainSol.add(new HarmonicRR5());
		mainSol.add(new HarmonicRR6());
		Solution solResult = new Solution();
		solResult.add(new Result());
		solResult.add(new StartMelodicRR());
		solResult.add(new GetPitchRR());
		solResult.add(new BarNumber(0));
		solResult.add(new Pitch(1, new DegreeImpl(1)));
		mainSol.add(solResult);
		
		
		System.out.println("Avant : ");
		System.out.println(mainSol);
		mainSol.addInertEventListener(new InertEventListener() {
			
			public void isInert(InertEvent e) {
				System.out.println("Après : ");
				System.out.println(e.getSource());
				Solution s = (Solution) e.getSource();
				try {
					final MusicWriter writer = new MusicWriter(60, NoteValues.DO, "outputMidi.mid");
					s.addInertEventListener(new InertEventListener() {
						
						public void isInert(InertEvent e) {
							Utils.logger.info("On a fait ce truc de merde");
							Utils.logger.info(""+e.getSource());
							System.out.println("C'est fini!!!");
							try {
								writer.writeFile();
							} catch (IOException e1) {
								e1.printStackTrace();
							} catch (InvalidMidiDataException e1) {
								e1.printStackTrace();
							}
						}
					});
					s.add(writer);
					s.add(new SolutionWriterRR());
				} catch (ChemicalException e1) {
					e1.printStackTrace();
				} catch (InvalidMidiDataException e1) {
					e1.printStackTrace();
				}
			}
		});
		mainSol.react();
	}

}
