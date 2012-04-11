package fr.insa.rennes.info.chemical.backend;

import java.math.BigInteger;

/**
 * This class represents a sub index provider over a simple element. On contrary to a sub index provider 
 * over a solution ({@link SubIndexProviderSolution}), this index provider does not contain
 * any other sub index provider: the bottom of the recursion is reached with an object
 * of this class.<br />
 * As the iteration is done on a simple type of object in a given solution (like {@link String}, or {@link Double}), 
 * 1 index is sufficient to describe the internal state of this sub index provider. This index (a simple 
 * integer) gives the rank of the object that must be tested to react within a reaction rule.
 * This index refers to an object of a certain type in a given solution.
 * 
 * 
 * @author Andréolli Cédric, Boulanger Chloé, Cléro Olivier, Guellier Antoine, Guilloux Sébastien, Templé Arthur
 * @see IndexProvider
 * @see IncrementStrategy
 * @see SubIndexProvider
 * @see SubIndexProviderSolution
 */
class SubIndexProviderElement  implements SubIndexProvider{
	/**
	 * The number of elements of the object type searched in the concerned solution. The is actually
	 * the maximum value of {@link #_currentValue}.
	 */
	private int _numberOfElementsInSolution;
	/**
	 * The current value of this sub index provider. A simple integer is sufficient, as
	 * this index provider iterates on ONE reactive in ONE solution. This integer varies from 
	 * 0 to {@link #_numberOfElementsInSolution}-1 and is the index of the target reactive in 
	 * the target solution.
	 */
	private int _currentValue;
	
	/**
	 * Constructs a sub index provider on a simple reactive/element, with the specified maximum value
	 * for the index.<br />
	 * Note that the index value is not set in this constructor, {@link #init()} has to be called.
	 * @param _nummberOfElementsInSolution the maximum value for the index
	 */
	public SubIndexProviderElement(int _nummberOfElementsInSolution) {
		this._numberOfElementsInSolution = _nummberOfElementsInSolution;
	}
	
	
	/**
	 * Returns the number of elements/reactives of the given type in the concerned solution.
	 * @return The number of elements/reactives of the given type in the concerned solution.
	 */
	public int get_nummberOfElementsInSolution() {
		return _numberOfElementsInSolution;
	}
	

	
	/**
	 * Initializes the sub index provider: sets the current index value to 0.
	 */
	public void init() {
		_currentValue = 0;
	}
	
	/**
	 * Returns the current value of the index.
	 * @return The current value of the index.
	 */
	public int getValue(){
		return _currentValue;
	}
	
	/**
	 * Increments the current value of the index, and checks if the overflow was 
	 * reached or not.
	 * @return <code>true</code> if the overflow was reached.
	 */
	public boolean increment(){
		if(++_currentValue >= _numberOfElementsInSolution){
			_currentValue = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * Constructs and returns a string representing the sub index provider. The string follows the
	 * pattern: "| current_index_value / maximum_index_value"
	 * @return A string representing the sub index provider
	 */
	public String toString(){
		return "|"+_currentValue+"/"+_numberOfElementsInSolution;
	}
	
	/**
	 * Returns the number of elements/reactive of the given type in the concerned solution.
	 * A cast in BigInteger is done.
	 * @return The number of elements/reactive of the given type in the concerned solution.
	 */
	public BigInteger getNumberOfElements() {
		return BigInteger.valueOf(_numberOfElementsInSolution);
	}
	
	/**
	 * Returns <code>true</code> if the sub index provider is valid. In this
	 * case it can not be invalid, as there can't be any conflict on indexes: there is
	 * only one index.
	 * @return <code>true</code>
	 */
	public boolean isValid() {
		return true;
	}
}
