/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of chemicalmozart.

    chemicalmozart is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    chemicalmozart is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with chemicalmozart.  If not, see <http://www.gnu.org/licenses/>
*/

package org.chemicalmozart.model.implementations.rythme;
import java.util.ArrayList;
import java.util.List;
import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.implementations.Note.Type;

public class SSSSEESSSSEE4{
	public Rythme getDuration() {
		return org.chemicalmozart.model.implementations.Rythme.whole;
	}

	public List<Note> getListNotes() {
		List<Note> l = new ArrayList<Note>();

		Note n0 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n0);
		Note n1 = new Note(1, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n1);
		Note n2 = new Note(2, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n2);
		Note n3 = new Note(3, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n3);
		Note n4 = new Note(4, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n4);
		Note n5 = new Note(5, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n5);
		Note n6 = new Note(6, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n6);
		Note n7 = new Note(7, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n7);
		Note n8 = new Note(8, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n8);
		Note n9 = new Note(9, Type.NONE, org.chemicalmozart.model.implementations.Rythme.sixteenth);
		l.add(n9);
		Note n10 = new Note(10, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n10);
		Note n11 = new Note(11, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		l.add(n11);
		return l;
	}
}