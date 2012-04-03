package org.chemicalmozart.model.implementations.rythme;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.interfaces.Rythme;

public class QEE2  implements Rythme{

	public List<Note> getListNotes() {
		Note n1 = new Note();
		n1.set_position(1);
		n1.set_rythme(org.chemicalmozart.model.implementations.Rythme.quater);

		Note n2 = new Note();
		n2.set_position(2);
		n2.set_rythme(org.chemicalmozart.model.implementations.Rythme.eighth);

		Note n3 = new Note();
		n3.set_position(3);
		n3.set_rythme(org.chemicalmozart.model.implementations.Rythme.eighth);

		List<Note> l = new ArrayList<Note>();
		l.add(n1);
		l.add(n2);
		l.add(n3);
		return l;
	}

}