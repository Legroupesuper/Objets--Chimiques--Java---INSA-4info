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
package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;


/**
 * This interface describes a special EventListener which can be used to detect the inertia of a solution.
 * If implemented and used, the <code>isInert</code> method will be called automatically when the solution reaches an inert state,
 * which means that every reaction is finished, or cannot react anymore. 
 * @see EventListener 
 * @see InertEvent
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 **/
public interface InertEventListener extends EventListener {
	/**
	 * Will be automatically called when a solution reaches the inert state, which means that
	 * every reaction is finished, or cannot react anymore. Implementation may contains
	 * specific instructions to execute when the reaction is over.
	 * @see InertEvent
	 * @param e the InertEvent fired by the inert solution.
	 */
	public void isInert(InertEvent e);
}