package org.chemicalmozart.model.implementations.rythme;
import java.util.ArrayList;
import java.util.List;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.Note.Type;

public class QDE2{
	public Rythme getDuration() {
		return org.chemicalmozart.model.implementations.Rythme.half;
	}

	public List<Note> getListNotes() {
		List<Note> l = new ArrayList<Note>();

		Note n0 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quaterdotted);
		l.add(n0);
		Note n1 = new Note(1, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n1);
		return l;
	}
}