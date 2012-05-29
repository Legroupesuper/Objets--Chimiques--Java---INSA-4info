package org.chemicalmozart.model.implementations.rythme;
import java.util.ArrayList;
import java.util.List;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.Note.Type;

public class QQQQ4{
	public Rythme getDuration() {
		return org.chemicalmozart.model.implementations.Rythme.whole;
	}

	public List<Note> getListNotes() {
		List<Note> l = new ArrayList<Note>();

		Note n0 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		l.add(n0);
		Note n1 = new Note(1, Type.NONE, org.chemicalmozart.model.implementations.Rythme.quater);
		l.add(n1);
		Note n2 = new Note(2, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		l.add(n2);
		Note n3 = new Note(3, Type.NONE, org.chemicalmozart.model.implementations.Rythme.quater);
		l.add(n3);
		return l;
	}
}