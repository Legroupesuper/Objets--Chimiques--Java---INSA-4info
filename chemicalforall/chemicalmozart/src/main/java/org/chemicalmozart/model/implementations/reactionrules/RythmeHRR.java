/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChemicalLibSuper.

    ChemicalLibSuper is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChemicalLibSuper is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChemicalLibSuper.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations.reactionrules;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.QuaterLeft;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation;
import org.chemicalmozart.model.implementations.solutionindentification.BarInCreation.BarInCreationState;

import fr.insa.rennes.info.chemical.backend.Solution;
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

	private Integer _position;
	private DegreeImpl _degree;
	/**
	 * @return the _position
	 */
	public Integer get_position() {
		return _position;
	}

	/**
	 * @param _position the _position to set
	 */
	public void set_position(Integer _position) {
		this._position = _position;
	}

	/**
	 * @return the _degree
	 */
	public DegreeImpl get_degree() {
		return _degree;
	}

	/**
	 * @param _degree the _degree to set
	 */
	public void set_degree(DegreeImpl _degree) {
		this._degree = _degree;
	}

	/**
	 * @return the _quaterLeft
	 */
	public QuaterLeft get_quaterLeft() {
		return _quaterLeft;
	}

	/**
	 * @param _quaterLeft the _quaterLeft to set
	 */
	public void set_quaterLeft(QuaterLeft _quaterLeft) {
		this._quaterLeft = _quaterLeft;
	}

	/**
	 * @return the _bic
	 */
	public BarInCreation get_bic() {
		return _bic;
	}

	/**
	 * @param _bic the _bic to set
	 */
	public void set_bic(BarInCreation _bic) {
		this._bic = _bic;
	}

	private QuaterLeft _quaterLeft;
	private BarInCreation _bic;
	/**
	 * The computeResult must choose a duration for the ChordImpl that we are going to generate.
	 * It must be a random choice between 2 or 4 quaters. It must take in consideration the elapsed time in QuaterLeft.
	 * Once the duration is chosen, it creates a ChordImpl based on the current DegreeImpl. The position of the DegreeImpl must be set correctly
	 *  (value of the int before it has been incremented).
	 * @return The new ChordImpl well initialized @see {@link ChordImpl}, the int increased by one, the QuaterLeft decreased by the duration.
	 */
	public Object[] computeResult() {
		QuaterLeft qLeft = _quaterLeft;
		Rythme chosenDuration;
		QuaterLeft newQLeft;
		int position;
		if (qLeft.getValue() == 2){
			chosenDuration = Rythme.half;
			position = 1;
			newQLeft = new QuaterLeft(0);
		}
		else{
			position = 0;
			int choice = (int)(Math.random()*2);
			if (choice == 0){
				chosenDuration = Rythme.half;
				newQLeft = new QuaterLeft(2);
			}else{
				chosenDuration = Rythme.whole;
				newQLeft = new QuaterLeft(0);
			}
		}
		ChordImpl chordImpl = new ChordImpl();
		chordImpl.setDuration(chosenDuration);
		chordImpl.set_degrees(_degree);
		chordImpl.set_position(position);
		_bic.set_state(BarInCreationState.HARMONICRR);
		return new Object[]{chordImpl, position+1, newQLeft, _bic};
	}

	/**
	 * Must check that the BarInCreation object is in the good state and that all objects are present.
	 */
	public boolean computeSelect() {
		System.out.println("Compute select de RythmeRR : "+_bic.get_state().equals(BarInCreationState.RYTHMEHRR));
		return _bic.get_state().equals(BarInCreationState.RYTHMEHRR);
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}
}
