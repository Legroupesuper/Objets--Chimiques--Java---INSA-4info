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
		System.out.println("On écrit un accord");
		try {
			_writer.addChord(_chord);
		} catch (ChemicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_chordPosition = _chordPosition + 1;
		return new Object[]{_writer, _chord};
	}

	public boolean computeSelect() {
		System.out.println("On teste les accords avec "+_chord+" - "+_chordPosition);
		return _chord.get_position() == _chordPosition;
	}

	public Multiplicity getMultiplicity() {
		// TODO Auto-generated method stub
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
