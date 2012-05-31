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

package org.chemicalmozart.model.implementations;




/**
 * Description of the class DegreeImpl.
 * It represents the classical notation of a degree in classical music
 *
 */
public class DegreeImpl {
	/**
	 * The value of the degree (I-II-III-IV-V-VI-VII)
	 */
	private int _value;

	/**
	 * The default constructor of a DegreeImpl.<br /><br/>
	 * For example, if you want to create a I degree :<br />
	 * DegreeImpl deg = new DegreeImpl(1);
	 * @param _value This value represents the degree of the DegreeImpl
	 */
	public DegreeImpl(int _value) {
		super();
		this._value = _value;
	}

	/**
	 * @return the _value
	 */
	public int get_value() {
		return this._value;
	}

	/**
	 * @param _value the _value to set
	 */
	public void set_value(int _value) {
		this._value = _value;
	}

	@Override
	public String toString() {
		return "DegreeImpl [_value=" + _value + "]";
	}

}