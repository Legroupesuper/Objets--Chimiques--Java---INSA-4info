package org.chemicalmozart.viewV2;

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
import org.chemicalmozart.utils.MusicWriter;
import org.chemicalmozart.utils.SolutionWriterRR;
import org.chemicalmozart.utils.MusicWriter.NoteValues;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.backend.Solution;
import fr.insa.rennes.info.chemical.backend.Utils;
import fr.insa.rennes.info.chemical.user.InertEvent;
import fr.insa.rennes.info.chemical.user.InertEventListener;

public class ModelBuilder {
	
	private Solution mainSol;
	
	public ModelBuilder(int mesureNumber){
		mainSol = new Solution();
		mainSol.add(new CreateBarRR());
		mainSol.add(new QuaterPerBar(4));
		mainSol.add(mesureNumber);
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
		
	}
	
	public void launchReaction(){
		mainSol.react();
	}
	
	public Solution getMainSol(){
		return mainSol;
	}
	
	
	
}
