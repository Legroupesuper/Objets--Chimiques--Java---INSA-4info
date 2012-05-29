/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;

import fr.insa.rennes.info.chemical.backend.ChemicalException;
/**
 * This class is used to export the track into a midi file
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 *
 */
public class MusicWriter {
	private static final long END = 1;
	/**
	 * The number of divisions in one beat.
	 */
	private int DIVISION = 4;
	/**
	 * Represents the note french notation
	 */
	public enum NoteValues{DO, DOD, RE, RED, MI, FA, FAD, SOL, SOLD, LA, LAD, SI};
	/**
	 * Represents the midi value of the first degree From C2 to B2
	 */
	private int _degreeValue;
	/**
	 * The track used to play the chords
	 */
	private Track _chordTrack;
	/**
	 * The track used to play the melody
	 */
	private Track _melodicTrack;
	/**
	 * The midi sequence
	 */
	private Sequence _sequence;
	/**
	 * The file which we are exporting the track into.
	 */
	private File _file;
	/**
	 * The delta time of the next chord
	 */
	private int _chordDelta;
	/**
	 * The delta time of the next melody note
	 */	
	private int _melodyDelta;
	/*
	 * The instrument used for the melody
	 */
	private int _melodicInstrument;
	
	/*
	 * The instrument used for the chords
	 */
	private int _chordsInstrument;
	/**
	 * Default constructor
	 * @param tempo The tempo of the track
	 * @param note The note used as a first degree
	 * @param fileName The file name
	 * @throws ChemicalException
	 * @throws InvalidMidiDataException
	 */
	public MusicWriter(int tempo, NoteValues note, String fileName, int melodicInstr, int chordInstr) throws ChemicalException, InvalidMidiDataException{
		_file = new File(fileName);
		_melodicInstrument = melodicInstr;
		_chordsInstrument = chordInstr;
		_degreeValue = DegresAssociation.getMidiNote(note);
		/*Create a new MIDI sequence with 1 ticks per beat  */
		_sequence = new Sequence(javax.sound.midi.Sequence.PPQ,DIVISION);
		
		/*Obtain a MIDI track from the sequence  */
		_chordTrack = _sequence.createTrack();
		_melodicTrack = _sequence.createTrack();
		_chordDelta = 0;
		_melodyDelta = 0;
		/*General MIDI sysex -- turn on General MIDI sound set*/
		byte[] b = {(byte)0xF0, 0x7E, 0x7F, 0x09, 0x01, (byte)0xF7};
		SysexMessage sm = new SysexMessage();
		sm.setMessage(b, 6);
		MidiEvent me = new MidiEvent(sm,(long)0);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		/*set tempo (meta event)*/
		MetaMessage mt = new MetaMessage();
		byte[] bt = DegresAssociation.microsecondPerQuaterNote(60);
		mt.setMessage(0x51 ,bt, 3);
		me = new MidiEvent(mt,(long)0);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		/*set track name (meta event)*/
		mt = new MetaMessage();
		String TrackName = new String("midifile track");
		mt.setMessage(0x03 ,TrackName.getBytes(), TrackName.length());
		me = new MidiEvent(mt,(long)0);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		/*set omni on*/
		ShortMessage mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7D,0x00);
		me = new MidiEvent(mm,(long)0);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		/*set poly on*/
		mm = new ShortMessage();
		mm.setMessage(0xB0, 0x7F,0x00);
		me = new MidiEvent(mm,(long)0);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		
		/*set instruments*/
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.PROGRAM_CHANGE, 1, _chordsInstrument, 0);
		me = new MidiEvent(mm,(long)_chordDelta);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.PROGRAM_CHANGE, 2, _melodicInstrument, 0);
		me = new MidiEvent(mm,(long)_chordDelta);
		_melodicTrack.add(me);
		
	}

	/**
	 * Add a chord to the current track
	 * @param deltaTime The delta time in sixteen from the start
	 * @param c The chord to add
	 * @throws ChemicalException
	 * @throws InvalidMidiDataException
	 */
	public void addChord(ChordImpl c) throws ChemicalException, InvalidMidiDataException{
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
		System.out.println("fond : "+fond);
		System.out.println("tierce : "+tier);
		System.out.println("quinte : "+qte);
		 
		/*Set the notes on*/
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON, 1, fond, 0x60);
		MidiEvent me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON, 1, tier, 0x60);
		me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON, 1, qte, 0x60);
		me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
		
		_chordDelta+=duration;
		/*Set the notes off*/
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF, 1, fond, 0x40);
		me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF, 1, tier, 0x40);
		me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
		
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF, 1, qte, 0x40);
		me = new MidiEvent(mm,(long)_chordDelta+DIVISION);
		_chordTrack.add(me);
	}
	
	/**
	 * Adds a melodic note into the track
	 * @param deltaTime The delta time in sixteen from the start
	 * @param n The current note
	 * @throws ChemicalException
	 * @throws InvalidMidiDataException
	 */
	public void addNote(Note n) throws ChemicalException, InvalidMidiDataException{
		Rythme rythm= n.get_rythme();
		long duration = DegresAssociation.getDuration(rythm);
		long note = DegresAssociation.getNoteValue(_degreeValue, n);
		
		ShortMessage mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_ON, 2, (int) note,0x60);
		MidiEvent me = new MidiEvent(mm,(long)_melodyDelta+DIVISION);
		_melodicTrack.add(me);
		_melodyDelta+=duration;
		mm = new ShortMessage();
		mm.setMessage(ShortMessage.NOTE_OFF, 2, (int) note,0x40);
		me = new MidiEvent(mm,(long)_melodyDelta+DIVISION);
		_melodicTrack.add(me);
	}
	
	/**
	 * Writes the file
	 * @throws IOException
	 * @throws InvalidMidiDataException
	 */
	public void writeFile() throws IOException, InvalidMidiDataException{
		MetaMessage mt = new MetaMessage();
        byte[] bet = {}; // empty array
		mt.setMessage(0x2F,bet,0);
		MidiEvent me = new MidiEvent(mt, (long)END);
		_chordTrack.add(me);
		_melodicTrack.add(me);
		MidiSystem.write(_sequence,1,_file);
	}
	
}
