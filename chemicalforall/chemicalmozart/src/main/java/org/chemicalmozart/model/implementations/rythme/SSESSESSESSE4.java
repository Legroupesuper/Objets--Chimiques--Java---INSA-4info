package org.chemicalmozart.model.implementations.rythme;
import java.util.ArrayList;
import java.util.List;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.Note.Type;

public class SSESSESSESSE4{
	public Rythme getDuration() {
		return org.chemicalmozart.model.implementations.Rythme.whole;
	}

	public List<Note> getListNotes() {
		List<Note> l = new ArrayList<Note>();

		Note n0 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n0);
		Note n1 = new Note(1, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n1);
		Note n2 = new Note(2, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n2);
		Note n3 = new Note(3, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n3);
		Note n4 = new Note(4, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n4);
		Note n5 = new Note(5, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n5);
		Note n6 = new Note(6, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n6);
		Note n7 = new Note(7, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n7);
		Note n8 = new Note(8, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n8);
		Note n9 = new Note(9, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n9);
		Note n10 = new Note(10, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n10);
		Note n11 = new Note(11, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n11);
		return l;
	}
}