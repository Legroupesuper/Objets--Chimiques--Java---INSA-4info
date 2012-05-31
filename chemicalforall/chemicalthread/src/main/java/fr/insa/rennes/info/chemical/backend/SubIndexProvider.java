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

import java.math.BigInteger;

/**
 * This interface describes the behavior of a sub index provider, used by a {@link IndexProvider}. 
 * Exactly like an index provider, a sub index provider concerns a given solution (and
 * a given set of reagent). The only two implementations are {@link SubIndexProviderElement}
 * and {@link SubIndexProviderSolution}. These are the two possible types of sub index provider.<br />
 * This specification is useful for the recursive structure that is contained in a {@link SubIndexProviderSolution}.
 * Indeed, a {@link SubIndexProviderSolution} can contain several {@link SubIndexProviderElement} or other
 * {@link SubIndexProviderSolution}, both implementing this interface. For more details see 
 * the {@link SubIndexProviderSolution} class.
 *  
 * @author Andreolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see IncrementStrategy
 * @see SubIndexProviderElement
 * @see SubIndexProviderSolution
 */
public interface SubIndexProvider {
	/**
	 * Initiates the sub index provider. Basically, it sets the sub index provider on its
	 * first index combination.
	 */
	public void init();
	
	/**
	 * Returns the current value of the index provider. The signification 
	 * of this value differs depending on the implementation, but a sub index provider
	 * must always return a value.
	 * @return The current value of the the index provider.
	 * @see SubIndexProviderElement#getValue()
	 * @see SubIndexProviderSolution#getValue()
	 */
	public int getValue();
	
	/**
	 * Returns the number of elements on which the sub index provider is iterating.
	 * Depending on the implementation, the computing of this value differs, but the meaning is basically 
	 * the number resulting of the multiplication of the number of each reagent that could be matched.<br />
	 * The {@link BigInteger} is justified by the possibly great number of combinations. For instance,
	 * a sub index provider for 6 different types of reagents in a solution containing 70 instances of 
	 * each of these 6 object, there is 70^6 combination (> 10^11).
	 * @return The number of elements on which the sub index provider is iterating.
	 */
	public BigInteger getNumberOfElements();
	
	/**
	 * Returns the number of increments that this index provider must perform to try all combination. 
	 * This number may be the same value as the number of elements, but can differ in some cases.
	 * @return The number of increments that this index provider must perform to try all combination. 
	 */
	public BigInteger getNumberOfIncrements();
	
	/**
	 * Returns <code>true</code> if the current state of the sub index provider is valid.
	 * A valid state is a state where there is no conflict between two dependent indexes. Two indexes
	 * are said dependent when they are pointing on the same type of object/reagent, in the same solution.
	 * For example, an index provider having two indexes pointing on the same integer in a given solution
	 * can not be valid (this integer would be considered as two integers in the reaction !).
	 * @return <code>true</code> if the current combination of the sub index provider is valid.
	 */
	public boolean isValid();
	
	/**
	 * Increments the sub index provider's value. This function is the most
	 * important one of this interface. It is supposed to go on to the next combination of
	 * indexes of reagents. At the end of this function, the sub index provider can
	 * be in a non valid state (this verification is done <em>a posteriori</em>
	 * in {@link IndexProvider#increment()}).
	 * @return <code>true</code> if an overflow was reached.
	 */
	public boolean increment();
}