package org.chemicalmozart;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.utils.MusicWriter;
import org.chemicalmozart.utils.MusicWriter.NoteValues;

import fr.insa.rennes.info.chemical.backend.ChemicalException;

public class MainMidi {
	public static void main(String[] args) {
		try {
			MusicWriter writer = new MusicWriter(60, NoteValues.DO, "monFichier.mid");
			ChordImpl cim = new ChordImpl();
			cim.setDuration(Rythme.half);
			cim.set_position(0);
			DegreeImpl d = new DegreeImpl(1);
			cim.set_degrees(d);
			writer.addChord(4, cim);
			
			cim = new ChordImpl();
			cim.setDuration(Rythme.half);
			cim.set_position(0);
			d = new DegreeImpl(4);
			cim.set_degrees(d);
			writer.addChord(12, cim);
			
			cim = new ChordImpl();
			cim.setDuration(Rythme.half);
			cim.set_position(0);
			d = new DegreeImpl(5);
			cim.set_degrees(d);
			writer.addChord(20, cim);
			
			cim = new ChordImpl();
			cim.setDuration(Rythme.half);
			cim.set_position(0);
			d = new DegreeImpl(5);
			cim.set_degrees(d);
			writer.addChord(28, cim);
			
			cim = new ChordImpl();
			cim.setDuration(Rythme.half);
			cim.set_position(0);
			d = new DegreeImpl(1);
			cim.set_degrees(d);
			writer.addChord(36, cim);
			
			
			Note n = new Note();
			Pitch pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(1));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(4, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(2));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(8, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(3));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(12, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(4));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(16, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(5));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(20, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(4));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(24, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(3));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(28, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(2));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(32, n);
			
			n = new Note();
			pitch = new Pitch();
			pitch.setDegree(new DegreeImpl(1));
			pitch.setOctave(3);
			n.set_pitch(pitch);
			n.set_rythme(Rythme.quater);
			writer.addNote(36, n);
			try {
				writer.writeFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ChemicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidMidiDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
