package org.chemicalmozart.littletests;

import org.chemicalmozart.model.implementations.BarNumber;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.QuaterPerBar;
import org.chemicalmozart.model.implementations.reactionrules.CreateBarRR;
import org.chemicalmozart.model.implementations.reactionrules.GetPitchRR;
import org.chemicalmozart.model.implementations.reactionrules.HarmonicRR1;
import org.chemicalmozart.model.implementations.reactionrules.StartMelodicRR;
import org.chemicalmozart.model.implementations.solutionindentification.Result;

import fr.insa.rennes.info.chemical.backend.Solution;
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
		mainSol.add(new BarNumber(0));
		mainSol.add(new DegreeImpl(1));
		mainSol.add(new HarmonicRR1());
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
			}
		});
		mainSol.react();
	}

}
