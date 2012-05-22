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
import org.chemicalmozart.model.implementations.rythme.RythmPattern;
import org.chemicalmozart.model.implementations.solutionindentification.RythmePull;
import fr.insa.rennes.info.chemical.backend.SubSolutionElements;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;




/**
 * This ReactionRule is used to select a rhythmic pattern in a SubSolution identified by a RhythmePull object. It's a one-shot rule.<br />
 * It takes the following parameters : (in this order)
 * <ul>
 * 	<li>SubSolution "RythmePull"
 * 		<ul>
 * 			<li>RythmePull : Object that identify the solution</li>
 * 			<li>org.chemicalmozart.model.interfaces.Rythme : A rhythm</li>
 * 		</ul>
 * </li>
 * <li>ChordImpl : the current chordImpl</li>
 * <li>Integer : the total number of ChordImpl</li>
 * <li>MelodicRR : a MelodicRR</li>
 * </ul>
 */
public class RythmicRR implements ReactionRule{
	/**
	 * The solution which contains the rhythmic patterns and identified by a RythmePull object
	 */
//	private SubSolution<SubSolutionElements> _rythmeSolution;
//	public SubSolution<SubSolutionElements> get_rythmeSolution() {
//		return _rythmeSolution;
//	}
//
//	public void set_rythmeSolution(SubSolution<SubSolutionElements> _rythmeSolution) {
//		this._rythmeSolution = _rythmeSolution;
//	}

	/**
	 * The current selected ChordImpl
	 */
//	private ChordImpl _chordImpl;
	/**
	 * The solution which contains the patterns
	 */
	//private Solution _solution;
	/**
	 * The current ChordNumber.<br />
	 * This parameter doesn't react and the same instance will be used for a same RythmicRR. The constructor must initialize it to 0.
	 */
//	@Dontreact private int _chordNumber;
	/**
	 * The total number of ChordImpl in the current bar
	 */
	private Integer _num;
	/**
	 * This number represents the current number of note that already exists in the Bar.<br />
	 * It will be used to set the position of every new note in the bar.<br />
	 * This parameter doesn't react. The constructor must initialize it to 0.
	 */
//	@Dontreact
//	private int _max;

	/**
	 * It starts to become META ! :)<br />
	 * _melodicRR contains a parameter that is set to false (not activated). This ReactionRule (melodicRR) can't react as long it has not been set to activated.
	 * <br />
	 * Each time the rythmicRR react, it increases the max parameter of the MelodicRR.
	 */
	//private MelodicRR _melodicRR;

	/**
	 * Constructor
	 * It initialize the subsolution, and _num and _max (parameters that don't react) to 0
	 */
	public RythmicRR() {
		super();
		SubSolutionElements elts = new SubSolutionElements();
		List<Class<? extends Object>> l = new ArrayList<Class<? extends Object>>();
		l.add(RythmePull.class);
		l.add(RythmPattern.class);
		elts.setTypeList(l);
//		_rythmeSolution = new SubSolution<SubSolutionElements>(elts);
//		this._chordNumber = 0;
//		this._max = 0;
	}

