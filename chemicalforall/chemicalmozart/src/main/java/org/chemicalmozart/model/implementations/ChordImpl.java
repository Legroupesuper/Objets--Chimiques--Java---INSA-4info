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
 * Description of the class ChordImpl.
 *
 * A ChordImpl is quite close to a DegreeImpl but it add the notion of rythme to the DregreeImpl.
 * A chord has a position in the bar.
 */
public class ChordImpl {
	/**
	 * The degree of the chord
	 */
	private DegreeImpl _degrees;
	/**
	 * The position of this chord in the bar. For exemple, the first generated chord in
	 * a bar will get the position 0, the second will have the position 1, etc
	 */
	private int _position;
	/**
	 * The duration of a chordimpl
	 */
	private Rythme duration;
	/**
	 * @return the _degrees
	 */
	public DegreeImpl get_degrees() {
		return this._degrees;
	}
	/**
	 * @return the _position
	 */
	public int get_position() {
		return this._position;
	}
	/**
	 * @return the duration
	 */
	public Rythme getDuration() {
		return this.duration;
	}
	/**
	 * @param _degrees the _degrees to set
	 */
	public void set_degrees(DegreeImpl _degrees) {
		this._degrees = _degrees;
	}
	/**
	 * @param _position the _position to set
	 */
	public void set_position(int _position) {
		this._position = _position;
	}
	/**
	 * @param duration the duration to set
	 */
	public void setDuration(Rythme duration) {
		this.duration = duration;
	}

}
