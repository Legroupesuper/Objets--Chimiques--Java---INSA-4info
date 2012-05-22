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
package fr.insa.rennes.info.chemical.backend;

/**
 * This class represents a simple pair of objects.
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * 
 * @param <A> Type of the first element of the pair.
 * @param <B> Type of the second element of the pair.
 */
class Pair<A, B> {
	/**
	 * First element of the pair.
	 */
	private A _first;
	/**
	 * Second element of the pair.
	 */
	private B _second;
	
	/**
	 * Constructs a pair of 2 elements.
	 * @param _first The first element.
	 * @param _second The second element.
	 */
	public Pair(A _first, B _second) {
		super();
		this._first = _first;
		this._second = _second;
	}
	
	/**
	 * Getter for the first element of the pair
	 * @return The first element of the pair
	 */
	public A get_first() {
		return _first;
	}
	
	/**
	 * Setter for the first element of the pair
	 * @param _first The new value of the first element of the pair
	 */
	public void set_first(A _first) {
		this._first = _first;
	}
	
	/**
	 * Getter for the second element of the pair
	 * @return The second element of the pair
	 */
	public B get_second() {
		return _second;
	}
	
	/**
	 * Setter for the second element of the pair
	 * @param _second The new value of the second element of the pair
	 */
	public void set_second(B _second) {
		this._second = _second;
	}	
}
