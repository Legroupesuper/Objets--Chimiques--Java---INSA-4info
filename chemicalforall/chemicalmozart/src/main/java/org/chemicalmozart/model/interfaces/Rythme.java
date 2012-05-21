/* 
	Copyright (C) 2012 Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

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
package org.chemicalmozart.model.interfaces;

import java.util.List;

import org.chemicalmozart.model.implementations.Note;
/**
 * This interface is used to describe a Rythme class.
 * @author cedricandreolli
 *
 */
public interface Rythme {
	/**
	 * 
	 * @return The duration of a rythme
	 */
	int getDuration();
	/**
	 * It returns a list of notes
	 * @return a list of notes with a specific rythme
	 */
	List<Note> getListNotes();
}