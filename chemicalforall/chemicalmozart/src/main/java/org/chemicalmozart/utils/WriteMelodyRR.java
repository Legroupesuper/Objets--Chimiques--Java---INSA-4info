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
