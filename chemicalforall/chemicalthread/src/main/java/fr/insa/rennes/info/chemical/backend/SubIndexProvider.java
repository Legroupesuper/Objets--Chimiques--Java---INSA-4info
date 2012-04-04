package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

/**
 * This interface describes the behavior of a sub index provider, used by a {@link IndexProvider}. 
 * Exactly like an index provider, a sub index provider concerns a given solution (and
 * a given set of reactive). The only two implementations are {@link SubIndexProviderElement}
 * and {@link SubIndexProviderSolution}. These are the two possible types of sub index provider.<br />
 * This specification is useful for the recursive structure that is contained in a {@link SubIndexProviderSolution}.
 * Indeed, a {@link SubIndexProvider} can contain several {@link SubIndexProviderElement} or {@link SubIndexProviderSolution},
 * both implementing this interface. For more details see the {@link SubIndexProviderSolution} class.
 *  
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see IncrementStrategy
 * @see SubIndexProviderElement
 * @see SubIndexProviderSolution
 */
public interface SubIndexProvider {
	/**
	 * Initiates the sub index provider. Basically, it sets the index provider on its
	 * first index combination and, if some indexes are in conflict, increments the values 
	 * until a valid state (or overflow) is reached.
	 * @see #isValid()
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
	 * the number resulting of multiplication of the number of each reactive that could be matched. 
	 * Note that it is also the number of combination of the sub index provider.<br />
	 * The {@link BigInteger} is justified by the possibly great number of combination. For instance,
	 * a sub index provider for 6 reactives in a solution containing 70 instances of each of these 4 object,
	 * there is 70^6 combination (> 10^11).
	 * @return The number of elements on which the sub index provider is iterating.
	 */
	public BigInteger getNumberOfElements();
	
	/**
	 * Returns <code>true</code> if the current state of the sub index provider is valid.
	 * A valid state is a state where there is no conflict between two dependent indexes. Two indexes
	 * are said dependent when they have the same type and are pointing in the same solution.
	 * For example, two indexes pointing on the same integer in a given solution
	 * can not be valid.
	 * @return <code>true</code> if the current combination of the sub index provider is valid.
	 */
	public boolean isValid();
	
	/**
	 * Set the current value of the sub index provider.
	 * @param v The new value of the sub index provider.
	 */
	public void setValue(int v);
	
	/**
	 * Increments the sub index provider's value. This function is the most
	 * important one of the interface. It is supposed to go on to the next combination of
	 * reactives. At the end of this function, the sub index provider either is valid 
	 * (see {@link #isValid()}) or has reached an overflow (i.e all combination have been tested).
	 * @return <code>true</code> if an overflow was reached.
	 */
	public boolean increment();
}
