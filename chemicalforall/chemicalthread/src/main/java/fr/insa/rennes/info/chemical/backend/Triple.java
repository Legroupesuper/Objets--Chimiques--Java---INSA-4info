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
package fr.insa.rennes.info.chemical.backend;


/**
 * This class represents a simple triplet of objects.
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * 
 * @param <A> Type of the first element of the triplet.
 * @param <B> Type of the second element of the triplet.
 * @param <C> Type of the third element of the triplet.
 */
class Triple<A, B, C> {
	/**
	 * First element of the triplet.
	 */
	private A _first;
	/**
	 * Second element of the triplet.
	 */
	private B _second;
	/**
	 * Third element of the triplet.
	 */
	private C _triple;
	
	/**
	 * Constructs a triplet of 3 elements.
	 * @param _first The first element.
	 * @param _second The second element.
	 * @param _triple The third element.
	 */
	public Triple(A _first, B _second, C _triple) {
		this._first = _first;
		this._second = _second;
		this._triple = _triple;
	}
	
	/**
	 * Getter for the first element of the triplet
	 * @return The first element of the triplet
	 */
	public A get_first() {
		return _first;
	}
	
	/**
	 * Setter for the first element of the triplet
	 * @param _first The new value of the first element of the triplet
	 */
	public void set_first(A _first) {
		this._first = _first;
	}
	
	/**
	 * Getter for the second element of the triplet
	 * @return The second element of the triplet
	 */
	public B get_second() {
		return _second;
	}
	
	/**
	 * Setter for the second element of the triplet
	 * @param _second The new value of the second element of the triplet
	 */
	public void set_second(B _second) {
		this._second = _second;
	}
	
	/**
	 * Getter for the third element of the triplet
	 * @return The third element of the triplet
	 */
	public C get_triple() {
		return _triple;
	}
	
	/**
	 * Setter for the third element of the triplet
	 * @param _triple The new value of the third element of the triplet.
	 */
	public void set_triple(C _triple) {
		this._triple = _triple;
	}
	
	
}