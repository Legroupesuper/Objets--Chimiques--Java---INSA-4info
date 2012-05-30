/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.utils;

import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.model.implementations.Note;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
import fr.insa.rennes.info.chemical.user.Dontreact;
import fr.insa.rennes.info.chemical.user.ReactionRule;

public class WriteMelodyRR implements ReactionRule{
	/**
	 * The writer used to create a midi file
	 */
	private MusicWriter _writer;
	public MusicWriter get_writer() {
		return _writer;
	}

	public void set_writer(MusicWriter _writer) {
		this._writer = _writer;
	}

	public Integer get_notePosition() {
		return _notePosition;
	}

	public void set_notePosition(Integer _notePosition) {
		this._notePosition = _notePosition;
	}

	public Note get_note() {
		return _note;
	}

	public void set_note(Note _note) {
		this._note = _note;
	}

	/**
	 * The position of the current note to add
	 */
	@Dontreact private Integer _notePosition;
	/**
	 * The current note
	 */
	private Note _note;
	
	public WriteMelodyRR(){
		this(0);
	}
	
	public WriteMelodyRR(int position){
		_notePosition = position;
	}
	
	public Object[] computeResult() {
		try {
			_writer.addNote(_note);
		} catch (ChemicalException e) {
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		_notePosition=_notePosition+1;
		return new Object[]{_writer, _note};
	}

	public boolean computeSelect(){
		return _note.get_position()==_notePosition;
	}

	public Multiplicity getMultiplicity() {
		return Multiplicity.INFINITY_SHOT;
	}

}