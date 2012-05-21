package org.chemicalmozart.utils;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Track;

import org.chemicalmozart.model.implementations.ChordImpl;

import fr.insa.rennes.info.chemical.backend.ChemicalException;

public class MusicWriter {
	private int DIVISION = 4;
	public enum NoteValues{DO, DOD, RE, RED, MI, FA, FAD, SOL, SOLD, LA, LAD, SI};
	/**
	 * Represents the midi value of the first degree From C2 to B2
	 */
	private int _degreeValue;
	private Track _chordTrack;
	private Sequence _sequence;
	private File _file;
	
	MusicWriter(int tempo, NoteValues note, String fileName) throws ChemicalException, InvalidMidiDataException{
		_file = new File(fileName);
		_degreeValue = DegresAssociation.getMidiNote(note);
		/*Create a new MIDI sequence with 1 ticks per beat  */
		_sequence = new Sequence(javax.sound.midi.Sequence.PPQ,DIVISION);
		/*Obtain a MIDI track from the sequence  */
		_chordTrack = _sequence.createTrack();

		/*General MIDI sysex -- turn on General MIDI sound set*/
		byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
		SysexMessage sm = new SysexMessage();
		sm.setMessage(b, 6);
		MidiEvent me = new MidiEvent(sm,(long)0);
		_chordTrack.add(me);
		/*set tempo (meta event)*/
		MetaMessage mt = new MetaMessage();
		byte[] bt = DegresAssociation.microsecondPerQuaterNote(60);
		mt.setMessage(0x51 ,bt, 3);
		me = new MidiEvent(mt,(long)0);
		_chordTrack.add(me);

		/*set track name (meta event)*/
		mt = new MetaMessage();
		String TrackName = new String("midifile track");
		mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
		me = new MidiEvent(mt,(long)0);
		_chordTrack.add(me);

		/*set omni on*/
		ShortMessage mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7D,0x00);
		me = new MidiEvent(mm,(long)0);
		_chordTrack.add(me);

		/*set poly on*/
		mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7F,0x00);
		me = new MidiEvent(mm,(long)0);
		_chordTrack.add(me);

		/*set instrument to Piano*/
		mm = new ShortMessage();
		mm.setMessage(0xC0, 0x00, 0x00);
		me = new MidiEvent(mm,(long)0);
		_chordTrack.add(me);
	}

	public void addChord(int deltaTime, ChordImpl c) throws ChemicalException, InvalidMidiDataException{
		int fond = DegresAssociation.getChordValue(_degreeValue, c);
		int tier, qte;
		long duration = DegresAssociation.getDuration(c);
		switch(DegresAssociation.getMode(c)){
		case MAJEUR:
			tier = fond + 4;
			break;
		default:
			tier = fond+3;
		}
		qte = fond + 7;
		/*Set the notes on*/
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON,fond,0x60);
		MidiEvent me = new MidiEvent(mm,(long)deltaTime);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON,tier,0x60);
		me = new MidiEvent(mm,(long)deltaTime);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON,qte,0x60);
		me = new MidiEvent(mm,(long)deltaTime);
		_chordTrack.add(me);
		
		/*Set the notes off*/
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF,fond,0x40);
		me = new MidiEvent(mm,(long)deltaTime+duration);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF,tier,0x40);
		me = new MidiEvent(mm,(long)deltaTime+duration);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF,qte,0x40);
		me = new MidiEvent(mm,(long)deltaTime+duration);
		_chordTrack.add(me);
	}
	
	public void writeFile() throws IOException{
		MidiSystem.write(_sequence,1,_file);
	}
}
