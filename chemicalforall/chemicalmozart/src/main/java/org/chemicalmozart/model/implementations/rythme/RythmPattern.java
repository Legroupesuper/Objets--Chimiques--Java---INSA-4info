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

import java.util.List;

import org.chemicalmozart.model.implementations.Note;
import org.chemicalmozart.model.implementations.Rythme;
/**
 * This interface is used to describe a Rythme class.
 * @author cedricandreolli
 *
 */
public class RythmPattern {
	private Rythme _duration;
	private List<Note> _listNotes;
	
	public RythmPattern(Rythme _duration, List<Note> _listNotes) {
		super();
		this._duration = _duration;
		this._listNotes = _listNotes;
	}
	/**
	 * 
	 * @return The duration of a rythme
	 */
	public Rythme getDuration(){
		return _duration;
	}
	/**
	 * It returns a list of notes
	 * @return a list of notes with a specific rythme
	 */
	public List<Note> getListNotes(){
		return _listNotes;
	}
}