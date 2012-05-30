/* 
	Copyright (C) 2012 Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur

    This file is part of libchloe.

    libchloe is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    libchloe is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.
	
    You should have received a copy of the GNU Lesser General Public License
    along with libchloe.  If not, see <http://www.gnu.org/licenses/>
*/
package fr.insa.rennes.info.chemical.user;

import java.util.EventListener;
import java.util.EventObject;



/**
 * This class represents a special EventObject which will be fired during the detection of a solution inertia.
 * Only to be used with the InertEventListener.
 * @see EventListener 
 * @see EventObject
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 **/
public class InertEvent extends EventObject{
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the InertEvent. 
	 * @see EventObject
	 * @param source the object on which the event initially occured.
	 */
	public InertEvent(Object source) {
		super(source);
	}

}