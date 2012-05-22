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
 * <p>
 * This interface describes an increment strategy of an index provider. It implements a strategy 
 * design pattern, where the <em>context</em> is the index provider, and the target
 * algorithm is the <code>increment</code> function. This class, invisible to the library user,
 * is represented by an enum type in the {@link Solution} class.<br />
 * This increment strategy defines the way to iterate on the reagents of a solution
 * when processing to the research of reagents for a reaction rule. Basically, the 
 * two different strategies are <em>ordered</em> (first elements first), and <em>random</em>.<br /> 
 * Other strategies could be implemented, as round robin. 
 * </p>
 * <p>
 * This interface is used by the class {@link IndexProvider}, that passes its sub index provider
 * to the function {@link #increment(SubIndexProviderSolution)}.
 * </p>
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see SubIndexProviderSolution
 */
interface IncrementStrategy {
	/**
	 * Increments the sub index provider's value by one. This function is supposed 
	 * to go on to the next combination of reagents of the specified {@link SubIndexProviderSolution} 
	 * object. 
	 * @param sipSol The sub index provider on a solution that has to be incremented.
	 * @throws ChemicalException
	 */
	public void increment(SubIndexProviderSolution sipSol) throws ChemicalException;
}