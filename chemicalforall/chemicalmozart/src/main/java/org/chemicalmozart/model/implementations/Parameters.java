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
 * Description of the class Parameters.
 *
 *
 */
public class Parameters {

	private int _speed;
	private int _scale;
	private int _measureNumber;
	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return _speed;
	}
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(int speed) {
		this._speed = speed;
	}
	/**
	 * @return the scale
	 */
	public int getScale() {
		return _scale;
	}
	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this._scale = scale;
	}
	/**
	 * @return the measureNumber
	 */
	public int getMeasureNumber() {
		return _measureNumber;
	}
	/**
	 * @param measureNumber the measureNumber to set
	 */
	public void setMeasureNumber(int measureNumber) {
		this._measureNumber = measureNumber;
	}
 
}