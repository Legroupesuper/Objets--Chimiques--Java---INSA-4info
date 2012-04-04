package org.chemicalmozart.model.implementations.rythme;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.interfaces.Rythme;

public class EQP2  implements Rythme{

	public List<Note> getListNotes() {
		Note n1 = new Note();
		n1.set_position(1);
		n1.set_rythme(org.chemicalmozart.model.implementations.Rythme.eighth);

		Note n2 = new Note();
		n2.set_position(2);
		n2.set_rythme(org.chemicalmozart.model.implementations.Rythme.quaterdotted);

		List<Note> l = new ArrayList<Note>();
		l.add(n1);
		l.add(n2);
		return l;
	}
}