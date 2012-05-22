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
package org.chemicalmozart.model.implementations.rythme;

import java.util.ArrayList;
import java.util.List;

import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Note.Type;
import org.chemicalmozart.model.implementations.Rythme;
import org.chemicalmozart.model.interfaces.RythmPattern;

public class EEQQQ4  implements RythmPattern{

	public Rythme getDuration() {
		return Rythme.whole;
	}
	public List<Note> getListNotes() {
		Note n1 = new Note(0, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.eighth);
		Note n2 = new Note(1, Type.NONE, org.chemicalmozart.model.implementations.Rythme.eighth);
		Note n3 = new Note(2, Type.BEFORE_STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		Note n4 = new Note(3, Type.STRONG, org.chemicalmozart.model.implementations.Rythme.quater);
		Note n5 = new Note(4, Type.BEFORE_STRONG, org.chemicalmozart.model.implementations.Rythme.quater);

		List<Note> l = new ArrayList<Note>();
		l.add(n1);
		l.add(n2);
		l.add(n3);
		l.add(n4);
		l.add(n5);
		return l;
	}
}