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
package fr.insa.rennes.info.chemical.backend;



/**
 * This class implements an ordered strategy iteration on reagents: 
 * the elements are selected in order of appearance in the the solution. 
 * Note that this order can be different than the insert order of the elements in the solution.
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 */
class OrderedIncrementStrategy implements IncrementStrategy {
	/**
	 * Increments the specified sub index provider of one step. Does not check
	 * the validity of the index provider, this is done in {@link IndexProvider#increment()}.
	 * @param sipSol The sub index provider on solution that has to be incremented
	 * @throws ChemicalException
	 */	
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException{
		boolean overflow = sipSol.increment();
		
		if(overflow)
			throw new ChemicalException("Overflow reached.");
	}

}