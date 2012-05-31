/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLoe.

    ChLoe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLoe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLoe.  If not, see <http://www.gnu.org/licenses/>
*/
package org.chemicalmozart.model.implementations;


/**
 * Description of the class Pitch.
 *
 *
 */
public class Pitch {

	private int _octave;
	private DegreeImpl _degree;

	/**
	 * Constructor using fields
	 * @param _octave
	 * @param _degree
	 */
	public Pitch(int _octave, DegreeImpl _degree) {
		super();
		this._octave = _octave;
		this._degree = _degree;
	}
	
	/**
	 * Default constructor
	 */
	public Pitch() {
		super();
		this._degree = null;
		this._octave = (Integer) null;
	}

	/**
	 * @return the degree
	 */
	public DegreeImpl getDegree() {
		return this._degree;
	}
	/**
	 * @return the octave
	 */
	public int getOctave() {
		return this._octave;
	}
	/**
	 * @param degree the degree to set
	 */
	public void setDegree(DegreeImpl degree) {
		this._degree = degree;
	}
	/**
	 * @param octave the octave to set
	 */
	public void setOctave(int octave) {
		this._octave = octave;
	}

	/** toString
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[_octave=" + _octave + ", _degree=" + _degree + "]";
	}
	
	

}