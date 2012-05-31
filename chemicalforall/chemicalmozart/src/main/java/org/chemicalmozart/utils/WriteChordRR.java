/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.utils;

import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.model.implementations.ChordImpl;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class WriteChordRR implements ReactionRule{
	/**
	 * The writer used to create a midi file
	 */
	private MusicWriter _writer;
	/**
	 * The position of the current note to add
	 */
	@Dontreact private Integer _chordPosition;
	/**
	 * The current note
	 */
	private ChordImpl _chord;

	public WriteChordRR(){
		this(0);
	}
	
	public WriteChordRR(int position){
		_chordPosition = position;
	}
	
	public Object[] computeResult() {
		try {
			_writer.addChord(_chord);
		} catch (ChemicalException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		_chordPosition = _chordPosition + 1;
		return new Object[]{_writer, _chord};
	}

	public boolean computeSelect() {
		return _chord.get_position() == _chordPosition;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

	public Integer get_chordPosition() {
		return _chordPosition;
	}

	public void set_chordPosition(Integer _chordPosition) {
		this._chordPosition = _chordPosition;
	}

	public ChordImpl get_chord() {
		return _chord;
	}

	public void set_chord(ChordImpl _chord) {
		this._chord = _chord;
	}

	public MusicWriter get_writer() {
		return _writer;
	}

	public void set_writer(MusicWriter _writer) {
		this._writer = _writer;
	}
}