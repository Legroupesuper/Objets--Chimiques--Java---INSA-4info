package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;

import fr.insa.rennes.info.chemical.backend.SubSolution;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.ReactionRule;



/**
 * This ReactionRule is used to generate the harmonic part of a measure.<br />
 * Starting from a DegreeImpl, it generates a ChordImpl. <br />
 * A ChordImpl is quite similar to a DegreeImpl but it also contains the notion of duration.<br />
 * Here is how it works, it takes the following reagents.<br />
 * <ul>
 * 	<li>Subsolution : (the order must be respected for the tests)
 * 		<ul>
 * 			<li>BarInCreation : An object that identify the current bar in creation</li>
 * 			<li>int : Which is the current number of ChordImpl in the subsolution</li>
 * 			<li>DegreeImpl : The current DegreeImpl</li>
 * 			<li>QuaterLeft : The number of quaters still free for the new ChordImpl</li>
 * 		</ul>
 *  </li>
 * </ul><br /><br />
 * It puts back in the subsolution :<br />
 * <ul>
 * 			<li>a int</li>
 * 			<li>ChordImpl</li>
 * 			<li>QuaterLeft</li>
 * 			<li>BarInCreation</li>
 * 		</ul>
 */
public class RythmeHRR implements ReactionRule{

	private SubSolution<SubSolutionElements> _sol;

	/**
	 * The constructor is used to fill the types we want to match in the subsolution
	 */
	public RythmeHRR() {
		super();
		_sol = new SubSolution<SubSolutionElements>(new SubSolutionElements());
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(BarInCreation.class);
		l.add(Integer.class);
		l.add(DegreeImpl.class);
		l.add(QuaterLeft.class);
		_sol.setTypeList(l);
	}

	/**
<<<<<<< HEAD
	 * The compute result must choose a duration for the ChordImpl that we are going to generate.
	 * It must be a random choice between 2 or 4 quaters. It must take in consideration the elapsed time in QuaterLeft. The chosen rhythm must be setted in
	 * the returned ChordImpl.
	 * Once the duration is chosen, it creates a ChordImpl based on the current DegreeImpl. The position of the DegreeImpl must be setted correctly
	 *  (value of the int before it has been incremented) and it's duration must be set to the correct value.
=======
	 * The computeResult must choose a duration for the ChordImpl that we are going to generate.
	 * It must be a random choice between 2 or 4 quaters. It must take in consideration the elapsed time in QuaterLeft.
	 * Once the duration is chosen, it creates a ChordImpl based on the current DegreeImpl. The position of the DegreeImpl must be set correctly
	 *  (value of the int before it has been incremented).
>>>>>>> branch 'master' of https://candreolli@github.com/antoineguay/Objets--Chimiques--Java---INSA-4info.git
	 * @return The new ChordImpl well initialized @see {@link ChordImpl}, the int increased by one, the QuaterLeft decreased by the duration.
	 */
	public Object[] computeResult() {
		QuaterLeft qLeft = (QuaterLeft)_sol.getElements().get(3);
		int chosenDuration;
		int position;
		if (qLeft.getValue() == 2){
			chosenDuration = 2;
			position = 1;
		}
		else{
			position = 0;
			int choice = (int)Math.random()*2;
			if (choice == 0){
				chosenDuration = 2;
			}else{
				chosenDuration = 4;
			}
		}
		ChordImpl chordImpl = new ChordImpl();
		chordImpl.setDuration(chosenDuration);
		QuaterLeft newQLeft = new QuaterLeft(4-chosenDuration);
		chordImpl.set_degrees((DegreeImpl)_sol.getElements().get(2));
		chordImpl.set_position(position);
		return new Object[]{chordImpl, position+1, newQLeft, (BarInCreation)_sol.getElements().get(0)};
	}

	/**
	 * Must check that the BarInCreation object is in the good state and that all objects are present.
	 */
	public boolean computeSelect() {
		List<Object> solElements = _sol.getElements();
		boolean barInCreationPresent = false;
		boolean barInCreationInGoodState = false;
		boolean intPresent = false;
		boolean QuaterLeftPresent = false;
		boolean DegreeImplPresent = false;
		if (solElements != null){
			if (solElements.size()>=4){
				barInCreationPresent = solElements.get(0) instanceof BarInCreation;
				if(barInCreationPresent){
					barInCreationInGoodState = 
							((BarInCreation)solElements.get(0)).get_state().equals(BarInCreation.BarInCreationState.RYTHMEHRR);
				}
				intPresent = solElements.get(1) instanceof Integer;
				DegreeImplPresent = solElements.get(2) instanceof DegreeImpl;
				QuaterLeftPresent = solElements.get(3) instanceof QuaterLeft;
			}
		}
			return (barInCreationInGoodState && intPresent && DegreeImplPresent && QuaterLeftPresent);
	}

	public Multiplicity getMultiplicity() {
		return null;
	}

	/**
	 * @return the sol
	 */
	public SubSolution<SubSolutionElements> getSol() {
		return this._sol;
	}
	/**
	 * @param sol the sol to set
	 */
	public void setSol(SubSolution<SubSolutionElements> sol) {
		this._sol = sol;
	}



}
