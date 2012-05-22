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
package org.chemicalmozart.model.implementations;




/**
 * Description of the class Note.
 *
 *
 */
public class Note {
	/**
	 * This enumeration defines the type of a note. A STRONG note is define as a note on the 1st or the 3rd quater.<br />
	 * A BEFORE_STRONG note is defined as the note that appears just before a strong note. All the other notes are defined as NONE.
	 */
	public enum Type{STRONG, BEFORE_STRONG, NONE};

	private Type _type;
	private int _octave;
	private Rythme _rythme;
	private int _position;
	private Pitch _pitch;
	private ChordImpl _chord;
	
	/**
	 * Constructors
	 */
	public Note() {
		this._type = Type.NONE;
	}
	public Note(int position, Type type, Rythme rythme){
		this._position = position;
		this._type = type;
		this._rythme = rythme;
	}

	public Note(Type type){
		this._type = type;
	}


	/**
	 * @return the _chord
	 */
	public ChordImpl get_chord() {
		return this._chord;
	}
	/**
	 * @return the octave
	 */
	public int get_octave() {
		return this._octave;
	}
	/**
	 * @return the pitch
	 */
	public Pitch get_pitch() {
		return this._pitch;
	}

	/**
	 * @return the position
	 */
	public int get_position() {
		return this._position;
	}
	/**
	 * @return the rythme
	 */
	public Rythme get_rythme() {
		return this._rythme;
	}
	/**
	 * @return the _type
	 */
	public Type get_type() {
		return this._type;
	}
	/**
	 * @param _chord the _chord to set
	 */
	public void set_chord(ChordImpl _chord) {
		this._chord = _chord;
	}
	/**
	 * @param octave the octave to set
	 */
	public void set_octave(int octave) {
		this._octave = octave;
	}
	/**
	 * @param pitch the pitch to set
	 */
	public void set_pitch(Pitch pitch) {
		this._pitch = pitch;
	}
	/**
	 * @param position the position to set
	 */
	public void set_position(int position) {
		this._position = position;
	}
	/**
	 * @param rythme the rythme to set
	 */
	public void set_rythme(Rythme rythme) {
		this._rythme = rythme;
	}
	/**
	 * @param _type the _type to set
	 */
	public void set_type(Type _type) {
		this._type = _type;
	}

}