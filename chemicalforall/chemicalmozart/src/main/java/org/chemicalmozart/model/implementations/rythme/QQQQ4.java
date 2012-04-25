package org.chemicalmozart.model.implementations.rythme;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Note.Type;
import org.chemicalmozart.model.interfaces.Rythme;

public class QQQQ4  implements Rythme{

	public int getDuration() {
		return 4;
	}
	public List<Note> getListNotes() {
		Note n1 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		Note n2 = new Note(1, Type.BEFORE_STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		Note n3 = new Note(2, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		Note n4 = new Note(3, Type.BEFORE_STRONG, org.chemicalmozart.model.implementations.Rythme.quater);

		List<Note> l = new ArrayList<Note>();
		l.add(n1);
		l.add(n2);
		l.add(n3);
		l.add(n4);
		return l;
	}
}