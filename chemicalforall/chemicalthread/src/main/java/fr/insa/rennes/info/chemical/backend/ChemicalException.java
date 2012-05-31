/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of ChLOE.

    ChLOE is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    ChLOE is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with ChLOE.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.backend;


/**
 * ChemicalException is an exception that indicates an error inherent to the chemical library.<br />
 * This exception can be thrown for an internal error, but the user will mostly receive exception concerning
 * a mishandling of the library. 
 * 
 * 
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
public class ChemicalException extends Exception {
	/**
	 * Perpetual version ID, avoid endless warnings...
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a ChemicalException with a detail message. 
	 * @param s The detail message
	 */
	public ChemicalException(String s) {
		super(s);
	}

}