	/**
	 * This method will be processed in 2 steps whereas the position of the ChordImpl is smaller than _num or not.
	 * <br /><br />
	 * If the position of the ChordImpl is smaller than _num-1, it must return :<br />
	 * <ul>
	 * 	<li>_num</li>
	 * 	<li>_chordImpl</li>
	 * 	<li>_rythmeSolution without the chosen element back into it</li>
	 * 	<li>_melodicRR with his max parameter increased by the number of notes returned by the chosen element in _rythmeSolution</li>
	 * 	<li>this</li>
	 * </ul>
	 * For each note returned by the chosen element in _rythmeSolution, it needs to increase its 
	 * position by the value of _max and set its chordimpl to the current chordimpl.
	 * Once it's done, it must also set properly _chordNumber (increment by 1) and _max (increment by the number of 
	 * notes returned by the chosen element in _rythmeSolution).
	 * <br />
	 * <br />
	 * If the position of the ChordImpl is equal to _num-1, it must return :<br />
	 * <ul>
	 * 	<li>_num</li>
	 * 	<li>_chordImpl</li>
	 *  <li>The notes setted in the chosen RythmPattern</li>
	 * 	<li>_melodicRR with his max parameter increased by the number of notes returned by the chosen element in _rythmeSolution and <b>activated</b> set to true</li>
	 * </ul>
	 * For each note returned by the chosen element in _rythmeSolution, it needs to increase the 
	 * position by the value of _max and set its chordimpl to the current chordimpl.
	 * Once it's done, it must also set properly _chordNumber (increment by 1) and _max (increment by the number of 
	 * notes returned by the chosen element in _rythmeSolution).
	 */
	public Object[] computeResult() {
//		Object[] result = null;
//		RythmPattern chosenRythm = (RythmPattern) this._rythmeSolution.getElements().get(1);
//		List<Note> listNotes = chosenRythm.getListNotes();
//		int nbNotesInChosenRythm = listNotes.size();
//		int chordImplPosition = this._chordImpl.get_position();
//		int melodicRRmax = this._melodicRR.get_max();
//
//		/*
//		 * If the position of the ChordImpl is smaller than _num
//		 */
//		if( chordImplPosition < this._num-1){
//			this._melodicRR.set_max(melodicRRmax + nbNotesInChosenRythm);
//			this._chordImpl.set_position(this._chordImpl.get_position()+this._max);
//			this._chordNumber++;
//			
//			for(Note n : listNotes){
//				n.set_position(n.get_position()+_max);
//			}
//			
//			this._max += nbNotesInChosenRythm;
//			List<Object> lres = new ArrayList<Object>();
//			lres.addAll(listNotes);
//			lres.add(this._num);
//			lres.add(this._chordImpl);
//			lres.add(this._melodicRR);
//			lres.add(_solution);
//			lres.add(this);
//			result = lres.toArray();
//		}else{
//			this._melodicRR.set_max(melodicRRmax + nbNotesInChosenRythm);
//			this._melodicRR.set_activated(true);
//			this._chordImpl.set_position(this._chordImpl.get_position()+this._max);
//			this._chordNumber++;
//			for(Note n : listNotes){
//				n.set_position(n.get_position()+_max);
//			}
//			this._max += nbNotesInChosenRythm;
//			List<Object> lres = new ArrayList<Object>();
//			lres.addAll(listNotes);
//			lres.add(this._num);
//			lres.add(this._chordImpl);
//			lres.add(this._melodicRR);
//			result = lres.toArray();
//		}
		
//		return result;
//		_rythmeSolution.getSolution().add(new RythmePull());
		System.err.println("On met le pâté");
		return  new Object[]{_num+1};
	}
 

	/**
	 * Must check that _chordNumber correspond to the position of the _chordImpl.
	 * It must also check that the selected subsolution rhythm has the same duration than _chordImpl.
	 */
	public boolean computeSelect() {
		System.err.println("On est là : "+_num);
//		boolean validPosition = _chordImpl.get_position() == _chordNumber;
//		boolean sameDuration = ((RythmPattern)_rythmeSolution.getElements().get(1)).getDuration() == _chordImpl.getDuration();
//		boolean containsPattern = false;
//		for(Object o : _solution){
//			if(o instanceof RythmePull)
//				containsPattern = true;
//		}
//		System.err.println("Compute select");
//		System.err.println("validPosition : "+validPosition+" sameDuration : "+sameDuration+" containsPattern : "+containsPattern);
//		return validPosition && sameDuration && containsPattern;
		return true;
	}
	

//	public ChordImpl get_chordImpl() {
//		return _chordImpl;
//	}
//
//	public void set_chordImpl(ChordImpl _chordImpl) {
//		this._chordImpl = _chordImpl;
//	}
//
//	public Solution get_solution() {
//		return _solution;
//	}
//
//	public void set_solution(Solution _solution) {
//		this._solution = _solution;
//	}


//	public int get_chordNumber() {
//		return _chordNumber;
//	}
//
//	public void set_chordNumber(int _chordNumber) {
//		this._chordNumber = _chordNumber;
//	}

	public Integer get_num() {
		return _num;
	}

	public void set_num(Integer _num) {
		this._num = _num;
	}

//	public int get_max() {
//		return _max;
//	}
//
//	public void set_max(int _max) {
//		this._max = _max;
//	}

//	public MelodicRR get_melodicRR() {
//		return _melodicRR;
//	}
//
//	public void set_melodicRR(MelodicRR _melodicRR) {
//		this._melodicRR = _melodicRR;
//	}

	/**
	 * One-shot
	 */
	public Multiplicity getMultiplicity() {
		return Multiplicity.ONE_SHOT;
	}

	

}
