package org.chemicalmozart.utils;

import org.chemicalmozart.model.implementations.ChordImpl;
import org.chemicalmozart.model.implementations.DegreeImpl;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Pitch;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.utils.MusicWriter.NoteValues;

import fr.insa.rennes.info.chemical.backend.ChemicalException;

public class DegresAssociation {
	public final static long MICROSECONDS_PER_MINUTE = 60000000;
	public enum Mode{MINEUR, MAJEUR};
	public static Mode getMode(ChordImpl c){
		if(c.get_degrees().get_value()==1)
			return Mode.MAJEUR;
		if(c.get_degrees().get_value()==2)
			return Mode.MINEUR;
		if(c.get_degrees().get_value()==3)
			return Mode.MINEUR;
		if(c.get_degrees().get_value()==4)
			return Mode.MAJEUR;
		if(c.get_degrees().get_value()==5)
			return Mode.MAJEUR;
		if(c.get_degrees().get_value()==6)
			return Mode.MINEUR;
		if(c.get_degrees().get_value()==7)
			return Mode.MINEUR;
		else
			return Mode.MAJEUR;
	}

	public static int getMidiNote(NoteValues note) throws ChemicalException{
		switch(note){
		case DO : 
			return 48;
		case DOD:
			return 49;
		case RE : 
			return 50;
		case RED:
			return 51;
		case MI : 
			return 52;
		case FA:
			return 53;
		case FAD : 
			return 54;
		case SOL:
			return 55;
		case SOLD : 
			return 56;
		case LA:
			return 57;
		case LAD : 
			return 58;
		case SI:
			return 59;
		default:
			throw new ChemicalException(note.toString()+" is not a valid value for the type NoteValues.");
		}
	}

	/**
	 * Returns the value of the tempo in microseconds per quarter-note. This is used
	 * by the midi format.
	 * @param bpm the tempo is bit per minute
	 * @return the ready to use array for the midi format
	 */
	public static byte[] microsecondPerQuaterNote(int bpm){
		long val = (MICROSECONDS_PER_MINUTE / bpm);
		byte vbottom = (byte) (val & 0xFF);
		byte vmiddle = (byte) ((val>>8)&0xFF);
		byte vtop = (byte)  ((val>>16)&0xFF);
		byte[] result = {vtop, vmiddle, vbottom};
		return result;
	}

	public static int getChordValue(int firstDegree, ChordImpl c) throws ChemicalException{
		int v = c.get_degrees().get_value();
		switch(v){
		case 1 : return firstDegree;
		case 2 : return firstDegree + 2;
		case 3 : return firstDegree + 4;
		case 4 : return firstDegree + 5;
		case 5 : return firstDegree + 7;
		case 6 : return firstDegree + 9;
		case 7 : return firstDegree + 11;
		default : throw new ChemicalException(v+ " is not a valid value for a degree");
		}
	}

	public static long getDuration(ChordImpl c) throws ChemicalException {
		switch(c.getDuration()){
		case eightdotted : return 3;
		case eighth : return 2;
		case half : return 8;
		case halfdotted : return 12;
		case quater : return 4;
		case quaterdotted : return 6;
		case sixteenth : return 1;
		case whole : return 16;
		default: throw new ChemicalException(c.getDuration()+ " is not a valid duration");
		}
	}
	
	public static long getNoteValue(int firstDegree, Note n) throws ChemicalException{
		int v;
		Pitch p = n.get_pitch();
		DegreeImpl deg = p.getDegree();
		switch(deg.get_value()){
		case 1 : v = firstDegree;break;
		case 2 : v = firstDegree + 2;break;
		case 3 : v = firstDegree + 4;break;
		case 4 : v = firstDegree + 5;break;
		case 5 : v = firstDegree + 7;break;
		case 6 : v = firstDegree + 9;break;
		case 7 : v = firstDegree + 11;break;
		default : throw new ChemicalException(n.get_pitch().getDegree().get_value()+ " is not a valid value for a degree");
		}
		v = v + 12*n.get_pitch().getOctave();
		return v;
	}

	public static long getDuration(Rythme rythm) throws ChemicalException {
		switch(rythm){
		case eightdotted : return 3;
		case eighth : return 2;
		case half : return 8;
		case halfdotted : return 12;
		case quater : return 4;
		case quaterdotted : return 6;
		case sixteenth : return 1;
		case whole : return 16;
		default: throw new ChemicalException(rythm+ " is not a valid duration");
		}
	}
}
